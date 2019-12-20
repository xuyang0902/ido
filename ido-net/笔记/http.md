     
[http协议](http://www.ruanyifeng.com/blog/2016/08/http.html)

        http 0.9
            只有GET
        http 1.0
            GET POST 和 HEAD
            请求头 请求体
            每次请求都要创建连接 请求完释放连接
        http 1.1
        
            PUT PATCH OPTIONS DELETE
            请求头中 加了一个connection: keep-alive 默认
            管道机制 在一个tcp请求中 客户端可以发送多个请求
            分块传输
        
        http 2
            二进制传输 把内容压缩成二进制进行传输
            多工
            头部压缩
            服务端可以主动push
        http 3.0
            tcp--->udp 还没制定出来
            
            
            
        https    = http + ssl
        非对称加密
        服务端（私钥）
        浏览器访问服务端的时候  会拿到（公钥） 会把证书的内容做hash 和CA机构的签名解密 看看是不是一样的 如果是一样的说明是合法的
        
        
        
        tcp的三次握手 四次挥手就不在这展开了
        
        
        
        
        
        
        
        