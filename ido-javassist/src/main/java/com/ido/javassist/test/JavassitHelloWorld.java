package com.ido.javassist.test;

import javassist.*;

/**
 * @author xu.qiang
 * @date 19/11/20
 */
public class JavassitHelloWorld {

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException {
        ClassPool pool = ClassPool.getDefault();

        //创建class
        CtClass targetClass = pool.makeClass("com.ido.yuren.Hello");

        //实现Ihello
        targetClass.addInterface(pool.get(IHello.class.getName()));


        //构建方法
        CtClass returnType = pool.get(void.class.getName());
        String methodName = "sayHello";
        CtClass[] parameters = new CtClass[]{pool.get(String.class.getName())};
        CtMethod sayHello = new CtMethod(returnType, methodName, parameters, targetClass);
        sayHello.setBody("{ " +
                "System.out.println(\"hello -- \" + $1);" +//第一个参数
                "System.out.println($args[0]);" + //入参数组
                "System.out.println($type);" +// 返回结果类型class
                "System.out.println($class);" +//当前class
                "System.out.println($$);" + //所有的参数
                "return ;" +
                " }");

        //目标class加method
        targetClass.addMethod(sayHello);

        Class aClass = targetClass.toClass();
        IHello o = (IHello)aClass.newInstance();
        o.sayHello("雨人");
    }
}
