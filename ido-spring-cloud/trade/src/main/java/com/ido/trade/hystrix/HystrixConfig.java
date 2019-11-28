package com.ido.trade.hystrix;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HystrixConfig {

    @Bean
    public HystrixMetricsStreamServlet hystrixMetricsStreamServlet(){
        return new HystrixMetricsStreamServlet();
    }

    @Bean
    public ServletRegistrationBean registration(HystrixMetricsStreamServlet servlet){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(servlet);

        registrationBean.setLoadOnStartup(1);
        registrationBean.setName("HystrixMetricsStreamServlet");
        registrationBean.addUrlMappings("/hystrix.stream");

        //是否启用该registrationBean
        registrationBean.setEnabled(true);
        return registrationBean;
    }
}