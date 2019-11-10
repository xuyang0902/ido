package com.ido.design.prototype.v1;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        Person p1 = new Person();
        Person p2 = (Person) p1.clone();
        System.out.println(p2.age + " " +p2.score);
        System.out.println(p2.location);
        p1.age = 30;
        System.out.println(p2.age);


        System.out.println(p1.age == p2.age);
        System.out.println(p1.location == p2.location);
        p1.location.street = "sh";
        System.out.println(p2.location.street);
    }
}
