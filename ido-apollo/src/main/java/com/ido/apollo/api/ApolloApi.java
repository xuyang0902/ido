package com.ido.apollo.api;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;

/**
 *


 java
 -Dapollo.meta=http://127.0.0.1:8080
 -Dapp.id=tz-trade

 Xxx.jar


 * @author xu.qiang
 * @date 19/11/14
 */
public class ApolloApi {

    public static void main( String[] args ) {


        System.setProperty("apollo.meta", "http://192.168.124.17:8080");
        System.setProperty("app.id", "tz-trade");

        System.out.println(System.getProperty("apollo.meta"));
        System.out.println(System.getProperty("app.id"));

        //application
        Config config = ConfigService.getAppConfig();
        System.out.println(config.getProperty("app_name", "XXXXXXX"));

        //监听远程的变化
        config.addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent changeEvent) {
                System.out.println("Changes for namespace " + changeEvent.getNamespace());
                for (String key : changeEvent.changedKeys()) {
                    ConfigChange change = changeEvent.getChange(key);
                    System.out.println(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
                }
            }
        });

        System.out.println(ConfigService.getConfig("trade_conf").getProperty("trade_switch","HHHHH"));


        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
