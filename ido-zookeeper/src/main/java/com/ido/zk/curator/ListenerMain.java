package com.ido.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xu.qiang
 * @date 19/11/25
 */
public class ListenerMain {

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


        //子节点
        List<String> list = curatorFramework.getChildren().forPath("/");
        for (String s : list) {
            System.out.println("根节点路径: /tbj/" + s);
        }


        //监听节点
        NodeCache nodeCache = new NodeCache(curatorFramework,"/",false);
        nodeCache.start();
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                byte[] data = nodeCache.getCurrentData().getData();

                System.out.println("==>tbj根节点数据变化  dataUpdate:" + new String(data));

            }
        });



        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework,"/",false);
        pathChildrenCache.start(PathChildrenCache.StartMode.NORMAL);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {

                PathChildrenCacheEvent.Type type = event.getType();

                if(type == PathChildrenCacheEvent.Type.CHILD_ADDED){
                    System.out.println(">>  新增节点");
                }else if(type == PathChildrenCacheEvent.Type.CHILD_UPDATED){
                    System.out.println(">>  孩子节点更新");
                }else if(type == PathChildrenCacheEvent.Type.CHILD_REMOVED){
                    System.out.println(">>  孩子被移除");
                }else if(type == PathChildrenCacheEvent.Type.CONNECTION_RECONNECTED){
                    System.out.println(">>  重连");
                }else if(type == PathChildrenCacheEvent.Type.CONNECTION_LOST){
                    System.out.println(">>  连接close");
                }else if(type == PathChildrenCacheEvent.Type.INITIALIZED){
                    System.out.println(">>  链接初始化");
                }else if(type == PathChildrenCacheEvent.Type.CONNECTION_SUSPENDED){
                    System.out.println(">>  链接挂起");
                }

            }
        });



        Thread.sleep(Integer.MAX_VALUE);




    }
}
