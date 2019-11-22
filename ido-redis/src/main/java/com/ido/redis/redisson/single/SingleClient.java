package com.ido.redis.redisson.single;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author xu.qiang
 * @date 19/11/22
 */
public class SingleClient {

    public static void main(String[] args) {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.1.121:6379");

        RedissonClient redissonClient = Redisson.create(config);

    }
}
