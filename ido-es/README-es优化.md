######  脑裂优化
        网络 GC
        3台master  不存数据.
        node.master true
        node.data false
        
        数据节点配置
        discovery.zen.ping.multicast.enabled:false
        discovery.zen.ping.unicast.host:["master01","master02","master03"]
        discovery.zen.ping.timeout:3(默认三秒)
        discovery.zen.minimum_msater_ndes(默认1)
        
        
######  JVM
        jvm.options
        -Xmx8G
        -Xms8G
        
        
######  锁定物理内存
######  分片 副本
        分片过多 打开索引文件太多
        分片太少，太集中
        
        建议数据总量/20G 为分片数
        
        增加副本，可以提升搜索能力
        副本过多，同步压力
        默认1 
        
######  合并索引
        segement 
######  关闭索引
        增对不使用的index close 减少内存使用
        close只会占用磁盘 不会占用内存
        
######  删除清除文档
        
######  设置索引 _all
        
######  设置索引 _source
######  版本一致
