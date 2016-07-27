package com.verdant.demo.common.base;

import java.util.Calendar;
import java.util.Date;

/**
 * 获取时间戳
 *
 * @author verdant
 * @since 2016/07/27
 */
public class GenTimestamp {

    private static long getMillis1() {
        return System.currentTimeMillis();
    }

    private static long getMillis2() {
        return new Date().getTime();
    }

    private static long getMillis3() {
        return Calendar.getInstance().getTimeInMillis();
    }

    private static long getNano() {
        return System.nanoTime();
    }

    public static void main(String[] args) {
        System.out.println(getMillis1());
        System.out.println(getMillis2());
        System.out.println(getMillis3());
        System.out.println(getNano());
    }
}
