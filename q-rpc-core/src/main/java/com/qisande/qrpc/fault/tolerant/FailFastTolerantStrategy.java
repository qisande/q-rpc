package com.qisande.qrpc.fault.tolerant;

import com.qisande.qrpc.model.RpcResponse;

import java.util.Map;

/**
 * @author qisan
 * @date 2024-10-18 15:46:10
 * @description: 快速失败 - 容错策略（立刻通知外层调用方）
 */
public class FailFastTolerantStrategy implements TolerantStrategy{

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错", e);
    }
}
