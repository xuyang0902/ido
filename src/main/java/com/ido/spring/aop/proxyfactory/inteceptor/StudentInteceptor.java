package com.ido.spring.aop.proxyfactory.inteceptor;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author xu.qiang
 * @date 19/5/8
 */
public class StudentInteceptor implements MethodInterceptor {


    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        System.out.println(">>>> StudentInteceptor begin");

        Object proceed = methodInvocation.proceed();

        System.out.println(">>>> StudentInteceptor end");

        return proceed;
    }
}
