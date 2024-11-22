package com.qisande.example.consumer;

import com.qisande.example.common.model.User;
import com.qisande.example.common.service.UserService;
import com.qisande.qrpc.proxy.ServiceProxyFactory;

/**
 * @author qisan
 * @date 2024-10-11 20:18:34
 * @description: 简易服务消费者示例
 */
public class EasyConsumerExample {

    public static void main(String[] args) {
        User user = new User();
        user.setName("qisande");
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
