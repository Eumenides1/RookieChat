package com.rookie.im.core.utils.redis;

import com.rookie.im.core.config.AppConfig;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

/**
 * @author eumenides
 * @description
 * @date 2024/4/28
 */
public class SingleClientStrategy {
    public RedissonClient getRedissonClient(AppConfig.RedisConfig redisConfig) {
        Config config = new Config();
        String node = redisConfig.getSingle().getAddress();
        node = node.startsWith("redis://") ? node : "redis://" + node;
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(node)
                .setDatabase(redisConfig.getDatabase())
                .setTimeout(redisConfig.getTimeout())
                .setConnectionMinimumIdleSize(redisConfig.getPoolMinIdle())
                .setConnectTimeout(redisConfig.getPoolConnTimeout())
                .setConnectionPoolSize(redisConfig.getPoolSize());
        if (StringUtils.isNotBlank(redisConfig.getPassword())) {
            serverConfig.setPassword(redisConfig.getPassword());
        }
        StringCodec stringCodec = new StringCodec();
        config.setCodec(stringCodec);
        return Redisson.create(config);
    }
}