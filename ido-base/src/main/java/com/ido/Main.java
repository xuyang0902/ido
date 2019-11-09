package com.ido;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;

/**
 * @author xu.qiang
 * @date 19/10/31
 */
public class Main {

    static Thread threada = null;
    static Thread threadb = null;


    static Object object = new Object();
    static Object object1 = new Object();
    static Object object2 = new Object();
    public static void main(String[] args) {


        final char[] chars = "ABCDE".toCharArray();

        final char[] chars1 = "12345".toCharArray();



        threada = new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    synchronized (object){

                        while(true){
                            System.out.println("A");

                            wait();
                        }
                    }

                }catch (Exception e){

                }



            }
        });



        threadb = new Thread(new Runnable() {
            @Override
            public void run() {

                synchronized (object){



                }
            }
        });


        threada.start();
        threadb.start();

    }
}
