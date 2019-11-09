package com.ido.jdk.jvm;

import java.util.ArrayList;
import java.util.List;

/**


 -Xmn10m
 -Xmx50m
 -Xms50m
 -XX:+PrintGC
 -XX:+PrintGCDetails
 -XX:+PrintGCTimeStamps
 -XX:+PrintGCDateStamps
 -XX:+PrintHeapAtGC
 -Xloggc:/Users/tbj/usr/local/tmp/jvm/gc.log
 -XX:+UseParallelGC
 -XX:ParallelGCThreads=20


 * @author xu.qiang
 * @date 19/9/17
 */
public class JvmMain {

    public static void main(String[] args) {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                while(true){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    System.out.println("非守护线程 一直跑 就算oom了  应用不能停");
                }


            }
        });

        thread.setDaemon(false);
        thread.start();

        try{
            List<BigObject> list = new ArrayList<BigObject>();

            int count = 0;
            while (true) {
                list.add(new BigObject());


                System.out.println(">> count:" + count++);
                try {
                /*
                 * 每隔1秒 加1M 数据
                 */
                    Thread.sleep(1000 * 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }





    }


    static class BigObject {

        private byte[] m_1 = new byte[1024 * 1024];


        public BigObject() {
        }
    }


}
