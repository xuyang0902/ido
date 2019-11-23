package com.ido.design.factorysimple;

/**
 *
 * 简单工厂
 *
 * 指定类型就创建什么
 *
 * 需要改代码 每次都要改
 * @author xu.qiang
 * @date 19/11/23
 */
public class Factory {


    public Car getCar(String name) {
        if (name.equals("LK")) {
            return new LKCar();
        } else if (name.equals("BWM")) {
            return new BMWCar();
        }

        return null;
    }
}
