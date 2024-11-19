package com.qisande.example.provider;

import com.qisande.example.common.service.UserService;
import com.qisande.qrpc.RpcApplication;
import com.qisande.qrpc.registry.LocalRegistry;
import com.qisande.qrpc.server.VertxHttpServer;


/**
 * @author qisan
 * @date 2024-10-11 20:16:51
 * @description: 简易服务提供者
 */
public class EasyProviderExample {

    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动 web 服务
        VertxHttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
