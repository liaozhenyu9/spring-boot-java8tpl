package com.lzy.java8tpl.test.easyExcel;

import com.alibaba.excel.EasyExcel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("stdEasyExcel")
@RequiredArgsConstructor
public class StdEasyExcelController {

    /**
     * 文件上传
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link StdUploadData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link StdUploadDataListener}
     * <p>
     * 3. 直接读即可
     */
    @PostMapping("upload")
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), StdUploadData.class, new StdUploadDataListener()).sheet().doRead();
        return "success";
    }
}
