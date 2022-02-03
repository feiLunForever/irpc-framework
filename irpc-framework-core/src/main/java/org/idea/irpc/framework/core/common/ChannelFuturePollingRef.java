package org.idea.irpc.framework.core.common;

import java.util.concurrent.atomic.AtomicLong;


/**
 * @Author linhao
 * @Date created in 8:46 下午 2022/1/5
 */
public class ChannelFuturePollingRef {

    private AtomicLong referenceTimes = new AtomicLong(0);


    public ChannelFutureWrapper getChannelFutureWrapper(ChannelFutureWrapper[] arr){
//        ChannelFutureWrapper[] arr = SERVICE_ROUTER_MAP.get(serviceName);
        long i = referenceTimes.getAndIncrement();
        int index = (int) (i % arr.length);
        return arr[index];
    }

}
