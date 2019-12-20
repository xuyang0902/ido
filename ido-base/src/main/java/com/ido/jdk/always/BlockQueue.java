package com.ido.jdk.always;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockQueue {


    Object[] items;
    int count;
    int putIndex;
    int takeIndex;
    ReentrantLock lock;
    Condition notEmpty;//不为空
    Condition notFull;//没有满

    public BlockQueue(int capicity) {
        items = new Object[capicity];
        lock = new ReentrantLock();
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    public void put(Object object) throws Exception {
        lock.lock();
        try {

            while (count == items.length) {
                notFull.await();
            }

            items[putIndex] = object;
            putIndex++;
            count++;
            if (putIndex == items.length) {
                putIndex = 0;
            }
            notEmpty.signal();

        } finally {
            lock.unlock();
        }

    }

    public Object take() throws Exception {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }

            Object object = items[takeIndex];
            items[takeIndex] = null;
            takeIndex++;
            count--;
            if (takeIndex == items.length) {
                takeIndex = 0;
            }

            notFull.signal();

            return object;
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {

        BlockQueue my = new BlockQueue(100);


        new Thread(new Runnable() {
            @Override
            public void run() {

                int index = 0;
                while (true) {

                    try {
                        my.put(index);
                        System.out.println("生产者生产-->" + index);
                        index++;

                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {

                    try {
                        System.out.println("消费者消费-->" + my.take());
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();


    }

}