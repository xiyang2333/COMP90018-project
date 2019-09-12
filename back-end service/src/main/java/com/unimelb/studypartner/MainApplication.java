package com.unimelb.studypartner;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by xiyang on 2019/9/4
 */
@SpringBootApplication
@MapperScan(basePackages = "com.unimelb.studypartner.mapper")
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
