package com.rookie.im.core.mq.publish;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.rookie.im.core.codec.proto.Message;
import com.rookie.im.core.constant.Constants;
import com.rookie.im.core.mq.factory.MqFactory;
import com.rookie.stack.common.enums.command.Command;
import com.rookie.stack.common.enums.command.CommandType;
import lombok.extern.slf4j.Slf4j;

/**
 * @author eumenides
 * @description
 * @date 2024/5/8
 */
@Slf4j
public class MqMessageProducer {
    public static void sendMessage(Message message, Command command) {
        Channel channel = null;
        String channelName = "";
        CommandType commandType = CommandType.getCommandType(command);
        switch (commandType){
            case SYSTEM:
                channelName = Constants.RabbitConstants.GatewaySystemService;
            default:
                log.info("invalid command type {} {}", commandType, command);
        }
        try {
            channel = MqFactory.getChannel(channelName);
            JSONObject o = (JSONObject) JSON.toJSON(message.getMessagePack());
            o.put("command", command);
            o.put("clientType", message.getMessageHeader().getClientType());
            o.put("imei",message.getMessageHeader().getImei());
            o.put("appId", message.getMessageHeader().getAppId());
            channel.basicPublish(channelName, "", null, o.toJSONString().getBytes());
        }catch (Exception e) {
            log.error("mq消息推送出现异常：{}", e.getMessage());
        }
    }
}
