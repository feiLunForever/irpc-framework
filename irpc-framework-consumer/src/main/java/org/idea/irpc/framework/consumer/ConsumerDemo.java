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
//        rpcReferenceWrapper.setAsync(true);
        //如果要使用future，这里要切换为false
        rpcReferenceWrapper.setAsync(false);
        //在初始化之前必须要设置对应的上下文
        DataService dataService = rpcReference.get(rpcReferenceWrapper);
        client.doSubscribeService(DataService.class);

        ConnectionHandler.setBootstrap(client.getBootstrap());
        client.doConnectServer();
        client.startClient();
        ExecutorService executorService= Executors.newFixedThreadPool(1);

        for (int i = 0; i < 60000; i++) {
            try {
                FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return dataService.sendData("test");
                    }
                });
                executorService.submit(futureTask);
                List<String> resultList = dataService.getList();
                System.out.println("result List is :" + resultList);
                System.out.println("等待了一段时间后");
                String result = futureTask.get();
                System.out.println(result);
            } catch (Exception e) {
                System.out.println(i);
                e.printStackTrace();
            }
        }
        System.out.println("结束调用60000次");
    }
}
