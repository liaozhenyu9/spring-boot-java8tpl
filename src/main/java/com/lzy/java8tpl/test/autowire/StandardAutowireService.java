package com.lzy.java8tpl.test.autowire;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StandardAutowireService {

    @NonNull
    private static String key;
//
//    @NonNull
//    private String key2;


    private final AutowireDao autowireDao2;

    public void test() {
        autowireDao2.test();
    }
}
