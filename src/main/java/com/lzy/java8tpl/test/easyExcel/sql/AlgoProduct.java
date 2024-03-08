package com.lzy.java8tpl.test.easyExcel.sql;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class AlgoProduct {

    @ExcelProperty("product_id")
    private Integer productId;

    @ExcelProperty("product_name")
    private String productName;

    @ExcelProperty("guldan_algorithm_id")
    private Integer guldanAlgorithmId;

    @ExcelProperty("capture_barcode")
    private String captureBarcode;

    @ExcelProperty("vms_barcode")
    private String vmsBarcode;

}
