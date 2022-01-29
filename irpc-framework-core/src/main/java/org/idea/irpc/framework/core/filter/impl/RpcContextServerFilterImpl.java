package org.idea.irpc.framework.core.filter.impl;

import org.idea.irpc.framework.core.common.RpcContext;
import org.idea.irpc.framework.core.common.RpcInvocation;
import org.idea.irpc.framework.core.filter.IServerFilter;

/**
 * 设置上下文信息
 *
 * @Author linhao
 * @Date created in 7:57 下午 2022/1/29
 */
public class RpcContextServerFilterImpl implements IServerFilter {

    @Override
    public void doFilter(RpcInvocation rpcInvocation) {
        RpcContext.setAttachments(rpcInvocation.getRpcContext());
    }
}
