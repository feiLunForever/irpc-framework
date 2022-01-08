package org.idea.irpc.framework.core.common;

import java.util.concurrent.atomic.AtomicLong;

import static org.idea.irpc.framework.core.common.cache.CommonClientCache.SERVICE_ROUTER_MAP;

/**
 * @Author linhao
 * @Date created in 8:46 下午 2022/1/5
 */
public class ChannelFutureRefWrapper {

    private AtomicLong referenceTimes = new AtomicLong(0);


    public ChannelFutureWrapper getChannelFutureWrapper(String serviceName){
        ChannelFutureWrapper[] arr = SERVICE_ROUTER_MAP.get(serviceName);
        long i = referenceTimes.getAndIncrement();
        int index = (int) (i % arr.length);
        return arr[index];
    }

    public static void main(String[] args) {
        ChannelFutureWrapper[] arr = new ChannelFutureWrapper[]{
                new ChannelFutureWrapper("10.2.3.4",9090,100),
                new ChannelFutureWrapper("10.2.3.5",9091,100),
                new ChannelFutureWrapper("10.2.3.6",9092,100)
        };
        ChannelFutureRefWrapper channelFutureRefWrapper = new ChannelFutureRefWrapper();
        for(int i=0;i<10000;i++){
//            ChannelFutureWrapper item = channelFutureRefWrapper.getChannelFutureWrapper();
//            System.out.println(item);
        }
    }
}
