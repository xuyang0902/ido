package com.ido.design.singleton;

/**
 * 懒汉模式
 */
public class Mgr02 {
    private static Mgr02 INSTANCE;
    private Mgr02(){}
    private static Mgr02 getInstance(){
        if (INSTANCE ==null){
            //sleep一下 ,暴露这种单例模式线程不安全的缺点
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            INSTANCE = new Mgr02();
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        /*Mgr02 mgr02 = Mgr02.getInstance();
        Mgr02 mgr021 = Mgr02.getInstance();
        System.out.println(mgr02 == mgr021);*/
        for (int i = 0; i < 100; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Mgr02.getInstance().hashCode());
                }
            }).start();
        }
    }






}
