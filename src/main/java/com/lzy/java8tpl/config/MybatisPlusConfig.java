package com.lzy.java8tpl.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;

@Configuration
@MapperScan({"com.lzy.java8tpl.mapper"})
public class MybatisPlusConfig {

    @Autowired
    @Qualifier("xxxDataSource")
    private DataSource dataSource;


    /**
     * 创建mybatis的SqlSessionFactory，是 MyBatis 框架中的核心类，用于创建 SqlSession 对象，提供对数据库的访问功能
     * @param globalConfig
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(GlobalConfig globalConfig) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);    //设置数据源
        //设置 Mapper XML 文件的位置
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        MybatisConfiguration configuration = new MybatisConfiguration();    //配置 MyBatis 的核心配置项，如默认脚本语言、下划线转驼峰等。
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);  //默认脚本语言
        configuration.setMapUnderscoreToCamelCase(true);    //下划线转驼峰
        sqlSessionFactory.setConfiguration(configuration);
        Interceptor[] interceptor = {mybatisPlusInterceptor()}; //添加分页插件
        sqlSessionFactory.setPlugins(interceptor);
        sqlSessionFactory.setGlobalConfig(globalConfig);    //全局配置
        return sqlSessionFactory.getObject();
    }

    /**
     * MyBatis Plus 的全局配置，对框架的一些行为进行统一的配置
     * @return
     */
    @Bean
    public GlobalConfig globalConfig(){
        GlobalConfig globalConfig = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.AUTO);    //告诉 MyBatis Plus 在插入操作时使用数据库的自增主键（或者类似的机制），而不是手动指定主键值
        dbConfig.setInsertStrategy(FieldStrategy.NOT_NULL); //在执行插入操作时，只插入非空字段，可以有效地避免插入空字段
        dbConfig.setUpdateStrategy(FieldStrategy.NOT_NULL); //在执行更新操作时，只更新非空字段
        globalConfig.setDbConfig(dbConfig);
        return globalConfig;
    }

    /**
     * 分页插件
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }

}
