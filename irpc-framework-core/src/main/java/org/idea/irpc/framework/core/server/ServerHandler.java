package org.idea.irpc.framework.core.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.idea.irpc.framework.core.common.RpcInvocation;
import org.idea.irpc.framework.core.common.RpcProtocol;
import org.idea.irpc.framework.core.common.exception.IRpcException;
import org.idea.irpc.framework.core.common.exception.MaxConnectionException;


import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static org.idea.irpc.framework.core.common.cache.CommonServerCache.*;

/**
 * 非共享模式，不存在线程安全问题
 *
 * @Author linhao
 * @Date created in 9:02 下午 2021/11/28
 */
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private Semaphore semaphore;

//    public ServerHandler(int maxConnection) {
//        this.semaphore = new Semaphore(maxConnection);
//    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    /**
     * 考虑到可以动态调整最大连接数
     *
     * @param semaphore
     */
    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ServerChannelReadData serverChannelReadData = new ServerChannelReadData();
        serverChannelReadData.setRpcProtocol((RpcProtocol) msg);
        serverChannelReadData.setChannelHandlerContext(ctx);
        SERVER_CHANNEL_DISPATCHER.add(serverChannelReadData);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //这里统一做异常捕获？
        cause.printStackTrace();
        if (cause instanceof MaxConnectionException) {
            MaxConnectionException maxConnectionException = (MaxConnectionException) cause;
            RpcProtocol rpcProtocol = maxConnectionException.getRpcProtocol();
            byte[] content = rpcProtocol.getContent();
            RpcInvocation response = SERVER_SERIALIZE_FACTORY.deserialize(content,RpcInvocation.class);
            response.setResponse("t");
            response.setTargetMethod("");
            response.setTargetServiceName("");
            response.setArgs(null);
            rpcProtocol.setContent(SERVER_SERIALIZE_FACTORY.serialize(response));
            ctx.writeAndFlush(rpcProtocol);
        }
//        Channel channel = ctx.channel();
//        if (channel.isActive()) {
//            ctx.close();
//        }
    }
}
