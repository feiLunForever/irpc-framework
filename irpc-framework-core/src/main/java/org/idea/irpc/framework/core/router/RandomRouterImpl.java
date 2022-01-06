package org.idea.irpc.framework.core.router;

import org.idea.irpc.framework.core.common.ChannelFutureWrapper;

import java.util.List;
import java.util.Random;

import static org.idea.irpc.framework.core.common.cache.CommonClientCache.CONNECT_MAP;

/**
 * 随机筛选
 *
 * @Author linhao
 * @Date created in 8:26 下午 2022/1/5
 */
public class RandomRouterImpl  implements IRouter{


    @Override
    public void refreshRouterFlag(boolean status) {

    }

    @Override
    public ChannelFutureWrapper select(Selector selector) {
        List<ChannelFutureWrapper> channelFutureWrappers = CONNECT_MAP.get(selector.getProviderServiceName());
        return channelFutureWrappers.get(new Random().nextInt(channelFutureWrappers.size()));
    }

}
