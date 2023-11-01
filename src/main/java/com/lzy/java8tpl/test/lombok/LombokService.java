package com.lzy.java8tpl.test.lombok;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LombokService {
    private final LombokDao lombokDao;

    private static final String key = "key";

    public String hello() {
        return lombokDao.hello();
    }
}
