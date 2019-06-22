package com.ido;

/**
 * ido
 *
 * @author xu.qiang
 * @date 19/4/25
 */
public class Ido {

    public static void main(String[] args) {

        Integer a = 127;

        int b = 127;

        System.out.println(a == b);


        System.out.println(Ido.get());

        //打印单数

        System.out.println("begin");

        //outloop这个标示是可以自定义的比如outloop1,ol2,out5
        outloop:
        for (int j = 0; j < 10; j++) {
            System.out.println("-->" + j);
            if (1 == j % 2) {
                break outloop; //如果是双数，结束外部循环
            }
        }

        System.out.println("end");


    }


    public static String get() {

        try {

            System.out.println("--->do");
            return "do";
        } finally {

            System.out.println("--->finally");
            return "finally";
        }
    }

}
