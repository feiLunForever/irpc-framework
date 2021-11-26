package org.idea.irpc.framework.core.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.io.UnsupportedEncodingException;

/**
 * @Author linhao
 * @Date created in 9:08 上午 2021/11/24
 */
public class ClientApplication {

    private static EventLoopGroup clientLoopGroup;
    private static Bootstrap bootstrap;

    private void init() {
        clientLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(clientLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
    }



    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
//        ClientApplication clientApplication = new ClientApplication();
//        clientApplication.init();
//        ChannelFuture channelFuture = clientApplication.doRequest("localhost", 9090);
//        while (true) {
//            Thread.sleep(3000);
//            channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("client send data".getBytes("UTF-8")));
////                    .addListener(ChannelFutureListener.CLOSE);
//        }
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new StringEncoder());
                    }
                });
        Channel channel = bootstrap.connect("localhost",9090).channel();
        while (true){
            System.out.println("发送数据");
            channel.writeAndFlush(123);
        }
    }

    private ChannelFuture doRequest(String host, int port) throws InterruptedException {
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ClientHandler());
            }
        });
        return bootstrap.connect(host, port).sync();
    }
}
