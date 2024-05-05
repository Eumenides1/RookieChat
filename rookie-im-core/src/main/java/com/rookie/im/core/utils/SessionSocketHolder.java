package com.rookie.im.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.rookie.im.core.codec.pack.LoginPack;
import com.rookie.im.core.codec.proto.Message;
import com.rookie.im.core.constant.Constants;
import com.rookie.im.core.domain.dto.UserClientDto;
import com.rookie.im.core.utils.redis.RedisManager;
import com.rookie.stack.common.domain.model.UserSession;
import com.rookie.stack.common.enums.ImConnectStatusEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

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

    public static void remove(Integer appId, String userId,Integer clientType,String imei) {
        UserClientDto userClientDto = new UserClientDto();
        userClientDto.setAppId(appId);
        userClientDto.setUserId(userId);
        userClientDto.setClientType(clientType);
        userClientDto.setImei(imei);
        CHANNELS.remove(userClientDto);
    }

    public static void remove(NioSocketChannel channel) {
        CHANNELS.entrySet().stream().filter(entity -> entity.getValue() == channel)
                .forEach(entity -> CHANNELS.remove(entity.getKey()));
    }

    public static void createUserSession(ChannelHandlerContext ctx, Message msg, LoginPack loginPack) {
        UserSession userSession = new UserSession();
        userSession.setAppId(msg.getMessageHeader().getAppId());
        userSession.setClientType(msg.getMessageHeader().getClientType());
        userSession.setUserId(loginPack.getUserId());
        userSession.setConnectState(ImConnectStatusEnum.ONLINE_STATUS.getCode());
        userSession.setImei(msg.getMessageHeader().getImei());

        RedissonClient redissonClient = RedisManager.getRedissonClient();
        RMap<String,String> map = redissonClient.getMap(msg.getMessageHeader().getAppId() + Constants.RedisConstants.UserSessionConstants + loginPack.getUserId());
        map.put(String.valueOf(msg.getMessageHeader().getClientType()) + ":" + msg.getMessageHeader().getImei(), JSONObject.toJSONString(userSession));

        SessionSocketHolder.put(msg.getMessageHeader().getAppId(), loginPack.getUserId(), msg.getMessageHeader().getClientType(), msg.getMessageHeader().getImei(), (NioSocketChannel) ctx.channel());
    }

    public static void  removeUserSession(NioSocketChannel nioSocketChannel){
        String userId = (String) nioSocketChannel.attr(AttributeKey.valueOf(Constants.UserId)).get();
        Integer appId = (Integer) nioSocketChannel.attr(AttributeKey.valueOf(Constants.AppId)).get();
        Integer clientType = (Integer) nioSocketChannel.attr(AttributeKey.valueOf(Constants.ClientType)).get();
        String imei = (String) nioSocketChannel.attr(AttributeKey.valueOf(Constants.Imei)).get();
        // 删除session
        SessionSocketHolder.remove(appId, userId, clientType,imei);
        // redis删除
        RedissonClient redissonClient = RedisManager.getRedissonClient();
        RMap<Object,Object> map = redissonClient.getMap(appId + Constants.RedisConstants.UserSessionConstants + userId);
        map.remove(clientType +":"+imei );
        nioSocketChannel.close();
    }

    public static void  offlineUserSession(NioSocketChannel nioSocketChannel){
        String userId = (String) nioSocketChannel.attr(AttributeKey.valueOf(Constants.UserId)).get();
        Integer appId = (Integer) nioSocketChannel.attr(AttributeKey.valueOf(Constants.AppId)).get();
        Integer clientType = (Integer) nioSocketChannel.attr(AttributeKey.valueOf(Constants.ClientType)).get();
        String imei = (String) nioSocketChannel.attr(AttributeKey.valueOf(Constants.Imei)).get();
        // 删除session
        SessionSocketHolder.remove(appId, userId, clientType,imei);
        RedissonClient redissonClient = RedisManager.getRedissonClient();
        RMap<String,String> map = redissonClient.getMap(appId + Constants.RedisConstants.UserSessionConstants + userId);
        String sessionStr = map.get(clientType.toString() + ":"+imei);

        if (!StringUtils.isBlank(sessionStr)) {
            UserSession userSession = JSONObject.parseObject(sessionStr, UserSession.class);
            userSession.setConnectState(ImConnectStatusEnum.OFFLINE_STATUS.getCode());
            map.put(clientType.toString()+":"+imei, JSONObject.toJSONString(userSession));
        }
    }

}
