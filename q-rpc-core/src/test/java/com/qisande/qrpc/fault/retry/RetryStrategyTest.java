package com.qisande.yurpc.fault.retry;

import com.qisande.yurpc.model.RpcResponse;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author qisan
 * @date 2024-10-17 22:07:05
 * @description:
 */
public class RetryStrategyTest {

    RetryStrategy retryStrategy = new NoRetryStrategy();

    @Test
    public void doRetry() {
        try {
            RpcResponse rpcResponse = retryStrategy.doRetry(() -> {
                System.out.println("测试重试");
                throw new RuntimeException("模拟重试失败");
            });
            System.out.println(rpcResponse);
        } catch (Exception e) {
            System.out.println("重试多次失败");
            e.printStackTrace();
        }
    }
}