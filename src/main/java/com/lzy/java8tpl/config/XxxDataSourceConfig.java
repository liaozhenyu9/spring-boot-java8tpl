package com.lzy.java8tpl.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;

@Configuration
public class XxxDataSourceConfig {

    @Primary
    @Bean("xxxDataSource")
    @ConfigurationProperties("spring.datasource.druid.xxx")
    public DataSource xxxDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

}
