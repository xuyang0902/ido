package com.ido.spring.ioc.bean;


import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xu.qiang
 * @date 19/5/8
 */
public class SimpleBean {

    private Yuren yuren;

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

    public Yuren getYuren() {
        return yuren;
    }

    public void setYuren(Yuren yuren) {
        this.yuren = yuren;
    }
}
