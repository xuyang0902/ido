[B站-神器的网站](https://www.bilibili.com/video/av59632817?from=search&seid=12518784909987380608)



##### 核心概念
        调度器Schedule  把trigger和Job整合起来 指定时间执行任务
        触发器Trigger  simple || cron 
        任务 JOB
    
        -----------
         JOB：任务
         JOB:每次执行任务都会创建一个JOB，JOB执行完，这个JOB对象会被回收
                实现JOB 重写execute方法
         JobDetail:表示任务实例，有几个重要的属性 name group jobClass jobDataMap
                 JobDetail job = JobBuilder.newJob(PrintWordsJob.class).withIdentity("job1", "group1").build();
         JobDataMap:可以用于保留数据，在JOB执行时，可以把map放到jobExecutionContext中
                                         
         JobExecutionContext 可以拿到jobdetail和trigger的信息
         
         有状态的job和无状态的job信息
            无状态的job每次调用都会新建一个jobdataMap
            有状态的job可以理解为多次调用期间可以持有一些状态信息 保存在JobDataMap中 通过@PersistJobDataAfterExecution注解来实现
         
         
         -----------
         Trigger：触发器
            SimpleTrigger： 指定日期 | 时间启动 且可能以间隔时间重复执行的
            CronTrigger：cron表达式 秒 分 时 日 月 周 年
                   秒（0-59）
                   分（0-59）
                   时（0-23）
                   日（1-31）
                   月（1-12）
                   周（1-7）
                   年【可选】
                   * 表示每一个时刻
                   , 表示多个值
                   - 表示区间
                   / 表示增量  3/20  表示从第3（秒 分 时） 开始 每个20 （秒 分 时） 执行
                   ? 日和星期用 这两个是互斥的  指定了日 周就用?   指定了周 日就用？   一般周都用？
                   
          -----------       
          调度器
          StdSchedulerFactory.getDefaultScheduler();        
          
          一个job可以对应多个trigger
          
          一个trigger只能指派到一个job执行
           
           
          SchedulerFactory 默认的 是StdSchedulerFactory  
             
          quartz.properties
          
          org.quartz.scheduler.instanceName: DefaultQuartzScheduler
          org.quartz.scheduler.rmi.export: false
          org.quartz.scheduler.rmi.proxy: false
          org.quartz.scheduler.wrapJobExecutionInUserTransaction: false
          
          org.quartz.threadPool.class: org.quartz.simpl.SimpleThreadPool
          org.quartz.threadPool.threadCount: 10
          org.quartz.threadPool.threadPriority: 5
          org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread: true
          
          org.quartz.jobStore.misfireThreshold: 60000
          
          org.quartz.jobStore.class: org.quartz.simpl.RAMJobStore
          
          
          -----------
          监听器:
            全局：监听所有
            非全局：监听局部
          

                      
                  
                  
                  
                 
                                        