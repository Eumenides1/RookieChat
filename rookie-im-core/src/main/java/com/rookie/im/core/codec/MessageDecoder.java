package com.rookie.im.core.codec;

import com.alibaba.fastjson.JSONObject;
import com.rookie.im.core.codec.proto.Message;
import com.rookie.im.core.codec.proto.MessageHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/4/23
 */
public class MessageDecoder extends ByteToMessageDecoder {
    private static final int INT_SIZE = 4;  // Size of an integer in bytes
    private static final int HEADER_LENGTH = 28;  // Total length of the header

    private static final int OFFSET_COMMAND = 0;
    private static final int OFFSET_VERSION = OFFSET_COMMAND + INT_SIZE;
    private static final int OFFSET_CLIENT_TYPE = OFFSET_VERSION + INT_SIZE;
    private static final int OFFSET_MESSAGE_TYPE = OFFSET_CLIENT_TYPE + INT_SIZE;
    private static final int OFFSET_APP_ID = OFFSET_MESSAGE_TYPE + INT_SIZE;
    private static final int OFFSET_IMEI_LENGTH = OFFSET_APP_ID + INT_SIZE;
    private static final int OFFSET_BODY_LENGTH = OFFSET_IMEI_LENGTH + INT_SIZE;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < HEADER_LENGTH) {
            return; // Not enough data
        }

        int startIndex = in.readerIndex();
        int command = in.getInt(startIndex + OFFSET_COMMAND);
        int version = in.getInt(startIndex + OFFSET_VERSION);
        int clientType = in.getInt(startIndex + OFFSET_CLIENT_TYPE);
        int messageType = in.getInt(startIndex + OFFSET_MESSAGE_TYPE);
        int appId = in.getInt(startIndex + OFFSET_APP_ID);
        int imeiLength = in.getInt(startIndex + OFFSET_IMEI_LENGTH);
        int bodyLen = in.getInt(startIndex + OFFSET_BODY_LENGTH);

        System.out.println("Command: " + command);
        System.out.println("Version: " + version);
        System.out.println("Client Type: " + clientType);
        System.out.println("Message Type: " + messageType);
        System.out.println("App ID: " + appId);
        System.out.println("IMEI Length: " + imeiLength);
        System.out.println("Body Length: " + bodyLen);

        if (in.readableBytes() < HEADER_LENGTH + imeiLength + bodyLen) {
            return; // Still not enough data, reset the reader index
        }

        in.skipBytes(HEADER_LENGTH); // Now we can safely skip the header

        byte[] imeiData = new byte[imeiLength];
        in.readBytes(imeiData);
        String imei = new String(imeiData, StandardCharsets.UTF_8);

        byte[] bodyData = new byte[bodyLen];
        in.readBytes(bodyData);

        MessageHeader messageHeader = createMessageHeader(command, version, clientType, messageType, appId, imeiLength, imei, bodyLen);
        Message message = createMessage(messageHeader, messageType, bodyData);

        out.add(message);
    }

    private MessageHeader createMessageHeader(int command, int version, int clientType, int messageType, int appId, int imeiLength, String imei, int bodyLen) {
        MessageHeader header = new MessageHeader();
        header.setCommand(command);
        header.setVersion(version);
        header.setClientType(clientType);
        header.setMessageType(messageType);
        header.setAppId(appId);
        header.setImeiLength(imeiLength);
        header.setImei(imei);
        header.setLength(bodyLen);
        return header;
    }

    private Message createMessage(MessageHeader header, int messageType, byte[] bodyData) throws Exception {
        Message message = new Message();
        message.setMessageHeader(header);
        if (messageType == 0x0) {
            String body = new String(bodyData, StandardCharsets.UTF_8);
            JSONObject parse = (JSONObject) JSONObject.parse(body);
            message.setMessagePack(parse);
        }
        return message;
    }
}

