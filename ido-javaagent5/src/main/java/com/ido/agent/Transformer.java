package com.ido.agent;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class Transformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classBytes) throws IllegalClassFormatException {
        byte[] transformed = null;

        ClassPool pool = null;
        CtClass cl = null;
        try {

            if(!className.startsWith("com/ido/agent")){
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