package com.qisande.example.provider;

import com.qisande.example.common.service.UserService;
import com.qisande.qrpc.bootstrap.ProviderBootstrap;
import com.qisande.qrpc.model.ServiceRegisterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qisan
 * @date 2024-10-15 15:06:21
 * @description: 服务提供者示例
 */
public class ProviderExample {

    public static void main(String[] args) {
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo<?> serviceRegisterInfo = new ServiceRegisterInfo<>(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        // 服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
