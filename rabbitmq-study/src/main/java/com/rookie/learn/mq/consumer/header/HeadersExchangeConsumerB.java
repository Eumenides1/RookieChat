package com.rookie.learn.mq.consumer.header;

import com.rookie.learn.mq.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author eumenides
 * @description
 * @date 2024/5/6
 */
@Component
public class HeadersExchangeConsumerB {
    @RabbitListener(queuesToDeclare = @Queue(RabbitMQConfig.HEADERS_EXCHANGE_QUEUE_B))
    public void process(Message message) throws Exception {
        MessageProperties messageProperties = message.getMessageProperties();
        String contentType = messageProperties.getContentType();
        System.out.println("队列[" + RabbitMQConfig.HEADERS_EXCHANGE_QUEUE_B + "]收到消息：" + new String(message.getBody(), contentType));
    }
}
