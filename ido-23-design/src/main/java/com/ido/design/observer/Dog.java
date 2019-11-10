package com.ido.design.observer;

public class Dog implements Observer{

    @Override
    public void actionOnWakeUp(WakeUpEvent wakeUpEvent) {
        wang();
    }
    public void wang(){
        System.out.println("dog wang ...");
    }
}
