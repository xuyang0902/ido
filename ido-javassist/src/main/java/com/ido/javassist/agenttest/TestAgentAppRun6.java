package com.ido.javassist.agenttest;

import com.ido.javassist.agenttest.model.Person;

/**
 * @author xu.qiang
 * @date 19/11/19
 */
public class TestAgentAppRun6 {

    public static void main(String[] args) {



        while(true){

            try {
                Thread.sleep(1000*5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Person person = new Person();
            person.setName("yuren");

            System.out.println(person.getName());
        }
    }
}
