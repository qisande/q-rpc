package com.qisande.examplespringbootprovider;

import com.qisande.example.common.model.User;
import com.qisande.example.common.service.UserService;
import com.qisande.qrpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

/**
 * @author qisan
 * @date 2024-10-18 18:10:49
 * @description:
 */
@Service
@RpcService
public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}

