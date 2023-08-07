package com.lzy.java8tpl.test.hutoolcrypto;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;

import java.util.HashMap;

public class HmacSha256Test {

    public static void main(String[] args) {
        byte[] b = SecureUtil.hmacSha256("123").digest("aaa");
        for (byte b1 : b) {
            System.out.print(b1);

        }
        System.out.println();
        String s = SecureUtil.hmacSha256("123").digestHex("aaa");

        String s2 = SecureUtil.hmacSha256().digestHex("aaa");



        System.out.println("sign:-------" + s + "=======" + s2);

        HashMap<String, String> params = new HashMap<>();
        params.put("app_id", "appId");
        params.put("timestamp", "timestamp");
        params.put("charset", "charset");
        params.put("method", "method");
        params.put("biz_content", "bizContent");
        params.put("methods","ssssssssss");
        String paramStr = MapUtil.sortJoin(params, "", "=", true, "a32cfc25f12a4dcea162525662e334ea");
        System.out.println(paramStr);
        

    }
}
