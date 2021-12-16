package org.idea.irpc.framework.core.common.event;

import org.idea.irpc.framework.core.common.event.listener.ServiceRegistryListener;
import org.idea.irpc.framework.core.common.event.listener.ServiceRemoveListener;
import org.idea.irpc.framework.core.common.utils.CommonUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author linhao
 * @Date created in 11:50 下午 2021/12/13
 */
public class IRpcListenerLoader {

    private static List<IRpcListener> iRpcListenerList = new ArrayList<>();

    public static void registerListener(IRpcListener iRpcListener) {
        iRpcListenerList.add(iRpcListener);
    }

    public void init() {
        registerListener(new ServiceRemoveListener());
        registerListener(new ServiceRegistryListener());
    }

    /**
     * 获取接口上的泛型T
     *
     * @param o     接口
     */
    public static Class<?> getInterfaceT(Object o) {
        Type[] types = o.getClass().getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) types[0];
        Type type = parameterizedType.getActualTypeArguments()[0];
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        }
        return null;
    }

    public static void sendEvent(IRpcEvent iRpcEvent) {
        if(CommonUtils.isEmptyList(iRpcListenerList)){
            return;
        }
        for (IRpcListener<?> iRpcListener : iRpcListenerList) {
            Class<?> type = getInterfaceT(iRpcListener);
            if(type.equals(iRpcEvent.getClass())){
                iRpcListener.callBack(iRpcEvent.getData());
            }
        }
    }


    public static void main(String[] args) {
        IRpcListenerLoader iRpcListenerLoader = new IRpcListenerLoader();
        iRpcListenerLoader.init();
        IRpcListenerLoader.sendEvent(new IRpcRegistryEvent("test"));
    }
}
