package com.verdant.jtools.common.generic;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 *
 * @author verdant
 * @since 2016/5/31
 */
public class ExceptionsUtils {

    /**
     * 将ErrorStack转化为String.
     *
     * @param e 异常
     * @return
     */
    public static String getStackTraceAsString(Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     *
     * @param e
     * @return
     */
    public static RuntimeException unchecked(Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else {
            return new RuntimeException(e);
        }
    }
}
