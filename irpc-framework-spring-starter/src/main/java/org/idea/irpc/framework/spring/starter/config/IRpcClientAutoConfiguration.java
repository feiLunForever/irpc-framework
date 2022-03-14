package org.idea.irpc.framework.spring.starter.config;

import org.idea.irpc.framework.core.client.Client;
import org.idea.irpc.framework.core.client.ConnectionHandler;
import org.idea.irpc.framework.core.client.RpcReference;
import org.idea.irpc.framework.core.client.RpcReferenceWrapper;
import org.idea.irpc.framework.interfaces.DataService;
import org.idea.irpc.framework.spring.starter.common.IRpcReference;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.lang.reflect.Field;

/**
 * @Author linhao
 * @Date created in 5:53 下午 2022/3/8
 */
public class IRpcClientAutoConfiguration implements BeanPostProcessor , InitializingBean , ApplicationListener<ApplicationReadyEvent> {

    private static RpcReference rpcReference = null;
    private static Client client = null;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(IRpcReference.class)){
                IRpcReference iRpcReference = field.getAnnotation(IRpcReference.class);
                try {
                    field.setAccessible(true);
                    Object refObj = field.get(bean);
                    RpcReferenceWrapper rpcReferenceWrapper = new RpcReferenceWrapper();
                    rpcReferenceWrapper.setAimClass(field.getType());
                    rpcReferenceWrapper.setGroup(iRpcReference.group());
                    rpcReferenceWrapper.setServiceToken(iRpcReference.serviceToken());
                    rpcReferenceWrapper.setUrl(iRpcReference.url());
                    rpcReferenceWrapper.setTimeOut(iRpcReference.timeOut());
                    //失败重试次数
                    rpcReferenceWrapper.setRetry(iRpcReference.retry());
                    rpcReferenceWrapper.setAsync(iRpcReference.async());
                    refObj = rpcReference.get(rpcReferenceWrapper);
                    field.set(bean,refObj);
                    client.doSubscribeService(field.getType());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                System.out.println(iRpcReference);
            }
        }
        return bean;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        client = new Client();
        rpcReference = client.initClientApplication();
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        System.out.println("所有配置都初始化好了");
        ConnectionHandler.setBootstrap(client.getBootstrap());
        client.doConnectServer();
        client.startClient();
    }
}
