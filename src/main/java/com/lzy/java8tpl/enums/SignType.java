package com.lzy.java8tpl.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SignType implements IEnum<String>{

    HmacSHA256("HmacSHA256", "");

    @JsonValue
    @EnumValue
    private final String value;

    private final String desc;

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }


    @JsonCreator
    public static SignType fromValue(String value) {
        for (SignType e : SignType.values()) {
            if (e.value.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }
}
