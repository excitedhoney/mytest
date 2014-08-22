package cn.precious.metal.entity;

public class NationalFlag {
	
	private String country ;  
	private String currency ;
	private int drawableId ;
	
	public NationalFlag(String country,String currency,int drawableId){
		this.country = country ;
		this.currency = currency ;
		this.drawableId = drawableId ;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public int getDrawableId() {
		return drawableId;
	}
	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}
	
	
}
