package com.ido.design.proxy.staticProxy;

public class TankTimeProxy implements Movable{
    //Tank tank;
    Movable movable;
    public TankTimeProxy(Movable movable){
        this.movable = movable;
    }
    @Override
    public void move() {
        long start = System.currentTimeMillis();
        movable.move();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
