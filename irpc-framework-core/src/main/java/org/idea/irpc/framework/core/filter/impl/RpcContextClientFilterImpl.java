package org.idea.irpc.framework.core.filter.impl;

import org.idea.irpc.framework.core.common.ChannelFutureWrapper;
import org.idea.irpc.framework.core.common.RpcContext;
import org.idea.irpc.framework.core.common.RpcInvocation;
import org.idea.irpc.framework.core.filter.IClientFilter;
import java.util.List;
import java.util.Map;
import static org.idea.irpc.framework.core.common.cache.CommonClientCache.CLIENT_CONFIG;

/**
 * 透传过滤器
 *
 * @Author linhao
 * @Date created in 2:33 下午 2022/1/29
 */
public class RpcContextClientFilterImpl implements IClientFilter {


    @Override
    public void doFilter(List<ChannelFutureWrapper> src, RpcInvocation rpcInvocation) {
        RpcContext.addAttachment("c_app_name",CLIENT_CONFIG.getApplicationName());
        Map<Object, Object> attachments = RpcContext.getAttachments();
        for (Object key : attachments.keySet()) {
            rpcInvocation.getRpcContext().put(String.valueOf(key), attachments.get(key));
        }
    }
}
