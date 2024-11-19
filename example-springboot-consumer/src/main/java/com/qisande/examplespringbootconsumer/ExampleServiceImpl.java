package com.qisande.examplespringbootconsumer;

import com.qisande.example.common.model.User;
import com.qisande.example.common.service.UserService;
import com.qisande.yurpc.springboot.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

/**
 * @author qisan
 * @date 2024-10-18 18:11:48
 * @description: 示例服务实现类
 */
@Service
public class ExampleServiceImpl {

    /**
     * 使用 Rpc 框架注入
     */
    @RpcReference
    private UserService userService;

    /**
     * 测试方法
     */
    public void test() {
        User user = new User();
        user.setName("yupi");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }
}
