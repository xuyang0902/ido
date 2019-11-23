package com.ido.design.factorymethod;

import com.ido.design.factorysimple.Car;

/**
 * @author xu.qiang
 * @date 19/11/23
 */
public class Test {

    private ICarFactory ICarFactory;

    public ICarFactory getICarFactory() {
        return ICarFactory;
    }

    public void setICarFactory(ICarFactory ICarFactory) {
        this.ICarFactory = ICarFactory;
    }


    /**
     * 应用的时候 给什么工厂 就生产什么
     *
     * 每次要新增一个工厂，新增工厂就好了，替换掉传进来的这个carfactory就可以
     *
     *
     * @return
     */
    public Car getCar(){
       return ICarFactory.createCar();
    }

}
