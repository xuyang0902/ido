package com.ido.spring.ioc;

import com.ido.spring.ioc.bean.SimpleBean;
import com.ido.spring.ioc.bean.Yuren;
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
        System.out.println(simpleBean.getYuren());

        Yuren yuren = applicationContext.getBean("yuren", Yuren.class);

        System.out.println( yuren.getSimpleBean());

    }

}
