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
            handleLogin(ctx, msg);
        } else if (command == SystemCommand.LOGOUT.getCommand()) {
            handleLogout(ctx);
        } else if (command == SystemCommand.PING.getCommand()) {
            handlePing(ctx);
        }
    }

    private void handleLogin(ChannelHandlerContext ctx, Message msg) throws Exception {
        LoginPack loginPack = JSON.parseObject(JSONObject.toJSONString(msg.getMessagePack()),
                new TypeReference<LoginPack>(){}.getType());
        setChannelAttributes(ctx, msg, loginPack);
        SessionSocketHolder.createUserSession(ctx, msg, loginPack);
    }
    private void handleLogout(ChannelHandlerContext ctx) {
        SessionSocketHolder.removeUserSession((NioSocketChannel) ctx.channel());
    }
    private void handlePing(ChannelHandlerContext ctx) {
        ctx.channel()
                .attr(AttributeKey.valueOf(Constants.ReadTime)).set(System.currentTimeMillis());
    }
    private void setChannelAttributes(ChannelHandlerContext ctx, Message msg, LoginPack loginPack) {
        ctx.channel().attr(AttributeKey.valueOf(Constants.UserId)).set(loginPack.getUserId());
        ctx.channel().attr(AttributeKey.valueOf(Constants.AppId)).set(msg.getMessageHeader().getAppId());
        ctx.channel().attr(AttributeKey.valueOf(Constants.ClientType)).set(msg.getMessageHeader().getClientType());
        ctx.channel().attr(AttributeKey.valueOf(Constants.Imei)).set(msg.getMessageHeader().getImei());
    }

}
