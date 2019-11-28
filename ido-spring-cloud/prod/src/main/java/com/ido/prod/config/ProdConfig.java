package com.ido.prod.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author xu.qiang
 * @date 19/11/28
 */
@Component
@PropertySource(value = "config.properties")
@ConfigurationProperties(prefix = "prod")
public class ProdConfig {


    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
