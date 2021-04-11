package com.example.iotplatform.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/11 4:40 下午
 */
@RestController
public class TestController {
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String helloSpringBoot(){
        return "Test!";
    }
}
