package com.lzy.java8tpl.test.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lzy.java8tpl.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StandardEnum implements IEnum<String> {
    A("1", "A"),
    B("2", "B"),
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
