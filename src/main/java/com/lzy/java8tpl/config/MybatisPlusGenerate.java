package com.lzy.java8tpl.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.util.*;

public class MybatisPlusGenerate {


    //要生成的表名
    private static String tables= "biz,device";

    //数据库配置四要素
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/eqm?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
    private static String username = "root";
    private static String password = "root";



    public static void main(String[] args) {
        FastAutoGenerator.create(url, username, password)
                //全局配置
                .globalConfig(builder -> {
                    builder.author("abc") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件，已迁移到策略配置中
                            .disableOpenDir() //禁止打开输出目录
                            .outputDir(System.getProperty("user.dir") + "/src/main/java"); // 指定输出目录
                })
                //包配置
                .packageConfig(builder -> {
                    builder.parent("com.lzy.java8tpl") // 设置父包名
                            .controller("controller")
                            .service("service")
                            .mapper("mapper")
                            .entity("entity")
//                            .moduleName("xxx") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/mapper")); // 设置mapperXml生成路径

                })
                //模板配置
                .injectionConfig(consumer -> {
//                    Map<String, String> customFile = new HashMap<>();
//                    // DTO、VO
//                    customFile.put("DTO.java", "/templates/entityDTO.java.ftl");
//                    customFile.put("VO.java", "/templates/entityVO.java.ftl");
//
//                    consumer.customFile(customFile);
                })
                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名 可边长参数“user”, “user1”
//                            .addTablePrefix("tb_", "gms_") // 设置过滤表前缀
                            //service策略配置
                            .serviceBuilder()
                            .formatServiceFileName("I%sDbService")
                            .formatServiceImplFileName("%sDbServiceImpl")
                            .enableFileOverride()   //覆盖已生成文件

                            // 实体类策略配置
                            .entityBuilder()
                            .idType(IdType.AUTO)//主键策略 雪花算法自动生成的id
//                            .addTableFills(new Column("create_time", FieldFill.INSERT)) // 自动填充配置
//                            .addTableFills(new Property("update_time", FieldFill.INSERT_UPDATE))
                            .enableLombok() //开启lombok
//                            .logicDeleteColumnName("deleted")// 说明逻辑删除是哪个字段
                            .enableTableFieldAnnotation()// 属性加上注解说明
                            .enableFileOverride()   //覆盖已生成文件

                            //controller 策略配置
                            .controllerBuilder()
                            .formatFileName("%sController")
                            .enableRestStyle() // 开启RestController注解
                            .enableFileOverride()   //覆盖已生成文件

                            // mapper策略配置
                            .mapperBuilder()
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation()//@mapper注解开启
                            .formatXmlFileName("%sMapper")
                            .enableFileOverride();   //覆盖已生成文件

                })

                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                //.templateEngine(new FreemarkerTemplateEngine())
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

        System.out.println("----------结束------------");
    }
}
