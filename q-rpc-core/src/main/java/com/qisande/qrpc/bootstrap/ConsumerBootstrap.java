package com.qisande.qrpc.bootstrap;

import com.qisande.qrpc.RpcApplication;

/**
 * @author qisan
 * @date 2024-10-18 17:23:55
 * @description: 服务消费者启动类（初始化）
 */
public class ConsumerBootstrap {

    /**
     * 初始化
     */
    public static void init() {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
    }

}
