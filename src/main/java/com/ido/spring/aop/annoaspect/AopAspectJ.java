package com.ido.spring.aop.annoaspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author xu.qiang
 * @date 19/1/3
 */
@Component
@Aspect
public class AopAspectJ {


    @Pointcut("execution(* com.ido.spring.aop.StudentService.study(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object doMethod(ProceedingJoinPoint pjp) throws Throwable {

        System.out.println("------before aspectj annotation-----");

        Object proceed = pjp.proceed();

        System.out.println("------after  aspectj annotation-----");

        return proceed;
    }

}
