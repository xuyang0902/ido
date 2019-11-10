package com.ido.design.factoryMethod;

public class Main {
    public static void main(String[] args) {
        /*Moveable car = new Car();
        car.go();*/
        /*Moveable plane = new plane();
        plane.go();*/
        /*Moveable car = new SimpleVehicleFactory().createCar();
        car.go();*/
        Moveable car = new CarFactory().create();
        car.go();
    }
}
