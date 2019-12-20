package com.ido.jdk.always;

public class BlockQueue2 {

    Object[] items;
    int count;
    int putIndex;
    int takeIndex;

    Object monitor = new Object();

    public BlockQueue2(int capacity) {
        items = new Object[capacity];

    }


    public void put(Object object) throws InterruptedException {

        synchronized (monitor) {

            //队列已经满了，等待 且释放锁
            while (count == items.length) {
                monitor.wait();
            }

            items[putIndex] = object;
            putIndex++;
            count++;
            if (putIndex == items.length) {
                putIndex = 0;
            }

            monitor.notify();
        }


    }

    public Object take() throws InterruptedException {

        synchronized (monitor) {

            //没有可以拿的数据，等待 且释放锁
            while (count == 0) {
                monitor.wait();
            }

            Object res = items[takeIndex];
            items[takeIndex] = null;
            takeIndex++;
            if (takeIndex == items.length) {
                takeIndex = 0;
            }
            count--;

            monitor.notify();
            return res;
        }

    }


    public static void main(String[] args) {

        BlockQueue2 my = new BlockQueue2(100);


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