package com.ido.jvm.classloader;


/**
 * @author xu.qiang
 * @date 18/11/7
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * ClassLoader类加载器，主要的作用是将class文件加载到jvm虚拟机中。
 * jvm启动的时候，并不是一次性加载所有的类，而是根据需要动态去加载类，主要分为隐式加载和显示加载。
 * 隐式加载：程序代码中不通过调用ClassLoader来加载需要的类，而是通过JVM类自动加载需要的类到内存中。
 * 例如，当我们在类中继承或者引用某个类的时候，JVM在解析当前这个类的时，发现引用的类不在内存中，那么就会自动将这些类加载到内存中。
 * 显示加载：代码中通过Class.forName（），this.getClass.getClassLoader.LoadClass（），
 * 自定义类加载器中的findClass（）方法等。
 * <p>
 * <p>
 * ps:加载器不一样，同一份class文件加载后生成的类是不相等的
 * @see https://blog.csdn.net/briblue/article/details/54973413 写的还不错的博客
 * @see https://www.cnblogs.com/gdpuzxs/p/7044963.html
 */
public class Launcher {

    public static void main(String[] args) throws Exception {


        System.out.println("sun.boot.class.path:");
        System.out.println(System.getProperty("sun.boot.class.path"));

        System.out.println("java.ext.dirs");
        System.out.println(System.getProperty("java.ext.dirs"));

        System.out.println("java.class.path");
        System.out.println(System.getProperty("java.class.path"));

        System.out.println("Launcher ---> " + Launcher.class.getClassLoader());
        System.out.println("Launcher ---> " + Launcher.class.getClassLoader().getParent());
        System.out.println("Launcher ---> " + Launcher.class.getClassLoader().getParent().getParent());
        System.out.println("String ---> " + String.class.getClassLoader());
        System.out.println("current context classloader ---> " + Thread.currentThread().getContextClassLoader());


        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>   ");




    }
}
