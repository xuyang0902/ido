package com.ido.sentinel;

/**
 * @author xu.qiang
 * @date 19/12/3
 */
public class Action implements Runnable {

    private long costTime;

    public Action(long costTime) {
        this.costTime = costTime;
    }

    @Override
    public void run() {

        doAwait();

        if(!Thread.currentThread().isInterrupted()){
            System.out.printf("[%s] Action cost time : %s \n", Thread.currentThread().getName(), costTime);
        }

    }


    private void doAwait() {

        try {
            Thread.sleep(costTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
