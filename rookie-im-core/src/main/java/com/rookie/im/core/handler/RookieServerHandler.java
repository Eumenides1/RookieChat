package com.rookie.im.core.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.rookie.im.core.codec.pack.LoginPack;
import com.rookie.im.core.codec.proto.Message;
import com.rookie.im.core.constant.Constants;
import com.rookie.im.core.utils.SessionSocketHolder;
import com.rookie.im.core.utils.redis.RedisManager;
import com.rookie.stack.common.domain.model.UserSession;
import com.rookie.stack.common.enums.ImConnectStatusEnum;
import com.rookie.stack.common.enums.command.SystemCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

/**
 * @author eumenides
 * @description
 * @date 2024/4/23
 */
@Slf4j
public class RookieServerHandler extends SimpleChannelInboundHandler<Message> {

    private Integer brokerId;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        Integer command = msg.getMessageHeader().getCommand();
        if (command == SystemCommand.LOGIN.getCommand()) {
            LoginPack loginPack = JSON.parseObject(JSONObject.toJSONString(msg.getMessagePack()),
                    new TypeReference<LoginPack>(){}.getType());

            ctx.channel().attr(AttributeKey.valueOf(Constants.UserId)).set(loginPack.getUserId());
            ctx.channel().attr(AttributeKey.valueOf(Constants.AppId)).set(msg.getMessageHeader().getAppId());
            ctx.channel().attr(AttributeKey.valueOf(Constants.ClientType)).set(msg.getMessageHeader().getClientType());
            ctx.channel().attr(AttributeKey.valueOf(Constants.Imei)).set(msg.getMessageHeader().getImei());


            UserSession userSession = new UserSession();
            userSession.setAppId(msg.getMessageHeader().getAppId());
            userSession.setClientType(msg.getMessageHeader().getClientType());
            userSession.setUserId(loginPack.getUserId());
            userSession.setConnectState(ImConnectStatusEnum.ONLINE_STATUS.getCode());
            userSession.setBrokerId(brokerId);
            userSession.setImei(msg.getMessageHeader().getImei());

            RedissonClient redissonClient = RedisManager.getRedissonClient();
            RMap<String,String> map = redissonClient.getMap(msg.getMessageHeader().getAppId() + Constants.RedisConstants.UserSessionConstants + loginPack.getUserId());
            map.put(String.valueOf(msg.getMessageHeader().getClientType())+":"+msg.getMessageHeader().getImei(), JSONObject.toJSONString(userSession));



            // 将 channel 存起来
            SessionSocketHolder.put(msg.getMessageHeader().getAppId(),
                    loginPack.getUserId(),
                    msg.getMessageHeader().getClientType(),
                    msg.getMessageHeader().getImei(),
                    (NioSocketChannel) ctx.channel());
        }
    }
}
