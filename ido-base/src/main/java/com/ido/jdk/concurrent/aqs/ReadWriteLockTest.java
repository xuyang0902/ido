package com.ido.jdk.concurrent.aqs;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * <p>
 * <p>
 * 读读不互斥
 * <p>
 * 读写互斥
 * <p>
 * 写写互斥
 *
 * 其实写锁 就是用的lock的逻辑
 * 读锁设计到shared的代码，不再一一分析，有点复杂
 *
 * @author xu.qiang
 * @date 19/4/11
 */
public class ReadWriteLockTest {

    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    volatile String data = null;


    public void putData(String data) {
        writeLock.lock();
        try {
            this.data = data;
        } finally {
            writeLock.unlock();
        }
    }


    public void getData() {
        readLock.lock();
        try {
            this.data = data;
        } finally {
            readLock.unlock();
        }
    }

    public String putAndGetData(String data) {

        writeLock.lock();
        try {
            this.data = data;
        } finally {

            //释放写锁前  拿到读锁，别的线程不能写数据，，那么我就可以拿着这个数据去做操作了，不担心释放写锁后 可能被别的线程先拿到写锁改了数据
            readLock.lock();
            writeLock.unlock();
            try {
                System.out.println(">> 肯定是我改的数据，否则有可能不是我改的数据啊啊啊啊啊  data:" + data);
                return this.data;
            } finally {
                readLock.unlock();
            }
        }

    }


}
