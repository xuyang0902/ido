package com.ido.springmvc.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author xu.qiang
 * @date 19/11/12
 */
@Component
@PropertySource("classpath:hello-springmvc.properties")
public class HelloSpringMVC {


    @Value("${hello}")
    private String hello;

    @Value("${mvc}")
    private String mvc;

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    public String getMvc() {
        return mvc;
    }

    public void setMvc(String mvc) {
        this.mvc = mvc;
    }
}
