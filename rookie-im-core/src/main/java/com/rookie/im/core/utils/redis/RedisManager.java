package com.rookie.im.core.utils.redis;

import com.rookie.im.core.config.AppConfig;
import io.netty.bootstrap.BootstrapConfig;
import lombok.Getter;
import org.redisson.api.RedissonClient;

/**
 * @author eumenides
 * @description
 * @date 2024/4/28
 */
public class RedisManager {

    @Getter
    private static RedissonClient redissonClient;

    private static Integer loginModel;

    public static void init (AppConfig config) {
        SingleClientStrategy singleClientStrategy = new SingleClientStrategy();
        redissonClient = singleClientStrategy.getRedissonClient(config.getRookie().getRedis());
    }

}
