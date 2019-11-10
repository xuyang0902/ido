package com.ido.design.proxy.staticProxy;

public class TankLogProxy implements Movable{
   /* Tank tank;
    public TankLogProxy(Tank tank){
        this.tank = tank;
    }*/
   Movable movable;
   public TankLogProxy(Movable movable){
       this.movable = movable;
   }
    @Override
    public void move() {
        System.out.println("Tank start moving ...");
        movable.move();
        System.out.println("Tank stoped ...");
    }
}
