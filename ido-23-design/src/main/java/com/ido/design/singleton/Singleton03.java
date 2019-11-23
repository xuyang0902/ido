package com.ido.design.singleton;

/**
 *
 * 静态内部类保证单列
 * @author xu.qiang
 * @date 19/11/23
 */
public class   Singleton03 {


    private static class Mgr05Holder{
        private final static Singleton03 INSTANCE = new Singleton03();
    }
    public static Singleton03 getInstance(){
        return Mgr05Holder.INSTANCE;
    }



}
