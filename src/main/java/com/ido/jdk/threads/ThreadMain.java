package com.ido.jdk.threads;

/**
 * @author xu.qiang
 * @date 19/8/21
 */
public class ThreadMain {


    public static void main(String[] args) {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                while(!Thread.currentThread().isInterrupted()){
                    System.out.println(">>>>");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        /*
         * 当jvm中全部都是守护线程的时候，可以直接退出了
         *
         * 改成false，那么jvm不会退出
         */
        thread.setDaemon(true);
        thread.start();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        try {
            //cpu让出给这个thread
            thread.join(3200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
