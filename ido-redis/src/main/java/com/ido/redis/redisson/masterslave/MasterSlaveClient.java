package com.ido.redis.redisson.masterslave;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author xu.qiang
 * @date 19/11/22
 */
public class MasterSlaveClient {

    public static void main(String[] args) {

        Config config = new Config();
        config.useSentinelServers()
                .setMasterName("mymaster")
                .addSentinelAddress("192.168.1.121:7379")
                .addSentinelAddress("192.168.1.121:7380")
                .addSentinelAddress("192.168.1.121:7381");

        RedissonClient redissonClient = Redisson.create(config);

    }
}
