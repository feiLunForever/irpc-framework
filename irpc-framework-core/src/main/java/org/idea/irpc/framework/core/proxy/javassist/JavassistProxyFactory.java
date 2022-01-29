package org.idea.irpc.framework.core.proxy.javassist;

import org.idea.irpc.framework.core.client.RpcReferenceWrapper;
import org.idea.irpc.framework.core.proxy.ProxyFactory;


/**
 * @Author linhao
 * @Date created in 5:32 下午 2021/12/4
 */
public class JavassistProxyFactory implements ProxyFactory {

    @Override
    public <T> T getProxy(RpcReferenceWrapper rpcReferenceWrapper) throws Throwable {
        return (T) ProxyGenerator.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                rpcReferenceWrapper.getAimClass(), new JavassistInvocationHandler(rpcReferenceWrapper));
    }
}
