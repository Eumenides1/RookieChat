package com.rookie.im.core.utils.redis;

import com.rookie.im.core.config.AppConfig;
import lombok.Getter;
import org.redisson.api.RedissonClient;

/**
 * @author eumenides
 * @description
 * @date 2024/4/28
 */
public class RedisManager {

    @Getter
    private static volatile RedissonClient redissonClient;
    private static final Object lock = new Object();

    public static void init(AppConfig config) {
        if (redissonClient == null) {
            synchronized (lock) {
                if (redissonClient == null) {
                    SingleClientStrategy singleClientStrategy = new SingleClientStrategy();
                    redissonClient = singleClientStrategy.getRedissonClient(config.getRookie().getRedis());
                    UserLoginListener userLoginMessageListener = new UserLoginListener(config.getRookie().getLoginModel());
                    userLoginMessageListener.listenerUserLogin();
                }
            }
        }
    }

    public static RedissonClient getRedissonClient() {
        if (redissonClient == null) {
            throw new IllegalStateException("Redisson client is not initialized yet. Please call init() first.");
        }
        return redissonClient;
    }
}
