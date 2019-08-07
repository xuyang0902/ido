package com.ido.popular.spring.ioc;

import com.ido.popular.spring.ioc.bean.SimpleBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xu.qiang
 * @date 19/5/8
 */
public class Main {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:**/spring/ioc-simple.xml");
        SimpleBean simpleBean = (SimpleBean) applicationContext.getBean("simpleBean",SimpleBean.class);
        System.out.println(simpleBean.getAge());


        simpleBean = (SimpleBean) applicationContext.getBean("simpleBean",SimpleBean.class);
        System.out.println(simpleBean.getAge());

    }

}
