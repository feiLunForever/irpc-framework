package org.idea.irpc.framework.core.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

/**
 * @Author linhao
 * @Date created in 8:21 下午 2021/11/24
 */
public class ClientHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        String response = byteBuf.toString();
        System.out.printf("[client] 接收到信息: %s \n",response);
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if(channel.isActive()){
            ctx.close();
        }
    }
}
