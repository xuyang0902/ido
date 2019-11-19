package com.ido.javassist;

/**
 * @author xu.qiang
 * @date 19/11/19
 */
public class TestAgentAppRun6 {

    public static void main(String[] args) {



        while(true){

            try {
                Thread.sleep(1000*5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("1111");
        }
    }
}
