## mybatis  

[优秀的学习mybatis博客？](https://www.cnblogs.com/dongying/tag/Mybatis%E6%B7%B1%E5%85%A5%E6%B5%85%E5%87%BA%E7%B3%BB%E5%88%97/)  


#### mybatis核心类


    SqlSessionFactoryBuilder
    SqlSessionFactory --> DefaultSqlSessionFactory
    SqlSession() --> DefaultSqlSession
    Executor --> SimpleExecutor
    MappedStatement
    
    //配置类
    Configuration --> jdbcType excuteType plugins proxyFactory mapperRegistry
    
#### mybatis执行流程
    1、解析xml 创建datasource transactionManager 
    2、解析mapper.xml 解析成mappedStatement
    3、openSqlSession 每个sqlSession拿到excutor（包含datasource openConnection流程）和configuration
    4、举例SimpleExecutor.
    5、根据statementId【namespace.selectID】拿到MappedStatement
    6、根据传入参数获取BoundSql 【这里完成sql 以及参数的封装】
    7、忽略一级缓存的代码实际就是session+sql+各种执行参数的行程的一个key
    8、executor构建StatementHandler 【RoutingStatementHandler】 实际根据sql的标签来选择StatementHandler
    9、executor通过transaction拿到Connection 并且通过proxy代理返回一个ConnectionHolder
    10、connection.prepareStatment
    11、statment.execute
    12、通过statment.getResultSet 以及metData构建返回的字段名称和类型以及jdbc的类型
    13、通过resultMap来创建返回对象 并且将resultSet中的值一个个的set进去
    14、返回给客户端


[你真的会用mybatis缓存吗？](https://www.jianshu.com/p/c553169c5921)  


##### 一级缓存  对应到开发中，在一个事务中，加入多次查询相同的sql，注意会走缓存
    默认开启的Session级别，默认是同一个sqlSession查询同样的sql 会走缓存
    可以手动关闭   <setting name="localCacheScope" value="STATEMENT"/>

##### 二级缓存 分布式环境使用大忌！！！ 缓存本地的，这类缓存 使用redis替代
    
    二级缓存针对多个sqlSession也会公用缓存，必须第一个sqlSession.commit后 才会生效
    
    <!--二级缓存关闭  总开关-->
    <setting name="cacheEnabled" value="false"/>
    
    打开需要xml配置总开关开启 且mapper.xml配置   <cache/> 【必须两者都开了，才会生效】

