package com.ido.spring.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xu.qiang
 * @date 19/5/8
 */
public class Main {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/ioc-simple.xml");
        SimpleBean simpleBean = (SimpleBean) applicationContext.getBean("simpleBean",SimpleBean.class);
        System.out.println(simpleBean.getAge());

    }

}
