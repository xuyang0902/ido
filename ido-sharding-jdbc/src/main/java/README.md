##### sharding-jdbc

        已经捐献给apache，包含sharding-jdbc sharding-proxy  sharding-sidecar
                
[shrding-jdbc](https://github.com/apache/incubator-shardingsphere)  
[中文官网](https://shardingsphere.apache.org/document/current/cn/overview/)
        
        
[官方使用教程](https://github.com/apache/incubator-shardingsphere-example)        
        
        
##### 我们关心sharding-jdbc 
        SQL
            逻辑表  
            真实表  库里面的真实表
            数据节点  datasource配置
            绑定表 两张表的分片规则一样的
            广播表 所有数据源都要存，且数据都保证一致 数据量不大且需要经常关联查的场景
        分片
            精确分片
            范围分片
            复合分片
            Hint分片
        配置
            数据源配置
            数据源分片规则配置
            表分片规则配置
            
            绑定表配置
            广播表配置
        
        
        
        内核
            解析引擎（词法解析 + 语法解析）-->路由引擎--> 改写引擎-->执行引擎-->归并引擎
        
        内存限制模式（不对连接数做限制 多线程去查询一个库） 和 链接限制模式（单线程查询库） sharding前期是让用户自己选的，后期根据sql自己优化
        根据maxConnectionSizePerQuery来优化，如果在该数据库上执行的sql数量/maxConnectionSizePerQuery > 1了 就用链接模式 否则直接用内存模式
        
        
        内存归并 性能高 消耗内存
        流式归并 尽量用这个 省内存
        
        
##### sharding-proxy 应该类似mycat的思想

##### sharding-sidecar servicemesh的db网格也是一个轻量的代理层，TODO      