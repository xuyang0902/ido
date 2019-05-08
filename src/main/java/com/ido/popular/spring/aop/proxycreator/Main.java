package com.ido.popular.spring.aop.proxycreator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xu.qiang
 * @date 19/5/5
 */
public class Main {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:**/spring/aop-proxycreator.xml");
        BirdService birdService = applicationContext.getBean("birdServiceImpl", BirdService.class);
        birdService.fly("笨鸟  >> ");

    }
}
