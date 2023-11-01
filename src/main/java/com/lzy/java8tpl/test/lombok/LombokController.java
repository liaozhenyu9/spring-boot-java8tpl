package com.lzy.java8tpl.test.lombok;

import com.lzy.java8tpl.api.R;
import com.lzy.java8tpl.api.RHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lombok")
@RequiredArgsConstructor
public class LombokController {

    private final LombokService lombokService;

    @RequestMapping("1")
    public R test1() {
        new LombokService(new LombokDao());
        return RHelper.success(lombokService.hello());
    }
}
