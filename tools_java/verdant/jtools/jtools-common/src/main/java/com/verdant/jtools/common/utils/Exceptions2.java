package com.verdant.jtools.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Author: verdant
 * Desc: 异常工具类
 */
public class Exceptions2 {

	/**
	 * 将ErrorStack转化为String.
	 * @param e
	 * @return
     */
	public static String getStackTraceAsString(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}
}
