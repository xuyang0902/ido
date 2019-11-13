###Rocketmq 看着这么多优秀的app 自己稍微总结一下吧

##### 四大角色 
    NameServer 保存着Broker，Producer，Consumer节点的信息 Topic的信息 消费组对应消费位置的信息等等
    Broker  存储CommitLog的地方  分发Topic构建Consumequeue 【定时和nameserver上报心跳 以及topic信息】
    Producer  发送消息，询问nameserver有几个broker一共有多少个topic的队列，轮训发 【定时上报】
    Consumer  消费消息，看一共有多少个消费队列，客户端负载均衡分配队列消费消息  【定时上报】
     
     
##### 个人总结
     消息存储
     消息发送
     消息消费
     nameserver

##### NameServer
	org.apache.rocketmq.namesrv.routeinfo.RouteInfoManager 数据都在内存，用于保存topic路由信息，
	topicQueueTable【topic队列表】 topicName-->QueueData【brokerName，读队列，写队列个数，等信息】
    brokerAddrTable【broker表】  brokerName-->BrokerData【clusterName，brokerName，brokerId:brokerAddr】
    clusterAddrTable[broker集群表]  clusterName-->Set<brokerName>
    brokerLiveTable【broker存活表】  brokerAddr-->channel等信息

##### Broker 消息存储，topic信息存储 消费端 生产端信息存储
	DefaultMessageStore
	CommitLog（消息存储的地方）
	MappedFileQueue（映射文件集合）
	MappedFile（mmap映射文件）
	ConsumerQueue（消费队列）

	org.apache.rocketmq.store.CommitLog.CommitRealTimeService#【没有用内存池的话，统计一下wrote的位置，用内存池的话 把内存池中的数据写到mmap映射内存】
	org.apache.rocketmq.store.CommitLog.GroupCommitService#同步刷盘【根据写请求来刷盘】
	org.apache.rocketmq.store.CommitLog.FlushRealTimeService#异步刷盘【直接异步刷盘】
	org.apache.rocketmq.store.DefaultMessageStore.ReputMessageService##【异步线程，把commitLog里面的数据分发到consumeQueue和indexFile】
	org.apache.rocketmq.store.DefaultMessageStore.FlushConsumeQueueService#【把consumequeue中的消息刷盘】

##### 文件恢复
    commitlog刷盘，consumequeue和index没有构建完成？重新构建
    consumequeue和index输盘成功了 commitlog没有刷盘?脏数据清理
    所有文件大小都是固定的，要确定最近一个文件的刷盘的位置?解析文件确定刷盘位置
    
##### 文件删除
    非当前文件再一定时间内没有被再次更新，则认为是过期文件，可以删除 默认72小时
    磁盘空间不足，也会触发删除
    官网提供接口删除 excuteDeleteFileManuly
    
    DefaultMessageStor#addScheduleTask ###cleanCommitLog cleanComsumequeue
##### HA
       1、元数据的复制  slave rpc-->master 拿topic ，消费进度，订阅组，延迟offset
       2、commitLog的复制   
            slave接收到到master发过来的commitlog消息后  上报自己同步的位置给master
            master有两个线程  读线程读取slave的同步位置  写线程，就是判断当前的消息位置是否需要发给slave 需要的话发过去
            
            同步复制，org.apache.rocketmq.store.ha.HAService.GroupTransferService  查看slave上报的同步位置是否比这个当前消息>=了
            
##### DLedger【4.5之后支持】 使用raft协议的文本同步  
       
#####  为什么线上不建议开启自动创建topic？
        集群有2个broker
        开启自动创建topic
         produer发一条消息，会根据默认的topic规则，选中一台broker发送消息
        只有一台broker创建了这个topic
        过了一段时间 这台broker把topic同步给nameserver了
        那么另外一台broker就被浪费掉了

#####  消息发送
        1、producer问nameserver 关于这个topic的队列信息【如果没有会用默认的topic配置信息】
        2、轮训找到broker的某个队列id
        3、组装message信息发到对应的broker上去

