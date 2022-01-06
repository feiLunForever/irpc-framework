package org.idea.irpc.framework.core.router;

import org.idea.irpc.framework.core.common.ChannelFutureWrapper;

/**
 * @Author linhao
 * @Date created in 8:08 下午 2022/1/5
 */
public interface IRouter {


    /**
     * 刷新一个随机选定的flag
     *
     * @param status
     */
    void refreshRouterFlag(boolean status);

    /**
     * 获取到请求到连接通道
     *
     * @return
     */
    ChannelFutureWrapper select(Selector selector);
}
