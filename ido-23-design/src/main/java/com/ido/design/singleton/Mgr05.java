package com.ido.design.singleton;

public enum Mgr05 {
    //唯一的取值，下面的调用只能调用这一个，没有争议
    INSTANCE,INSTANCE1;
    //剩下的都是业务方法
    public void m() {
        System.out.println("m方法...");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Mgr05.INSTANCE.hashCode());
                }
            }).start();
        }
        for (int i = 0; i < 100; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Mgr05.INSTANCE1.hashCode());
                }
            }).start();
        }
        Mgr05.INSTANCE.m();
    }
}
