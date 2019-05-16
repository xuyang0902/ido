package com.ido.popular.spring.aop;

/**
 * @author xu.qiang
 * @date 19/5/8
 */
public class StudentServiceImpl implements StudentService{


    @Override
    public void study() {
        System.out.println("学生在学习");
    }
}
