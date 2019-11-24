package com.ido.design.adaptor;

/**
 *
 * 本例子是对象适配器
 *
 *
 * 类适配器，适配器 继承目标接口和被适配器类
 *
 * @author xu.qiang
 * @date 2019/11/24
 */
public class MonkeyAdaptor implements Person {

    private Monkey monkey;
    @Override
    public void smoking() {
        monkey.smoking();
    }


    public Monkey getMonkey() {
        return monkey;
    }

    public void setMonkey(Monkey monkey) {
        this.monkey = monkey;
    }
}
