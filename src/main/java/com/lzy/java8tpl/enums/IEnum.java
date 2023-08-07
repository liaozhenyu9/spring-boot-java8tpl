package com.lzy.java8tpl.enums;

public interface IEnum<E extends Enum<?>, T>{

    T getValue();

    String getDesc();

}
