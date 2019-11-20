package com.ido.agent;


import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;


/**
 * jdk6新特性
 * <p>
 * 在 Java SE 5 的基础上，Java SE 6 针对这种状况做出了改进，开发者可以在 main 函数开始执行以后，
 * 再启动自己的 Instrumentation 程序。
 *
 * @author xu.qiang
 * @date 2018-11-07
 * com.ido.javassist.TestAgent6  直接绑定进程id 就可以在指定进程中执行 agent的代码
 */
public class MainAgent {

    public static void agentmain(String args, Instrumentation instrumentation) throws UnmodifiableClassException {

        System.out.println("Hello MainAgent args: " + args);

        //jvm跑起来了还能改么？
        instrumentation.addTransformer(new ClassFileTransformer() {

            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

                System.out.println(">>>>转换器调用了，className:" + className);

                /**
                 * 对重定义的类有限制，
                 *
                 *
                 父类是同一个；
                 实现类的接口数也要相同；
                 类访问符必须一致；
                 字段数和字段名必须一致；
                 新增的方法必须是 private static/final 的；
                 可以删除修改方法；
                 */

                return classfileBuffer;
            }
        }, true/*可重新转换*/);


        //当前 jvm一共加载类多少class
        Class[] classes = instrumentation.getAllLoadedClasses();
        for (Class cls : classes) {
            System.out.println(cls.getName());

            if (cls.getName().contains("Person")) {
                //重转换类需要执行类
                instrumentation.retransformClasses(cls);
            }

        }


    }
}