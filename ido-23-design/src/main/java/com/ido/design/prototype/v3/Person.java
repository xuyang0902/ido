package com.ido.design.prototype.v3;

public class Person implements Cloneable{
    int age = 8;
    int score = 100;
    String name = "zkg";
    Location location = new Location(new StringBuffer("bj"),22);

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Person p = (Person)super.clone();
        p.location = (Location)location.clone();
        return p;
    }
}
