#####   Redis

###### 环境搭建？ 

###### 简单介绍
        高性能缓存数据库
        KV存储
        单线程执行（6.0以后有多线程版本，但仅仅是解析收到的网络包是多线程的，执行命令还是单线程的）
        速度快 10万/s 读写性能
        能持久化 RDB 和 AOF两种方式
        主从复制 （不保证数据一致性）
        高可用（哨兵机制，3.0后可以集群部署）
        客户端语言多，java php c c++ node.js etc  太多了
        默认总共有16个db。一般我们都用0那个 其他不用 可能很多人都不知道哦 哈哈哈哈
        
###### 场景
        缓存
        计数器
        消息队列 ？？ ->虽然redis有这个功能，但是在互联网环境不会用这个，专业的事情交给专业的人 用消息中间件 比如RocketMQ
    
## 安装    
       默认端口6379。安装还是蛮简单的不整理了
       
###### 数据类型
        字符串  key-value的映射
            set 
            setnx    
            get 
            //减少网络包传输
            mset
            mget 
        hash   key--（filed-value）的映射关系
            hset
            hget
            hlen
            hmset
            hmget
            hexists
            hkeys 
        list  数组列表（工作中用的很少）
            lpush rpush linsert
            lrange lindex llen
            lset 
            lpop rpop lrem ltrim
        set   不可重复 （用的也很少）
            sadd
            smember
            srem
            
        有序集合 （排行榜可以用）
            zadd 
            zcard
            zscore
            zrange
            zrem
            
#####其他常用键值对管理
        rname
        dbsize
        expire
        pexpire 
        ttl
        pttl
        keys 
        scan
        sscan
        hscan
        zscan
        select 0 //选择第几个数据库 一共16个 0-15
        flushdb  //只清空当前数据库
        flushAll  //清空所有数据库
          
          
####### redis/bin
          ./redis-cli -h 192.168.1.1 -p 6379
          ./redis-server ./redis-conf &
          redis-benchmark -h 192.168.1.111 -c 100 -n 10000//100个客户端一共执行10000次
        
###### pipiline
        mset，mget有啥区别？
        pipiline更灵活，客户端相传啥 传啥，非原子性的，一次pipiline命令也不建议过多
###### redis事务

        muti//开启事务
        exec//结束事务
        discard//停止事务
        语法错误  事务无法正常结束
        语法正确 执行错误，可以正常结束事务
        
        
###### LUA脚本
         EVAL命令
         执行lua脚本，保证脚本中的命令在是原子执行的
         
###### 发布订阅
        publish channel:test "hello world"
        subscrible channel:test
        
        pubsub numsub channel:test //    channel:test     
        unsubscribe channel:test

###### Redis 持久化
        RDB  有.rdb文件恢复快， 
            save 阻塞redis 知道rbd全部完成
            bgsave 子线程去持久化
            
            优点：1，压缩后的二进制文，适用于备份、全量复制，用于灾难恢复
                 2，加载RDB恢复数据远快于AOF方式
            缺点：1，无法做到实时持久化，每次都要创建子进程，频繁操作成本过高
                 2，保存后的二进制文件，存在老版本不兼容新版本rdb文件
            
            
        
        AOF 保存的是执行命令，恢复慢 可以实时持久化
           redis.conf    appendonly yes (no)
           
           如何从AOF恢复？
           1. 设置appendonly yes；
           2. 将appendonly.aof放到dir参数指定的目录；
           3. 启动Redis，Redis会自动加载appendonly.aof文件。
           
           redis重启时恢复加载AOF与RDB顺序及流程：
           1.当AOF和RDB文件同时存在时，优先加载AOF
           2.若关闭了AOF，加载RDB文件
           3.加载AOF/RDB成功，redis重启成功
           4.AOF/RDB存在错误，redis启动失败并打印错误信息
           
        
###### 主从复制
        slaveof 192.168.1.111 6379  从节点配置信息
        
