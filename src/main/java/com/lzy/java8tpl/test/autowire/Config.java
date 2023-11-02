package com.lzy.java8tpl.test.autowire;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public AutowireDao autowireDao2() {
        AutowireDao autowireDao = new AutowireDao();
        autowireDao.setName("not default");
        return autowireDao;
    }
}
