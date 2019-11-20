package com.ido.design.flyweight;

/**
 * java中String使用的就是享元模式
 */
public class TestString {

    static{
        System.out.println("static{}");
    }

    public TestString() {
        System.out.println("()");
    }

    {
        System.out.println("{}");
    }

    public static void main(String[] args) {

        new TestString();

       //


        String s3 = new String("计算机基础");
        String intern = s3.intern();

        String s4 = new String("计算机基础");
        System.out.println(s4.intern() == intern);




    }


}
