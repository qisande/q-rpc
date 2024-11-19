package com.qisande.yurpc.fault.retry;

/**
 * @author qisan
 * @date 2024-10-17 22:08:05
 * @description: 重试策略键名常量
 */
public interface RetryStrategyKeys {

    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间间隔
     */
    String FIXED_INTERVAL = "fixedInterval";
}
