package com.rookie.learn.mq.consumer;

import com.rookie.learn.mq.config.RabbitMQConfig;
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
@RabbitListener(queues = RabbitMQConfig.RABBITMQ_DEMO_TOPIC)
public class ConsumerService {
    @RabbitHandler
    public void process(Map map) {
        System.out.println("收到消息：" + map.toString());
    }

}
