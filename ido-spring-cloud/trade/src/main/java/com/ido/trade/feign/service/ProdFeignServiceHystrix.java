package com.ido.trade.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author xu.qiang
 * @date 19/11/28
 */
@Component
public class ProdFeignServiceHystrix implements ProdFeignService{


    @Override
    public String getProduName() {

        System.out.println("-->>>熔断器工作了");
        return "熔断了";
    }
}
