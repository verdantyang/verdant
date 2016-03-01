package com.verdant.jtools.common.support.spring;

import com.alibaba.fastjson.JSON;
import com.verdant.jtools.common.utils.StringUtils2;
import com.verdant.jtools.metadata.constant.CommonEnum;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ContextHolder extends ApplicationObjectSupport {


    private static ApplicationContext applicationContext;

    @PostConstruct
    private void initialize() {
        applicationContext = getApplicationContext();
    }

    /**
     * 获取对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static MessageSource getMessageSource() {
        return getBean(MessageSource.class);
    }

    public static String getMessage(String code) {
        return getMessageSource().getMessage(code, (Object[]) null, code + " is undefined|999", LocaleContextHolder.getLocale());
    }

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
        if (StringUtils2.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils2.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils2.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
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
        return getRequestURL().replaceAll(getRequest().getRequestURI(),"");
    }

    /**
     * 获取当前请求地址
     *
     * @return url
     */
    public static String getRequestURL()
    {
        return getRequest().getRequestURL().toString();
    }

    /**
     * 获取应用根目录
     * D:\Tomcat 6.0\webapps\test\
     */
    public static String getWebRoot() {
        return getRequest().getRealPath("/");
    }

    /**
     * 获取应用根目录+指定目录地址
     * D:\Tomcat 6.0\webapps\test\  + path
     */
    public static String getWebRoot(String path) {
        return getWebRoot() + path;
    }

    /**
     * get current user T
     */
    public static <T> T getCurrentUser(Class<T> clazz) {
        return getCurrentUser(CommonEnum.USER.name(), clazz);
    }

    public static <T> T getCurrentUser(String key, Class<T> clazz) {
        return (T) getSession().getAttribute(key);
    }

    /**
     * set current user
     */
    public static void setCurrentUser(Object obj) {
        setCurrentUser(CommonEnum.USER.name(), obj);
    }

    public static void setCurrentUser(String key, Object obj) {
        getSession().setAttribute(key, obj);
    }


}
