package org.idea.irpc.framework.provider.springboot.service.impl;

import org.idea.irpc.framework.interfaces.OrderService;
import org.idea.irpc.framework.spring.starter.common.IRpcService;

import java.util.Arrays;
import java.util.List;

/**
 * @Author linhao
 * @Date created in 6:41 下午 2022/3/8
 */
@IRpcService
public class OrderServiceImpl implements OrderService {

    public List<String> getOrderNoList() {
        return Arrays.asList("item1","item2");
    }
}
