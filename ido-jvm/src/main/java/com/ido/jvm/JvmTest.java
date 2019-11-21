package com.ido.jvm;

import java.util.Map;

/**
 * @author xu.qiang
 * @date 19/11/19
 */
public class JvmTest {

    public final int a ;

    {
        System.out.println("{}");
    }
    static{
        System.out.println("static{}");
    }


    public JvmTest() {
        a = 100;
        System.out.println(a);
    }

    public static void main(String[] args) {
        JvmTest jvmTest = new JvmTest();

        while(true){
            byte[] M_1 = new byte[100* 1024*1024];

            try {
                Thread.sleep(1000*5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
