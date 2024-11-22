package com.qisande.qrpc.serializer;

import com.qisande.qrpc.spi.SpiLoader;

/**
 * @author qisan
 * @date 2024-10-12 20:33:05
 * @description: 序列化器工厂（用于获取序列化器对象）
 */
public class SerializerFactory {

    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     */
    public static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }
}
