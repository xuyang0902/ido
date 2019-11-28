package com.ido.prod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author xu.qiang
 * @date 19/11/28
 */
@EnableEurekaClient
@SpringBootApplication
public class ProdApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProdApplication.class,args);
    }
}
