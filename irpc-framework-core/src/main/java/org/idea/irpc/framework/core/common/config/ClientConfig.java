package org.idea.irpc.framework.core.common.config;

/**
 * @Author linhao
 * @Date created in 10:41 上午 2021/12/11
 */
public class ClientConfig {

    private String applicationName;

    private String registerAddr;

    private String proxyType;

    public String getProxyType() {
        return proxyType;
    }

    public void setProxyType(String proxyType) {
        this.proxyType = proxyType;
    }

    public String getRegisterAddr() {
        return registerAddr;
    }

    public void setRegisterAddr(String registerAddr) {
        this.registerAddr = registerAddr;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
