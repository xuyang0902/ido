package com.ido.spring.aop.proxycreator;

import com.ido.spring.aop.StudentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xu.qiang
 * @date 19/5/5
 */
public class Main {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:**/spring/aop-proxycreator.xml");
        StudentService studentService = (StudentService) applicationContext.getBean("studentService",StudentService.class);
        studentService.study();

    }
}
