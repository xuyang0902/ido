package com.ido.jdk.collections.queue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xu.qiang
 * @date 19/12/19
 */
public class MyBlockQueue<T> {


    private Object[] items;
    private int count;
    private int putIndex;
    private int takeIndex;
    private ReentrantLock lock = null;
    private Condition notEmpty = null;//不为空 等着take
    private Condition notFull = null;


    public MyBlockQueue(Object[] items, int size) {
        this.items = new Object[size];
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
    }

    private void put(Object object) throws Exception {

        try{
            lock.lock();

            //为什么需要用while 防止虚假唤醒
            while (this.putIndex == items.length) {
                notFull.await();
            }

            putIndex++;
            count++;
            if (putIndex == items.length) {
                putIndex = 0;
            }

            //生产者  通知可以take了
            notEmpty.signal();
        } finally {
            lock.unlock();
        }

    }

    private Object take() throws InterruptedException {

        try {
            lock.lock();

            //为什么需要用while 防止虚假唤醒
            //数组中没有东西可以拿，take阻塞
            while (count == 0) {
                notEmpty.await();
            }

            Object res = items[takeIndex];

            //take的下标+1 当take到了最后一个下标，从头开始拿
            takeIndex++;
            if (takeIndex == items.length) {
                takeIndex = 0;
            }

            //数据拿走了
            count--;
            //通知队列中又可以放数据了
            notFull.signal();

            return res;
        } finally {
            lock.unlock();
        }
    }


}
