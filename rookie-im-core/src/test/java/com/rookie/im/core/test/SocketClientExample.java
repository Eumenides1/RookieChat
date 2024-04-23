package com.rookie.im.core.test;
/**
 * @author eumenides
 * @description
 * @date 2024/4/23
 */

import java.util.UUID;

import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketClientExample {
    public static void main(String[] args) {
        String imei = UUID.randomUUID().toString().replace("-", "");

        // 基础数据
        int command = 9888;
        int version = 1;
        int clientType = 4;
        int messageType = 0x0;
        int appId = 10000;
        String name = "jaguarliu";

        // 构建数据
        try (Socket socket = new Socket("127.0.0.1", 9000);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            byte[] commandBytes = intToBytes(command);
            byte[] versionBytes = intToBytes(version);
            byte[] clientTypeBytes = intToBytes(clientType);
            byte[] messageTypeBytes = intToBytes(messageType);
            byte[] appIdBytes = intToBytes(appId);
            byte[] imeiBytes = imei.getBytes(StandardCharsets.UTF_8);
            byte[] imeiLengthBytes = intToBytes(imeiBytes.length);


            // 使用 Hutool 的 JSONUtil
            String jsonData = JSONUtil.toJsonStr(new Data(name, appId, clientType, imei));
            byte[] body = jsonData.getBytes(StandardCharsets.UTF_8);
            byte[] bodyLengthBytes = intToBytes(body.length);

            // 发送数据
            for (int x = 0; x < 100; x++) {
                out.write(commandBytes);
                out.write(versionBytes);
                out.write(clientTypeBytes);
                out.write(messageTypeBytes);
                out.write(appIdBytes);
                out.write(imeiLengthBytes);
                out.write(bodyLengthBytes);
                out.write(imeiBytes);
                out.write(body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] intToBytes(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value};
    }
    @lombok.Data
    static class Data {
        String name;
        int appId;
        int clientType;
        String imei;

        Data(String name, int appId, int clientType, String imei) {
            this.name = name;
            this.appId = appId;
            this.clientType = clientType;
            this.imei = imei;
        }
    }
}



