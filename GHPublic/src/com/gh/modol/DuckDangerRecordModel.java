package com.gh.modol;

import java.io.Serializable;

public class DuckDangerRecordModel  implements Serializable
{
	public String wharfnameString;
	
	private String id;
	private String ship;
	private String startport;
	public String startidString;
	private String targetport;	
	public String tartgetidString;
	private String goods;
	private String goodstype;
	public String rankidString;
	private String goodsweight;
	private String portime;
	private String status;
	public String statusidString;
	private String unit;	
	public String unitidString;
	
	private String number;
	private String endtime;

	
	
	public String getunit() {
		return unit;
	}
	public void setunit(String unit) {
		this.unit = unit;
	}

	public String getnumber() {
		return number;
	}
	public void setnumber(String number) {
		this.number = number;
	}
	
	public String getendtime() {
		return endtime;
	}
	public void setendtime(String endtime) {
		this.endtime = endtime;
	}
	
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
	
	public String getship() {
		return ship;
	}
	public void setship(String ship) {
		this.ship = ship;
	}
	
	
	public String getstartport() {
		return startport;
	}
	public void setstartport(String startport) {
		this.startport = startport;
	}
	
	public String gettargetport() {
		return targetport;
	}
	public void settargetport(String targetport) {
		this.targetport = targetport;
	}
	
	public String getgoods() {
		return goods;
	}
	public void setgoods(String goods) {
		this.goods = goods;
	}
	

	public String getgoodstype() {
		return goodstype;
	}
	public void setgoodstype(String goodstype) {
		this.goodstype = goodstype;
	}
	
	public String getgoodsweight() {
		return goodsweight;
	}
	public void setgoodsweight(String goodsweight) {
		this.goodsweight = goodsweight;
	}
	
	public String getportime() {
		return portime;
	}
	public void setportime(String portime) {
		this.portime = portime;
	}
	
	public String getstatus() {
		return status;
	}
	public void setstatus(String status) {
		this.status = status;
	}
	
	
}