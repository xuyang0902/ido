## mybatis  


[你真的会用mybatis缓存吗？](https://www.jianshu.com/p/c553169c5921)  


###一级缓存
     session级别的缓存，同一个sql多次查询返回结果都为第一次查询的结构（key=sqlsession.hachcode+statementd+sql+参数)
     当前session有C U D操作都会删除缓存
     
     --  一级缓存默认SESSION  可以设置为STATEMENT（可以理解为只对这个STATEMENT有效）
     <setting name="localCacheScope" value="SESSION"/>
     
     总结：
     Mybatis一级缓存的生命周期和SqlSession一致。
     Mybatis的缓存是一个粗粒度的缓存，没有更新缓存和缓存过期的概念，同时只是使用了默认的hashmap，也没有做容量上的限定。
     Mybatis的一级缓存最大范围是SqlSession内部，有多个SqlSession或者分布式的环境下，有操作数据库写的话，会引起脏数据，
     建议是把一级缓存的默认级别设定为Statement，即不使用一级缓存。
     
     
### 二级缓存  分布式环境下的大坑 禁用
     不同sqlsession之间共享查询结果  
     1、  在配置文件SqlMapConfig.xml中加入以下内容（开启二级缓存总开关）：cacheEnabled设置为 true
     2、  每个mapper里面的每个sql可以配置useCache 来开启和关闭二级缓存。
     
     cache标签用于声明这个namespace使用二级缓存，并且可以自定义配置。
     
     type:   cache使用的类型，默认是PerpetualCache，这在一级缓存中提到过。
     eviction:  定义回收的策略，常见的有FIFO，LRU。
     flushInterval:  配置一定时间自动刷新缓存，单位是毫秒
     size:   最多缓存对象的个数
     readOnly:   是否只读，若配置可读写，则需要对应的实体类能够序列化。
     blocking:    若缓存中找不到对应的key，是否会一直blocking，直到有对应的数据进入缓存。
     
     总结
     Mybatis的二级缓存相对于一级缓存来说，实现了SqlSession之间缓存数据的共享，同时粒度更加的细，能够到Mapper级别，
     通过Cache接口实现类不同的组合，对Cache的可控性也更强。
     Mybatis在多表查询时，极大可能会出现脏数据，有设计上的缺陷，安全使用的条件比较苛刻。
     在分布式环境下，由于默认的Mybatis Cache实现都是基于本地的，分布式环境下必然会出现读取到脏数据，需要使用集中式缓存将Mybatis的Cache接口实现，
     有一定的开发成本，不如直接用Redis，Memcache实现业务上的缓存就好了。
     