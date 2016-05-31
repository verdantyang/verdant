package com.verdant.jtools.common.web.utils;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * @Author: verdant
 * @Desc: HttpCookie的工具类
 */
public class CookieUtils2 {

    /**
     * 设置 Cookie, 过期时间自定义
     *
     * @param name   名称
     * @param value  值
     * @param path
     * @param maxAge 过期时间, 单位秒
     * @return
     */
    public static Cookie addCookie(String name, String value, String path, int maxAge) {
        Cookie cookie = null;
        try {
            cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
            cookie.setMaxAge(maxAge);
            if (null != path) {
                cookie.setPath(path);
            }
            WebUtils2.getResponse().addCookie(cookie);
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
     * @param name      名称
     * @param isRemoved 是否移除
     * @return 值
     */
    public static Cookie getCookie(String name, boolean isRemoved) {
        Cookie[] cookies = getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    if (isRemoved) {
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        WebUtils2.getResponse().addCookie(cookie);
                    }
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 批量获取Cookie
     *
     * @return
     */
    public static Cookie[] getCookies() {
        return WebUtils2.getRequest().getCookies();
    }

    /**
     * 获取Cookie的值
     * @param name
     * @param isRemoved
     * @return
     */
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

    /**
     * 移除Cookie
     * @param name
     */
    public static void removeCookie(String name) {
        getCookie(name, true);
    }
}
