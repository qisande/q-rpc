package com.qisande.qrpc.fault.retry;

import com.qisande.qrpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * @author qisan
 * @date 2024-10-17 22:00:00
 * @description: 重试策略
 */
public interface RetryStrategy {

    /**
     * 重试
     *
     * @param callable
     * @return
     * @throws Exception
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
