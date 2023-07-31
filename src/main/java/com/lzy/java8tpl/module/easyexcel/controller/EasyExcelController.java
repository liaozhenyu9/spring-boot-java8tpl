package com.lzy.java8tpl.module.easyexcel.controller;

import com.alibaba.excel.util.ListUtils;
import com.lzy.java8tpl.module.easyexcel.data.DemoData;
import com.lzy.java8tpl.module.easyexcel.util.EasyExcelUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("easyexcel")
public class EasyExcelController {

    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(LocalDateTime.now());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        EasyExcelUtils.download(response, "测试", "模板", DemoData.class, data());
    }
}
