package com.qisande.yurpc.proxy;

import cn.hutool.core.collection.CollUtil;
import com.qisande.yurpc.RpcApplication;
import com.qisande.yurpc.config.RpcConfig;
import com.qisande.yurpc.constant.RpcConstant;
import com.qisande.yurpc.fault.retry.RetryStrategy;
import com.qisande.yurpc.fault.retry.RetryStrategyFactory;
import com.qisande.yurpc.fault.tolerant.TolerantStrategy;
import com.qisande.yurpc.fault.tolerant.TolerantStrategyFactory;
import com.qisande.yurpc.loadbalancer.LoadBalancerFactory;
import com.qisande.yurpc.loadbalancer.LoadBalancer;
import com.qisande.yurpc.model.RpcRequest;
import com.qisande.yurpc.model.RpcResponse;
import com.qisande.yurpc.model.ServiceMetaInfo;
import com.qisande.yurpc.registry.Registry;
import com.qisande.yurpc.registry.RegistryFactory;
import com.qisande.yurpc.serializer.Serializer;
import com.qisande.yurpc.serializer.SerializerFactory;
import com.qisande.yurpc.server.tcp.VertxTcpClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * @author qisan
 * @date 2024-10-11 21:51:02
 * @description: 服务代理（JDK 动态代理）
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }

            // 负载均衡
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            // 将调用方法名（请求路径）作为负载均衡参数
            HashMap<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

            // rpc 请求
            // 使用重试机制
            RpcResponse rpcResponse = null;
            try {
                RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
                rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
                );
            } catch (Exception e) {
                // 容错机制
                TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
                rpcResponse = tolerantStrategy.doTolerant(null, e);
            }
            return rpcResponse.getData();
        } catch (IOException e) {
            throw new RuntimeException("调用失败");
        }
    }

}
