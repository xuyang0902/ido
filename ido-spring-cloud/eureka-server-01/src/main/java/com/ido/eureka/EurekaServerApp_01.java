package com.ido.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 *
 *  本地跑  host配置
 127.0.0.1 eureka.s1
 127.0.0.1 eureka.s2

 * @author xu.qiang
 * @date 19/11/28
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApp_01 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApp_01.class, args);
    }
}
