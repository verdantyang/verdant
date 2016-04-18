package com.verdant.jtools.spring.utils;

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
 * web层 req res msg 管理
 */
public class WebUtils {

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

    public static Cookie[] getCookies() {
        return CookieUtils.getCookies();
    }

    public static Cookie getCookie(String name) {
        return CookieUtils.getCookie(name);
    }

    public static String getCookieValue(String name) {
        return CookieUtils.getCookieValue(name);
    }

    public static void removeCookie(String name) {
        CookieUtils.removeCookie(name);
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
     * /test
     *
     * @return context path
     */
    public static String getContextPath() {
        return getRequest().getContextPath();
    }

    /**
     * 获取当前请求的域名
     * URL：http://wechat.cloudguarder.com/cgptest/api/wx/authorize
     * URI:                               /cgptest/api/wx/authorize
     *
     * @return http://wechat.cloudguarder.com
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
     * D:\Tomcat 6.0\webapps\test\
     */
    public static String getWebRoot() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
    }

    /**
     * 获取应用根目录+指定目录地址
     * D:\Tomcat 6.0\webapps\test\  + path
     */
    public static String getWebRoot(String path) {
        return getWebRoot() + path;
    }

}
