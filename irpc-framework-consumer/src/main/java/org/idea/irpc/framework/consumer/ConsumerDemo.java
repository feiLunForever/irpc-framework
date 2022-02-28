package org.idea.irpc.framework.consumer;

import org.idea.irpc.framework.core.client.*;
import org.idea.irpc.framework.core.common.config.ClientConfig;
import org.idea.irpc.framework.interfaces.DataService;

import java.util.List;
import java.util.concurrent.*;


/**
 * @Author linhao
 * @Date created in 4:25 下午 2022/2/4
 */
public class ConsumerDemo {

    public static void doAsyncRef() {
        RpcReferenceFuture rpcReferenceFuture = new RpcReferenceFuture<>();

    }

    public static void main(String[] args) throws Throwable {
        Client client = new Client();
        RpcReference rpcReference = client.initClientApplication();
        RpcReferenceWrapper<DataService> rpcReferenceWrapper = new RpcReferenceWrapper<>();
        rpcReferenceWrapper.setAimClass(DataService.class);
        rpcReferenceWrapper.setGroup("dev");
        rpcReferenceWrapper.setServiceToken("token-a");
        rpcReferenceWrapper.setTimeOut(3000);
        //失败重试次数
        rpcReferenceWrapper.setRetry(0);
        rpcReferenceWrapper.setAsync(false);
        DataService dataService = rpcReference.get(rpcReferenceWrapper);
        //订阅服务
        client.doSubscribeService(DataService.class);

        ConnectionHandler.setBootstrap(client.getBootstrap());
        client.doConnectServer();
        client.startClient();
        String result = dataService.testErrorV2();
        System.out.println("结束调用");
        System.out.println(result);
    }
}
