package com.lzy.java8tpl.test.easyExcel.api;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lzy.java8tpl.api.ParamErrorException;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Data
public class XEraProductApplyData {

    @ExcelProperty("productId")
    private Integer productId;

    @ExcelProperty("productName")
    private String productName;

    @ExcelProperty("barcode")
    private String barcode;

    @ExcelProperty("frontImg")
    private String frontImg;

    @ExcelProperty("backImg")
    private String backImg;

    @ExcelProperty("leftImg")
    private String leftImg;

    @ExcelProperty("rightImg")
    private String rightImg;

    @ExcelProperty("topImg")
    private String topImg;

    @ExcelProperty("bottomImg")
    private String bottomImg;

    public SkuApplyParam convertToSkuApplyParam() {
        if (!StringUtils.hasText(barcode) || !StringUtils.hasText(productName) || !StringUtils.hasText(frontImg) || !StringUtils.hasText(backImg) || !StringUtils.hasText(leftImg) || !StringUtils.hasText(rightImg) || !StringUtils.hasText(topImg) || !StringUtils.hasText(bottomImg)) {
            throw new ParamErrorException("必填字段不能为空");
        }
        return new SkuApplyParam().setSkuCode(barcode.trim())
                .setSkuName(productName.trim())
                .setImgUrls(Arrays.asList(frontImg.trim(), backImg.trim(), leftImg.trim(), rightImg.trim(), topImg.trim(), bottomImg.trim()));
    }

}
