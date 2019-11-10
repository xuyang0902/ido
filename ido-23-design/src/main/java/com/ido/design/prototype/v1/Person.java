package com.ido.design.prototype.v1;

public class Person implements Cloneable{
    int age = 8;
    int score = 100;
    Location location = new Location("bj",22);

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
