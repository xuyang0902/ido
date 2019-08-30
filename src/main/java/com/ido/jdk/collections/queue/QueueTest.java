package com.ido.jdk.collections.queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author xu.qiang
 * @date 19/8/27
 */
public class QueueTest {

    public static void main(String[] args) {



        /**
         * 先进先出，单向链表
         * 	    抛出异常	    返回特殊值
         插入	add(e)	    offer(e)     添加一个元素
         移除	remove()	poll()       移除头部元素
         检查	element()	peek()       返回头部元素

         add        增加一个元索                     如果队列已满，则抛出一个IIIegaISlabEepeplian异常
     　　remove   移除并返回队列头部的元素    如果队列为空，则抛出一个NoSuchElementException异常
     　　element  返回队列头部的元素             如果队列为空，则抛出一个NoSuchElementException异常
     　　offer       添加一个元素并返回true       如果队列已满，则返回false
     　　poll         移除并返问队列头部的元素    如果队列为空，则返回null
     　　peek       返回队列头部的元素             如果队列为空，则返回null
     　

       　put         添加一个元素                      如果队列满，则阻塞
     　　take        移除并返回队列头部的元素     如果队列为空，则阻塞
         *
         */



        LinkedList linkedList = new LinkedList();

        linkedList.addLast(2);
        linkedList.addLast(3);
        linkedList.addFirst(1);

        linkedList.remove();
        System.out.println(linkedList.pop());
        System.out.println(linkedList.pop());

    }
}
