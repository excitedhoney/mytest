package cn.precious.metal.entity;

import com.j256.ormlite.field.DatabaseField;

public class CustomNotice {
	@DatabaseField(generatedId = true)
	private int id ;
	@DatabaseField
	private String phoneNumber ;
	@DatabaseField
	private int fangshi ;   // 本地   还是短信
	@DatabaseField
	private int oritation ; // >=   <=
	@DatabaseField
	private String symbol ;
	@DatabaseField
	private String name ;
	@DatabaseField
	private double buyPrice ;
	@DatabaseField
	private double salePrice ;
	@DatabaseField
	private double setPrice ;
	@DatabaseField
	private long createTime ;
	@DatabaseField
	private long runTime ;
	@DatabaseField
	private boolean isHostory ;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getFangshi() {
		return fangshi;
	}
	public void setFangshi(int fangshi) {
		this.fangshi = fangshi;
	}
	public int getOritation() {
		return oritation;
	}
	public void setOritation(int oritation) {
		this.oritation = oritation;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}
	public double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	public double getSetPrice() {
		return setPrice;
	}
	public void setSetPrice(double setPrice) {
		this.setPrice = setPrice;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getRunTime() {
		return runTime;
	}
	public void setRunTime(long runTime) {
		this.runTime = runTime;
	}
	public boolean isHostory() {
		return isHostory;
	}
	public void setHostory(boolean isHostory) {
		this.isHostory = isHostory;
	}
	
}
