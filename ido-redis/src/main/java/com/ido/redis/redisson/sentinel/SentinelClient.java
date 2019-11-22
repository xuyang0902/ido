package com.ido.redis.redisson.sentinel;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author xu.qiang
 * @date 19/11/22
 */
public class SentinelClient {

    public static void main(String[] args) {

        Config config = new Config();
        config.useMasterSlaveServers()
                .setMasterAddress("192.168.1.121:7379")
                .addSlaveAddress("192.168.1.122:7379");
        RedissonClient redissonClient = Redisson.create(config);

    }
}
