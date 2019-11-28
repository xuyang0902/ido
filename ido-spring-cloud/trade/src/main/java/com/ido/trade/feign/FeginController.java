package com.ido.trade.feign;

import com.ido.trade.feign.service.ProdFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xu.qiang
 * @date 19/11/28
 */
@RestController
public class FeginController {

    @Autowired
    private ProdFeignService prodFeignService;


    @GetMapping("testFeign")
    public String testReign(){


        String produName = prodFeignService.getProduName();

        System.out.println("--->" + produName);

        return "testFeign-SUU";
    }

}
