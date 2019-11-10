package com.ido.design.observer;
//事件类 表示婴儿睡醒这个事件
public class WakeUpEvent {
    long timestamp;
    String loc;
    Child source;
    public WakeUpEvent(long timestamp, String loc,Child source){
        this.timestamp = timestamp;
        this.loc = loc;
        this.source = source;
    }
}
