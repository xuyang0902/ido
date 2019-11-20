package com.ido.agent;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;


/**
 *
 *
 * ClassFileTransformer  一个提供此接口的实现以转换类文件的代理。转换在 JVM 定义类之前发生。
 *
 *

 *
 *
 *
 */
public class Transformer implements ClassFileTransformer {


    /**
     * @param loader  定义要转换的类加载器；如果是引导加载器，则为 null
     * @param className  完全限定类内部形式的类名称和 The Java Virtual Machine Specification 中定义的接口名称。例如，"java/util/List"。
     * @param classBeingRedefined 如果是被重定义或重转换触发，则为重定义或重转换的类；如果是类加载，则为 null
     * @param protectionDomain 要定义或重定义的类的保护域
     * @param classBytes 类文件格式的输入字节缓冲区（不得修改）
     * @return
     * @throws IllegalClassFormatException
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classBytes) throws IllegalClassFormatException {
        byte[] transformed = null;

        ClassPool pool = null;
        CtClass cl = null;
        try {

            if(!className.startsWith("com/ido/javassist")){
                return null;
            }

            pool = ClassPool.getDefault();

            cl = pool.makeClass(new java.io.ByteArrayInputStream(classBytes));


            if (!cl.isInterface()) {

                System.out.println("Transforming " + className);

                CtMethod[] methods = cl.getDeclaredMethods();
                for (int i = 0; i < methods.length; i++) {
                    if (methods[i].isEmpty() == false) {
                        AOPInsertMethod(methods[i]);
                    }
                }
                transformed = cl.toBytecode();
            }

            return transformed;
        } catch (Exception e) {
            System.err.println("Could not instrument  " + className
                    + ",  exception : " + e.getMessage());
            e.printStackTrace();
            return transformed;
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }

    }

    private void AOPInsertMethod(CtMethod method) throws NotFoundException, CannotCompileException {
        //situation 1:添加监控时间
        method.instrument(new ExprEditor() {

            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                m.replace("{ long stime = System.currentTimeMillis(); $_ = $proceed($$);System.out.println(\""
                        + m.getClassName() + "." + m.getMethodName()
                        + " cost:\" + (System.currentTimeMillis() - stime) + \" ms\");}");
            }
        });
    }
}