package com.ido.design.observer;

/**
 * @author xu.qiang
 * @date 19/11/23
 */
public class MessageObserver implements Observer {
    @Override
    public void update(Object args) {
        System.out.println("-->>>MessageObserver观察者收到消息 args:" + args);
    }
}
