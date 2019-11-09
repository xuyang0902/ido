
### Netty 为什么这么多RPC,消息中间件，大数据生态圈，都使用的通信层组件？ https://netty.io/

##### why netty?

      netty底层基于jdk的NIO，我们为什么不直接基于jdk的nio或者其他nio框架？下面是我总结出来的原因
      
      1.使用jdk自带的nio需要了解太多的概念，编程复杂
      2.netty底层IO模型随意切换，而这一切只需要做微小的改动
      3.netty自带的拆包解包，异常检测等机制让你从nio的繁重细节中脱离出来，让你只需要关心业务逻辑
      4.netty解决了jdk的很多包括空轮训在内的bug
      5.netty底层对线程，selector做了很多细小的优化，精心设计的reactor线程做到非常高效的并发处理
      6.自带各种协议栈让你处理任何一种通用协议都几乎不用亲自动手
      7.netty社区活跃，遇到问题随时邮件列表或者issue


##### netty底层核心类  我简单的理解
      NioEventLoop  （Reactor线程  其实就是一个死循环可以简单理解为 boss和work线程都是这三部 select ，handleKey，runTask）
            boss线程handleKey是处理accept的请求，转发到work线程中处理
            work线程handleKey是处理实际的进出传输数据
            解决的jdk空轮训的bug，大致的思想是空轮训超时后 重新创建一个新的selector 把keys都移动到这个新的selector上
      Channel 包装了jdk的channel【子类有一个Unsafe的类，需要关注】
      DefaultChannelPipeline  channel管道，boss线程work线程都有各自的管道，其实有in管道和out管道
            boss线程管道：所有的新连接全部都要经过这个管道
            work线程管道：所有的数据写入写出都经过这个管道
      NioServerSocketChannel：使用nio作为服务端的通信机制
      
      
###### 服务端启动的解释     
      EventLoopGroup 已经在我的其他文章中详细剖析过，说白了，就是一个死循环，不停地检测IO事件，处理IO事件，执行任务
      ServerBootstrap 是服务端的一个启动辅助类，通过给他设置一系列参数来绑定端口启动服务
      group(bossGroup, workerGroup) 我们需要两种类型的人干活，一个是老板，一个是工人，老板负责从外面接活，接到的活分配
                给工人干，放到这里，bossGroup的作用就是不断地accept到新的连接，将新的连接丢给workerGroup来处理
      .channel(NioServerSocketChannel.class) 表示服务端启动的是nio相关的channel，channel在netty里面是一大核心概念，
                可以理解为一条channel就是一个连接或者一个服务端bind动作，后面会细说
      .handler(new SimpleServerHandler() 表示服务器启动过程中，需要经过哪些流程，这里SimpleServerHandler最终的顶层
                接口为ChannelHander，是netty的一大核心概念，表示数据流经过的处理器，可以理解为流水线上的每一道关卡
      childHandler(new ChannelInitializer<SocketChannel>)...表示一条新的连接进来之后，该怎么处理，也就是上面所说的，
                老板如何给工人配活
      ChannelFuture f = b.bind(8888).sync(); 这里就是真正的启动过程了，绑定8888端口，等待服务器启动完毕，才会进入下行代码
      f.channel().closeFuture().sync(); 等待服务端关闭socket
      bossGroup.shutdownGracefully(); 
      workerGroup.shutdownGracefully(); 关闭两组死循环
      
##### 黏包拆包工具类 tcp的传输过程避免不了黏包拆包 netty是如何巧妙的处理的？ todo  需要补充知识点



##### EventLoopGroup & EventLoop

##### ChannelHandler

##### Pipeline

##### ByteBuf
         
         *      +-------------------+------------------+------------------+
         *      | discardable bytes |  readable bytes  |  writable bytes  |
         *      +-------------------+------------------+------------------+
         *      |                   |                  |                  |
         *      0      <=      readerIndex   <=   writerIndex    <=    capacity
         *   
         
         Pooled 和 Unpooled
         Unsafe 和 非Unsafe
         Heap   和 Direct
         
         建议：IO用直接内存，应用用堆内存
         
         
###### 解码器  ByteToMessageDecoder  
        累加字节流
        调用子类的decode方法进行解析（如果解析不出来，那就等待下一次数据过来）
        将解析到的ByteBuf乡下传播
        
        
        常用 固定长度，分隔符，长度域的解码器
        
        
###### 编码器  MessageToByteEncoder
        
        
######  writeAndFlush()   # ctx.channel().writeAndFlush();
        
        1、tail节点开始往前传播
        2、逐个调用channelHandler的write方法
        3、逐个调用channelHandler的flush方法
      
[官网Netty博客](https://netty.io/wiki/related-articles.html)

      写的不错，值得阅读，当然是在结合源码的，还是得自己多debug，
     
##### 如何debug调试 ，阅读代码？
     看看boss和worker线程是什么时候创建的 你会恍然大悟的。。如何调试？？
     连接服务端  telnet 127.0.0.1 9999  
     发送消息   send haha

##### netty3和netty4？
     这两个版本的代码还是相差的比较多的，3里面的低版本甚至还是jboss的包。建议直接看4吧，如果觉得4的版本真的看不太懂，
     可以看3.2.6那个版本，那个版本的代码比较简单


#####  性能调优 单机百万连接调优

        0.单机百万连接 server开100个端口  客户端一直连呗。
        
        1.突破局部文件句柄数限制
        
        #查看linux系统的文件句柄数
        ulimit -n  
        #修改文件句柄数配置  *表示任何用户 可以打开1000000句柄文件
        vi /etc/security/limits.conf
        * soft nofile 1000000
        * hard nofile 1000000
        
        2.突破全局文件句柄数限制
        #全局文件句柄数        
        cat /proc/sys/fs/file-max
        #改全局文件句柄数限制
        echo 2000000 >> cat /proc/sys/fs/file-max
        
        
#####  应用级别性能瓶颈 

        hanlder里面业务处理慢，会把woker线程堵住

#####  应用调优过程

        1、在handler中 封装task给业务线程池中处理
        2、添加handler的时候，扔一个线程池进去 

   
   
        

        
        
        
        


        