##### 造轮子
[xrpc](https://github.com/xuyang0902/xrpc)

##### Dubbo SPI
    增强了JAVA的spi
    dubbo的spi很重要，是看懂源码的基础啊
[Dubbo Spi](http://dubbo.apache.org/zh-cn/docs/source_code_guide/dubbo-spi.html)
    
    ##使用方法  核心类ExtensionLoader
    ExtensionLoader.getExtensionLoader(接口.class).getExtension("实现类配置名称").接口方法();
    
    ## 加载目录  META-INF/services/  META-INF/dubbo/ META-INF/dubbo/internal/
    
    
    1、获取接口对应的ExtensionLoader
    2、从ExtensionLoader获取 name指定class的对象
        getExtension(String name)
            2.1 如name = true 获取默认的拓展实现类
            2.2 先从缓存中获取，获取不到创建拓展实现然后放入缓存
            2.3 创建拓展类
                    获取所有扩展类，根据name获取目标扩展类（从spi指定的路径下查找 缓存到cachedClasses）
                    反射创建扩展类
                    向实例中注入依赖（从objectFactory对象中获取属性 下面会分析objectFactory）
                    如果存在wrapper类，把扩展对象当做wrapper的构造参数扔进去创建wrapper对象
            
     loadExtensionClasses() ###SPI路径查找class
        1、接口加了SPI注解  需要看下SPI是否配置了默认的扩展接口 
        2、分别从META-INF/services/  META-INF/dubbo/ META-INF/dubbo/internal/ 加载class
            2.1 找文件名 文件夹路径 + 接口名称
            2.2 按行解析 
                    如果扩展类有Adaptive注解 缓存 cachedAdaptiveClass（只能有一个 否则报错）
                    如果扩展类是wrapper，缓存到cachedWrapperClasses（可以有多个）
                    检查扩展类有Activate注解 缓存到 cachedActivates 
                        不是Adaptive和wrapper的扩展类 需要缓存到 extensionClasses
                        
                        
     dubbo的objectFactory  
     其实是 AdaptiveExtensionFactory是一个wrapper的ExtenstionFactory dubbo目前提供了SPI和Spring两个容器，想要对象可以从这个容器中拿
    
##### 自适应拓展
    这段代码就比较有趣了，一般代码你debug，多看几遍都能知道调用关系，dubbo这个代码，调用到这里没了？？？没了？
    因为这些代码是动态生成的，然后用javaassist编译后加载的。

[dubbo-自适应拓展](http://dubbo.apache.org/zh-cn/docs/source_code_guide/adaptive-extension.html)    
        
        getAdaptiveExtension() #加了一道缓存
        createAdaptiveExtension（）#创建自适应扩展
            如果有cachedAdaptiveClass就直接反射改类
            如果没有 创建自适应的扩展类 动态生成一段code 使用javaassit编译 得到代理class
            
            动态生成代码那段比较多  具体可以看 /Users/tbj/usr/local/codes/github/ido/ido-dubbo/src/main/java/com/ido/dubbo/debug/clazz_source/Protocol$Adpative.java
            1、检查接口至少有一个方法存在Adaptive注解
            2、具体就不分析了吧，代码有点多，大致意思就是通过方法参数获取url 根据url里面的name以及spi的机制来获取对应的处理类
     
##### 服务导出
        ServiceBean-->ServiceConfig->AbstractServiceConfig-->AbstractInterfaceConfig-->AbstractMethodConfig-->AbstractConfig
    
        ## 会动态生成一个扩展类，具体可以参考 /Users/tbj/usr/local/codes/github/ido/ido-dubbo/src/main/java/com/ido/dubbo/debug/clazz_source/Protocol$Adpative.java
        ## 主要功能就是重写了export和refer方法，最终都是要根据url.getProtocol()来获取响应的实现类，用实现类去执行对应的export方法和refer方法 当url中的协议没有的时候 默认dubbo
        private static final Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        
        ## 动态生成一个扩展类 具体可以参考 /Users/tbj/usr/local/codes/github/ido/ido-dubbo/src/main/java/com/ido/dubbo/debug/clazz_source/ProxyFactory$Adpative.java
        ## 和上面Protocol雷同 默认javassist 具体生成代码规则不细分析了属于SPI模块
        private static final ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
        
        
        ##最终服务导出，会走到这两行关键的代码，我们重点分析以下这个
        
        Invoker<?> invoker = proxyFactory.getInvoker(ref, (Class) interfaceClass, registryURL.addParameterAndEncoded(Constants.EXPORT_KEY, url.toFullString()));
        Exporter<?> exporter = protocol.export(invoker);
        exporters.add(exporter);
        
        其实服务导出很简单，大方向只需要关注以下几点
        注册中心：注意protocol.export(invoker)，其实这里是一个调用链的过程，invoker的url最开始保留的是注册中心的地址 所以是先走到 RegistryProtocol  
        服务调用：这里其实是注册中心的protocol那边调用过来导出服务协议的
                会把proxyFactory.getInvoker(ref, (Class) interfaceClass, url);构建的invoker对象包装成一个DubboExporter(invoker,Key,cacheMap);
                这里这个key很关键！！！！ for example：
        通信协议：默认使用netty
               ExtensionLoader.getExtensionLoader(Transporter.class);##原理同上Protocol ProxyFactory 不再具体分析
               最终netty服务会走到这里  这里的requestHandler是一个核心点，最终所有的通信 都会在这个handler种处理，具体的逻辑就是根据传入的参数 和channel信息构建对应的key获取到DubboExporter执行里面的invoker处理
               com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol.createServer(URL url);
                server = Exchangers.bind(url, requestHandler);
                

        protocol还有几个包装类  会形成一个调用链关系
        com.alibaba.dubbo.rpc.protocol.ProtocolListenerWrapper
        com.alibaba.dubbo.rpc.protocol.ProtocolFilterWrapper
        com.alibaba.dubbo.registry.integration.RegistryProtocol#export 这里里面会把url重点哎呦export
        
        
        服务端netty收到客户端发过来的信息，通过serviceKey从缓存中找到Invoker，传入参数执行。
        
    
[dubbo-服务导出](http://dubbo.apache.org/zh-cn/docs/source_code_guide/export-service.html)    

##### 服务导入
        ReferenceBean-->ReferenceConfig-->AbstractReferenceConfig-->AbstractInterfaceConfig-->AbstractMethodConfig-->AbstractConfig
        
        
        
        客户端创建的其实是一个代理类，这个代理类，会把本地的执行信息发送给客户端。
        当然这个代理类，需要做负载均衡（选择调用哪个producer）
        
        dubbo的流程，先订阅zk目录 最终调用会调到MockClusterInvoker
        
        
##### 服务字典
[服务字段-注册中心](http://dubbo.apache.org/zh-cn/docs/source_code_guide/directory.html)
        关键类
        AbstractDirectory  根据invocation可以获取Invoker列表
        
        RegistryDirectory


##### 服务路由
        

##### 集群

        解决网络调用过程中，一台调用失败了，如何处理
        mock=com.alibaba.dubbo.rpc.cluster.support.wrapper.MockClusterWrapper
        failover=com.alibaba.dubbo.rpc.cluster.support.FailoverCluster  失败转移，当出现失败，重试其它服务器，通常用于读操作，但重试会带来更长延迟。 默认
        failfast=com.alibaba.dubbo.rpc.cluster.support.FailfastCluster  失败一次就异常
        failsafe=com.alibaba.dubbo.rpc.cluster.support.FailsafeCluster   失败安全，出现异常时，直接忽略，通常用于写入审计日志等操作。
        failback=com.alibaba.dubbo.rpc.cluster.support.FailbackCluster 
        forking=com.alibaba.dubbo.rpc.cluster.support.ForkingCluster
        available=com.alibaba.dubbo.rpc.cluster.support.AvailableCluster
        mergeable=com.alibaba.dubbo.rpc.cluster.support.MergeableCluster
        broadcast=com.alibaba.dubbo.rpc.cluster.support.BroadcastCluster

##### 负载均衡
        LoadBalance的实现
        默认是random
##### 服务过程调用
##### Dubbo Admin


##### 读Dubbo源码总结
        first 搞清dubbo的SPI 不然是不可能读懂源码的
        second 搞懂com.alibaba.dubbo.common.URL这个类 贯穿dubbo
        third 搞懂com.alibaba.dubbo.rpc.Invoker
        
        dubbo默认是使用netty作为底层rpc通信的模块，所以netty至少是要会用的
