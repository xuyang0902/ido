package com.ido.redis.redisson.cluster;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.cluster.ClusterNodeInfo;
import org.redisson.config.Config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author xu.qiang
 * @date 19/11/22
 */
public class ClusterClient {

    private volatile RedissonClient redissonClient;

    public RedissonClient getClient() {

        if (redissonClient == null) {
            synchronized (this) {
                if (redissonClient != null) {
                    return redissonClient;
                }
                Config config = new Config();
                //集群模式
                config.useClusterServers()
                        .setScanInterval(2000)//每隔2秒扫描一次集群的状态
                        .addNodeAddress("redis://192.168.1.121:7000")
                        .addNodeAddress("redis://192.168.1.121:7001")
                        .addNodeAddress("redis://192.168.1.121:7002")
                        .addNodeAddress("redis://192.168.1.121:7003")
                        .addNodeAddress("redis://192.168.1.121:7004")
                        .addNodeAddress("redis://192.168.1.121:7005")
                        .addNodeAddress("redis://192.168.1.121:7006");

                redissonClient = Redisson.create(config);
                return redissonClient;
            }
        }

        return redissonClient;
    }

    public void shutDown() {
        if (redissonClient != null) {
            redissonClient.shutdown();
        }
    }


    public static void main(String[] args) {

        ClusterClient clusterClient = new ClusterClient();

        RedissonClient redisson = clusterClient.getClient();


        /**
         * 真的是反人类的设计啊，，，，不懂为什么redisson这么设计，感觉api比jedis 难用多了
         *
         * 当然redissson有分布式锁的实现，这个比jedis强大的多
         */

        //key Value存储
        System.out.println("============kv===========");
        RBucket<String> name = redisson.getBucket("name");
        name.set("updateName");
        name.set("up", 100, TimeUnit.MINUTES);

        System.out.println("name -->" + redisson.getBucket("name").get());

        //map
        System.out.println("============map===========");
        RMap<String, String> user = redisson.getMap("user");
        user.put("name", "yuren");
        user.put("age", "18");
        System.out.println("user:name -->" + redisson.getMap("user").get("name"));
        System.out.println("user:age -->" + redisson.getMap("user").get("age"));


        //list
        System.out.println("============list===========");
        RList<String> list = redisson.getList("list");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");

        List<Object> list1 = redisson.getList("list").readAll();
        for (Object o : list1) {
            System.out.println("list -->" + o);
        }


        //set
        System.out.println("============set===========");
        RSet<Object> set = redisson.getSet("set");
        set.add("1");
        set.add("1");
        set.add("1");
        set.add("2");

        Iterator<Object> set1 = redisson.getSet("set").iterator();
        while (set1.hasNext()) {
            System.out.println("set -->" + set1.next());
        }

        //sortset
        System.out.println("============sortSet===========");

        RSortedSet<String> sortSet = redisson.getSortedSet("sortSet");
        sortSet.add("a");
        sortSet.add("h");
        sortSet.add("b");
        sortSet.add("h");

        Iterator<Object> iterator = redisson.getSortedSet("sortSet").iterator();
        while (iterator.hasNext()) {
            System.out.println("sortSet -->" + iterator.next());
        }

        System.out.println("============分布式锁===========");

        RLock lock = redisson.getLock("key1");

        try {
            boolean tryLock = lock.tryLock();
            if (tryLock) {

            }
        } finally {
            lock.unlock();
        }


    }


}
