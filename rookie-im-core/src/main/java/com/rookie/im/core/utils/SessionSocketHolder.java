package com.rookie.im.core.utils;

import com.rookie.im.core.domain.dto.UserClientDto;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author eumenides
 * @description
 * @date 2024/4/24
 */
public class SessionSocketHolder {
    private static final Map<UserClientDto, NioSocketChannel> CHANNELS = new ConcurrentHashMap<>();
    public static void put(Integer appId, String userId,Integer clientType, String imei,  NioSocketChannel channel) {
        UserClientDto userClientDto = new UserClientDto();
        userClientDto.setAppId(appId);
        userClientDto.setUserId(userId);
        userClientDto.setClientType(clientType);
        userClientDto.setImei(imei);
        CHANNELS.put(userClientDto, channel);
    }

    public static NioSocketChannel get(Integer appId, String userId,Integer clientType,String imei) {
        UserClientDto userClientDto = new UserClientDto();
        userClientDto.setAppId(appId);
        userClientDto.setUserId(userId);
        userClientDto.setClientType(clientType);
        userClientDto.setImei(imei);
        return CHANNELS.get(userClientDto);
    }

    public static void remove(Integer appId, String userId,Integer clientType) {
        UserClientDto userClientDto = new UserClientDto();
        userClientDto.setAppId(appId);
        userClientDto.setUserId(userId);
        userClientDto.setClientType(clientType);
        CHANNELS.remove(userClientDto);
    }

    public static void remove(NioSocketChannel channel) {
        CHANNELS.entrySet().stream().filter(entity -> entity.getValue() == channel)
                .forEach(entity -> CHANNELS.remove(entity.getKey()));
    }

}
