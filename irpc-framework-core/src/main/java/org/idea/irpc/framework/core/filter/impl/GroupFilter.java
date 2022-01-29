package org.idea.irpc.framework.core.filter.impl;

import org.idea.irpc.framework.core.common.RpcInvocation;
import org.idea.irpc.framework.core.common.utils.CommonUtils;
import org.idea.irpc.framework.core.filter.IFilter;
import org.idea.irpc.framework.core.registy.URL;

import java.util.List;

/**
 * 分组过滤器
 *
 * @Author linhao
 * @Date created in 10:18 上午 2022/1/29
 */
public class GroupFilter implements IFilter {

    @Override
    public List<URL> doFilter(RpcInvocation rpcInvocation) {
        String group = (String) rpcInvocation.getAttachments().get("group");
        if (CommonUtils.isEmpty(group)) {
//            return ;
        }
        return null;
    }
}
