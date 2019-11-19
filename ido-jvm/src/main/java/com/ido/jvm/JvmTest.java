package com.ido.jvm;

/**
 * @author xu.qiang
 * @date 19/11/19
 */
public class JvmTest {

    public static void main(String[] args) {

        while(true){
            byte[] M_1 = new byte[1024*1024];

            try {
                Thread.sleep(1000*5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
