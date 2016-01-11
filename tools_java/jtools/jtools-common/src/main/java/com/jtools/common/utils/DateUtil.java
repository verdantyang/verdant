package com.jtools.common.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 日期工具类
 */
public class DateUtil {
	//showType 的取值：例如“yyyy年MM月dd日hh时mm分ss秒”，“yyyy-MM-dd HH:mm:ss” 等
	public static Calendar cal = new GregorianCalendar(); 
	public static int YEAR = cal.get(Calendar.YEAR);
	public static int MONTH = cal.get(Calendar.MONTH)+1;
	public static int DAY_OF_MONTH = cal.get(Calendar.DAY_OF_MONTH);
	public static int DAY_OF_WEEK = cal.get(Calendar.DAY_OF_WEEK)-1;
	
	public static Date getNowTime(){
		return new Date();
	}
	
	//以showType的格式，返回String类型的当前时间
	public static String getNowTime(String showType){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(showType);
		return sdf.format(date);
	}
	
	//将date按照showType转换成string
	public static String turnDateToString(Date dDate,String showType){
		DateFormat df = new SimpleDateFormat(showType);
		String dateString = df.format(dDate);
		return dateString;
	}
	
	//返回将string转换成date类型
	public static Date turnStringToDate(String sDate,String showType){
		//sDate的格式要和showType相符
		DateFormat df = new SimpleDateFormat(showType);
		Date date = null;
		try {
			date = df.parse(sDate);
		} catch (ParseException e) {
			System.out.println("格式不一致，返回null");
		}
		return date;
	}
	
	//获得时间差
	public static boolean getTimeDifference(Long timeBegin,Long timeEnd,Long timeStanding){
		//大于指定时间返回true，否则返回false
		Long difference = timeEnd - timeBegin;
		if(difference>=timeStanding){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 获取一年中的第几周
	 * 时间格式'yyyy-MM-dd'
	 * cuiqinglong create 2008-3-8
	 * @param sDate
	 * @return
	 */
	public static int getWeek(String sDate){
		int year = new Integer(sDate.substring(0,4));
		int month = new Integer(sDate.substring(5,7))-1;
		int day = new Integer(sDate.substring(8,10));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		int weekno=cal.get(Calendar.WEEK_OF_YEAR);
		return weekno;
	}
	/**
	 * 获取一年中的第几季度
	 * 时间格式'yyyy-MM-dd'
	 * cuiqinglong create 2008-3-8
	 * @param sDate
	 * @return
	 */
	public static int getQuarter(String sDate){
		int year = new Integer(sDate.substring(0,4));
		int month = new Integer(sDate.substring(5,7));
		switch (month) {
		case 1:
			return 1;
		case 2:
			return 1;
		case 3:
			return 1;
		case 4:
			return 2;
		case 5:
			return 2;
		case 6:
			return 2;
		case 7:
			return 3;
		case 8:
			return 3;
		case 9:
			return 3;
		case 10:
			return 4;
		case 11:
			return 4;
		case 12:
			return 4;
		default:
			return 1;
		}
	}
	/**
	 * 获取半年，上半年0，下半年1
	 * 时间格式'yyyy-MM-dd'
	 * cuiqinglong create 2008-3-8
	 * @param sDate
	 * @return
	 */
	public static int getHYear(String sDate){
		int month = new Integer(sDate.substring(5,7));
		if(month>=1 && month<=6){
			return 0;
		}else{
			return 1;
		}
	}
	
	public static String getYesterday(String sDate){
		if(sDate.equals("")){
			sDate=getNowTime("yyyy-MM-dd");
		}
		int year = new Integer(sDate.substring(0,4));
		int month = new Integer(sDate.substring(5,7))-1;
		int day = new Integer(sDate.substring(8,10));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day-1);
		return DateUtil.turnDateToString(cal.getTime(),"yyyy-MM-dd");
	}
	
	public static String getTomorrow(String sDate){
		if(sDate.equals("")){
			sDate=getNowTime("yyyy-MM-dd");
		}
		int year = new Integer(sDate.substring(0,4));
		int month = new Integer(sDate.substring(5,7))-1;
		int day = new Integer(sDate.substring(8,10));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day+1);
		return DateUtil.turnDateToString(cal.getTime(),"yyyy-MM-dd");
	}
	
