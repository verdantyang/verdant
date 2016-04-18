package com.verdant.jtools.spring.utils;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * CookieUtils : 针对Http Cookie的工具类
 */
public class CookieUtils {

    /**
     * 设置 Cookie, 过期时间自定义
     *
     * @param name   名称
     * @param value  值
     * @param maxAge 过期时间, 单位秒
     */
    public static Cookie addCookie(String name, String value, String path, int maxAge) {
        Cookie cookie = null;
        try {
            cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
            cookie.setMaxAge(maxAge);
            if (null != path) {
                cookie.setPath(path);
            }
            WebUtils.getResponse().addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cookie;
    }

    /**
     * 设置 Cookies, 过期时间自定义
     *
     * @param values 值
     * @param path   路径
     * @param maxAge 过期时间, 单位秒
     * @return Cookies
     */
    public static ArrayList<Cookie> addCookies(Map<String, String> values, String path, int maxAge) {
        Set<Map.Entry<String, String>> entries = values.entrySet();
        ArrayList<Cookie> cookies = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries) {
            cookies.add(addCookie(entry.getKey(), entry.getValue(), path, maxAge));
        }
        return cookies;
    }

    public static Cookie[] getCookies() {
        return WebUtils.getRequest().getCookies();
    }



    /**
     * 获得指定Cookie的值
     *
     * @param name 名称
     * @return 值
     */
    public static Cookie getCookie(String name) {
        return getCookie(name, false);
    }
    /**
     * 获得指定Cookie的值
     *
     * @param name      名字
     * @param isRemoved 是否移除
     * @return 值
     */
    public static Cookie getCookie(String name, boolean isRemoved) {
        String value = null;
        Cookie[] cookies = getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    if (isRemoved) {
                        cookie.setMaxAge(0);
                        WebUtils.getResponse().addCookie(cookie);
                    }
                    return cookie;
                }
            }
        }
        return null;
    }

    public static String getCookieValue(String name, boolean isRemoved) {
        Cookie cookie = getCookie(name, isRemoved);
        String value = null;
        if (cookie != null) {
            try {
                value = URLDecoder.decode(cookie.getValue(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * 获得指定Cookie的值，并删除。
     *
     * @param name 名称
     * @return 值
     */
    public static String getCookieValue(String name) {
        return getCookieValue(name, false);
    }

    public static void removeCookie(String name) {
        getCookie(name, true);
    }
}
