# 线程池

多线程创建的常见方式:
1. 继承Thread 
2. 实现Runnable


## JDK提供的好用的工具

```
package com.ido.jdk.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * jdk多线程的工具类
 *
 * @author xu.qiang
 * @date 19/4/26
 */
public class JdkUtilMain {

    public static void main(String[] args) {

        /*
         * 固定线程数的线程池
         */
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        fixedThreadPool.execute(new Task("newFixedThreadPool"));
        fixedThreadPool.shutdown();

        /*
         * 创建一个可缓存线程池，有需要就加线程，空闲超过一定时间就自动回收
         */
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new Task("newCachedThreadPool"));
        cachedThreadPool.shutdown();

        /*
         * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
         */
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(new Task("newSingleThreadExecutor"));
        singleThreadExecutor.shutdown();


        /*
         * 创建一个定长线程池，支持定时及周期性任务执行。
         */
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        scheduledThreadPool.schedule(new Task("newFixedThreadPool"),10, TimeUnit.SECONDS);
        scheduledThreadPool.shutdown();


    }


    static class Task implements Runnable {

        private String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "   线程池类型" + name);
        }

    }
}
```

## 实际工作？
然而我们工作并不推荐直接使用JDK的工具，建议直接创建ThreadPoolExecutor，我们需要了解其中的核心参数，以及运行原理

```
package com.ido.jdk.threads;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xu.qiang
 * @date 19/4/26
 */
public class SuggestMutiThreadPoolMain {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 建议的创建线程池的方式
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1,//核心线程数
                4,//最大线程数
                5, //空闲存活时间
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(4),//队列大小
                new ThreadFactory() {//线程工厂，自定义可以清晰的看清自己定义的线程名称

                    private AtomicInteger index = new AtomicInteger(0);

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "MyThreadPoll_" + index.getAndIncrement());
                    }
                },
                new RejectedExecutionHandler() {//队列满了，线程也在工作 就会进入拒绝策略
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

                        if (r instanceof OrderTask) {

                            String orderNo = ((OrderTask) r).getOrderNo();

                            if (!executor.isShutdown()) {

                                try {

                                    System.out.println("订单号：" + orderNo + "进入到等待策略");
                                    executor.getQueue().put(r);

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                System.out.println("线程池已经关闭了，订单号：" + orderNo);
                            }

                        } else {
                            System.out.println("非法任务");
                        }


                    }
                });
        //允许核心线程数在空闲情况下回收
        //threadPoolExecutor.allowCoreThreadTimeOut(true);

        int loop = 8;
        while (loop-- > 0) {
            threadPoolExecutor.execute(new OrderTask(loop + ""));
        }


        try {
            //睡一会 等上面的线程都处理完，看看哪些核心线程数会被回收
            Thread.sleep(1000 * 50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        loop = 8;
        while (loop-- > 0) {
            threadPoolExecutor.execute(new OrderTask(loop + ""));
        }


    }


    static class OrderTask implements Runnable {

        private String orderNo;

        public OrderTask(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public void run() {

            //模拟跑10秒
            long begin = System.nanoTime();
            while (true) {
                long end = System.nanoTime();

                if (((end - begin) / 1000 / 1000 / 1000) > 10) {
                    break;
                }
            }

            System.out.println(Thread.currentThread().getName() + " : 处理订单 " + orderNo);

        }

        public String getOrderNo() {
            return orderNo;
        }
    }
}

```


参考以上代码，博主贴一张跑代码时，线程的情况
![线程池运行情况]( /image/线程池运行状况.jpg)

这里我简单说一下线程池的运行逻辑

1. task请求进来
2. 是否到达核心线程数，没到就直接创建线程 处理task
3. 达到核心数了，判断队列能都放进去，能放就放在队列中
4. 队列放不下了，线程数是否达到最大，没有达到最大 就创建线程处理
5. 达到最大线程数，走到拒绝策略

```
java.util.concurrent.ThreadPoolExecutor#execute源码代码分析
public void execute(Runnable command) {
    if (command == null)
        throw new NullPointerException();
    /*
     * Proceed in 3 steps:
     *
     * 1. If fewer than corePoolSize threads are running, try to
     * start a new thread with the given command as its first
     * task.  The call to addWorker atomically checks runState and
     * workerCount, and so prevents false alarms that would add
     * threads when it shouldn't, by returning false.
     *
     * 2. If a task can be successfully queued, then we still need
     * to double-check whether we should have added a thread
     * (because existing ones died since last checking) or that
     * the pool shut down since entry into this method. So we
     * recheck state and if necessary roll back the enqueuing if
     * stopped, or start a new thread if there are none.
     *
     * 3. If we cannot queue task, then we try to add a new
     * thread.  If it fails, we know we are shut down or saturated
     * and so reject the task.
     */
    int c = ctl.get();
    if (workerCountOf(c) < corePoolSize) {
        if (addWorker(command, true))
            return;
        c = ctl.get();
    }
    if (isRunning(c) && workQueue.offer(command)) {
        int recheck = ctl.get();
        if (! isRunning(recheck) && remove(command))
            reject(command);
        else if (workerCountOf(recheck) == 0)
            addWorker(null, false);
    }
    else if (!addWorker(command, false))
        reject(command);
}

```


线程空闲的时候，非核心线程会自动回收

如果核心线程有长时间空闲的情况，也建议回收，设置以下配置
//threadPoolExecutor.allowCoreThreadTimeOut(true);

### 注意事项
* 使用线程池，减少在创建和销毁线程上所花的时间以及系统资源的开销,解决资 源不足的问题 
* 尽量不要用 Executors 去创建,而是通过 ThreadPoolExecutor 的方式,规避资源耗尽的风险。 

















