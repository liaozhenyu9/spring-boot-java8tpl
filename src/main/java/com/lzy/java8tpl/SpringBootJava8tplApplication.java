package com.lzy.java8tpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class SpringBootJava8tplApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJava8tplApplication.class, args);
    }

}
