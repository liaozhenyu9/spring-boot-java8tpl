package com.lzy.java8tpl.test.easyExcel.sql;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.lzy.java8tpl.api.ParamErrorException;
import com.lzy.java8tpl.test.easyExcel.StdUploadData;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 模板的读取类
 *
 * @author Jiaju Zhuang
 */
// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
@Slf4j
public class AlgoProductUploadDataListener implements ReadListener<AlgoProduct> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    private List<AlgoProduct> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
//    private UploadDAO uploadDAO;

    public AlgoProductUploadDataListener() {
        // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
//        uploadDAO = new UploadDAO();
    }

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param uploadDAO
     */
//    public UploadDataListener(UploadDAO uploadDAO) {
//        this.uploadDAO = uploadDAO;
//    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. It is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(AlgoProduct data, AnalysisContext context) {
//        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        if (cachedDataList.size() > 5000) {
            log.error("The number of rows exceeds 5000");
            throw new ParamErrorException("导入数据不能超过5000行");
        }
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
//        if (cachedDataList.size() >= BATCH_COUNT) {
//            saveData();
//            // 存储完成清理 list
//            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
//        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
//        uploadDAO.save(cachedDataList);

        List<String> sqlList = cachedDataList.stream().map(e -> {
            String sql = "insert into algorithm_product (product_id, product_name, guldan_algorithm_id, capture_barcode, vms_barcode) values ('"
                    + e.getProductId() + "', '" + e.getProductName().trim() + "', '" + e.getGuldanAlgorithmId() + "', '" + e.getCaptureBarcode().trim() + "', '" + e.getVmsBarcode().trim() + "');";
            return sql;
        }).collect(Collectors.toList());

        writeSqlListToDisk(sqlList);

        log.info("存储数据库成功！");
    }

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     *
     * @param ex
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception ex, AnalysisContext context) {
        log.error("excel data convert error", ex);
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (ex instanceof ExcelDataConvertException) {
            ExcelDataConvertException e = (ExcelDataConvertException)ex;
            log.error("line {} column {} convert error, error data:{}", e.getRowIndex(), e.getColumnIndex(), JSON.toJSONString(e.getCellData()));
            throw new ParamErrorException(String.format("第%d行第%d列数据格式错误", e.getRowIndex(), e.getColumnIndex()));
        }
        throw new ParamErrorException("数据解析异常：" + ex.getMessage());
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    public static void writeSqlListToDisk(List<String> sqlList) {
        // 指定文件路径
        Path file = Paths.get("E:/动态商品库数据.sql");

        try {
            // 创建/打开文件并获取输出流
            Files.createDirectories(file.getParent()); // 如果目录不存在，则创建
            BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8);

            // 遍历 List 并按行写入文件
            for (String sql : sqlList) {
                writer.write(sql);
                writer.newLine(); // 添加换行符以分隔每一行
            }

            // 关闭写入流
            writer.close();

        } catch (IOException e) {
            System.err.println("Error occurred while writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
