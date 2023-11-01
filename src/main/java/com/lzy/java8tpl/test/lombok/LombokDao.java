package com.lzy.java8tpl.test.lombok;

import org.springframework.stereotype.Component;

@Component
public class LombokDao {
    public String hello() {
        return "hello";
    }
}