###### Redis 哨兵
        主备模式 主挂了，虽然有slave 但是我们应用程序不能直接切过去 
        
        哨兵机制，当主节点发生故障时，自动完成故障转移并通知应用程序实现高可用
        
        三台哨
        哨兵做以下三件事情
        1、每隔10秒向主节点发送info 获取 master 和slave的信息
        2、每隔2秒 使用发布订阅方式 相互发现 哨兵们
        3、每隔和master进行ping pong操作
        
        假如一半的哨兵认为master挂了
        选举出来一个哨兵 来做故障转移
        故障转移完成后，把master的节点的信息都同步到各个哨兵的文件里面
        哨兵集群对外提供服务。。客户端直接连接哨兵的ip + 端口
        
        
###### Redis3.0集群模式
        单机瓶颈
        hashsolt的概念
        rediscluster采用哈希分区   虚拟槽分区（solt）
        key根据hash函数 CRC16(key)& 16383 映射到0-16383   2的14次方  16384个solt
        
        为什么用虚拟槽？
        1.解耦数据与节点的关系分布式存储
        redisCluster的缺点
        1、键的批量操作支持有限，mset mget 这些key可能分布在不同的机器上
        2、键事务支持有限，当多个key分布在不同节点时是无法使用事务的，同一个节点是支持事务的
        3、键是分区的最小粒度，不能讲一个很大的键值对映射到不同的节点？？？   这个我觉得问题没那么大
        4、不支持多数据库 只有0
        5、复制结构只支持单层结构 不支持树形结构
        
        
        
        //访问集群模式的 需要加上 -c 会根据solt进行转发
        redis-cli -h 192.168.1.1 -p 6379 -c
        
        cluster info 查看当前集群状态
        cluster nodes 节点信息
        
        检查集群状态
        redis-trib.rb check 192.168.1.111:6379
        
        
        集群搭建。。
        自动安装模式
        1、下载ruby
        2、依次启动6个节点
        port 6379
        cluster-enabled yes
        cluster-node-timeout 15000
        cluster-config-file /usrlocalbin/cluster/data/nodes-6379.conf
        //创建集群 有一个副本
        3、./redis-trib.rb create --replicas 1 192.168.1.111:6379 
                                            192.168.1.111:6380
                                            192.168.1.111:6381 
                                            192.168.1.111:6389 
                                            192.168.1.111:6390 
                                            192.168.1.111:6391
                                
        集群扩容
        不展开
        
###### API使用   
        见code
        redis的JAVA客户端有很多
        常见的有 
        jedis       A blazingly small and sane redis java client
        letture     Advanced Redis client for thread-safe sync, async, and reactive usage. Supports Cluster, Sentinel, Pipelining, and codecs.
        Redisson   distributed and scalable Java data structures on top of Redis server
        当然还有其他好多
        
        
####### 过期策略
        定期删除
            默认每隔100ms取过期的key（随机抽取一些key） 检查是否过期 过期的删除
        惰性删除
            获取某个key的时候，redis会检查这个key是否删除，如果过期的，就直接删除，不会返回任何东西
        
            
        内存淘汰 主动清除（当前已用内存超过maxmemory限定时）
        volatile-lru : 只对设置了过期时间的key进行LRU （默认值） （对已经设置了过期时间的数据结构，挑选最少使用的数据淘汰）
        allkeys-lru: 删除LRU算法的key（从全部的数据结构，挑选最少使用的数据淘汰）
        volatile-random : 随机删除即将过期的key（对已经设置了过期时间的数据，采用随机删除的数据淘汰）
        allkeys-random : 随机删除（对所有的数据，采用随机删除的数据淘汰）
        volatile-ttl ：删除即将过期的（只对设置了过期时间的数据，采用挑选将要过期的数据淘汰）
        noeviction ：永不过期（这个清理过程是阻塞的）
        