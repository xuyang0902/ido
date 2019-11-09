package com.ido.netty.demo;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂
 *
 * @author xu.qiang
 * @date 18/4/12
 */
public class MyThreadFactory implements ThreadFactory {

    private AtomicInteger index = new AtomicInteger(0);

    private String threadNamePrefix = "Ido-";


    public MyThreadFactory() {
    }

    public MyThreadFactory(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, this.threadNamePrefix + index.incrementAndGet());
    }
}
