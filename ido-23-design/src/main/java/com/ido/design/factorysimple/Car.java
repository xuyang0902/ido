package com.ido.design.factorysimple;

/**
 * @author xu.qiang
 * @date 19/11/23
 */
public class Car {

    private String name;

    public Car() {
    }

    public Car(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
