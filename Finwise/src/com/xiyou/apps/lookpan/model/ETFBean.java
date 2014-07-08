package com.xiyou.apps.lookpan.model;

public class ETFBean {

	private String time;
	private String val;
	private String unit;
	private String change;

	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	@Override
	public String toString() {
		return "ETFBean [time=" + time + ", val=" + val + ", unit=" + unit
				+ ", change=" + change + "]";
	}

}
