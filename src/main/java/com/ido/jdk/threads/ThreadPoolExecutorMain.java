package com.ido.jdk.threads;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 建议自己手动创建ThreadPoolExecutor
 * @author xu.qiang
 * @date 19/4/26
 */
public class ThreadPoolExecutorMain {

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
