package com.qisande.yurpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.qisande.yurpc.model.RpcRequest;
import com.qisande.yurpc.model.RpcResponse;
import com.qisande.yurpc.serializer.JdkSerializer;
import com.qisande.yurpc.serializer.Serializer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author qisan
 * @date 2024-10-11 21:51:02
 * @description: 服务代理（JDK 动态代理）
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 制定序列化器
        Serializer serializer = new JdkSerializer();
        // 构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterType(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            // 发请求
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getData();
            }
            // 反序列化
            // 响应
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
