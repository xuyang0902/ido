package com.ido.trade.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 指定需要调用的 应用，方法  参数也可以定义
 *
 *
 * feign自带熔断器 需要在配置中打开
 *
 * @author xu.qiang
 * @date 19/11/28
 */
@FeignClient(value = "PROD" ,fallback = ProdFeignServiceHystrix.class)
public interface ProdFeignService {


    @GetMapping("/getName")
    public String getProduName();

}
