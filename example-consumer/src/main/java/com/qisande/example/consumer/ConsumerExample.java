package com.qisande.example.consumer;

import com.qisande.example.common.model.User;
import com.qisande.example.common.service.UserService;
import com.qisande.qrpc.bootstrap.ConsumerBootstrap;
import com.qisande.qrpc.proxy.ServiceProxyFactory;

/**
 * @author qisan
 * @date 2024-10-12 18:17:21
 * @description: 简易服务消费者示例
 */
public class ConsumerExample {

    public static void main(String[] args) {
        // 服务提供者初始化
        ConsumerBootstrap.init();

        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("qisande");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
