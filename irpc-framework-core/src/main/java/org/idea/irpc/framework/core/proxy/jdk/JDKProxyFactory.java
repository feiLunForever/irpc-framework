package org.idea.irpc.framework.core.proxy.jdk;



import com.alibaba.fastjson.JSON;
import org.idea.irpc.framework.core.common.RpcData;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

import static org.idea.irpc.framework.core.common.cache.CommonClientCache.RESP_MAP;
import static org.idea.irpc.framework.core.common.cache.CommonClientCache.SEND_QUEUE;

/**
 * @Author linhao
 * @Date created in 8:56 上午 2021/11/30
 */
public class JDKProxyFactory {

    public static <T> T getProxy(final Class clazz){

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String uuid = UUID.randomUUID().toString();
                RESP_MAP.put(uuid,new RpcData());
                RpcData rpcData = new RpcData();
                rpcData.setData(String.valueOf(args[0]));
                rpcData.setUuid(uuid);
                String json = JSON.toJSONString(rpcData);
                SEND_QUEUE.add(json);
                return true;
            }
        });
    }

}
