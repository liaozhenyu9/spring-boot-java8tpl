package com.lzy.java8tpl.param;

import lombok.Data;

@Data
public class JsonAliasParam {
    private Integer id;
    private String name;
    private U u;

    @Data
    public static class U {
        private String name;
    }
}
