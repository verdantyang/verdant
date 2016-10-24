package com.verdant.jdm.vo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.verdant.jdm.tools.ToolsUtil;

public class DateInfo {

	private int date_id;
	private int season_id;
	private int week_id;
	private int day_id;
	public int getDate_id() {
		return date_id;
	}
	public void setData_id(int date_id) {
		this.date_id = date_id;
	}
	public int getSeason_id() {
		return season_id;
	}
	public void setSeason_id(int season_id) {
		this.season_id = season_id;
	}
	public int getWeek_id() {
		return week_id;
	}
	public void setWeek_id(int week_id) {
		this.week_id = week_id;
	}
	public int getDay_id() {
		return day_id;
	}
	public void setDay_id(int day_id) {
		this.day_id = day_id;
	}	
	
	public DateInfo(int date_id) {
		this.date_id = date_id;
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		cal.set(Calendar.YEAR, date_id/10000);
		cal.set(Calendar.MONTH, date_id/100%100-1);
		cal.set(Calendar.DATE, date_id%100);
		String dateDay = df.format(cal.getTime());
		int month = cal.get(Calendar.MONTH)+1;
		this.season_id = (month-1)/3 + 1;
		this.day_id = cal.get(Calendar.DAY_OF_WEEK)-1;
		int dateWeek = cal.get(Calendar.WEEK_OF_YEAR);
		if( 0 == this.day_id ) {
			this.day_id = 7;
			dateWeek += 1;
		}
		cal.set(Calendar.MONTH, season_id*3-3);
		cal.set(Calendar.DATE, 1);
		int seasonStartWeek = cal.get(Calendar.WEEK_OF_YEAR);
		if( 0 == cal.get(Calendar.DAY_OF_WEEK)-1 ) 
			seasonStartWeek += 1;
		
		this.week_id = dateWeek-seasonStartWeek+1;
		String seasonStartDay = df.format(cal.getTime());
		ToolsUtil.devPrintString("dateDay:"+dateDay);
		ToolsUtil.devPrintString("day_id:"+this.day_id);
		ToolsUtil.devPrintString("season_id:"+this.season_id);
		ToolsUtil.devPrintString("dateWeek:"+dateWeek);
		ToolsUtil.devPrintString("seasonStartWeek:"+seasonStartWeek);
		ToolsUtil.devPrintString("seasonStartDay:"+seasonStartDay);
		ToolsUtil.devPrintString("week_id:"+this.week_id);
	}
	
	public int dayAdd(int days) {
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		cal.set(Calendar.YEAR, date_id/10000);
		cal.set(Calendar.MONTH, date_id/100%100-1);
		cal.set(Calendar.DATE, date_id%100);
		cal.add(Calendar.DATE, days);
		String dateDay = df.format(cal.getTime());
		return Integer.parseInt(dateDay);
	}
}