package com.jtools.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: verdant
 * Create: 2016/1/22
 * Func:   日期工具类
 */
public class DateUtils2 {

    /**
     * 以showType的格式，返回String类型的当前时间
     *
     * @param showType
     * @since 1.0
     * @return
     */
    public static String getNowTime(String showType) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(showType);
        return sdf.format(date);
    }

    /**
     * 将date按照showType转换成String
     *
     * @param dDate
     * @param showType
     * @since 1.0
     * @return
     */
    public static String turnDateToString(Date dDate, String showType) {
        DateFormat df = new SimpleDateFormat(showType);
        String dateString = df.format(dDate);
        return dateString;
    }

    /**
     * 将string转换成Date类型
     *
     * @param sDate    sDate的格式要和showType相符
     * @param showType
     * @since 1.0
     * @return
     */
    public static Date turnStringToDate(String sDate, String showType) {
        DateFormat df = new SimpleDateFormat(showType);
        Date date = null;
        try {
            date = df.parse(sDate);
        } catch (ParseException e) {
            System.out.println("Error|DateUtils2|turnStringToDate|格式不一致");
        }
        return date;
    }

    /**
     * 将时间戳转换成Date类型
     *
     * @param timestamp    时间戳
     * @since 1.0
     * @return
     */
    public static Date turnTimestampToDate(long timestamp) {
        return new Date(timestamp);
    }

    /**
     * 获取处于一年中的第几周
     *
     * @param sDate
     * @since 1.0
     * @return
     */
    public static int getWeek(Date sDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sDate);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取处于一年中的第几季度
     *
     * @param sDate
     * @since 1.0
     * @return
     */
    public static int getQuarter(Date sDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sDate);
        return cal.get(Calendar.MONTH) / 4 + 1;
    }

    /**
     * 获取days天后的日期
     *
     * @param dDate
     * @param days
     * @since 1.0
     * @return
     */
    public static Date getSomeDaysAgo(Date dDate, int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dDate);
        calendar.add(calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 计算一个月有多少天
     *
     * @param dDate
     * @since 1.0
     * @return
     */
    public static int getMouthDayCount(Date dDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据毫秒数格式化时间
     * @param src
     * @since 1.0
     * @return
     */
    public static String timeConversion(long src) {
        int second = 1000;
        int minute = second * 60;
        int hour = minute * 60;
        int day = hour * 24;
        StringBuilder sb = new StringBuilder();
        if (src / day > 0) {
            sb.append( src / day + "天");
            src = src % day;
        }
        if (src / hour > 0) {
            sb.append( src / hour + "小时");
            src = src % hour;
        }
        if (src / minute > 0) {
            sb.append( src / minute + "分");
            src = src % minute;
        }
        if (src != 0)
            sb.append( src / second + "秒");
        return sb.toString();
    }
}