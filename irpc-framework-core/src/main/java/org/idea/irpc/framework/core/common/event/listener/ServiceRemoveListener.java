package org.idea.irpc.framework.core.common.event.listener;

import org.idea.irpc.framework.core.client.ConnectionHandler;
import org.idea.irpc.framework.core.common.event.IRpcListener;
import org.idea.irpc.framework.core.common.event.IRpcRemoveEvent;
import org.idea.irpc.framework.core.registy.zookeeper.ProviderNodeInfo;

/**
 * 服务移除监听器
 *
 * @Author linhao
 * @Date created in 10:09 上午 2021/12/16
 */
public class ServiceRemoveListener implements IRpcListener<IRpcRemoveEvent> {

    @Override
    public void callBack(Object data){
        ProviderNodeInfo providerNodeInfo = (ProviderNodeInfo) data;
        ConnectionHandler.disConnect(providerNodeInfo.getServiceName(),providerNodeInfo.getAddress());
    }
}
