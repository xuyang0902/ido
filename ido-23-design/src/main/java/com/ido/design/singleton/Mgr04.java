package com.ido.design.singleton;
/**
 * 静态内部类方式(JVM帮我们保证线程安全)
 * JVM保证单例
 * 加载外部类时不会加载内部类，这样可以实现懒加载
 */
public class Mgr04 {
    private Mgr04(){

    }
    private static class Mgr05Holder{
        private final static Mgr04 INSTANCE = new Mgr04();
    }
    public static Mgr04 getInstance(){
        return Mgr05Holder.INSTANCE;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Mgr04.getInstance().hashCode());
                }
            }).start();
        }
    }
}
