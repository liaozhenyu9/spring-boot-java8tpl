package com.lzy.java8tpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringBootJava8tplApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJava8tplApplication.class, args);
    }

}
