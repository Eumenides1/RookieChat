package com.rookie.im.core.codec.utils;

import com.alibaba.fastjson.JSONObject;
import com.rookie.im.core.codec.proto.Message;
import com.rookie.im.core.codec.proto.MessageHeader;
import io.netty.buffer.ByteBuf;

import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * @author eumenides
 * @description
 * @date 2024/4/24
 */
public class ByteBufToMessageUtils {
    private static final int INT_SIZE = 4;  // Size of an integer in bytes
    private static final int HEADER_LENGTH = 28;  // Total length of the header

    private static final int OFFSET_COMMAND = 0;
    private static final int OFFSET_VERSION = OFFSET_COMMAND + INT_SIZE;
    private static final int OFFSET_CLIENT_TYPE = OFFSET_VERSION + INT_SIZE;
    private static final int OFFSET_MESSAGE_TYPE = OFFSET_CLIENT_TYPE + INT_SIZE;
    private static final int OFFSET_APP_ID = OFFSET_MESSAGE_TYPE + INT_SIZE;
    private static final int OFFSET_IMEI_LENGTH = OFFSET_APP_ID + INT_SIZE;
    private static final int OFFSET_BODY_LENGTH = OFFSET_IMEI_LENGTH + INT_SIZE;
    public static Message transition(ByteBuf in) {

        int startIndex = in.readerIndex();
        int command = in.getInt(startIndex + OFFSET_COMMAND);
        int version = in.getInt(startIndex + OFFSET_VERSION);
        int clientType = in.getInt(startIndex + OFFSET_CLIENT_TYPE);
        int messageType = in.getInt(startIndex + OFFSET_MESSAGE_TYPE);
        int appId = in.getInt(startIndex + OFFSET_APP_ID);
        int imeiLength = in.getInt(startIndex + OFFSET_IMEI_LENGTH);
        int bodyLen = in.getInt(startIndex + OFFSET_BODY_LENGTH);


        if (in.readableBytes() < HEADER_LENGTH + imeiLength + bodyLen) {
            in.resetReaderIndex();
            return null; // Still not enough data, reset the reader index
        }

        in.skipBytes(HEADER_LENGTH); // Now we can safely skip the header

        byte[] imeiData = new byte[imeiLength];
        in.readBytes(imeiData);
        String imei = new String(imeiData, StandardCharsets.UTF_8);

        byte[] bodyData = new byte[bodyLen];
        in.readBytes(bodyData);

        MessageHeader messageHeader = createMessageHeader(command, version, clientType, messageType, appId, imeiLength, imei, bodyLen);
        Message message = createMessage(messageHeader, messageType, bodyData);

        return message;
    }
    private static MessageHeader createMessageHeader(int command, int version, int clientType, int messageType, int appId, int imeiLength, String imei, int bodyLen) {
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

    private static Message createMessage(MessageHeader header, int messageType, byte[] bodyData) {
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
