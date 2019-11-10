package com.ido.design.singleton;
/**
 * lazy loading
 * 懒汉式
 * 虽然达到了按需初始化的目的，但却带来线程不安全的问题
 * 可以通过synchronized解决，但也带来效率下降
 * 这是目前好的解决方法之一
 */
public class Mgr03 {
    private static volatile Mgr03 INSTANCE;
    private Mgr03(){

    }
    public static Mgr03 getInstance(){
        if (INSTANCE == null){
            //双重检查
            synchronized (Mgr03.class){
                //拿到锁之后如果还为空那就不执行了，为空执行不下去了
                if (INSTANCE == null){
                    INSTANCE = new Mgr03();
                }
            }
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Mgr03.getInstance().hashCode());
                }
            }).start();
        }
    }
}
