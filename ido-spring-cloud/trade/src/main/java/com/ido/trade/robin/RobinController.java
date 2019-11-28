package com.ido.trade.robin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author xu.qiang
 * @date 19/11/28
 */
@RestController
public class RobinController {


    @Autowired
    public RestTemplate restTemplate;


    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


    @GetMapping("/testRobin")
    public String httpTestRobin() {


        /**
         * 使用 restTemplate的方式调用服务
         */
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://PROD/getName", String.class);
        System.out.println(forEntity.getBody());

        return "testRobin-SUCC";
    }

}
