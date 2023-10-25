package com.lzy.java8tpl.test.jackson;


import lombok.Data;
import lombok.Getter;

@Data
public class JacksonTestParam {

    public String p = "2222";


    private Integer id = 10;

    private String name = "张三";

    private String e;

    private boolean aa;

    private Boolean bb;

    public JacksonTestParam() {
        this.id = 200;
        this.name = "xxx";
    }

    public String getPhone() {
        return "12345678";
    }

    public void setEmail(String email) {
        this.e = email;
    }

    public String getE() {
        return e;
    }

    public boolean isFlag() {
        return true;
    }

    public String isSuccess() {
        return "abceyyy";
    }


}
