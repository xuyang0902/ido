## Spring集成mybatis后代码原理怎么执行的？



### 

        
    <!-- myBatis文件 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="configLocation" value="classpath:/mybatis/mybatis-config-spring.xml"/>
        <property name="mapperLocations" value="classpath*:/mybatis/user.xml"/>
        <property name="dataSource" ref="dataSource"></property>
    </bean>

    <!-- DAO接口所在包名，Spring会自动查找其下的类 ,包下的类需要使用@MapperScan注解,否则容器注入会失败 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.zx.yuren.mybatis.mapper" />
        <property name="sqlSessionFactory" value="sqlSessionFactory" />
    </bean>
    
    
    ##1
    SqlSessionFactoryBean 这个是一个工厂Bean，其实返回的是真实对象 查询getObject方法
    
    属性：configLocation 配置mybatis基本配置文件
         mapperLocations 配置mybatis的mapper文件位置
         dataSource 配置应用的dataSource
    
         
         
    ####这东西是一个BeanDefinitionRegistryPostProcessor 扫描指定包下的条件，构建BeanDefinition 实际构建的是org.mybatis.spring.mapper.MapperFactoryBean
    org.mybatis.spring.mapper.MapperScannerConfigurer
    
    当然也可以不用MapperScannerConfigurer，直接用SqlSessionDaoSupport  然后getSqlSession().update....(statementId,param);
    