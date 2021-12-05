package org.idea.irpc.framework.core.proxy.javassist;

import org.idea.irpc.framework.core.proxy.ProxyFactory;
import org.idea.irpc.framework.core.proxy.jdk.JDKClientInvocationHandler;


/**
 * @Author linhao
 * @Date created in 5:32 下午 2021/12/4
 */
public class JavassistProxyFactory implements ProxyFactory {

    @Override
    public <T> T getProxy(Class clazz) throws Throwable {
        return (T) ProxyGenerator.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                clazz, new JDKClientInvocationHandler(clazz));
    }
}
