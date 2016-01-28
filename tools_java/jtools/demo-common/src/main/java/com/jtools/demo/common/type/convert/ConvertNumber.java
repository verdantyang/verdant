package com.jtools.demo.common.type.convert;

import com.jtools.common.utils.ReflectUtils2;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Func:   数字类型转换（int、Integer、String）
 */
public class ConvertNumber {

    /**
     * int转Integer
     *
     * @param src
     * @return
     */
    public static Integer intToInteger(int src) {
        return new Integer(src);
    }

    /**
     * int转String
     *
     * @param src
     * @return
     */
    public static String intToStr(int src) {
        return String.valueOf(src);
    }

    /**
     * String转int
     *
     * @param src
     * @return
     */
    public static int strToInt(String src) {
        int dest = 0;
        try {
            dest = Integer.parseInt(src);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return dest;
    }

    /**
     * String转Integer
     *
     * @param src
     * @return
     */
    public static Integer strToInteger(String src) {
        Integer dest = null;
        try {
            dest = Integer.parseInt(src);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return dest;
    }

    /**
     * Integer转String
     *
     * @param src
     * @return
     */
    public static String integerToStr(Integer src) {
        return String.valueOf(src);
    }

    public static void main(String[] args) {
        String fm = "Trans from {%s} to {%s}";
        Integer integer = new Integer(10);
        String str = "10";
        System.out.println(String.format(fm,
                ReflectUtils2.getClassName(integer),
                ReflectUtils2.getClassName(integerToStr(integer))));
        System.out.println(String.format(fm,
                ReflectUtils2.getClassName(str),
                ReflectUtils2.getClassName(strToInteger(str))));
    }
}
