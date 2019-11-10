package com.ido.design.proxy.staticProxy;

public class Main {
    public static void main(String[] args) {
        /*TankTimeProxy tankTimeProxy = new TankTimeProxy(new Tank());
        tankTimeProxy.move();*/
        /*TankLogProxy tankLogProxy = new TankLogProxy(new Tank());
        tankLogProxy.move();*/
        new TankLogProxy(new TankTimeProxy(new Tank())).move();
    }
}
