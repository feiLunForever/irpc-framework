package org.idea.irpc.framework.spring.starter.config;

import org.idea.irpc.framework.core.common.event.IRpcListenerLoader;
import org.idea.irpc.framework.core.server.ApplicationShutdownHook;
import org.idea.irpc.framework.core.server.Server;
import org.idea.irpc.framework.core.server.ServiceWrapper;
import org.idea.irpc.framework.spring.starter.common.IRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * @Author linhao
 * @Date created in 7:29 下午 2022/3/7
 */
public class IRpcServerAutoConfiguration implements InitializingBean, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(IRpcServerAutoConfiguration.class);

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        Server server = new Server();
        server.initServerConfig();
        IRpcListenerLoader iRpcListenerLoader = new IRpcListenerLoader();
        iRpcListenerLoader.init();
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(IRpcService.class);
        for (String beanName : beanMap.keySet()) {
            Object bean = beanMap.get(beanName);
            IRpcService iRpcService = bean.getClass().getAnnotation(IRpcService.class);
            ServiceWrapper dataServiceServiceWrapper = new ServiceWrapper(bean, iRpcService.group());
            dataServiceServiceWrapper.setServiceToken(iRpcService.serviceToken());
            dataServiceServiceWrapper.setLimit(iRpcService.limit());
            server.exportService(dataServiceServiceWrapper);
        }
        ApplicationShutdownHook.registryShutdownHook();
        server.startApplication();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
