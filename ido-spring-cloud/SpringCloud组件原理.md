##### Eureka （AP的设计）
     EurekaServer 
        服务上线
            --> 写注册表 
            ---实时同步--> ReadWrite缓存 
            ---异步线程定时同步--> ReadOnly缓存
            
        服务心跳->收集心跳信息，异步线程对比心跳信息和服务注册表，更新注册表信息   
         
     EurekaClient  
        服务拉取（定时通信拉）
            -->从ReadOnly缓存拉
            --->从ReadWrite缓存拉
            --->最后到注册表拉
            
        服务下线 --> 通知EurekaServer下线 
        服务心跳 --> 定时上报心跳给服务端   
        
        
     服务的发现需要几十秒甚至是分钟级别的   如何优化?  时间变小了，那么eureka的服务端带宽，机器压力会变大，能承接的容量会变小
     
       ## Eureka client 去 Eureka 中拉取服务注册表的时间 配置 3 秒	
       eureka.client.registryFetchIntervalSeconds = 30000	
       ## 心跳上报时间 默认 30s ，调整为 3s 	
       eureka.client.leaseRenewalIntervalInSeconds = 30	
       
       ## Eureka  两个缓存之间的同更新时间 配置成 3s	
       eureka.server.responseCacheUpdateIntervalMs = 3000	
       ## 线程多少时间检查一次心跳 默认 60s ,可以配置为 6000,6秒中	
       eureka.server.evictionIntervalTimerInMs = 60000	
       ## 服务发现的时效性，默认90 秒钟才下线。可以调整为 9s	
       eureka.instance.leaseExpirationDurationInSeconds = 90
       
       
       
     AP设计 A机房和B机房网络不通了，但是A机房内还能相互调用，B机房内也能相互调用，网络出现问题的几秒内 客户端缓存还没更新 调用会有错误，可以熔断  
     
     
      
##### robin 
        负载均衡
        
##### feign
        http的调用 
        proxy 通过robin 选出url 基于http发送请求
        
        为什么第一调用的时候都会超时？？？？
        每个服务第一次被请求的时候会初始化ribbon的组件，初始化组件需要耗费一定的时间 ，所以很容易导致超时
        
        #启动的时候就初始化相关组件
        ribbon.eager-load.enabled=true
        
        
        
##### zuul
        服务网关路由


##### Hystrix
        默认10秒滑动窗口，请求超过最小请求数 且 失败率达到阈值，熔断器开启。调用fallback方法
        
        熔断器开启后，有一个活动窗口，5秒内放过一个请求，判断服务是否可用，如果可用，重置熔断器。
        
        执行方法CMD模式，有单独的线程
        
        舱壁模式：C调用 AB   A现在不可用，熔断A就可以了，不需要熔断B   所以A B 线程隔离
        
        如果使用Hystrix 一定要注意threadLocal的问题---->这里可能回事一个坑哦
        
        有一点重。
        
##### Sentinel   alibaba开源组件     
        
        
        