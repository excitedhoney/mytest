package cn.precious.metal.view.entity;

import cn.precious.metal.view.GoldPointValue;


public class GLineToolsEntity {

	private double yPositionValue ;
	
	private String glineType ;
	
	private GoldPointValue firstValue ;
	
	private GoldPointValue secondValue ;
	
	private  boolean  showHorLine ;

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

	public boolean isShowHorLine() {
		return showHorLine;
	}

	public void setShowHorLine(boolean showHorLine) {
		this.showHorLine = showHorLine;
	}
	
}