	public static String getOneWeekAgo(String sDate){
		if(sDate.equals("")){
			sDate=getNowTime("yyyy-MM-dd");
		}
		int year = new Integer(sDate.substring(0,4));
		int month = new Integer(sDate.substring(5,7))-1;
		int day = new Integer(sDate.substring(8,10));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day-7);
		return DateUtil.turnDateToString(cal.getTime(),"yyyy-MM-dd");
	}
	
	public static String getOneMonthAgo(String sDate){
		if(sDate.equals("")){
			sDate=getNowTime("yyyy-MM-dd");
		}
		int year = new Integer(sDate.substring(0,4));
		int month = new Integer(sDate.substring(5,7))-1;
		int day = new Integer(sDate.substring(8,10));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return DateUtil.turnDateToString(cal.getTime(),"yyyy-MM-dd");
	}
	public static String getSomeDaysAgo(String sDate,int days){
		if(sDate.equals("")){
			sDate=getNowTime("yyyy-MM-dd");
		}
		int year = new Integer(sDate.substring(0,4));
		int month = new Integer(sDate.substring(5,7))-1;
		int day = new Integer(sDate.substring(8,10));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day-days);
		return DateUtil.turnDateToString(cal.getTime(),"yyyy-MM-dd");
	}
	public static String getSomeSecondsAgo(String sDate,int seconds){
		if(sDate.equals("")){
			sDate=getNowTime("yyyy-MM-dd HH:mm:ss");
		}
		int year = new Integer(sDate.substring(0,4));
		int month = new Integer(sDate.substring(5,7))-1;
		int day = new Integer(sDate.substring(8,10));
		int hour = new Integer(sDate.substring(11,13));
		int minute = new Integer(sDate.substring(14,16));
		int second = new Integer(sDate.substring(17,19));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second-seconds);
		return DateUtil.turnDateToString(cal.getTime(),"yyyy-MM-dd HH:mm:ss");
	}
	public static String getSomeSecondsAfter(String sDate,int seconds){
		if(sDate.equals("")){
			sDate=getNowTime("yyyy-MM-dd HH:mm:ss");
		}
		int year = new Integer(sDate.substring(0,4));
		int month = new Integer(sDate.substring(5,7))-1;
		int day = new Integer(sDate.substring(8,10));
		int hour = new Integer(sDate.substring(11,13));
		int minute = new Integer(sDate.substring(14,16));
		int second = new Integer(sDate.substring(17,19));
		//logger.debug("year:"+year+" month:"+month+" day:"+day+" hour:"+hour+" minute:"+minute+" second:"+second);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second+seconds);
		return DateUtil.turnDateToString(cal.getTime(),"yyyy-MM-dd HH:mm:ss");
	}
	//计算一个月有多少天
	public static List<Date> getMouthDayCount(String yearmonth){
		String str[]=yearmonth.split("-");
		Calendar   cal   =   Calendar.getInstance();   
		cal.set(Calendar.YEAR,Integer.parseInt(str[0]));
		cal.set(Calendar.MONTH,Integer.parseInt(str[1])-1);//7月   
		int   maxDate   =   cal.getActualMaximum(Calendar.DATE);
		List<Date> list=new ArrayList<Date>();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(DateUtil.turnStringToDate(yearmonth + "-" + maxDate,"yyyy-MM-dd"));
	    calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);//让日期减1 2
	    list.add(DateUtil.turnStringToDate(yearmonth + "-01", "yyyy-MM-dd"));
		list.add(calendar.getTime());
		
		return list;
	}
	
	public static Date getAfterDate(Date date,int days)   
	{   
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) + days);   
	    return calendar.getTime();
	} 
	public static Date getBeforeDate(Date date,int days)   
	{   
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) - days);   
	    return calendar.getTime();
	} 
	/**
	 * 把日期和时间组合成一个日期完整形式
	 * @param date   yyyy-MM-dd
	 * @param time   HH:mm:ss
	 * @return    拼接好的日期形式
	 *
	 */
	public static Date addDateAndTime(Date date,Date time){
		SimpleDateFormat sdfD = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfT = new SimpleDateFormat("HH:mm:ss");
		DateFormat df =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDate = sdfD.format(date);
		String formatTime = sdfT.format(time);
		try {
			Date parse = df.parse(formatDate+" "+formatTime);
			return parse;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(getAfterDate(new Date(), 0));
	}
}

