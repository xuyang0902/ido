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


    private static final ReentrantLock lock = new ReentrantLock(true/*true 公平锁*/);


    private volatile static int num = 0;

    public static void main(String[] args) throws Exception {


        //抢锁，抢不到会阻塞，
        lock.lock();
        try {


            System.out.println("-->c");

        } finally {
            lock.unlock();
        }


        //非公平只抢一次
        boolean tryLock = lock.tryLock();
        try {
            System.out.println("-->c");

        } finally {

            if (tryLock) {
                lock.unlock();
            }
        }

        //非公平抢锁，被中断直接异常
        lock.lockInterruptibly();
        try {
            System.out.println("-->c");

        } finally {
                lock.unlock();
        }


    }


}
