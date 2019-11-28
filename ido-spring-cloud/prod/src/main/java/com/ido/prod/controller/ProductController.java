package com.ido.prod.controller;

import com.ido.prod.config.ProdConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xu.qiang
 * @date 19/11/28
 */
@RestController
public class ProductController {

    @Autowired
    private ProdConfig prodConfig;

    @GetMapping(value = "/getName")
    public String getProductName() {

        System.out.println("-->>>>>>>>>>>");
        return prodConfig.getName();
    }
}
