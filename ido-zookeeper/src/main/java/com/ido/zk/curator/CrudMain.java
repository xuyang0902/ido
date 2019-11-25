package com.ido.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;


/**
 *


 基于zookeeper的zk分布式锁实现

 -------------------------------------------------------------------------
 注意curator的版本和服务器zk的安装的版本
 The are currently two released versions of Curator, 2.x.x and 3.x.x:

 Curator 2.x.x - compatible with both ZooKeeper 3.4.x and ZooKeeper 3.5.x
 Curator 3.x.x - compatible only with ZooKeeper 3.5.x and includes support
 for new features such as dynamic reconfiguration, etc.
 -------------------------------------------------------------------------



 * @author xu.qiang
 * @date 19/11/25
 */
public class CrudMain {


    public static void main(String[] args) throws Exception {




        //1 重试机制 50ms重试一次  最大重试3次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(50, 3);

        //2 通过工厂创建连接
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.1.121:2181")
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(100)
                .namespace("tbj")
                .build();
        //3 开启连接
        curatorFramework.start();


        //创建永久节点
        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/trade","data".getBytes());

        //创建临时节点
        curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/trade_e","data_e".getBytes());

        //更新数据
        curatorFramework.setData().forPath("/trade","data_update".getBytes());


        //查询数据
        byte[] bytes = curatorFramework.getData().forPath("/trade");
        System.out.println(new String(bytes));

        //删除节点
        curatorFramework.delete().forPath("/trade");


        Thread.sleep(Integer.MAX_VALUE);


    }


}
