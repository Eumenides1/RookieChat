package com.rookie.im.core.mq.factory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rookie.im.core.config.AppConfig;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

/**
 * @author eumenides
 * @description
 * @date 2024/5/8
 */
public class MqFactory {

    private static final Object lock = new Object();
    private static ConnectionFactory factory = null;
    private static Connection connection = null;
    private static ConcurrentHashMap<String, Channel> channelHashMap = new ConcurrentHashMap<>();

    public static Channel getChannel(String channelName) {
        return channelHashMap.computeIfAbsent(channelName, k -> {
            try {
                return getConnection().createChannel();
            } catch (IOException e) {
                throw new RuntimeException("Failed to create a channel for " + k, e);
            }
        });
    }

    private static Connection getConnection() {
        synchronized (lock) {
            if (connection == null) {
                try {
                    connection = factory.newConnection();
                } catch (IOException | TimeoutException e) {
                    throw new RuntimeException("Failed to create a connection", e);
                }
            }
            return connection;
        }
    }
    public static void init(AppConfig.Rabbitmq rabbitmq) {
        synchronized (lock) {
            if (factory == null) {
                factory = new ConnectionFactory();
                factory.setHost(rabbitmq.getHost());
                factory.setPort(rabbitmq.getPort());
                factory.setUsername(rabbitmq.getUserName());
                factory.setPassword(rabbitmq.getPassword());
                factory.setVirtualHost(rabbitmq.getVirtualHost());
            }
        }
    }

    public static void close() {
        channelHashMap.forEach((k, v) -> {
            try {
                if (v.isOpen()) {
                    v.close();
                }
            } catch (IOException | TimeoutException e) {
                // Log or handle close exceptions
            }
        });
        try {
            if (connection != null && connection.isOpen()) {
                connection.close();
            }
        } catch (IOException e) {
            // Log or handle close exceptions
        }
    }

}
