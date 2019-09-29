package com.ido.jdk.concurrent.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock来实现
 *
 *
 * condition中有两个node  条件队列，单向链表，
 *
 * await的时候 park住
 *
 * signal的时候 把条件队列的node 放到阻塞队列中去抢锁，
 * await醒来去抢锁抢到的走下去
 *
 * @author xu.qiang
 * @date 19/8/30
 */
public class ConditionTest<T> {


    private ReentrantLock reentrantLock;
    private Condition notEmpty;//不为空 可以take走
    private Condition notFull;//没有满 还可以put

    private int takeIndex = 0;
    private int putIndex = 0;
    private int count = 0;

    private Object[] items;

    public ConditionTest(int capacity) {
        items = new Object[capacity];
        reentrantLock = new ReentrantLock();
        notEmpty = reentrantLock.newCondition();
        notFull = reentrantLock.newCondition();
    }


    public T take() throws InterruptedException {
        reentrantLock.lockInterruptibly();
        try {
            while (count == 0) {

                System.out.println("------->>>>>"+ Thread.currentThread().getName() + "===>>>> notEmpty.await()" );

                notEmpty.await();
            }
            T item = (T)items[takeIndex];
            items[takeIndex] = null;
            takeIndex++;
            if (takeIndex == items.length) {
                takeIndex = 0;
            }
            count--;
            notFull.signal();
            return item;
        } finally {

            reentrantLock.unlock();
        }
    }


    public void put(T object) throws InterruptedException {
        if (object == null) {
            throw new NullPointerException();
        }

        reentrantLock.lockInterruptibly();
        try {
            while (count == items.length) {
                notFull.await();
            }

            items[putIndex] = object;
            putIndex++;
            if (putIndex == items.length) {
                putIndex = 0;
            }
            count++;
            notEmpty.signalAll();
        } finally {

            reentrantLock.unlock();
        }
    }


    public static void main(String[] args) {

        final ConditionTest<Integer> conditionTest = new ConditionTest<Integer>(10);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "消费：" + conditionTest.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, "Consumer-1");
        thread.start();

        Thread consumer2 = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "消费：" + conditionTest.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, "Consumer-2");
        consumer2.start();


        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {

                int index = 0;
                while (true) {
                    try {
                        Thread.sleep(1000 * 3);
                        int i = index++;
                        conditionTest.put(i);
                        System.out.println(Thread.currentThread().getName() + "生产：" + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "Producer");
        thread1.start();


    }
}
