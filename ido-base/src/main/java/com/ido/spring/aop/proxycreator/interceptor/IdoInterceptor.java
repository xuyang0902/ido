package com.ido.spring.aop.proxycreator.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author xu.qiang
 * @date 19/5/5
 */
public class IdoInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(IdoInterceptor.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {


        try {
            logger.info(" clazz.method:{}  IdoInterceptor begin", methodInvocation.getThis().getClass().getName() + "." + methodInvocation.getMethod());

            return methodInvocation.proceed();

        } finally {
            logger.info(" clazz.method:{}  IdoInterceptor end", methodInvocation.getThis().getClass().getName() + "." + methodInvocation.getMethod());
        }

    }
}
