package org.idea.irpc.framework.common.consumer.controller;

import org.idea.irpc.framework.interfaces.OrderService;
import org.idea.irpc.framework.interfaces.good.GoodRpcService;
import org.idea.irpc.framework.interfaces.pay.PayRpcService;
import org.idea.irpc.framework.interfaces.user.UserRpcService;
import org.idea.irpc.framework.spring.starter.common.IRpcReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author linhao
 * @Date created in 10:13 上午 2022/3/19
 */
@RestController
@RequestMapping(value = "/api-test")
public class ApiTestController {

    @IRpcReference
    private UserRpcService userRpcService;
    @IRpcReference
    private GoodRpcService goodRpcService;
    @IRpcReference
    private PayRpcService payRpcService;

    @GetMapping(value = "/do-test")
    public boolean doTest() {
        String userId = userRpcService.getUserId();
        System.out.println("userRpcService result: " + userId);
        boolean goodResult = goodRpcService.decreaseStock();
        System.out.println("goodRpcService result: " + goodResult);
        boolean payResult = payRpcService.doPay();
        System.out.println("payRpcService result: " + payResult);
        List<Map<String, String>> resultList = userRpcService.findMyGoods(userId);
        System.out.println("userRpcService's findMyGoods result:" + resultList);
        return true;
    }


    @GetMapping(value = "/do-test-2")
    public void doTest2(){
        String userId = userRpcService.getUserId();
        System.out.println("userRpcService result: " + userId);
        boolean goodResult = goodRpcService.decreaseStock();
        System.out.println("goodRpcService result: " + goodResult);
    }
}
