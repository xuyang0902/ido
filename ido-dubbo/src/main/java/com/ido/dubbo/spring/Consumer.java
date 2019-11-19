package com.ido.dubbo.spring;

import com.ido.dubbo.debug.api.HelloDubbo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xu.qiang
 * @date 19/11/19
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo-consumer.xml");
        context.start();

        HelloDubbo helloDubbo = (HelloDubbo)context.getBean("helloDubbo", HelloDubbo.class);

        String s = helloDubbo.sayHello("Ping");
        System.out.println(s);
    }
}
