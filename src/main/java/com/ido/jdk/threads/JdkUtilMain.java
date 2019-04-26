package com.ido.jdk.threads;


import java.util.concurrent.*;

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
