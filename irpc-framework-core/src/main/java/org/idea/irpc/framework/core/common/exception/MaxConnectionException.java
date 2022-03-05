package org.idea.irpc.framework.core.common.exception;

import org.idea.irpc.framework.core.common.RpcProtocol;

/**
 * @Author linhao
 * @Date created in 9:53 下午 2022/3/5
 */
public class MaxConnectionException extends IRpcException{

    private String errorInfo;

    public MaxConnectionException(RpcProtocol rpcProtocol) {
        super(rpcProtocol);
    }

    public MaxConnectionException(String errorInfo){
        super(null);
        this.errorInfo = errorInfo;
    }
}
