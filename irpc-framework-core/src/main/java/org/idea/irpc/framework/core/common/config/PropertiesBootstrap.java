package org.idea.irpc.framework.core.common.config;

import java.io.IOException;

import static org.idea.irpc.framework.core.common.constants.RpcConstants.*;

/**
 * @Author linhao
 * @Date created in 10:46 上午 2021/12/12
 */
public class PropertiesBootstrap {

    private volatile boolean configIsReady;

    public static final String SERVER_PORT = "irpc.serverPort";
    public static final String REGISTER_ADDRESS = "irpc.registerAddr";
    public static final String REGISTER_TYPE = "irpc.registerType";
    public static final String APPLICATION_NAME = "irpc.applicationName";
    public static final String PROXY_TYPE = "irpc.proxyType";
    public static final String ROUTER_TYPE = "irpc.router";
    public static final String SERVER_SERIALIZE_TYPE = "irpc.serverSerialize";
    public static final String CLIENT_SERIALIZE_TYPE = "irpc.clientSerialize";

    public static ServerConfig loadServerConfigFromLocal() {
        try {
            PropertiesLoader.loadConfiguration();
        } catch (IOException e) {
            throw new RuntimeException("loadServerConfigFromLocal fail,e is {}", e);
        }
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setServerPort(PropertiesLoader.getPropertiesInteger(SERVER_PORT));
        serverConfig.setApplicationName(PropertiesLoader.getPropertiesStr(APPLICATION_NAME));
        serverConfig.setRegisterAddr(PropertiesLoader.getPropertiesStr(REGISTER_ADDRESS));
        serverConfig.setRegisterType(PropertiesLoader.getPropertiesStr(REGISTER_TYPE));
        serverConfig.setServerSerialize(PropertiesLoader.getPropertiesStrDefault(SERVER_SERIALIZE_TYPE,JDK_SERIALIZE_TYPE));
        return serverConfig;
    }

    public static ClientConfig loadClientConfigFromLocal(){
        try {
            PropertiesLoader.loadConfiguration();
        } catch (IOException e) {
            throw new RuntimeException("loadClientConfigFromLocal fail,e is {}", e);
        }
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setApplicationName(PropertiesLoader.getPropertiesNotBlank(APPLICATION_NAME));
        clientConfig.setRegisterAddr(PropertiesLoader.getPropertiesNotBlank(REGISTER_ADDRESS));
        clientConfig.setRegisterType(PropertiesLoader.getPropertiesNotBlank(REGISTER_TYPE));
        clientConfig.setProxyType(PropertiesLoader.getPropertiesStrDefault(PROXY_TYPE,JDK_PROXY_TYPE));
        clientConfig.setRouterStrategy(PropertiesLoader.getPropertiesStrDefault(ROUTER_TYPE,RANDOM_ROUTER_TYPE));
        clientConfig.setClientSerialize(PropertiesLoader.getPropertiesStrDefault(CLIENT_SERIALIZE_TYPE,JDK_SERIALIZE_TYPE));
        return clientConfig;
    }


}
