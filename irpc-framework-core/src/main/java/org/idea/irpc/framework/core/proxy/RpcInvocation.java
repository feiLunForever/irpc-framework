package org.idea.irpc.framework.core.proxy;

/**
 * @Author linhao
 * @Date created in 10:19 上午 2021/11/30
 */
public interface RpcInvocation {

    /**
     * 发送数据
     *
     * @param body
     */
    void sendData(String body);
}
