package org.idea.irpc.framework.core.proxy;

import java.lang.reflect.InvocationHandler;

public interface ProxyFactory {

    <T> T getProxy(Object target, InvocationHandler handler) throws Throwable;
}