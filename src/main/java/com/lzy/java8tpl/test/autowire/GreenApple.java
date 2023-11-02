package com.lzy.java8tpl.test.autowire;

import org.springframework.stereotype.Component;

@Component
public class GreenApple implements IApple {

    @Override
    public String getColor() {
        return "Green";
    }
}
