package org.idea.irpc.framework.core.common.config;

/**
 * @Author linhao
 * @Date created in 10:41 上午 2021/12/11
 */
public class ClientConfig {

    private Integer port;

    private String serverAddr;

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
