package com.lzy.java8tpl.test.httpclient;

import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.apache.http.util.EntityUtils;

/**
 * Example demonstrating how to evict expired and idle connections
 * from the connection pool.
 */
public class ClientEvictExpiredConnections {

    public static void main(String[] args) throws Exception {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .evictExpiredConnections()
                .evictIdleConnections(5L, TimeUnit.SECONDS)
                .build();
        try {
            // create an array of URIs to perform GETs on
            String[] urisToGet = {
                    "http://hc.apache.org/",
                    "http://hc.apache.org/httpcomponents-core-ga/",
                    "http://hc.apache.org/httpcomponents-client-ga/",
            };

            for (int i = 0; i < urisToGet.length; i++) {
                String requestURI = urisToGet[i];
                HttpGet request = new HttpGet(requestURI);

                System.out.println("Executing request " + requestURI);

                CloseableHttpResponse response = httpclient.execute(request);
                try {

                    System.out.println(response.getStatusLine());
                    System.out.println("----------------------------------------");
                    System.out.println(EntityUtils.toString(response.getEntity()));
                } finally {
                    response.close();
                }
            }

            PoolStats stats1 = cm.getTotalStats();
            System.out.println("Connections kept alive: " + stats1.getAvailable());

            // Sleep 10 sec and let the connection evictor do its job
            Thread.sleep(10000);

            PoolStats stats2 = cm.getTotalStats();
            System.out.println("Connections kept alive: " + stats2.getAvailable());

        } finally {
            httpclient.close();
        }
    }

}
