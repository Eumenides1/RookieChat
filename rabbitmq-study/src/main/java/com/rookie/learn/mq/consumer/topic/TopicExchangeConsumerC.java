package com.rookie.learn.mq.consumer.topic;

import com.rookie.learn.mq.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author eumenides
 * @description
 * @date 2024/5/6
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(RabbitMQConfig.TOPIC_EXCHANGE_QUEUE_C))
public class TopicExchangeConsumerC {

    @RabbitHandler
    public void process(Map<String, Object> map) {
        System.out.println("队列[" + RabbitMQConfig.TOPIC_EXCHANGE_QUEUE_C + "]收到消息：" + map.toString());
    }
}