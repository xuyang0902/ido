### Apollo
##### 分布式配置中心

[apollo github](https://github.com/ctripcorp/apollo)        


###### apollo的几个角色
        0、config service -->configDB 和client交互 获取配置，长轮训消息
        1、admin service -->configDB 和portal交互 配置管理，修改发布等操作
        2、eureka --> 服务注册发现 configservice 和admin service会注册上来 
                实际部署的时候eureka和config service一起起来的 |当然也有分开部署的模式（暂时没有深究）
        3、metaserver --> 在eureka上面包装了一层保存admin和config service的服务列表，相当于一个eureka的客户端
                部署的时候和config service在一个jvm进程中 对外提供http接口查询 admin service和config service
        4、portal web管理页面-->portalDB 通过meta server获取admin service列表 做loadbalance 往admin发送指令


        apollo的拉消息也是长轮训的方式 推拉结合

###### 部署
        
        本地部署 比较简单 看下demo.sh
        https://github.com/nobodyiam/apollo-build-scripts
        
        分布式部署
        https://github.com/ctripcorp/apollo/wiki/%E5%88%86%E5%B8%83%E5%BC%8F%E9%83%A8%E7%BD%B2%E6%8C%87%E5%8D%97
        
        分布式环境的简单部署下
        
        git clone  https://github.com/ctripcorp/apollo.git
        ###下载实在是太慢了，网盘分享下  https://pan.baidu.com/s/1D56kfxzKqQeTuLLj3c5ljg
        
        ###执行前把configdb和portaldb地址 用户名密码 eureka地址meta-server地址调整一下
        执行 scripts/build.sh 构建包
        
        ###创建数据库
        configdb.serverconfig关注下eureka的地址配置
        
        portdb.serverconfig  关注下环境配置的支持apollo.portal.envs
        
        
        ##创建apollo-install 把打包好的 admin config portal .zip移动过来
        mkdir apollo-install
        cd  apollo-install
        mv /Users/tbj/usr/local/tmp/apollo-source/apollo-master/apollo-adminservice/target/apollo-adminservice-1.6.0-SNAPSHOT-github.zip ./
        mv /Users/tbj/usr/local/tmp/apollo-source/apollo-master/apollo-portal/target/apollo-portal-1.6.0-SNAPSHOT-github.zip ./
        mv /Users/tbj/usr/local/tmp/apollo-source/apollo-master/apollo-configservice/target/apollo-configservice-1.6.0-SNAPSHOT-github.zip ./
        
        
       ###解压
       unzip apollo-configservice-1.6.0-SNAPSHOT-github.zip -d configservice
       unzip apollo-adminservice-1.6.0-SNAPSHOT-github.zip -d adminservice
       unzip apollo-portal-1.6.0-SNAPSHOT-github.zip -d portal

       注意数据库的配置 以及portal的conf/apollo-env.properties里面meta-sever的配置。
       
       #日志文件位置授权
       sudo mkdir -p /opt/logs
       sudo chmod 777 /opt/logs/
       
       #依次启动configService adminService portal 
       ./configservice/scripts/startup.sh
       ./adminservice/scripts/startup.sh
       ./portal/scripts/startup.sh
       
       
       虽然我走的是分布式部署，但是我只有一台机器。实际部署的情况下 config admin portal肯定是多节点部署的，这些机器都是无状态的
       
       
       那么需要注意啥呢？？ 
       注意configDB库serviceconfig表eureka的配置  需要是多个configservice的地址的集合 8080/eureka;用英文逗号隔开
       注意portDB的环境支持
       注意portal的conf/app-env.properties里面meta地址的配置  需要是多个configservice的地址的集合 8080端口 用英文逗号隔开
        
       

       8070端口是portal
       8080是configservice
       8090是adminservice
       
       
###### 使用教程
        部门在 portalDB.configservice   organizations下面 env也要关注下 Portal支持哪些环境
        
        
        一个环境可以有多个集群
        namespace相当于是文件的意思 有共有的和私有的  创建私有的可以覆盖共有的
        
        
        
###### 应用 见code  使用了api方式和spring注解方式的使用
        
######  有一个疑问？adminServer写完之后如何通知到客户端   
        其实adminserver发布后往releasemessage写了一条数据，configserver每秒回去扫描这个表，有变化了就通知客户端
        
        configserver和应用是保持长轮训的机制来实现的
       