package com.qisande.yurpc.loadbalancer;

import cn.hutool.core.lang.hash.Hash32;
import cn.hutool.core.util.HashUtil;
import com.qisande.yurpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author qisan
 * @date 2024-10-17 21:12:06
 * @description: 一致性哈希负载均衡器
 */
public class ConsistentHashLoadBalancer implements LoadBalancer {

    private final Hash32<Object> hashFunc = (key) -> HashUtil.fnvHash(key.toString());
    private final int numberOfReplicas = 10;
    private final SortedMap<Integer, ServiceMetaInfo> circle = new ConcurrentSkipListMap<>();

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        circle.clear();
        for (ServiceMetaInfo serviceMetaInfo : serviceMetaInfoList) {
            add(serviceMetaInfo);
        }
        return get(requestParams);
    }

    private void add(ServiceMetaInfo serviceMetaInfo) {
        for (int i = 0; i < this.numberOfReplicas; i++) {
            this.circle.put(this.hashFunc.hash32(serviceMetaInfo.getServiceAddress() + "#" + i), serviceMetaInfo);
        }
    }

    private ServiceMetaInfo get(Object key) {
        int hash = this.hashFunc.hash32(key);
        if (!this.circle.containsKey(hash)) {
            SortedMap<Integer, ServiceMetaInfo> tailMap = this.circle.tailMap(hash);
            hash = tailMap.isEmpty() ? this.circle.firstKey() : tailMap.firstKey();
        }
        return this.circle.get(hash);
    }
}
