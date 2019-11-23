package com.ido.design.singleton;

/**
 *
 * 懒汉 双重检查 用volatile 需要是get
 * @author xu.qiang
 * @date 19/11/23
 */
public class Singleton02 {


    public volatile static Singleton02 singleton02;



    public static Singleton02 getInstance(){

        if(singleton02 == null){
            synchronized (Singleton02.class){
                if(singleton02 == null){
                    singleton02 = new Singleton02();
                }
                return singleton02;
            }
        }

        return singleton02;

    }

}
