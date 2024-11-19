package com.qisande.yurpc.fault.tolerant;

import com.qisande.yurpc.model.RpcResponse;

import java.util.Map;

/**
 * @author qisan
 * @date 2024-10-18 15:44:36
 * @description: 容错策略
 */
public interface TolerantStrategy {

    /**
     * 容错
     *
     * @param context 上下文，用于传递数据
     * @param e       异常
     * @return
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
