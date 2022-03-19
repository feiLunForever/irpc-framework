package org.idea.irpc.framework.common.consumer.controller;

import org.idea.irpc.framework.interfaces.user.UserRpcService;
import org.idea.irpc.framework.spring.starter.common.IRpcReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author linhao
 * @Date created in 10:13 上午 2022/3/19
 */
@RestController
@RequestMapping(value = "/api-test")
public class ApiTestController {

    @IRpcReference
    private UserRpcService userRpcService;

    @GetMapping(value = "/do-test")
    public boolean doTest(){
        String userId = userRpcService.getUserId();
        System.out.println(userId);
        return true;
    }
}
