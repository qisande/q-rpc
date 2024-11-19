package com.qisande.yurpc.server;

/**
 * @author qisan
 * @date 2024-10-11 20:23:37
 * @description: HTTP 服务器接口
 */
public interface HttpServer {

    /**
     * 启动服务器
     *
     * @param port
     */
    void doStart(int port);
}
