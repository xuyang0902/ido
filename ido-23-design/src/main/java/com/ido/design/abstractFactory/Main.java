package com.ido.design.abstractFactory;

import com.ido.design.abstractFactory.magic.MagicFactory;
import com.ido.design.abstractFactory.modern.ModernFactory;

public class Main {
    public static void main(String[] args) {
        /*Car c = new Car();
        c.go();
        AK47 w = new AK47();
        w.shoot();
        Bread b = new Bread();
        b.printName();*/
        /*Broom b = new Broom();
        b.go();
        MagicStick m = new MagicStick();
        m.shoot();
        MushRoom r = new MushRoom();
        r.printName();*/
        /*AbstracFactory f = new ModernFactory();

        Food food = f.createFood();
        food.printName();
        Vehicle vehicle = f.createVehicle();
        vehicle.go();
        Weapon weapon = f.createWeapon();
        weapon.shoot();*/
        AbstracFactory f = new MagicFactory();
        Food food = f.createFood();
        food.printName();
        Vehicle vehicle = f.createVehicle();
        vehicle.go();
        Weapon weapon = f.createWeapon();
        weapon.shoot();
    }
}
