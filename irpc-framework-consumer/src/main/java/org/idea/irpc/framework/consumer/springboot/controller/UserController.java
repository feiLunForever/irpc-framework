package org.idea.irpc.framework.consumer.springboot.controller;

import org.idea.irpc.framework.interfaces.OrderService;
import org.idea.irpc.framework.interfaces.UserService;
import org.idea.irpc.framework.spring.starter.common.IRpcReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Author linhao
 * @Date created in 5:43 下午 2022/3/8
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @IRpcReference
    private UserService userService;

    @IRpcReference
    private OrderService orderService;

    @GetMapping(value = "/test")
    public void test(){
        userService.test();
    }

    @GetMapping(value = "/get-order-no")
    public List<String> getOrderNo(){
        return orderService.getOrderNoList();
    }


}
