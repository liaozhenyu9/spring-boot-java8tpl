package com.lzy.java8tpl.test.autowire;

import com.lzy.java8tpl.api.R;
import com.lzy.java8tpl.api.RHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("autowire")
@RequiredArgsConstructor
public class StdAutowireController {

    private final StdAutowireService stdAutowireService;

    private final AutowireService autowireService;

    @RequestMapping("1")
    public R test1() {
        new StdAutowireService(new AutowireDao());

        stdAutowireService.test();
        autowireService.test();
        return RHelper.success();
    }
}
