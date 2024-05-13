package com.rookie.im.core.utils.redis;

import com.alibaba.fastjson.JSONObject;
import com.rookie.im.core.codec.pack.MessagePack;
import com.rookie.stack.common.constant.Constants;
import com.rookie.im.core.domain.dto.UserClientDto;
import com.rookie.im.core.utils.SessionSocketHolder;
import com.rookie.stack.common.enums.ClientType;
import com.rookie.stack.common.enums.DeviceMultiLoginEnum;
import com.rookie.stack.common.enums.command.SystemCommand;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.listener.MessageListener;

import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/5/9
 */
@Slf4j
public class UserLoginListener {

    private Integer loginModel;

    public UserLoginListener(Integer loginModel) {
        this.loginModel = loginModel;
    }

    public void listenerUserLogin () {
        RTopic topic = RedisManager.getRedissonClient().getTopic(Constants.RedisConstants.UserLoginChannel);
        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence charSequence, String s) {
                log.info("收到用户上线通知：{}", s);
                UserClientDto dto = JSONObject.parseObject(s, UserClientDto.class);
                List<NioSocketChannel> nioSocketChannels = SessionSocketHolder.get(dto.getAppId(), dto.getUserId());
                for (NioSocketChannel nioSocketChannel : nioSocketChannels) {
                    if (shouldKickClient(dto, nioSocketChannel)) {
                        kickClient(nioSocketChannel);
                    }
                }
            }
        });
    }

    private boolean shouldKickClient(UserClientDto dto, NioSocketChannel nioSocketChannel) {
        Integer clientType = (Integer) nioSocketChannel.attr(AttributeKey.valueOf(Constants.ClientType)).get();
        String imei = dto.getImei();

        if (loginModel== DeviceMultiLoginEnum.ONE.getLoginMode()) {
            // 执行ONE对应的逻辑
            System.out.println(isSameClient(dto,clientType,imei));
            return isSameClient(dto, clientType, imei);
        } else if (loginModel == DeviceMultiLoginEnum.TWO.getLoginMode()) {
            // 执行TWO对应的逻辑
            return clientType != ClientType.WEB.getCode() && !isSameClient(dto, clientType, imei);
        } else if (loginModel == DeviceMultiLoginEnum.THREE.getLoginMode()) {
            // 执行THREE对应的逻辑
            boolean isSameClient = (clientType == ClientType.IOS.getCode() || clientType == ClientType.ANDROID.getCode())
                    && (dto.getClientType() == ClientType.IOS.getCode() || dto.getClientType() == ClientType.ANDROID.getCode())
                    || (clientType == ClientType.MAC.getCode() || clientType == ClientType.WINDOWS.getCode())
                    && (dto.getClientType() == ClientType.MAC.getCode() || dto.getClientType() == ClientType.WINDOWS.getCode());
            return dto.getClientType() != ClientType.WEB.getCode() && isSameClient && !isSameClient(dto, clientType, imei);
        }
        return false;
    }

    private boolean isSameClient(UserClientDto dto, Integer clientType, String imei) {
        return (clientType + ":" + imei).equals(dto.getClientType() + ":" + dto.getImei());
    }

    private void kickClient(NioSocketChannel channel) {
        MessagePack<Object> pack = new MessagePack<>();
        pack.setToId((String) channel.attr(AttributeKey.valueOf(Constants.UserId)).get());
        pack.setUserId((String) channel.attr(AttributeKey.valueOf(Constants.UserId)).get());
        pack.setCommand(SystemCommand.MUTUALLOGIN.getCommand());
        log.info("发送下线通知：{}", pack.toString());
        channel.writeAndFlush(pack);
    }
}
