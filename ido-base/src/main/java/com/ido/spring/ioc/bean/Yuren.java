package com.ido.spring.ioc.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xu.qiang
 * @date 19/10/30
 */
public class Yuren {

    private SimpleBean simpleBean;

    public SimpleBean getSimpleBean() {
        return simpleBean;
    }

    public void setSimpleBean(SimpleBean simpleBean) {
        this.simpleBean = simpleBean;
    }
}
