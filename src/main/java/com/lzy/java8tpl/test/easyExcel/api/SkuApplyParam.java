package com.lzy.java8tpl.test.easyExcel.api;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class SkuApplyParam {
    /**
     * 调⽤⽅系统中的SKU Name
     * 限制：⻓度1<x<=100，⾮空字符，前后有空格会被去除
     * （必填）
     */
    @Length(min = 2, max = 100, message = "skuName长度必须大于1，小于等于100")
    private String skuName;


    private String skuSpec;


    private String skuCode;


    private Boolean isStandard;


    private List<String> imgUrls;

    /**
     * 申请结果回调
     * 申请被处理的结果回调，最多回调三次，没有被应答时候间隔15分钟在发送⼀次
     * （可选）
     */

    private String callbackUrl;

}
