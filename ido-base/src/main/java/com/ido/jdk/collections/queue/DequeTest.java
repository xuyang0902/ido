package com.ido.jdk.collections.queue;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xu.qiang
 * @date 19/8/27
 */
public class DequeTest {

    public static void main(String[] args) throws InterruptedException {

        PriorityBlockingQueue<Integer> integers = new PriorityBlockingQueue<Integer>();

        integers.add(1);
        integers.add(20);
        integers.add(10);


        System.out.println(integers.take());
        System.out.println(integers.take());
        System.out.println(integers.take());


        SynchronousQueue synchronousQueue = new SynchronousQueue();

        synchronousQueue.put(1);

        System.out.println("--->");
        synchronousQueue.put(1);
        System.out.println("--->>>");

        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();

        atomicInteger.decrementAndGet();


    }
}
