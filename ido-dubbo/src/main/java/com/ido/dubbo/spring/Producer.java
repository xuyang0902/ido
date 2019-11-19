package com.ido.dubbo.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * 实际就是在api的方式下面封装了springxml配置的解析
 *
 * @see DubboNamespaceHandler
 *
 * @author xu.qiang
 * @date 19/11/19
 */
public class Producer {


    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo-producer.xml");
        context.start();
        System.in.read(); // 按任意键退出
    }


}
