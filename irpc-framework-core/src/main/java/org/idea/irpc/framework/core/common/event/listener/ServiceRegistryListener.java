package org.idea.irpc.framework.core.common.event.listener;

import org.idea.irpc.framework.core.common.event.IRpcListener;
import org.idea.irpc.framework.core.common.event.IRpcRegistryEvent;
import org.idea.irpc.framework.core.registy.zookeeper.ProviderNodeInfo;

/**
 * 服务注册监听器
 *
 * @Author linhao
 * @Date created in 10:15 上午 2021/12/16
 */
public class ServiceRegistryListener implements IRpcListener<IRpcRegistryEvent> {

    @Override
    public void callBack(Object data) {
        ProviderNodeInfo providerNodeInfo = (ProviderNodeInfo) data;
        System.out.println("ServiceRegistryListener");
    }

}
