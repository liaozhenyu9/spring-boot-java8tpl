package com.lzy.java8tpl.api;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class ParamAssert {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ParamErrorException(message);
        }
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, null);
    }

    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new ParamErrorException(message);
        }
    }

    public static void isNull(@Nullable Object object) {
        isNull(object, null);
    }

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new ParamErrorException(message);
        }
    }

    public static void notNull(@Nullable Object object) {
        notNull(object, null);
    }

    public static void hasText(@Nullable String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new ParamErrorException(message);
        }
    }

    public static void hasText(@Nullable String text) {
        hasText(text, null);
    }

    public static void notEmpty(@Nullable Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new ParamErrorException(message);
        }
    }

    public static void notEmpty(@Nullable Object[] array) {
        notEmpty(array, null);
    }

    public static void noNullElements(@Nullable Object[] array, String message) {
        if (array != null) {
            Object[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (element == null) {
                    throw new ParamErrorException(message);
                }
            }
        }
    }

    public static void noNullElements(@Nullable Object[] array) {
        if (array != null) {
            Object[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (element == null) {
                    throw new ParamErrorException(null);
                }
            }
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ParamErrorException(message);
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection) {
        notEmpty(collection, null);
    }

    public static void noNullElements(@Nullable Collection<?> collection, String message) {
        if (collection != null) {
            Iterator var2 = collection.iterator();

            while(var2.hasNext()) {
                Object element = var2.next();
                if (element == null) {
                    throw new ParamErrorException(message);
                }
            }
        }
    }

    public static void noNullElements(@Nullable Collection<?> collection) {
        if (collection != null) {
            Iterator var2 = collection.iterator();

            while(var2.hasNext()) {
                Object element = var2.next();
                if (element == null) {
                    throw new ParamErrorException(null);
                }
            }
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new ParamErrorException(message);
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map) {
        notEmpty(map, null);
    }

    public static void checkBetween (long value, long min, long max, String message) {
        if (value < min || value > max) {
            throw new ParamErrorException(message);
        }
    }

}
