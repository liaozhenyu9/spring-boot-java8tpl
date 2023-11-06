package com.lzy.java8tpl.test.enums;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EnumParam {
    private TestType testType;

    private String name;
}
