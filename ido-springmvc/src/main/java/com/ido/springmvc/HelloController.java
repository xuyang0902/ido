package com.ido.springmvc;

import com.ido.springmvc.property.HelloSpringMVC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xu.qiang
 * @date 19/11/11
 */
@Controller
public class HelloController {

    @Autowired
    private HelloSpringMVC helloSpringMVC;


    @ResponseBody
    @RequestMapping(value = "/hello/{id}", method = RequestMethod.GET)
    public Map<String, Object> getName(@PathVariable int id, @RequestParam(required = false) String name) {

        System.out.println("id：" + id);
        System.out.println("name:" + name);
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("hello", helloSpringMVC.getHello() + "##" + helloSpringMVC.getMvc());

        return map;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {

        return "hello";
    }
}
