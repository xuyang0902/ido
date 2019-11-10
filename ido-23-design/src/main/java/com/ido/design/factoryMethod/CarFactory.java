package com.ido.design.factoryMethod;

/**
 工厂方法

 我们可以针对每一种产品来做一个工厂。这种方法叫工厂方法。

 举例：工厂方法，实现任意定制生产过程。
 */
public class CarFactory {
    public Moveable create(){
        System.out.println("生产...");
        return new Car();
    }
}
