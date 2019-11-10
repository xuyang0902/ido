package com.ido.design.proxy.dynamicProxy;

public class Tank implements Movable {
    @Override
    public void move() {
        System.out.println("move...");
    }
}
