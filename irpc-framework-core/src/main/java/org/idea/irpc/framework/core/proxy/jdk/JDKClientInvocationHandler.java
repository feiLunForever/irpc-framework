package org.idea.irpc.framework.core.proxy.jdk;

import org.idea.irpc.framework.core.client.RpcReferenceWrapper;
import org.idea.irpc.framework.core.common.RpcInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static org.idea.irpc.framework.core.common.cache.CommonClientCache.RESP_MAP;
import static org.idea.irpc.framework.core.common.cache.CommonClientCache.SEND_QUEUE;

/**
 * 各种代理工厂统一使用这个InvocationHandler
 *
 * @Author linhao
 * @Date created in 6:59 下午 2021/12/5
 */
public class JDKClientInvocationHandler implements InvocationHandler {

    private final static Object OBJECT = new Object();

    private RpcReferenceWrapper rpcReferenceWrapper;

    public JDKClientInvocationHandler(RpcReferenceWrapper rpcReferenceWrapper) {
        this.rpcReferenceWrapper = rpcReferenceWrapper;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcInvocation rpcInvocation = new RpcInvocation();
        rpcInvocation.setArgs(args);
        rpcInvocation.setTargetMethod(method.getName());
        rpcInvocation.setTargetServiceName(rpcReferenceWrapper.getAimClass().getName());
        rpcInvocation.setUuid(UUID.randomUUID().toString());
        rpcInvocation.setAttachments(rpcReferenceWrapper.getAttatchments());
        RESP_MAP.put(rpcInvocation.getUuid(), OBJECT);
        SEND_QUEUE.add(rpcInvocation);
        long beginTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - beginTime < 3*1000) {
            Object object = RESP_MAP.get(rpcInvocation.getUuid());
            if (object instanceof RpcInvocation) {
                return ((RpcInvocation)object).getResponse();
            }
        }
        throw new TimeoutException("client wait server's response timeout!");
    }
}