#####  消费消费

        broker消息消费的机制
        ----broker处理client的拉消息请求-------
        1.请求参数 topic queueid offset maxNums 
        2.根据consumequeue解析出来从commitlog中获取数据GetMessageResult 返回出去
        3.master 堆积的消息超过内存的百分之40 建议从slave拉
        
        
        -------broker通知clinet拉消息的机制---------
        1.客户端如果想broker拉消息没拉到，会在broker有一个缓存 topic@queueid拉请求的缓存
        2.处理拉请求的机制
          broker有一个线程PullRequestHoldService 每隔5秒会检查持有的请求
          broker有commitlog分发到consumequeue的时候也会检查这个请求
        3.检查请求做了什么？ topic queueId broker生产的最大的consumequeue的逻辑offset
          3.1 每次检查 先clone一份请求然后 清理缓存【防止一直在处理这个请求】
          3.2 如果broker的consumequeue的逻辑位置>请求的的offset 那么broker通知客户端来拉数据
          3.3 如果请求挂起时间 已经超时了也通知客户端来拉数据
          
        客户端消息消费的逻辑
        1.入口org.apache.rocketmq.client.impl.consumer.RebalanceService
        2.每隔20秒把客户端的consumerTable遍历一遍，每个topic都执行下 this.rebalanceByTopic(topic, isOrder);
        3.广播模式不分析了 分析集群模式
        4.获取topic有多少ConsumeQueue，这个consumerGroup存在的机器 ids
        5.根据AllocateMessageQueueAveragely策略 计算出当前的id应该负责消费哪个consumequeue
        6.会往processQueueTable 放入本机需要处理的topic队列信息
        7.立马执行拉消息请求 往org.apache.rocketmq.client.impl.consumer.PullMessageService放pullRequest
        8.根据请求队列发拉请求给broker

#####  消费重试
        1、消费失败后，返回return_later，client把消息回发给broker
        2、brocker把这个消息 重新组装 替换 topic 变成延迟队列的topic 队列id根据重试次数的一个行程的一个算法 
        3、延迟消息定时任务把消息拉出来解析 重新把消息 topic替换成原来的topic 写到commitlog

#####  事务消息
        1、prepare消息 不会进consumequeue
        2、有一个线程会轮训prepare消息进行回调[猜测 因为刚工作中分布式事务不用mq做 所以没仔细看过]
        3、回调到client后 client发送commit消息
       
##### 零copy
        rocketmq使用mmap 做内存映射 
        mmap可以将一段用户空间内存映射到内存空间，当映射成功后，用户对这块内存区域的修改，可以直接反应到内核空间，同样的
        内核空间对这块区域的修改也能直接反映到用户空间，正因为这种映射关系，就不需要在用户态和内核态之间copy，提升了速度
        
        
        mlock 锁定这块区域，尽量不要把这块区域的内存换到交换区
        msdvise 建议把整块区域的数据都读到映射区域，减少缺页中断
 
##### 启动调试
        所有的配置参考源码目录 distribution/conf

        /Users/tbj/usr/local/tmp/rocketmq/ 目录下把conf文件copy过来
        #启动namesrv
        #设置ROCKETMQ_HOM=/Users/tbj/usr/local/tmp/rocketmq/ 
        org.apache.rocketmq.namesrv.NamesrvStartup 
        #启动broker
        #设置ROCKETMQ_HOM=/Users/tbj/usr/local/tmp/rocketmq/ 
        #启动参数  -c /Users/tbj/usr/local/tmp/rocketmq/conf/broker.conf
        org.apache.rocketmq.broker.BrokerStartup  
        
        broker.conf
        brokerClusterName = DefaultCluster
        brokerName = broker-a
        brokerId = 0
        namesrvAddr = 127.0.0.1:9876
        deleteWhen = 04
        fileReservedTime = 48
        brokerRole = SYNC_MASTER
        flushDiskType = ASYNC_FLUSH
        autoCreateTopicEnable = false
        
        storePathRootDir=/Users/tbj/usr/local/tmp/rocketmq/store
        storePathCommitLog=/Users/tbj/usr/local/tmp/rocketmq/store/commitlog
        storePathConsumeQueue=/Users/tbj/usr/local/tmp/rocketmq/store/consumequeue
        storePathindex=/Users/tbj/usr/local/tmp/rocketmq/store/index
        storeCheckpoint=/Users/tbj/usr/local/tmp/rocketmq/store/checkpoint
        abortFile=/Users/tbj/usr/local/tmp/rocketmq/store/abort
        