package com.qisande.yurpc.registry;

import com.qisande.yurpc.serializer.JdkSerializer;
import com.qisande.yurpc.serializer.Serializer;
import com.qisande.yurpc.spi.SpiLoader;

/**
 * @author qisan
 * @date 2024-10-14 19:57:31
 * @description: 注册中心工厂（用于获取注册中心对象）
 */
public class RegistryFactory {

    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认注册中心
     */
    public static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }
}
