package com.ido.design.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xu.qiang
 * @date 19/11/23
 */
public class MessageStore {

    private String message ;

    private List<Observer> messageObservers = new ArrayList<Observer>();

    public MessageStore() {

    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        messageUpdate();
    }

    public void messageUpdate(){
        //通知所有观察者
        notifyObserver();
    }

    /**
     * 观察者注册
     * @param observer
     */
    public void registerObserver(Observer observer){
        if(!messageObservers.contains(observer)){
            this.messageObservers.add(observer);
        }
    }

    /**
     * 移除观察者
     * @param observer
     */
    public void removeObserver(Observer observer){
        if(messageObservers.contains(observer)){
            this.messageObservers.remove(observer);
        }
    }

    /**
     * 信息变化 通知观察者
     */
    public void notifyObserver(){
        for (Observer observer : messageObservers) {
            observer.update(this.getMessage());
        }
    }
}
