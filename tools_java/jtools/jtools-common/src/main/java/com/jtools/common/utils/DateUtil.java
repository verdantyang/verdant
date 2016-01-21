package com.jtools.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 */
public class DateUtil {

    /**
     * 以showType的格式，返回String类型的当前时间
     *
     * @param showType
     * @return
     */
    public static String getNowTime(String showType) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(showType);
        return sdf.format(date);
    }

    /**
     * 将date按照showType转换成string
     *
     * @param dDate
     * @param showType
     * @return
     */
    public static String turnDateToString(Date dDate, String showType) {
        DateFormat df = new SimpleDateFormat(showType);
        String dateString = df.format(dDate);
        return dateString;
    }

    /**
     * 将string转换成date类型
     *
     * @param sDate    sDate的格式要和showType相符
     * @param showType
     * @return
     */
    public static Date turnStringToDate(String sDate, String showType) {
        DateFormat df = new SimpleDateFormat(showType);
        Date date = null;
        try {
            date = df.parse(sDate);
        } catch (ParseException e) {
            System.out.println("DateUtil|turnStringToDate|Error|格式不一致，返回null");
        }
        return date;
    }

    /**
     * 获取处于一年中的第几周
     *
     * @param sDate
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
     * @return
     */
    public static int getMouthDayCount(Date dDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}