package org.idea.irpc.framework.core.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.idea.irpc.framework.core.common.RpcDecoder;
import org.idea.irpc.framework.core.common.RpcEncoder;
import org.idea.irpc.framework.core.common.config.PropertiesBootstrap;
import org.idea.irpc.framework.core.common.config.ServerConfig;
import org.idea.irpc.framework.core.common.event.IRpcListenerLoader;
import org.idea.irpc.framework.core.common.utils.CommonUtils;
import org.idea.irpc.framework.core.filter.IClientFilter;
import org.idea.irpc.framework.core.filter.IServerFilter;
import org.idea.irpc.framework.core.filter.server.ServerFilterChain;
import org.idea.irpc.framework.core.filter.server.ServerLogFilterImpl;
import org.idea.irpc.framework.core.filter.server.ServerTokenFilterImpl;
import org.idea.irpc.framework.core.registy.URL;
import org.idea.irpc.framework.core.registy.zookeeper.ZookeeperRegister;
import org.idea.irpc.framework.core.serialize.SerializeFactory;
import org.idea.irpc.framework.core.serialize.fastjson.FastJsonSerializeFactory;
import org.idea.irpc.framework.core.serialize.hessian.HessianSerializeFactory;
import org.idea.irpc.framework.core.serialize.jdk.JdkSerializeFactory;
import org.idea.irpc.framework.core.serialize.kryo.KryoSerializeFactory;


import java.io.IOException;
import java.util.LinkedHashMap;

import static org.idea.irpc.framework.core.common.cache.CommonClientCache.EXTENSION_LOADER;
import static org.idea.irpc.framework.core.common.cache.CommonServerCache.*;
import static org.idea.irpc.framework.core.common.constants.RpcConstants.*;
import static org.idea.irpc.framework.core.common.constants.RpcConstants.KRYO_SERIALIZE_TYPE;
import static org.idea.irpc.framework.core.spi.ExtensionLoader.EXTENSION_LOADER_CACHE;

/**
 * @Author linhao
 * @Date created in 8:12 上午 2021/11/29
 */
public class Server {

    private static EventLoopGroup bossGroup = null;

    private static EventLoopGroup workerGroup = null;

    private ServerConfig serverConfig;

    private static IRpcListenerLoader iRpcListenerLoader;


    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public void startApplication() throws InterruptedException {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.option(ChannelOption.SO_SNDBUF, 16 * 1024)
                .option(ChannelOption.SO_RCVBUF, 16 * 1024)
                .option(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                System.out.println("初始化provider过程");
                ch.pipeline().addLast(new RpcEncoder());
                ch.pipeline().addLast(new RpcDecoder());
                ch.pipeline().addLast(new ServerHandler());
            }
        });
        this.batchExportUrl();
        bootstrap.bind(serverConfig.getServerPort()).sync();
        IS_STARTED = true;
    }

    public void initServerConfig() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        ServerConfig serverConfig = PropertiesBootstrap.loadServerConfigFromLocal();
        this.setServerConfig(serverConfig);
        SERVER_CONFIG = serverConfig;
        //序列化技术初始化
        String serverSerialize = serverConfig.getServerSerialize();
        EXTENSION_LOADER.loadExtension(SerializeFactory.class);
        LinkedHashMap<String, Object> serializeFactoryMap = EXTENSION_LOADER_CACHE.get(SerializeFactory.class.getName());
        SerializeFactory serializeFactory = (SerializeFactory) serializeFactoryMap.get(serverSerialize);
        if (serializeFactory == null) {
            throw new RuntimeException("no match serialize type for" + serverSerialize);
        }
        SERVER_SERIALIZE_FACTORY = serializeFactory;
        //过滤链技术初始化
        EXTENSION_LOADER.loadExtension(IServerFilter.class);
        LinkedHashMap<String, Object> iServerFilterMap = EXTENSION_LOADER_CACHE.get(IServerFilter.class.getName());
        ServerFilterChain serverFilterChain = new ServerFilterChain();
        for (String iServerFilterKey : iServerFilterMap.keySet()) {
            IServerFilter iServerFilter = (IServerFilter) iServerFilterMap.get(iServerFilterKey);
            serverFilterChain.addServerFilter(iServerFilter);
        }
        SERVER_FILTER_CHAIN = serverFilterChain;
    }

    /**
     * 暴露服务信息
     *
     * @param serviceWrapper
     */
    public void exportService(ServiceWrapper serviceWrapper) {
        Object serviceBean = serviceWrapper.getServiceObj();
        if (serviceBean.getClass().getInterfaces().length == 0) {
            throw new RuntimeException("service must had interfaces!");
        }
        Class[] classes = serviceBean.getClass().getInterfaces();
        if (classes.length > 1) {
            throw new RuntimeException("service must only had one interfaces!");
        }
        if (REGISTRY_SERVICE == null) {
            REGISTRY_SERVICE = new ZookeeperRegister(serverConfig.getRegisterAddr());
        }
        //默认选择该对象的第一个实现接口
        Class interfaceClass = classes[0];
        PROVIDER_CLASS_MAP.put(interfaceClass.getName(), serviceBean);
        URL url = new URL();
        url.setServiceName(interfaceClass.getName());
        url.setApplicationName(serverConfig.getApplicationName());
        url.addParameter("host", CommonUtils.getIpAddress());
        url.addParameter("port", String.valueOf(serverConfig.getServerPort()));
        url.addParameter("group", String.valueOf(serviceWrapper.getGroup()));
        url.addParameter("limit", String.valueOf(serviceWrapper.getLimit()));
        PROVIDER_URL_SET.add(url);
        if (CommonUtils.isNotEmpty(serviceWrapper.getServiceToken())) {
            PROVIDER_SERVICE_WRAPPER_MAP.put(interfaceClass.getName(), serviceWrapper);
        }
    }

    /**
     * 批量暴露URL
     */
    public void batchExportUrl() {
        Thread task = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (URL url : PROVIDER_URL_SET) {
                    REGISTRY_SERVICE.register(url);
                }
            }
        });
        task.start();
    }

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        Server server = new Server();
        server.initServerConfig();
        iRpcListenerLoader = new IRpcListenerLoader();
        iRpcListenerLoader.init();
        ServiceWrapper dataServiceServiceWrapper = new ServiceWrapper(new DataServiceImpl(), "dev");
        dataServiceServiceWrapper.setServiceToken("token-a");
        dataServiceServiceWrapper.setLimit(2);
        ServiceWrapper userServiceServiceWrapper = new ServiceWrapper(new UserServiceImpl(), "dev");
        userServiceServiceWrapper.setServiceToken("token-b");
        userServiceServiceWrapper.setLimit(2);
        server.exportService(dataServiceServiceWrapper);
        server.exportService(userServiceServiceWrapper);
        ApplicationShutdownHook.registryShutdownHook();
        server.startApplication();
    }
}
