package com.ido.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin.server.internal.EnableZipkinServer;

/**
 * @author xu.qiang
 * @date 19/11/28
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZipkinServer
public class ZipKinApplication {

    public static void main(String[] args) {

        SpringApplication.run(ZipKinApplication.class,args);
    }
}
