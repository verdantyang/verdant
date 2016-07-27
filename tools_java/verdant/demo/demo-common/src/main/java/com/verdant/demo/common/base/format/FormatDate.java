package com.verdant.demo.common.base.format;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式化
 *
 * @author verdant
 * @since 2016/07/27
 */
public class FormatDate {
    private final static SimpleDateFormat fmt1 = new SimpleDateFormat("yyyyMMdd");
    private final static SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");  //Z即+0800

    private static String format1(SimpleDateFormat fmt) {
        String time = fmt.format(new Date());
        return time;
    }

    public static void main(String[] args) {
        System.out.println(format1(fmt1));
        System.out.println(format1(fmt2));
    }
}
