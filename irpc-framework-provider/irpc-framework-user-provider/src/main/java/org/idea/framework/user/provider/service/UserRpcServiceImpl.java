package org.idea.framework.user.provider.service;

import org.idea.irpc.framework.interfaces.user.UserRpcService;
import org.idea.irpc.framework.spring.starter.common.IRpcService;

import java.util.UUID;

/**
 * @Author linhao
 * @Date created in 10:07 上午 2022/3/19
 */
@IRpcService
public class UserRpcServiceImpl implements UserRpcService {

    @Override
    public String getUserId() {
        System.out.println("this is getUser Id");
        return UUID.randomUUID().toString();
    }
}
