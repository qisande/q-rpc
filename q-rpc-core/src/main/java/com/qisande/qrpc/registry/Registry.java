package com.qisande.yurpc.registry;

import com.qisande.yurpc.config.RegistryConfig;
import com.qisande.yurpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * @author qisan
 * @date 2024-10-14 19:27:22
 * @description: 注册中心
 */
public interface Registry {

    /**
     * 初始化
     *
     * @param registryConfig
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     *
     * @param serviceMetaInfo
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     *
     * @param serviceMetaInfo
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     * @param serviceKey
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();

    /**
     * 心跳检测（服务端）
     */
    void heartBeat();

    /**
     * 监听（消费端）
     *
     * @param key
     */
    void watch(String key, String serviceKey);
}