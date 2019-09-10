package com.unimelb.studypartner.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiyang on 2019/9/4
 */
@RestController
public class HelloWorld {

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }
}
