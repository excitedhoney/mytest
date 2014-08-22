package cn.precious.metal.entity;

public class ETFReport {

	private String time;
	private double val;
	private String unit;
	private double change;
	
	public ETFReport(String time,double val,String unit,double change){
		this.time = time ;
		this.val  =val ;
		this.unit = unit ;
		this.change = change ;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public double getVal() {
		return val;
	}
	public void setVal(double val) {
		this.val = val;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public double getChange() {
		return change;
	}
	public void setChange(double change) {
		this.change = change;
	}
	
	
}
