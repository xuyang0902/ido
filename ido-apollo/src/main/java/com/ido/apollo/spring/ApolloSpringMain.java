package com.ido.apollo.spring;

import com.ido.apollo.spring.config.Application;
import com.ido.apollo.spring.config.TradeConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author xu.qiang
 * @date 19/11/14
 */
public class ApolloSpringMain {

    public static void main(String[] args) throws InterruptedException {


        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/spring/spring-context.xml");


        while(true){

            Thread.sleep(1000*5);

            System.out.println(context.getBean(TradeConfig.class).getTradeSwitch());
            System.out.println(context.getBean(Application.class).getApp_name());
        }






    }

}
