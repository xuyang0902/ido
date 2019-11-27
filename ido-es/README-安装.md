
#### 安装ES 6.2.4
        
        1.安装jdk 1.8
            配置JAVA_HOME
            添加PATH
        2.解压es targ包  bin目录执行
           ./elasticsearch
           [2019-11-25T18:23:24,128][WARN ][o.e.b.ElasticsearchUncaughtExceptionHandler] [unknown] uncaught exception in thread [main]
           org.elasticsearch.bootstrap.StartupException: java.lang.RuntimeException: can not run elasticsearch as root
            
            如何解决不能以root角色启动 
            #方式1
            ./elasticsearch -Des.insecure.allow.root=true  
            #方式2 可执行文件中 添加一下参数
            ES_JAVA_OPTS="-Des.insecure.allow.root=true"  
            #方式3 添加用户
            ##创建组 用户
            groupadd elsearch
            useradd elsearch -g elsearch -p elasticsearch

            #指定目录文件
            mkdir -p /usr/local/opt
            mv elasticsearch-6.5.4 /usr/local/opt/
            cd /usr/local/opt/

            #文件权限
            chown -R elsearch:elsearch elasticsearch-6.5.4

            #用elsearch用户登录 执行es启动
            su elsearch cd  elasticsearch-6.5.4/bin/
            ./elasticsearch

            #config/eleaticearch.yml 
            
            vim elasticsearch.yml
            cluster.name: elasticsearch 
            node.name: es-10
            network.host: 0.0.0.0
            http.port: 9200
            path.data: /home/elasticsearch
            bootstrap.memory_lock: false
            bootstrap.system_call_filter: false
            
                cluster.name ： 项目名称
                node.name ： 节点名称，约定为es+${IP最后一段}
                path.data ：文件分布目录
                bootstrap.memory_lock/bootstrap.system_call_filter： 因为Centos6不支持SecComp，而ES5.2.0默认bootstrap.system_call_filter为true进行检测，
                            所以导致检测失败，失败后直接导致ES不能启动；（仅为启动规避措施，设置为false长期使用有隐患，后面会提到）
                network.host： 默认设置为127.0.0.1，启动时为开发者模式，外部不能访问；设置为0.0.0.0时，启动是为生产模式，外部可以访问，用于数据写入和读取
                http.port：默认端口9200
            
            ######## !!!!!!!!!!! 低版本OS不支持       SecComp     
            bootstrap.memory_lock: false
            bootstrap.system_call_filter: false
            
         
            #修改支持跨域访问 修改es的配置文件
            elacticsearch.yml
            添加
            http.cors.enabled: true
            http.cors.allow-origin: "*"
            
            
            #文件句柄数
            cd /etc/security/
            vi limits.conf
            * soft nofile 65536
            * hard nofile 65536
            * soft nproc 4096
            * hard nproc 4096
            
            #用户线程数
            cd /etc/security/limits.d
            vi 90-nproc.conf
            90-nproc.conf
            elsearch    soft   nproc  4096
            
            #虚拟内存
            vi /etc/sysctl.conf
            vm.max_map_count=655360
            执行一下命令生效 sysctl -p
            
          


            
###### 安装Head插件
        事先安装好nodejs
        
        #下载插件
         wget https://github.com/mobz/elasticsearch-head.git
         #安装grunti-cli
         npm install -g grunt-cli
         
         #安装依赖
         cd elasticsearch-head-master
         cnpm install
         
         #修改配置
         vi Gruntfile.js
         
         connect: {
                                 server: {
                                         options: {
                                                 port: 9100,
                                                 base: '.',
                                                 keepalive: true,
                                                 #修改配置
                                                 hostname:'*'
                                         }
                                 }
        }
        
        #修改集群地址  localhost改成es的ip
        cd _site/
        vi app.js
        this.base_uri = this.config.base_uri || this.prefs.get("app-base_uri") || "http://192.168.1.121:9200";

     
        
        #打开插件9100端口
        #启动es
        bin/elasticsearch
        #启动es-head
        /usr/local/opt/elasticsearch-head/node_modules/grunt/bin/grunt server

        

###### 安装kibana

        vim /usr/local/opt/kibana654/config/kibana.yml
        5601端口
        kibana的ip
        es的地址
        
        
        nohup ./kibana &

###### X-PACK监控
        
###### Logstash安装   
        输入  过滤  输出
     
     
         input {
             file {
                 path => ["/data/nginxlog/Nginx-Access/*_current"]
                 type => "access-log"
             }
         }
         filter {
             if [type] == "nginx-error-log"{
                 grok{
                     match => [
                         "message", "%{NOTSPACE:hostname} (?<log_time>%{YEAR}[./]%{MONTHNUM}[./]%{MONTHDAY} %{TIME}) \[%{LOGLEVEL:severity}\] %{POSINT:pid}#%{NUMBER}: %{GREEDYDATA:errormessage},\ client: %{IP:client}, server: %{GREEDYDATA:server}, request: %{GREEDYDATA:request}"
                         "message", "%{NOTSPACE:hostname} (?<log_time>%{YEAR}[./]%{MONTHNUM}[./]%{MONTHDAY} %{TIME}) \[%{LOGLEVEL:severity}\] %{POSINT:pid}#%{NUMBER}: %{GREEDYDATA:errormessage},\ client: %{IP:client}, server: %{GREEDYDATA:server}"
                     ]
                 }
                 date {
                     match => ["log_time", "yyyy/MM/dd HH:mm:ss"]
                 }
             }
             else if [type] == "application-tomcat"{
                 grok{
                     match => [
                         "message", "%{NOTSPACE:tomcat_host} (?<tomcat_log_time>%{YEAR}[-]%{MONTHNUM}[-]%{MONTHDAY} %{TIME}) %{DATA:level} %{GREEDYDATA:log_msg}"
                     ]
                 }
                 date {
                     match => ["tomcat_log_time", "yyyy-MM-dd HH:mm:ss,Z"]
                 }
             }
         }
         output {
             elasticsearch {
                 hosts => [ "172.16.37.10:9200" ]
                 index => "%{type}-%{+YYYY.MM.dd}"
             }
         }


            
            
