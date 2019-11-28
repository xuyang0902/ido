package com.ido.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 *
 * 仅仅是为了搭建高可用的eureka server 再创建一个server
 *
 *
 * 当然用spring boot
 * @author xu.qiang
 * @date 19/11/28
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApp_02 {

    public static void main(String[] args)
    {
        SpringApplication.run(EurekaServerApp_02.class, args);
    }
}
