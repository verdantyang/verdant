package com.verdant.jdm.po;


@SuppressWarnings("serial")
public class Scp_traffic_data implements java.io.Serializable {
	private int date_id;
	private int t_qty;
	private int fx_id;
	private String gczbs;
	public int getDate_id() {
		return date_id;
	}
	public void setData_id(int date_id) {
		this.date_id = date_id;
	}
	public int getT_qty() {
		return t_qty;
	}
	public void setT_qty(int t_qty) {
		this.t_qty = t_qty;
	}
	public int getFx_id() {
		return fx_id;
	}
	public void setFx_id(int fx_id) {
		this.fx_id = fx_id;
	}
	public String getGczbs() {
		return gczbs;
	}
	public void setGczbs(String gczbs) {
		this.gczbs = gczbs;
	}
}