package org.idea.irpc.framework.core.router;

import org.idea.irpc.framework.core.common.ChannelFutureRefWrapper;
import org.idea.irpc.framework.core.common.ChannelFutureWrapper;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.idea.irpc.framework.core.common.cache.CommonClientCache.CONNECT_MAP;

/**
 * 随机筛选
 *
 * @Author linhao
 * @Date created in 8:26 下午 2022/1/5
 */
public class RandomRouterImpl implements IRouter {

    private volatile boolean refreshFlag = true;

    private ChannelFutureRefWrapper channelFutureRefWrapper;

    @Override
    public void refreshRouterFlag(boolean status) {
        this.refreshFlag = status;
    }

    @Override
    public ChannelFutureWrapper select(Selector selector) {
        if (refreshFlag) {
            List<ChannelFutureWrapper> channelFutureWrappers = CONNECT_MAP.get(selector.getProviderServiceName());
            ChannelFutureWrapper[] arr = new ChannelFutureWrapper[channelFutureWrappers.size()];
            int[] result = createRandomIndex(arr.length);
            for (int i = 0; i < result.length; i++) {
                arr[i] = channelFutureWrappers.get(result[i]);
            }
            channelFutureRefWrapper = new ChannelFutureRefWrapper(arr);
            this.refreshFlag = false;
        }
        return channelFutureRefWrapper.getChannelFutureWrapper();
    }

    private int[] createRandomIndex(int len) {
        int[] arrInt = new int[len];
        Random ra = new Random();
        for (int i = 0; i < arrInt.length; i++) {
            arrInt[i] = -1;
        }
        int index = 0;
        while (index < arrInt.length) {
            int num = ra.nextInt(len);
            //如果数组中不包含这个元素则赋值给数组
            if (!contains(arrInt, num)) {
                arrInt[index++] = num;
            }
        }
        return arrInt;
    }

    public boolean contains(int[] arr, int key) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == key) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
//        int[] result = createRandomIndex(10);
//        for (int i : result) {
//            System.out.println(i);
//        }
    }

}
