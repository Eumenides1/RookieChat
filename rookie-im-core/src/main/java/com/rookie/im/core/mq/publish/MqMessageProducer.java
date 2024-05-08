package com.rookie.im.core.mq.publish;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.rookie.im.core.mq.factory.MqFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author eumenides
 * @description
 * @date 2024/5/8
 */
@Slf4j
public class MqMessageProducer {
    public static void sendMessage(Object message) {
        Channel channel = null;
        String channelName = "";

        try {
            channel = MqFactory.getChannel(channelName);
            channel.basicPublish(channelName, "", null, JSONObject.toJSONString(message).getBytes());
        }catch (Exception e) {
            log.error("mq消息推送出现异常：{}", e.getMessage());
        }
    }
}
