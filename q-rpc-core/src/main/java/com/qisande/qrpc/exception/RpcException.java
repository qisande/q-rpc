package com.qisande.yurpc.exception;

/**
 * @author qisan
 * @date 2024-10-21 11:35:13
 * @description: 自定义异常类
 */
public class RpcException extends RuntimeException {

    public RpcException(String message) {
        super(message);
    }
}
