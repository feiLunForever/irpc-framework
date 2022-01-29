package org.idea.irpc.framework.core.client;

/**
 * rpc远程调用包装类
 *
 * @Author linhao
 * @Date created in 11:28 上午 2022/1/29
 */
public class RpcReferenceWrapper<T> {

    private Class<T> aimClass;

    private String group;

    public Class<T> getAimClass() {
        return aimClass;
    }

    public void setAimClass(Class<T> aimClass) {
        this.aimClass = aimClass;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
