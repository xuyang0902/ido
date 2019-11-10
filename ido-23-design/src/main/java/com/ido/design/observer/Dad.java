package com.ido.design.observer;

public class Dad implements Observer {
    @Override
    public void actionOnWakeUp(WakeUpEvent wakeUpEvent) {
        feed();
    }
    public void feed(){
        System.out.println("dad feeding ...");
    }
}

