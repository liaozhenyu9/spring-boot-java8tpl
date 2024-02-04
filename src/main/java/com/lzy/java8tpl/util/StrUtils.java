package com.lzy.java8tpl.util;

public class StrUtils {
    public static String truncateString(String input, int maxLength) {
        if (input == null) {
            return null;
        }
        if (input.length() > maxLength) {
            return input.substring(0, maxLength);
        } else {
            return input;
        }
    }
}
