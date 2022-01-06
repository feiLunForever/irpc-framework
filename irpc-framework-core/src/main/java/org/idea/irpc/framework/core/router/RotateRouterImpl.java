package org.idea.irpc.framework.core.router;

import org.idea.irpc.framework.core.common.ChannelFutureRefWrapper;
import org.idea.irpc.framework.core.common.ChannelFutureWrapper;

import java.util.List;

import static org.idea.irpc.framework.core.common.cache.CommonClientCache.CONNECT_MAP;

/**
 * 轮训策略
 *
 * @Author linhao
 * @Date created in 8:30 下午 2022/1/5
 */
public class RotateRouterImpl implements IRouter{

    private volatile boolean refreshFlag = true;

    private ChannelFutureRefWrapper channelFutureRefWrapper;

    @Override
    public void refreshRouterFlag(boolean status) {
        this.refreshFlag=status;
    }

    @Override
    public ChannelFutureWrapper select(Selector selector) {
        if(refreshFlag){
            List<ChannelFutureWrapper> channelFutureWrappers = CONNECT_MAP.get(selector.getProviderServiceName());
            ChannelFutureWrapper[] arr = new ChannelFutureWrapper[channelFutureWrappers.size()];
            for (int i=0;i<channelFutureWrappers.size();i++) {
                arr[i]=channelFutureWrappers.get(i);
            }
            channelFutureRefWrapper = new ChannelFutureRefWrapper(arr);
            this.refreshFlag=false;
        }
        return channelFutureRefWrapper.getChannelFutureWrapper();
    }
}
