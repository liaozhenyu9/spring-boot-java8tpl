package com.lzy.java8tpl.test.kafka.cloud3点1后函数式编程方式;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private Integer id;

    private String name;

    public String briefIntroduction() {
        return "我叫" + name + "，我的编号是：" + id;
    }
}

