package com.rookie.im.core.mq.reciver;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rookie.stack.common.constant.Constants;
import com.rookie.im.core.mq.factory.MqFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author eumenides
 * @description
 * @date 2024/5/8
 */
@Slf4j
public class MessageReciver {

    private static void startReciverMessage(){
        try {
            Channel channel = MqFactory.getChannel(Constants.RabbitConstants.MessageService2Im);
            channel.queueDeclare(Constants.RabbitConstants.MessageService2Im ,
                    true,false,false,null);
            channel.queueBind(
                    Constants.RabbitConstants.MessageService2Im ,
                    Constants.RabbitConstants.MessageService2Im, ""
            );
            channel.basicConsume(Constants.RabbitConstants.MessageService2Im, false,
                        new DefaultConsumer(channel) {
                            @Override
                            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                                String msgStr = new String(body);
                                log.info("接收到服务端发来的消息：{}", msgStr);
                            }
                        }
                    );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void init(){
        startReciverMessage();
    }


}
