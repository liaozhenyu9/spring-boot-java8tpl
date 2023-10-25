package com.lzy.java8tpl.test.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TestType {

    AAA("1", "AAA"),
    BBB("2", "BBB")
    ;

    @JsonValue
    private final String value;

    private final String desc;

    @JsonCreator
    public static TestType fromValue(String value) {
        for (TestType e : TestType.values()) {
            if (e.name().equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }
}
