package com.qisande.qrpc.server.tcp;

import cn.hutool.core.util.IdUtil;
import com.qisande.qrpc.RpcApplication;
import com.qisande.qrpc.model.RpcRequest;
import com.qisande.qrpc.model.RpcResponse;
import com.qisande.qrpc.model.ServiceMetaInfo;
import com.qisande.qrpc.protocol.*;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author qisan
 * @date 2024-10-17 14:53:47
 * @description: Vertx TCP 请求客户端
 */
public class VertxTcpClient {

    /**
     * 发送请求
     *
     * @param rpcRequest
     * @param serviceMetaInfo
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static RpcResponse doRequest(RpcRequest rpcRequest, ServiceMetaInfo serviceMetaInfo) throws ExecutionException, InterruptedException {
        // 发送 TCP 请求
        Vertx vertx = Vertx.vertx();
        NetClient netClient = vertx.createNetClient();
        CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
        netClient.connect(serviceMetaInfo.getServicePort(), serviceMetaInfo.getServiceHost(),
            result -> {
                if (!result.succeeded()) {
                    System.out.println("Failed to connect to TCP server");
                    return;
                }
                System.out.println("Connected to TCP server");
                NetSocket socket = result.result();
                // 发送数据
                // 构造消息
                ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
                ProtocolMessage.Header header = new ProtocolMessage.Header();
                header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
                header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
                header.setSerializer((byte) ProtocolMessageSerializerEnum.getEnumByValue(RpcApplication.getRpcConfig().getSerializer()).getKey());
                header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
                header.setRequestId(IdUtil.getSnowflakeNextId());
                protocolMessage.setHeader(header);
                protocolMessage.setBody(rpcRequest);
                // 编码请求
                try {
                    Buffer encodeBuffer = ProtocolMessageEncoder.encode(protocolMessage);
                    // 发送数据
                    socket.write(encodeBuffer);
                } catch (IOException e) {
                    throw new RuntimeException("协议消息编码错误");
                }

                // 接收响应
                TcpBufferHandlerWrapper bufferHandlerWrapper = new TcpBufferHandlerWrapper(buffer -> {
                    try {
                        ProtocolMessage<RpcResponse> rpcResponseProtocolMessage = (ProtocolMessage<RpcResponse>) ProtocolMessageDecoder.decode(buffer);
                        responseFuture.complete(rpcResponseProtocolMessage.getBody());
                    } catch (IOException e) {
                        throw new RuntimeException("协议消息解码错误");
                    }
                });
                socket.handler(bufferHandlerWrapper);
            }
        );
        RpcResponse rpcResponse = responseFuture.get();
        netClient.close();
        return rpcResponse;
    }
}
