package com.lzy.java8tpl.config;

import com.lzy.java8tpl.interceptor.RestTemplateLogInterceptor;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class RestTemplateConfig {

    @Autowired
    private HttpPoolProperties httpPoolProperties;

    /**
     * 首先实例化一个连接池管理器，设置最大连接数、并发连接数
     * @return
     */
    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(){
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(httpPoolProperties.getMaxTotal());    //最大连接数
        connectionManager.setDefaultMaxPerRoute(httpPoolProperties.getDefaultMaxPerRoute());    //并发数
        connectionManager.setValidateAfterInactivity(httpPoolProperties.getValidateAfterInactivity());
        return connectionManager;
    }

    @Bean("restTemplateHttpClient")
    public HttpClient httpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(httpPoolProperties.getConnectionRequestTimeout())  //从连接池获取连接的超时时间
                .setConnectTimeout(httpPoolProperties.getConnectTimeout())  //连接超时时间
                .setSocketTimeout(httpPoolProperties.getSocketTimeout())    //响应超时时间
                .build();


        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
        headers.add(new BasicHeader("Accept-Language", "zh-CN"));
        headers.add(new BasicHeader("Connection", "Keep-Alive"));
        headers.add(new BasicHeader("Content-type", "application/json;charset=UTF-8"));

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig) //请求配置
                .setConnectionManager(poolingHttpClientConnectionManager()) //设置连接池管理器
                .setDefaultHeaders(headers) //默认请求头
                .setConnectionTimeToLive(httpPoolProperties.getConnTimeToLive(), TimeUnit.MILLISECONDS) //连接最大存活时间
                .evictExpiredConnections()  //使用后台线程主动从连接池中驱逐过期的连接
                .evictIdleConnections(httpPoolProperties.getMaxIdleTime(), TimeUnit.MILLISECONDS)   //使用后台线程主动从连接池中驱逐空闲连接
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setRetryHandler(new DefaultHttpRequestRetryHandler(2, true))
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpComponentsFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
        //通过BufferingClientHttpRequestFactory对象包装现有的ResquestFactory，用来支持多次调用getBody()方法
        BufferingClientHttpRequestFactory bufferingFactory = new BufferingClientHttpRequestFactory(httpComponentsFactory);
        RestTemplate restTemplate = new RestTemplate(bufferingFactory);
        restTemplate.setInterceptors(Arrays.asList(new RestTemplateLogInterceptor()));
        return restTemplate;
    }


}
