package com.ido.design.builder;

public class Main {
    public static void main(String[] args) {
        Person person = new Person.PersonBuilder()
                .baseInfo(1,"张三",18)
                .loc("bj","23")
                .score(100)
                .weight(60)
                .build();
        System.out.println(person);
    }
}
