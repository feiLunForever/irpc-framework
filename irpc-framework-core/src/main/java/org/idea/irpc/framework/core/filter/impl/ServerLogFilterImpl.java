package org.idea.irpc.framework.core.filter.impl;

import org.idea.irpc.framework.core.common.ChannelFutureWrapper;
import org.idea.irpc.framework.core.common.RpcContext;
import org.idea.irpc.framework.core.common.RpcInvocation;
import org.idea.irpc.framework.core.filter.IClientFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author linhao
 * @Date created in 8:01 下午 2022/1/29
 */
public class ClientLogFilterImpl implements IClientFilter {

    private static Logger logger = LoggerFactory.getLogger(ClientLogFilterImpl.class);

    @Override
    public void doFilter(List<ChannelFutureWrapper> src, RpcInvocation rpcInvocation) {
        logger.info(RpcContext.getAttachment("c_app_name")+" do invoke -----> "+rpcInvocation.getTargetServiceName());
    }

}
