package com.ido.sentinel;

import java.util.concurrent.*;

/**
 * @author xu.qiang
 * @date 19/12/3
 */
public class SemaphoreBreakDemo {

    public static void main(String[] args) {


        Semaphore semaphore =new Semaphore(5);

        ExecutorService threadPool = Executors.newFixedThreadPool(10);


        for(int index = 0 ;index < 10;index++){

            threadPool.submit(new Runnable() {
                @Override
                public void run() {

                    try {
                        semaphore.acquire();

                        new Action(1000 * 5).run();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        semaphore.release();;
                    }
                }
            });
        }


        threadPool.shutdown();




    }
}
