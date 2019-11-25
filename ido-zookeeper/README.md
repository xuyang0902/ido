####### Zookeeper
####### 安装不掩饰了，

        是一个文件系统
        能存数据
        CP模式
        选举
        基数节点 
        有发布订阅
        能用临时节点做分布式锁
        2181 对客户端端口
        2888 通信端口
        3888 选举端口
        
        ZAB协议
            选举 ，过半机制
            广播
            
            leader写 同步到半数以上机器才算成功 数据一致性
            follower 同步数据 可以读  （客户单可能脏读）
            observer  只读节点
            
            2pc
            预提交 写事务日志 发给follower拿ack（follower也是写事务日志） 
            提交 提交数据 DataTree修改数据
        
        
        
        

        zkclient比较简单 就  用一下curator
        
        -------------------------------------------------------------------------
        注意curator的版本和服务器zk的安装的版本
        The are currently two released versions of Curator, 2.x.x and 3.x.x:
        
        Curator 2.x.x - compatible with both ZooKeeper 3.4.x and ZooKeeper 3.5.x
        Curator 3.x.x - compatible only with ZooKeeper 3.5.x and includes support
        for new features such as dynamic reconfiguration, etc.