package com.ido.sentinel;

import java.util.concurrent.*;

/**
 * @author xu.qiang
 * @date 19/12/3
 */
public class TimeOutBreakDemo {

    public static void main(String[] args) {


        long timeOut = 500;




        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        Future<?> submit = singleThreadExecutor.submit(new Action(1000 * 2));


        try {
            submit.get(timeOut,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            submit.cancel(true);
        }


        singleThreadExecutor.shutdown();

    }
}
