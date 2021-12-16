package org.idea.irpc.framework.core.common.event;

/**
 * @Author linhao
 * @Date created in 10:25 上午 2021/12/16
 */
public class IRpcRemoveEvent implements IRpcEvent {

    private Object data;

    public IRpcRemoveEvent(Object data) {
        this.data = data;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public IRpcEvent setData(Object data) {
        this.data = data;
        return this;
    }
}
