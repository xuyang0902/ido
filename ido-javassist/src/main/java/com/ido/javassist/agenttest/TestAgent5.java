package com.ido.javassist.agenttest;

import com.ido.javassist.agenttest.model.Person;

/**
 *
 * vm参数
 *
 * -javaagent:/Users/tbj/.m2/repository/com/ido/ido-javaagent5/1.0/ido-javaagent5-1.0.jar
 *
 *
 * @author xu.qiang
 * @date 19/11/19
 */
public class TestAgent5 {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        System.out.println("--->>>haha");


        TestAgent5 agent5 = new TestAgent5();
        agent5.setName("agent5");

        System.out.println(agent5.getName());


        /**
         * 类加载的时候会调用转换器 加强这个类
         */
        Person person = new Person();
        person.setName("yuren");
        person.setAge(18);
        person.setSex(1);

        System.out.println(person.getName());

    }


    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
