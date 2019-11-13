package com.ido.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xu.qiang
 * @date 19/11/12
 */
@RestController
public class HelloController {

    @GetMapping(value = "/hello")
    public Map<String,Object> get(){


        Map<String,Object> map = new HashMap<String,Object>();
        map.put("hello","哈罗");
        map.put("boot","步特");
        return map;
    }
}
