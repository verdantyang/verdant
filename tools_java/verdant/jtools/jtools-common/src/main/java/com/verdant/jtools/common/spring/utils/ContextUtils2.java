package com.verdant.jtools.common.spring.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class ContextUtils2 extends ApplicationObjectSupport {

    public static <T> T getBean(Class<T> clazz) {
        return getWebAppContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getWebAppContext().getBean(name, clazz);
    }

    public static WebApplicationContext getWebAppContext() {
        return ContextLoader.getCurrentWebApplicationContext();
    }

    public static MessageSource getMessageSource() {
        return ContextUtils2.getBean(MessageSource.class);
    }

    public static String getMessage(String code) {
        return getMessageSource().getMessage(code, (Object[]) null, code + " is undefined|999", LocaleContextHolder.getLocale());
    }
}
