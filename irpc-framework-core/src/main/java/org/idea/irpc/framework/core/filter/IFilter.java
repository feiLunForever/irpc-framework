package org.idea.irpc.framework.core.filter;

import org.idea.irpc.framework.core.common.RpcInvocation;
import org.idea.irpc.framework.core.registy.URL;

import java.util.List;

/**
 * @Author linhao
 * @Date created in 10:10 上午 2022/1/29
 */
public interface IFilter {

    /**
     * 执行过滤链
     *
     * @param rpcInvocation
     */
    List<URL> doFilter(RpcInvocation rpcInvocation);


}
