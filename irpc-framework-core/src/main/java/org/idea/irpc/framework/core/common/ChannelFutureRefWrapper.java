package org.idea.irpc.framework.core.common;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author linhao
 * @Date created in 8:46 下午 2022/1/5
 */
public class ChannelFutureRefWrapper {

    private AtomicLong referenceTimes = new AtomicLong(0);

    private ChannelFutureWrapper[] channelFutureWrappers;

    public ChannelFutureRefWrapper(ChannelFutureWrapper[] channelFutureWrappers) {
        this.channelFutureWrappers = channelFutureWrappers;
    }

    public ChannelFutureWrapper getChannelFutureWrapper(){
        long i = referenceTimes.getAndIncrement();
        int index = (int) (i % channelFutureWrappers.length);
        return channelFutureWrappers[index];
    }

    public static void main(String[] args) {
        ChannelFutureWrapper[] arr = new ChannelFutureWrapper[]{
                new ChannelFutureWrapper("10.2.3.4",9090),
                new ChannelFutureWrapper("10.2.3.5",9091),
                new ChannelFutureWrapper("10.2.3.6",9092)
        };
        ChannelFutureRefWrapper channelFutureRefWrapper = new ChannelFutureRefWrapper(arr);
        for(int i=0;i<10000;i++){
            ChannelFutureWrapper item = channelFutureRefWrapper.getChannelFutureWrapper();
            System.out.println(item);
        }
    }
}
