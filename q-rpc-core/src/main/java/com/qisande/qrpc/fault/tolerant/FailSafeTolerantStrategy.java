package com.qisande.yurpc.fault.tolerant;

import com.qisande.yurpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author qisan
 * @date 2024-10-18 15:47:25
 * @description: 静默处理异常 - 容错策略
 */
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy{

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理异常", e);
        return new RpcResponse();
    }
}
