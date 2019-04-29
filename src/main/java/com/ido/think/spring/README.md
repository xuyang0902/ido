# Spring的IOC和AOP设计
       
### 鄙人拙见    
    如何学习一个框架？ 
    1、会用（知其然） 
    2、理解其如何实现(知其所以然)。
   ——
    了解思想，假设让自己实现，会怎么做？ 
       
### 方向
	本篇不会一行一行的引导大家去读spring的源码，主要是带领代价理解Spring的设计思想。
	当然，希望读完本篇文章，对读者后续阅读spring源码有一定的帮助。
 
### Spring 核心
    * IOC: 控制反转，依赖注入
    你想要什么 告诉他好了，他都会给你（除非是真的没有 哈哈）
    * AOP: 面向切面编程
    不手动改变你的代码，依旧可以在你代码执行的前后加点我相加的东西   
	    
[容易混淆的两个概念:beanFactory和factoryBean](https://blog.csdn.net/qiesheng/article/details/72875315)
    
    BeanFactory：这就是爸爸啊，IOC核心对象，所有的bean都是从这里get
    FactoryBean：就是一个简单的bean，只不过这个bean可以把你想要的bean包装一下，大名鼎鼎的org.springframework.aop.framework.ProxyFactoryBean了解一下

### Spring提供的一些常用的扩展类
    1. FactroyBean：我们熟悉的AOP基础bean 
    2. BeanPostProcessor：在每个bena初始化前后做操作
    3. InstantiationAwareBeanPostProcessor：在Bean实例化前后做一些操作 
    2和3有啥区别呢？ 一个是初始化 一个是实例化
    4. BeanNameAware、ApplicationContextAware 和 BeanFactoryAware
    实现了Aware接口，你想要对应的东西，spring就能给你送进来 
    5. BeanFactoryPostProcessor
    Spring允许在Bean创建之前，读取Bean的元属性，并根据自己的需求对元属性进行改变，比如将Bean的scope从singleton改变为prototype。其实修改的是beanDefinition 
    6. InitialingBean：在属性设置完毕后做一些自定义操作 
    7. DisposableBean： 在关闭容器前做一些操作。

### 个人简单图解Spring的IOC
![Spring-IOC]( /image/spring-ioc.png)

以上这幅图去掉了spring很多杰设计，只保留了一部分核心的关于IOC和AOP
的设计。

1. 解析xml（包括扫描注解） 放到beanDefinitionMap
2. spring开放扩展BeanDefinitionRegistryPostProcessor 操作注册表（意味着 用户可以 获取一些他指定想要的bean 但是不需要再进行xml配置了）
3. spring开放扩展BeanFactoryPostProcessor,，操作beanDefinition（意味着用户可以 根据自定义的规则 修改指定对象的属性）
4. spring开放扩展 BeanPostProcessors 操作bean（意味着可以包装这个bean ，这就可以做很多事情了呀）
5. getBean：获取bean 查找注册表，根据beanDefinition获取bean的信息 实例化，最后看是否需要通过BeanPostProcessors 加强。
*后续我会对getBean这块写一篇源码级别的分享。*





### 推荐源码阅读 

这位大兄弟的几篇源码分析 写的很用心，值得阅读，分享一下。
[Spring IOC](https://javadoop.com/post/spring-ioc)
[Spring Aop 使用](https://javadoop.com/post/spring-aop-intro)
[Spring Aop 源码分析](https://javadoop.com/post/spring-aop-source)


*Spring基本已经是职场Java程序员的必修课，近几年微服务的火热，Spring boot cloud的出现也丰富了spring的生态圈。  好好学习天天向上*



#spring# #架构
    
       

   