package com.lzy.java8tpl.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SignType implements IEnum<SignType, String>{

    HmacSHA256("HmacSHA256", "");

    @JsonValue
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
        for (SignType signType : SignType.values()) {
            if (signType.value.equalsIgnoreCase(value)) {
                return signType;
            }
        }
        throw new IllegalArgumentException("Invalid SignType value: " + value);
    }
}
