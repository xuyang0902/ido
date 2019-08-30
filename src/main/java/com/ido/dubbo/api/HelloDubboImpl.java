package com.ido.dubbo.api;

/**
 * @author xu.qiang
 * @date 18/11/28
 */
public class HelloDubboImpl implements HelloDubbo {
    @Override
    public String sayHello(String name) {

        System.out.println("log..hello Dubbo:" + name);

        return "hello_" + name;
    }
}
