package com.qisande.qrpc.bootstrap;

import com.qisande.qrpc.RpcApplication;
import com.qisande.qrpc.config.RegistryConfig;
import com.qisande.qrpc.config.RpcConfig;
import com.qisande.qrpc.model.ServiceMetaInfo;
import com.qisande.qrpc.model.ServiceRegisterInfo;
import com.qisande.qrpc.registry.LocalRegistry;
import com.qisande.qrpc.registry.Registry;
import com.qisande.qrpc.registry.RegistryFactory;
import com.qisande.qrpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * @author qisan
 * @date 2024-10-18 17:12:58
 * @description: 服务提供者初始化
 */
public class ProviderBootstrap {

    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 本地注册
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());

            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }
        }

        // 启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }
}
