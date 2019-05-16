package com.ido.jdk.cas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author xu.qiang
 * @date 19/5/10
 */
public class CasMain {

    public static void main(String[] args) {


        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(16);



        int loop =10000000;

        final AtomicLong num = new AtomicLong(0);
        long begin = System.nanoTime();
        while(loop-- > 0){
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    num.incrementAndGet();
                }
            });

        }
        long end = System.nanoTime();
        System.out.println( (end-begin)/1000/1000 +"ms");



        loop =10000000;
        final CasMain casMain = new CasMain();
        begin = System.nanoTime();
        while(loop-- > 0){
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    casMain.incr();
                }
            });
        }
        end = System.nanoTime();
        System.out.println( (end-begin)/1000/1000 +"ms");

    }


    public int num = 0;

    public synchronized int incr(){
        return num++;
    }
}
