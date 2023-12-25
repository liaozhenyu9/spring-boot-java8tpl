package com.lzy.java8tpl.test.kafka;

import lombok.Data;

@Data
public class User {
    private Integer id;

    private String name;

    public String briefIntroduction() {
        return "我叫" + name + "，我的编号是：" + id;
    }
}

