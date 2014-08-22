package cn.limc.androidcharts.entity;

import cn.limc.androidcharts.GoldPointValue;

public class GLineToolsEntity {

	private double yPositionValue ;
	
	private String glineType ;
	
	private GoldPointValue firstValue ;
	
	private GoldPointValue secondValue ;

	public GoldPointValue getFirstValue() {
		return firstValue;
	}

	public void setFirstValue(GoldPointValue firstValue) {
		this.firstValue = firstValue;
	}

	public GoldPointValue getSecondValue() {
		return secondValue;
	}

	public void setSecondValue(GoldPointValue secondValue) {
		this.secondValue = secondValue;
	}

	public String getGlineType() {
		return glineType;
	}

	public void setGlineType(String glineType) {
		this.glineType = glineType;
	}

	public double getyPositionValue() {
		return yPositionValue;
	}

	public void setyPositionValue(double yPositionValue) {
		this.yPositionValue = yPositionValue;
	}
	
	
}
