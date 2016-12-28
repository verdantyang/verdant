package com.verdant.jtools.common.web.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Web工具类.
 *
 * @author verdant
 * @since 2016/09/27
 */
public class WebUtils2 {
    public static ServletRequestAttributes getServletRequestAttributes() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes());
    }

    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getServletRequestAttributes().getResponse();
    }

    public static HttpSession getSession() {
        return getServletRequestAttributes().getRequest().getSession(true);
    }

    public static Cookie getCookie(String name) {
        return CookieUtils2.getCookie(name);
    }

    public static String getCookieValue(String name) {
        return CookieUtils2.getCookieValue(name);
    }

    public static Cookie[] getCookies() {
        return CookieUtils2.getCookies();
    }

    public static void removeCookie(String name) {
        CookieUtils2.removeCookie(name);
    }


    /**
     * response输出JSON对象
     *
     * @param responseBody
     */
    public static void responseJSON(Object responseBody) {
        try {
            HttpServletResponse response = getResponse();
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("text/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JSON.toJSONString(responseBody));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置SESSION属性
     */
    public static void setSessionAttribute(String name, Object value) {
        getSession().setAttribute(name, value);
    }


    /**
     * 获取访问IP地址
     */
    public static String getRemoteAddress() {
        HttpServletRequest request = getRequest();
        return getRemoteAddress(request);
    }

    /**
     * 获取请求IP地址
     * @param request
     * @return
     */
    public static String getRemoteAddress(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取session 属性
     */
    public static <T> T getSessionAttribute(String name) {
        return (T) getSession().getAttribute(name);
    }

    /**
     * 获取Web容器访问上下文路径
     *
     * @return context path
     */
    public static String getContextPath() {
        return getRequest().getContextPath();
    }

    /**
     * 获取当前请求的跟域名
     *
     * @return
     */
    public static String getRequestRoot() {
        return getRequestURL().replaceAll(getRequest().getRequestURI(), "");
    }

    /**
     * 获取当前请求地址
     *
     * @return url
     */
    public static String getRequestURL() {
        return getRequest().getRequestURL().toString();
    }

    /**
     * 获取应用根目录
     *
     * @return
     */
    public static String getWebRoot() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
    }

}
