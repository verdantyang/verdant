package com.verdant.jtools.common.utils.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: verdant
 * Desc: 日期工具类
 */
public class DateUtils2 {

    /**
     * 以showType的格式，返回String类型的当前时间
     *
     * @param showType
     * @return
     * @since 1.0
     */
    public static String getNowTime(String showType) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(showType);
        return sdf.format(date);
    }

    /**
     * 将date按照showType转换成String
     *
     * @param date
     * @param showType
     * @return
     * @since 1.0
     */
    public static String turnDateToString(Date date, String showType) {
        DateFormat df = new SimpleDateFormat(showType);
        String dateString = df.format(date);
        return dateString;
    }

    /**
     * 将String转换成Date类型
     *
     * @param strDate  sDate的格式要和showType相符
     * @param showType
     * @return
     * @since 1.0
     */
    public static Date turnStringToDate(String strDate, String showType) {
        DateFormat df = new SimpleDateFormat(showType);
        Date date = null;
        try {
            date = df.parse(strDate);
        } catch (ParseException e) {
            System.err.println("Error|DateUtils2|turnStringToDate|格式不一致");
        }
        return date;
    }

    /**
     * 将时间戳转换成Date类型
     *
     * @param timestamp 时间戳
     * @return
     * @since 1.0
     */
    public static Date turnTimestampToDate(long timestamp) {
        return new Date(timestamp);
    }

    /**
     * 获取处于一年中的第几周
     *
     * @param date
     * @return
     * @since 1.0
     */
    public static int getWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取处于一年中的第几季度
     *
     * @param date
     * @return
     * @since 1.0
     */
    public static int getQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) / 4 + 1;
    }

    /**
     * 获取days天后的日期
     *
     * @param date
     * @param days
     * @return
     * @since 1.0
     */
    public static Date getSomeDaysAgo(Date date, int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 计算一个月有多少天
     *
     * @param date
     * @return
     * @since 1.0
     */
    public static int getMouthDayCount(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据毫秒数格式化时间
     *
     * @param src
     * @return
     * @since 1.0
     */
    public static String timeConversion(long src) {
        int second = 1000;
        int minute = second * 60;
        int hour = minute * 60;
        int day = hour * 24;
        StringBuilder sb = new StringBuilder();
        if (src / day > 0) {
            sb.append(src / day + "天");
            src = src % day;
        }
        if (src / hour > 0) {
            sb.append(src / hour + "小时");
            src = src % hour;
        }
        if (src / minute > 0) {
            sb.append(src / minute + "分");
            src = src % minute;
        }
        if (src != 0)
            sb.append(src / second + "秒");
        return sb.toString();
    }
}