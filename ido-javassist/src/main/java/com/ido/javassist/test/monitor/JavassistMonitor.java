package com.ido.javassist.test.monitor;

import javassist.*;

/**
 * @author xu.qiang
 * @date 19/11/20
 */
public class JavassistMonitor {

    public static void main(String[] args) throws Exception {

        ClassPool pool = new ClassPool(true);

        //！！！！！！！！！！！
        //！！！！！！！！！！！
        //！！！！！！！！！！！
        //这里必须注意，如果直接用类引用，直接报废，相当于已经加载过class了,不能重复加载
        CtClass targetClass = pool.get("com.ido.javassist.test.monitor.SayHello");
        CtMethod method = targetClass.getDeclaredMethod("hello");
        //copy原来的方法 形成新方法
        CtMethod agentMethod = CtNewMethod.copy(method, "helloAgent", targetClass, null);
        targetClass.addMethod(agentMethod);

        String src = "{" +
                "long begin =  System.nanoTime();" +
                "helloAgent($$);" +
                "long end =  System.nanoTime();" +
                "System.out.println(end -begin);" +
                "return;" +
                "}";
        //重写原来的方法
        method.setBody(src);

        //载入当前classLoder
        targetClass.toClass();

        //把字节码写到指定目录查看下
        targetClass.writeFile("/usr/local/tmp/");


        //这个时候 加载的SayHello是替换了字节码的class
        SayHello hello = new SayHello();
        hello.hello("KK");

    }


}
