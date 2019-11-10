package com.ido.design.proxy.staticProxy;

import java.util.Random;

public class Tank implements Movable{
    @Override
    public void move() {
        System.out.println("tank cla cla ...");
        try{
            //随机睡眠10秒钟，模拟它开了几秒钟
            Thread.sleep(new Random().nextInt(10000));
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
