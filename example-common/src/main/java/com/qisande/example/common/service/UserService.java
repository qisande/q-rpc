package com.qisande.example.common.service;

import com.qisande.example.common.model.User;

/**
 * @author qisan
 * @date 2024-10-11 20:11:05
 * @description: 用户服务
 */
public interface UserService {

    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 新方法 - 获取数字
     *
     * @return
     */
    default short getNumber() {
        return 1;
    }
}
