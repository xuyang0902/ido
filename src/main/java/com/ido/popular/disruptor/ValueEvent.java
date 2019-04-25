package com.ido.popular.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * 环装数组 位置的数据对象
 *
 * @author xu.qiang
 * @date 2019/4/25
 */
public final class ValueEvent {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    /**
     * 数据对象数据工厂
     */
    public final static EventFactory<ValueEvent> EVENT_FACTORY = new EventFactory<ValueEvent>() {
        @Override
        public ValueEvent newInstance() {
            return new ValueEvent();
        }
    };


}