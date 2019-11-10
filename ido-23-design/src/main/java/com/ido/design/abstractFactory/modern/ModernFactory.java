package com.ido.design.abstractFactory.modern;


import com.ido.design.abstractFactory.AbstracFactory;
import com.ido.design.abstractFactory.Food;
import com.ido.design.abstractFactory.Vehicle;
import com.ido.design.abstractFactory.Weapon;


public class ModernFactory extends AbstracFactory {
    @Override
    public Food createFood() {
        return new Bread();
    }

    @Override
    public Vehicle createVehicle() {
        return new Car();
    }

    @Override
    public Weapon createWeapon() {
        return new AK47();
    }
}
