package com.ido.design.singleton;

/**
 *
 * 饿汉 一开始就创建
 * @author xu.qiang
 * @date 19/11/23
 */
public class Singleton01 {


    public static Singleton01 singleton01 = new Singleton01();

    public static Singleton01 getInstance(){
        return singleton01;
    }

}
