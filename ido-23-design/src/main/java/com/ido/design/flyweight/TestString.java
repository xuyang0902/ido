package com.ido.design.flyweight;

/**
 * java中String使用的就是享元模式
 */
public class TestString {
    public static void main(String[] args) {
        String s1 = "123";
        String s2 = "123";
        String s3 = new String("123");
        String s4 = new String("123");
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s3 == s4);
        System.out.println(s3.intern() == s1);
        System.out.println(s3.intern() == s4.intern());
    }


}
