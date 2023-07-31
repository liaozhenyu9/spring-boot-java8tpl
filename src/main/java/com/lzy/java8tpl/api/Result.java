package com.lzy.java8tpl.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    private String code;
    private String msg;
    private T data;
    private long timestamp;
}
