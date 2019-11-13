package com.ido.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xu.qiang
 * @date 19/11/12
 */
@Configuration
@PropertySource(value = "classpath:config/jdbc.properties")
public class DBConfig {


}
