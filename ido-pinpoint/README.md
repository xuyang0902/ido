##### 分布式监控系统

        目前主流的分布式监控系统
        PinPoint  java -agent
        Apache SkyWorking
        ZipKin
        Cat  以jar包的形式侵入引用
        阿里鹰眼 闭源
        

        目前tbj用的是tmonitor@大为开发的
        
        原理就是用javagent 拦截了
        httpclient
        druid
        datasource
        log4j
        dubbo
        redis
        rocktemq
        springmvc
        在tomcat容器起来的时候就改写字节码
        
        把数据发送到es上
        
        
        tmonitor的服务端 会把收集到的数据做分析
            traceId
                spanId
        会以调用链的形式呈现在页面上
        
        
        
        