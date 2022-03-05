package org.idea.irpc.framework.core.common.exception;

import org.idea.irpc.framework.core.common.RpcProtocol;

/**
 * @Author linhao
 * @Date created in 9:04 上午 2022/3/3
 */
public class IRpcException extends RuntimeException{

    private RpcProtocol rpcProtocol;

    public RpcProtocol getRpcProtocol() {
        return rpcProtocol;
    }

    public void setRpcProtocol(RpcProtocol rpcProtocol) {
        this.rpcProtocol = rpcProtocol;
    }

    public IRpcException(RpcProtocol rpcProtocol){
        this.rpcProtocol = rpcProtocol;
    }

    public IRpcException(RpcProtocol rpcProtocol,String message){
        super(message);
        this.rpcProtocol = rpcProtocol;
    }

}
