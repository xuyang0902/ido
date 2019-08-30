package com.ido.jdk.concurrent.aqs;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁
 * 非公平锁
 *
 * @author xu.qiang
 * @date 19/8/29
 */
public class ReentrantLockTest {

    private static final ReentrantLock lock = new ReentrantLock(true);


    private volatile static int num = 0;

    public static void main(String[] args) throws InterruptedException {




        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();

                try {


                    try {
                        Thread.sleep(1000 * 5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("-->a");

                    //执行业务代码

                } finally {
                    lock.unlock();
                }

            }
        }, "a");

        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();

                try {

                    try {
                        Thread.sleep(1000 * 10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    System.out.println("-->b");

                    //执行业务代码

                } finally {
                    lock.unlock();
                }

            }
        }, "b");

        Thread c = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();


                try {

                    try {
                        Thread.sleep(1000 * 10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("-->c");

                } finally {
                    lock.unlock();
                }


            }
        }, "c");

        a.start();
        b.start();
        c.start();



    }


}
