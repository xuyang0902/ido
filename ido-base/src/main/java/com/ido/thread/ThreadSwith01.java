package com.ido.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * @author xu.qiang
 * @date 2019/11/10
 */
public class ThreadSwith01 {

    static Thread thread01 = null;
    static Thread thread02 = null;

    public static void main(String[] args) throws InterruptedException {


        thread01 = new Thread(new Runnable() {
            @Override
            public void run() {

                String[] split = "A,B,C,D,E".split(",");
                int index = 0;

                for (String s : split) {
                    //等待
                    LockSupport.park();
                    System.out.println(Thread.currentThread().getName() + " -->> print" + s);
                    //執行完通知thread02
                    LockSupport.unpark(thread02);
                }

            }
        }, "Thread01");

        thread02 = new Thread(new Runnable() {
            @Override
            public void run() {


                String[] split = "1,2,3,4,5".split(",");
                for (String s : split) {
                    //我先打印
                    System.out.println(Thread.currentThread().getName() + " -->> print" + s);
                    //喚醒他hread01
                    LockSupport.unpark(thread01);
                    //等待通知
                    LockSupport.park();


                }
            }
        }, "Thread02");

        thread01.start();
        thread02.start();


        Thread.sleep(1000 * 10);
    }
}
