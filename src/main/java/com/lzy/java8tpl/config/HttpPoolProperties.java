package com.lzy.java8tpl.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "http.pool.conn")
@Component
@Getter
@Setter
public class HttpPoolProperties {

    //最大连接数
    private Integer maxTotal;

    //并发数
    private Integer defaultMaxPerRoute;

    //从连接池获取连接的超时时间
    private Integer connectionRequestTimeout;

    //客户端和服务器建立连接的超时时间
    private Integer connectTimeout;

    //客户端从服务器读取数据超时时间
    private Integer socketTimeout;

    //不活跃连接校验时间，使用连接时如果连接的空闲的时间超过该时间会先校验连接是否有效
    private Integer validateAfterInactivity;

    //连接最大存活时间 (1小时)
    private Integer connTimeToLive;

    //连接最大的空闲时间，空闲多久会被后台线程清除掉
    private Integer maxIdleTime;

}
