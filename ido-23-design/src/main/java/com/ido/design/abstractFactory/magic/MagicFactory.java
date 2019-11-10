package com.ido.design.abstractFactory.magic;

import com.ido.design.abstractFactory.AbstracFactory;
import com.ido.design.abstractFactory.Food;
import com.ido.design.abstractFactory.Vehicle;
import com.ido.design.abstractFactory.Weapon;

public class MagicFactory extends AbstracFactory {
    @Override
    public Food createFood() {
        return new MushRoom();
    }

    @Override
    public Vehicle createVehicle() {
        return new Broom();
    }

    @Override
    public Weapon createWeapon() {
        return new MagicStick();
    }
}
