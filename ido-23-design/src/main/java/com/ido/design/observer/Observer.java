package com.ido.design.observer;

/**
 * 把观察者扔到需要被观察者对象中，只要被观察者有变化 及时通知到观察者
 *
 * 被观察者提供观察者的注册 移除 通知方法
 *
 *
 * @author xu.qiang
 * @date 19/11/23
 */
public interface Observer {

    void update(Object args);
}
