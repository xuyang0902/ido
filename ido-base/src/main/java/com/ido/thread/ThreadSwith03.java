package com.ido.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xu.qiang
 * @date 2019/11/10
 */
public class ThreadSwith03 {

    static Thread thread01 = null;
    static Thread thread02 = null;
    static volatile boolean flag = true;
    static Object monitor = new Object();


    public static void main(String[] args) throws InterruptedException {

        thread01 = new Thread(new Runnable() {
            @Override
            public void run() {

                String[] split = "A,B,C,D,E".split(",");

                for (String s : split) {
                    synchronized (monitor){
                        while(!flag){
                            try {
                                monitor.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        flag = !flag;
                        System.out.println(Thread.currentThread().getName() + " -->> print" + s);

                        monitor.notify();

                    }
                }

            }
        }, "Thread01");

        thread02 = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] split = "1,2,3,4,5".split(",");
                for (String s : split) {

                    synchronized (monitor){
                        while(flag){
                            try {
                                monitor.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        flag = !flag;
                        System.out.println(Thread.currentThread().getName() + " -->> print" + s);

                        monitor.notify();

                    }
                }
            }
        }, "Thread02");

        thread01.start();
        thread02.start();


        Thread.sleep(1000 * 10);
    }
}
