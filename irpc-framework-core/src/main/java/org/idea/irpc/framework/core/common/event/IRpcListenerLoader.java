package org.idea.irpc.framework.core.common.event;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author linhao
 * @Date created in 11:50 下午 2021/12/13
 */
public class IRpcListenerLoader {

    private static List<IRpcListener> iRpcListenerList = new ArrayList<>();

    public void registerListener(IRpcListener iRpcListener){
        iRpcListenerList.add(iRpcListener);
    }

    public void sendEvent(Object data){

    }
}
