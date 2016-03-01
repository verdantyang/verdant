package com.verdant.jtools.cache.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.TypeUtils;


public class CacheHelper {

    public static String getKey(String name, Object ko) {
        return getPrefix(name) + String.valueOf(ko);
    }

    public static String getPrefix(String name) {
        return name + ":";
    }

    public static String serialize(Object o) {
        if (o instanceof CharSequence) {
            return String.valueOf(o);
        }
        return JSON.toJSONString(o);
    }

    public static Object deserialize(String s, Class clz) {
        if (s == null || s.length() == 0)
            return null;
        if (s.startsWith("{"))
            return clz == null ? JSON.parseObject(s) : JSON.parseObject(s, clz);
        if (s.startsWith("["))
            return clz == null ? JSON.parseArray(s) : JSON.parseArray(s, clz);
        return TypeUtils.castToJavaBean(s, clz);
    }

}
