package com.verdant.jdm.po;

import java.util.Date;


@SuppressWarnings("serial")
public class Jdm_regression_site_flow implements java.io.Serializable {
	private int year_id;
	private int season_id;
	private int week_id;
	private String site_id;
	private int direction_id;
	private String pow_coef;
	private Date update_time;
	public int getYear_id() {
		return year_id;
	}
	public void setYear_id(int year_id) {
		this.year_id = year_id;
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
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
	public int getDirection_id() {
		return direction_id;
	}
	public void setDirection_id(int direction_id) {
		this.direction_id = direction_id;
	}
	public String getPow_coef() {
		return pow_coef;
	}
	public void setPow_coef(String pow_coef) {
		this.pow_coef = pow_coef;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
}