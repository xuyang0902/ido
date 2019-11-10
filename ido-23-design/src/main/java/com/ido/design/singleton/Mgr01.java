package com.ido.design.singleton;

/**
 * 饿汉式
 */
public class Mgr01 {
    private static final Mgr01 INSTANCE = new Mgr01();
    private Mgr01(){};
    private static Mgr01 getInstance(){
        return INSTANCE;
    }
    public static void main(String[] args) {
        Mgr01 mgr01 = Mgr01.getInstance();
        Mgr01 mgr011 = Mgr01.getInstance();
        System.out.println(mgr01 == mgr011);
    }

}
