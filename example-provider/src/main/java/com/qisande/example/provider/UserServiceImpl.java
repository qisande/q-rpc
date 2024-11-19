package com.qisande.example.provider;

import com.qisande.example.common.model.User;
import com.qisande.example.common.service.UserService;

/**
 * @author qisan
 * @date 2024-10-11 20:15:27
 * @description:
 */
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
