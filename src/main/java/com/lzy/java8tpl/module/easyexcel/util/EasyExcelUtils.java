package com.lzy.java8tpl.module.easyexcel.util;

import com.alibaba.excel.EasyExcel;
import com.lzy.java8tpl.api.ServiceErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Collection;

@Slf4j
public class EasyExcelUtils {

    public static void download(HttpServletResponse response,
                                               @NonNull String fileName,
                                               @NonNull String sheetName,
                                               Class head,
                                               Collection<?> data) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), head)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet(sheetName)
                    .doWrite(data);
        } catch (Exception e) {
            log.error("File Export Error", e);
            throw new ServiceErrorException("下载文件失败" + e.getMessage(), e);
        }
    }
}
