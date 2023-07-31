package com.lzy.java8tpl.param;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UnrecognizedPageParam {
    private Integer current;

    private Integer size;
}
