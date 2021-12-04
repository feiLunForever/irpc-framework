package org.idea.irpc.framework.core.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.idea.irpc.framework.core.common.RpcDecoder;
import org.idea.irpc.framework.core.common.RpcEncoder;
import org.idea.irpc.framework.core.common.RpcProtocol;
import org.idea.irpc.framework.core.proxy.jdk.JDKProxyFactory;
import org.idea.irpc.framework.core.proxy.RpcInvocation;

import static org.idea.irpc.framework.core.common.cache.CommonClientCache.SEND_QUEUE;

/**
 * @Author linhao
 * @Date created in 8:22 上午 2021/11/29
 */
public class Client {

    public static void main(String[] args) throws InterruptedException{
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new RpcEncoder());
                ch.pipeline().addLast(new RpcDecoder());
                ch.pipeline().addLast(new ClientHandler());
            }
        });
        ChannelFuture channelFuture = bootstrap.connect("localhost",9090).sync();
        System.out.println("============ 服务启动 ============");
        Client client = new Client();
        client.startClient(channelFuture);
        client.startSend();
        Thread.yield();
    }


    public void startSend(){
        Thread sendDataJob = new Thread(new SendDataJob());
        sendDataJob.start();
    }

    public void startClient(ChannelFuture channelFuture){
        Thread asyncSendJob = new Thread(new AsyncSendJob(channelFuture));
        asyncSendJob.start();
    }

    class SendDataJob implements Runnable{

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                    RpcInvocation rpcInvocation = JDKProxyFactory.getProxy(RpcInvocation.class);
                    rpcInvocation.sendData("send data");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class AsyncSendJob implements Runnable{

        private ChannelFuture channelFuture;

        public AsyncSendJob(ChannelFuture channelFuture) {
            this.channelFuture = channelFuture;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String data = SEND_QUEUE.take();
                    RpcProtocol rpcProtocol = new RpcProtocol(data.getBytes().length,data.getBytes());
                    channelFuture.channel().writeAndFlush(rpcProtocol);
                    System.out.println("发送数据"+rpcProtocol);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
