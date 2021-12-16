package org.idea.irpc.framework.core.common.event;

/**
 * @Author linhao
 * @Date created in 10:23 上午 2021/12/16
 */
public class IRpcRegistryEvent implements IRpcEvent {

    private Object data;

    public IRpcRegistryEvent(Object data) {
        this.data = data;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public IRpcRegistryEvent setData(Object data) {
        this.data = data;
        return this;
    }
}
