package com.ido.spring.ioc;



/**
 * @author xu.qiang
 * @date 19/5/8
 */
public class SimpleBean {


    public static String name = "haha";

    {
        System.out.println(">>>>SimpleBean 方法块执行");
    }

    static {
        System.out.println(">>>>SimpleBean static块 执行");
    }

    public SimpleBean() {
        System.out.println(">>>>SimpleBean 构造器执行");
    }

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        System.out.println(">>>>SimpleBean setAge :" + age);
        this.age = age;
    }
}
