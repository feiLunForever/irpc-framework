package org.idea.irpc.framework.core.common.config;

/**
 * @Author linhao
 * @Date created in 10:40 上午 2021/12/11
 */
public class ServerConfig {

    private Integer serverPort;

    private String registerAddr;

    private String applicationName;

    public String getRegisterAddr() {
        return registerAddr;
    }

    public void setRegisterAddr(String registerAddr) {
        this.registerAddr = registerAddr;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
