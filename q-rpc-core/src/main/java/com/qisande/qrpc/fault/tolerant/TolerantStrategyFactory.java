package com.qisande.yurpc.fault.tolerant;

import com.qisande.yurpc.spi.SpiLoader;

/**
 * @author qisan
 * @date 2024-10-18 15:51:40
 * @description:
 */
public class TolerantStrategyFactory {

    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错策略
     */
    private static final TolerantStrategy DEFAULT_RETRY_STRATEGY = new FailFastTolerantStrategy();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static TolerantStrategy getInstance(String key) {
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }
}
