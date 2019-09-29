package com.ido.jdk.concurrent.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *
 * 信号量，同一个时刻最多能处理多少
 * @author xu.qiang
 * @date 19/8/30
 */
public class SemaphoreTest {

    public static void main(String[] args) {


        final Semaphore semaphore = new Semaphore(5);

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(16);

        for (int index = 0; index < 10; index++) {
            final int finalIndex = index;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {

                        //减少许可
                        semaphore.acquire(1);

                        System.out.println("处理业务逻辑 _" + finalIndex);

                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {

                        //增加许可
                        semaphore.release(1);
                    }

                }
            });
        }


        try {
            Thread.sleep(1000 * 60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        fixedThreadPool.shutdown();


    }
}
