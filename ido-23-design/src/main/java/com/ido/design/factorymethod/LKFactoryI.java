package com.ido.design.factorymethod;

import com.ido.design.factorysimple.Car;
import com.ido.design.factorysimple.LKCar;

/**
 * @author xu.qiang
 * @date 19/11/23
 */
public class LKFactoryI implements ICarFactory {
    @Override
    public Car createCar() {
        return new LKCar();
    }
}
