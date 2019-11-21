##### xxl-job

##### elastic-job
[elasticjob](http://elasticjob.io/index_zh.html)


##### legends
        引入了quartz来触发任务
        
        1、页面配置  group name clazz ip cron 参数等信息  入库
        2、异步线程load db数据，创建jobDetail schedule根据cron触发
        3、执行任务前 数据入库，select for update 然后 legneds-server发送http到指定client 更新db状态这条数据不再触发 
           60秒内只能提交一次上来
        4、client拿到请求参数执行响应job
        5、拿执行结果使用长轮训方式
        
        legends-server 存在单点问题
        
        
        2018年卢云改造legends  
        
        legends-server A
        legends-server B
        legends-server C
        server可以多台部署，引入zk临时节点做选举。把服务端做到高可用了，但是引来了新的问题
        服务写到zk上
        
        A是master，有一个任务已经触发了，但是A网络闪断，B抢到锁了，作为master 这个时刻集群中有两个master 如何处理？
        
        目前是怎么解决的？
            1、db的UK键，来尽量避免两个master的问题
                    但是因为分布式环境时钟不一致的问题，所以尽量避免短时间重复执行的job
            2、客户端任务自身分布式锁确保下
          
    