package com.ido.design.prototype.v2;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        Person p1 = new Person();
        Person p2 = (Person) p1.clone();
        System.out.println(p2.age + " " +p2.score + " " +p2.name);
        System.out.println(p2.location);
        p1.age = 30;
        p1.name = "msb";
        System.out.println(p2.age + " " +p2.score + " " +p2.name);

        System.out.println(p1.location == p2.location);
        p1.location.street = "sh";
        System.out.println(p2.location.street);
    }
}
