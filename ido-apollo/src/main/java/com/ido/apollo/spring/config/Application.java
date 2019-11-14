package com.ido.apollo.spring.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author xu.qiang
 * @date 19/11/14
 */
@Configuration
@EnableApolloConfig
public class Application {


    @Value("${app_name:kkk}")
    String app_name;


    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }


    @ApolloConfigChangeListener("application")
    public void change(ConfigChangeEvent changeEvent) {

        Set<String> strings = changeEvent.changedKeys();

        for (String string : strings) {

            System.out.println("key:" + string + "value:" + changeEvent.getChange(string));

        }
    }
}
