package org.idea.irpc.framework.core.proxy;


import org.idea.irpc.framework.core.client.RpcReferenceWrapper;

public interface ProxyFactory {

    <T> T getProxy(RpcReferenceWrapper rpcReferenceWrapper) throws Throwable;
}