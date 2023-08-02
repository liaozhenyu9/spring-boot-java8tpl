package com.lzy.java8tpl.test.hutoolcrypto;

import cn.hutool.crypto.SecureUtil;

public class HmacSha256Test {

    public static void main(String[] args) {
        byte[] b = SecureUtil.hmacSha256("123").digest("aaa");
        for (byte b1 : b) {
            System.out.print(b1);

        }
        System.out.println();
        String s = SecureUtil.hmacSha256("123").digestHex("aaa");
        System.out.println(s);

    }
}
