package org.idea.irpc.framework.core.common.exception;

/**
 * @Author linhao
 * @Date created in 9:04 上午 2022/3/3
 */
public class IRpcException extends Exception{

    public IRpcException(){}

    public IRpcException(String message){
        super(message);
    }
}
