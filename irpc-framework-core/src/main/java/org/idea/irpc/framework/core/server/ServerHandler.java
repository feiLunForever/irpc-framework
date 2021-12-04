package org.idea.irpc.framework.core.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.idea.irpc.framework.core.common.RpcProtocol;

/**
 * @Author linhao
 * @Date created in 9:02 下午 2021/11/28
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        RpcProtocol rpcProtocol = (RpcProtocol) msg;
        System.out.println("[server] 接收到数据请求" + rpcProtocol.toString());
        String responseMsg = "msg from server";
        RpcProtocol respRpcProtocol = new RpcProtocol(responseMsg.getBytes().length,responseMsg.getBytes());
        ctx.writeAndFlush(respRpcProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }
}
