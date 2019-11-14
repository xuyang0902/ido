package com.ido.apollo.spring.config;

import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author xu.qiang
 * @date 19/11/14
 */
@Configuration
@EnableApolloConfig("trade_conf")
public class TradeConfig {

    @Value("${trade_switch:false}")
    private String tradeSwitch;

    public String getTradeSwitch() {
        return tradeSwitch;
    }

    public void setTradeSwitch(String tradeSwitch) {
        this.tradeSwitch = tradeSwitch;
    }
}
