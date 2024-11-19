package com.qisande.yurpc.protocol;

/**
 * @author qisan
 * @date 2024-10-17 14:27:22
 * @description: 协议常量
 */
public interface ProtocolConstant {

    /**
     * 消息头长度
     */
    int MESSAGE_HEADER_LENGTH = 17;

    /**
     * 协议魔数
     */
    byte PROTOCOL_MAGIC = 0x1;

    /**
     * 协议版本号
     */
    byte PROTOCOL_VERSION = 0x1;

}
