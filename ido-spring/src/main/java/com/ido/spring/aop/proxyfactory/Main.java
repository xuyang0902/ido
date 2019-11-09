package com.ido.spring.aop.proxyfactory;

import com.ido.spring.aop.StudentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xu.qiang
 * @date 19/5/8
 */
public class Main {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:**/spring/aop-proxyfactory.xml");
        StudentService studentService = (StudentService) applicationContext.getBean("studentService",StudentService.class);


         studentService = (StudentService) applicationContext.getBean("studentService",StudentService.class);

        studentService.study();

    }

}
