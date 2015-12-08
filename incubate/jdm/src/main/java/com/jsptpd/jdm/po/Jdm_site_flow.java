package com.jsptpd.jdm.po;


@SuppressWarnings("serial")
public class Jdm_site_flow implements java.io.Serializable {
	private int date_id;
	private String site_id;
	private int direction_id;
	private int total_flow;
	public int getDate_id() {
		return date_id;
	}
	public void setData_id(int date_id) {
		this.date_id = date_id;
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
	public int getTotal_flow() {
		return total_flow;
	}
	public void setTotal_flow(int total_flow) {
		this.total_flow = total_flow;
	}
}