package com.ido.jvm.reference;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.lang.ref.*;
import java.lang.reflect.Field;

/**
 * @author xu.qiang
 * @date 19/12/6
 */
public class JvmTest {


    public static void main(String[] args) throws InterruptedException {

        System.out.println("----------软应用 内存不足才会触发回收 用的也很少--------");
        testSoft();

        System.out.println("---------弱应用 gc回收  用的很少啊 ---------");

        testWeak();
        System.out.println("--------虚引用  gc回收后 队列中会有一个通知 一般用于资源回收 ----------");


        testPhantom();
    }


    /**
     * gc不会直接回收软引用的，只有当内存不足的时候才会回收
     */
    public static void testSoft() {

        SoftReference<String> softReference = new SoftReference<String>(new String("soft"));

        System.out.println(softReference.get());

        byte[] haha = new byte[1024 * 1024 * 600];
        System.gc();
        System.out.println(softReference.get());
    }


    /**
     * 垃圾回收的时候 弱应用直接回收
     */
    public static void testWeak() {

        WeakReference<String> weakReference = new WeakReference<String>(new String("weak"));
        System.out.println(weakReference.get());

        System.gc();
        ;
        System.out.println(weakReference.get());

    }


    public static void testPhantom() throws InterruptedException {


        String s = new String("phantom");

        System.out.println(s.hashCode());

        ReferenceQueue<String> referenceQueue = new ReferenceQueue<String>();
        PhantomReference<String> phantomReference = new PhantomReference<String>(s, referenceQueue);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    Object poll = referenceQueue.poll();

                    if (poll != null) {


                        try {
                            Field rereferent = Reference.class
                                    .getDeclaredField("referent");

                            rereferent.setAccessible(true);
                            Object result = rereferent.get(poll);
                            System.out.println("被gc回收了 collect："
                                    + result.getClass() + "@"
                                    + result.hashCode() + "\t"
                                    + (String) result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        }).start();


        //强引用需要丢掉 才能被回收
        s = null;

        System.gc();

        Thread.sleep(1000 * 10);


    }
}
