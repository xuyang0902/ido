package com.ido.jdk.concurrent.aqs;

import java.util.concurrent.CountDownLatch;

/**
 * @author xu.qiang
 * @date 19/8/30
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {


        //设置个数
        final CountDownLatch countDownLatch = new CountDownLatch(4);


        for (int index = 0; index < 4; index++) {


            final int finalIndex = index;
            new Thread(new Runnable() {
                @Override
                public void run() {


                    try {

                        System.out.println("任务  -- " + finalIndex);
                    } finally {
                        //减个数 减到0的时候 唤醒获取lock的线程
                        countDownLatch.countDown();
                    }


                }
            }).start();
        }

        System.out.println("等待4个任务都执行完再往下面走");
        //await的这里是共享锁
        countDownLatch.await();

        System.out.println(">> 继续走");
    }
}
