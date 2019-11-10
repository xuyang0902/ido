package com.ido.design.factoryMethod;
/*
简单工厂

当我们new一个交通工具的时候，如果有控制权限的要求，而每个对象的权限是不一样的。
飞机有飞机的权限，汽车有汽车的权限。
这时候用简单的面向对象的方式就不合适了。
这时候就要用工厂，让产生对象的过程不用new，交给工厂处理。
因为工厂可以灵活的控制生产过程，定制权限、修饰、日志。
 */
public class SimpleVehicleFactory {
    public Car createCar(){
        System.out.println("生产...");
        return new Car();
    }
    public Plane createPlane(){
        System.out.println("生产...");
        return new Plane();
    }
}
