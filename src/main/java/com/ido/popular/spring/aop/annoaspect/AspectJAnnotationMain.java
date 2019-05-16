package com.ido.popular.spring.aop.annoaspect;

import com.ido.popular.spring.aop.StudentService;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xu.qiang
 * @date 19/1/2
 */
public class AspectJAnnotationMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:**/spring/aop-annotation.xml");
        StudentService studentService = applicationContext.getBean("studentService",StudentService.class);
        studentService.study();



    }
}
