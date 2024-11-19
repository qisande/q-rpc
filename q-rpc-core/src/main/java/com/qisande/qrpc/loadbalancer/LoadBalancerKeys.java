package com.qisande.yurpc.loadbalancer;

/**
 * @author qisan
 * @date 2024-10-17 21:17:23
 * @description:
 */
public interface LoadBalancerKeys {

    String ROUND_ROBIN = "roundRobin";
    String RANDOM = "random";
    String CONSISTENT_HASH = "consistentHash";
}
