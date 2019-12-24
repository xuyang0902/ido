

####### 几个角色
    
    master
        api-server
        controller-manager
        schedule
    worker
        kubelet  -- agent
        kube-proxy
    etcd

        
###### 在k8s中部署应用            
        开发war包
        dockefile把war包打进来
        k8s编排资源，创建pod（副本数） 创建service  对内网络打通，对外网络打通

###### 几个核心概念
        pod: 最小部署单元
           
        service: 一组pod 对外
        
######  kubectl 命令

######  yml编排
        yml记不住，可以用kubectl的命令导出模板 改改就好了
        
        #run命令导出
        kubectl run my-deploy --image=nginx --dry-run -o yaml  > my-deploy.yaml
        
        #get命令导出
        kubectl get my-deploy/nginx -o=yaml --export > my-deploy.yaml
        
###### POD
        分类  
            基础容器 维护pod网络
            init容器 先与业务容器开始执行
            业务容器  并行启动 
        实现机制
            共享网络：一个pod部署两个 image 网络用的是同一个，网络的namespace共享
            共享存储： 数据卷。
        存在的意义
            两个亲密的应用场景之间的交互（文件 网络的交互）
        镜像拉取策略  imagePUllPolicy
            IfNotpresent ：宿主机镜像不存在去拉取（默认）
            Always：每次创建pod都会重新拉取
            Never：永远不会主动拉取这个镜像
        资源限制
            spec.containers[].resources:requests.cpu:"250m" #最少 一核cpu的百分之25
            spec.containers[].resources:requests.memory:"64Mi" # 最少  64兆内存
            spec.containers[].resources:limits.cpu  #最多
            spec.containers[].resources:limits.memory #最多
        重启策略 restartPolicy
            Always  当容器终止退出后，总是重启容器
            OnFailure 异常退出（退出状态 非0） 才重启容器
            Never 当容器终止退出后，，从不重启容器
            
        健康检查 Probe
            livenessProbe  检查失败 插死容器，根据 重启策略来操作
                支持httpGet exec tpcSocket
            readlinessProbe 检查失败 k8s把pod从service的endpoints中剔除  ep
            
        调度约束
            --> api-server -->etcd 创建pod
                           --->schedule 调度器需要放到哪台node上
                           --->etcd node信息和pod信息存储到etcd
                           --->schedule 调用schedule调度
                           --->kubelet-agent-->run docker
                           --->etcd更新pod状态
            
        故障排查
            Pending pod已经提交到k8s，还在调度
            Runnning pod绑定到一个节点，并且撞见了所有容器
            Succeeded 成功
            Failed  pod的所有容器均已经终止，且至少一个容器在故障中终止
            Unknown apiserver无法获取pod状态   master和pod所在node的kubelet通信出问题
            
            kubectl describle TYPE/NAME
            kubectl logs TYPE/NAME [-c container]
            kubectl exec POD [-c container] --CMMAND[args ...]
            
        
######  最常用的控制器 Deployment
        deployment功能及应用场景
            部署无状态应用
            管理pod和ReplicaSet
            具有上线部署 副本设定 滚动升级 回滚等功能
            提供声明式更新 列入只更新一个新的image
            
        使用deploy部署java应用
            
        应用升级 回滚 弹性伸缩
        
######  Service 
        定义
        存在的意义
        三种类型 ClusterIP NodePort LB
        Iptables工作原理  
        IPVS工作原理 效率比iptables好
        部署集群内部DNS服务 CoreDNS
        
######  Ingress
        通过service绑定pod。
        
        Ingress Controller部署（nginx）
        Http
        Https
        ingress nginx工作原理  upstream里面会是 serverName pod-id；
        
        
###### Volume 数据卷
        emptyDir 容器数据共享  
        hostPath 访问宿主机   宿主机保存
        NFS 网络存储 
        
###### k8s 集群资源监控
        cAdvisor和InfluxDB

###### ELK收集k8s平台日志
        日志
            k8s组件日志
            应用日志
            
        filebeat+ELK  
          filebeat采集--> logstash 分析--> elk存储-->> kibana 可视化
          
        方案一：node部署日志手机程序
        方案二：pod中附加专用日志收益容器
        方案三：应用程序直接推送日志
          
          
        Log-Pilot了解一下
        
        
        
        
        
        