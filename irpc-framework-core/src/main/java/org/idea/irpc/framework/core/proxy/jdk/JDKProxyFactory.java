package org.idea.irpc.framework.core.proxy.jdk;


import org.idea.irpc.framework.core.proxy.ProxyFactory;

import java.lang.reflect.Proxy;

/**
 * @Author linhao
 * @Date created in 8:56 上午 2021/11/30
 */
public class JDKProxyFactory implements ProxyFactory {

    @Override
    public <T> T getProxy(final Class clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                new JDKClientInvocationHandler(clazz));
    }

}
