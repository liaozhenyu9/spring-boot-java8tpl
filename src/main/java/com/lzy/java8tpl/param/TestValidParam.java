package com.lzy.java8tpl.param;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class TestValidParam {
    @NotNull(message = "id不能为空")
    private String name;
}
