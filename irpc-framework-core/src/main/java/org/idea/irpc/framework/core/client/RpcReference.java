package org.idea.irpc.framework.core.client;

import org.idea.irpc.framework.core.proxy.ProxyFactory;

/**
 * @Author linhao
 * @Date created in 10:49 上午 2021/12/11
 */
public class RpcReference {

    public ProxyFactory proxyFactory;

    public RpcReference(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    /**
     * 根据接口类型获取代理对象
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T get(Class<T> tClass) throws Throwable {
        return proxyFactory.getProxy(tClass);
    }
}
