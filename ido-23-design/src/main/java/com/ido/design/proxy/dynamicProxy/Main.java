package com.ido.design.proxy.dynamicProxy;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        Tank tank = new Tank();
        Movable movable = (Movable) Proxy.newProxyInstance(
                Tank.class.getClassLoader(),
                new Class[]{Movable.class},
                new LoggerHandler(tank));
        movable.move();
    }
}
