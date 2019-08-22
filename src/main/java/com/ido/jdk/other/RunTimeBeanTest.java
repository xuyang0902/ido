package com.ido.jdk.other;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @author xu.qiang
 * @date 19/8/19
 */
public class RunTimeBeanTest {

    public static void main(String[] args) throws InterruptedException {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName(); // format: "pid@hostname"


        System.out.println("main-->" + name);


        Thread.sleep(1000);


        new Thread(new Runnable() {
            @Override
            public void run() {
                RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
                String name = runtime.getName(); // format: "pid@hostname"


                System.out.println("Thread-->" + name);
            }
        }).start();


        Thread.sleep(10000);
    }
}
