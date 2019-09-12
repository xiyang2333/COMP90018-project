package com.unimelb.studypartner.web;

import com.unimelb.studypartner.common.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiyang on 2019/9/4
 */
@RestController
public class HelloWorld {

    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("/hello")
    public String index() {
        redisUtil.set("aaa", "Bbb");
        String a = redisUtil.get("aaa").toString();
        return a;
    }
}
