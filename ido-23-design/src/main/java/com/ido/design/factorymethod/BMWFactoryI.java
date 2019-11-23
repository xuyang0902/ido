package com.ido.design.factorymethod;

import com.ido.design.factorysimple.BMWCar;
import com.ido.design.factorysimple.Car;

/**
 * @author xu.qiang
 * @date 19/11/23
 */
public class BMWFactoryI implements ICarFactory {
    @Override
    public Car createCar() {
        return new BMWCar();
    }
}
