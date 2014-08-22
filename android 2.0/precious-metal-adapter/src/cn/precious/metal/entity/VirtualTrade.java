package cn.precious.metal.entity;

import com.j256.ormlite.field.DatabaseField;

public class VirtualTrade {
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String phoneNumber;
	@DatabaseField
	private int orientation; // 交易方向 0做空 1 做多
	@DatabaseField
	private String treaty; // 商品代号
	@DatabaseField
	private String title; // 商品代号
	@DatabaseField
	private String type; // 商品的类别 sg（天津） tg （上交所）
	@DatabaseField
	private float transactionPrice; // 成交价
	@DatabaseField
	private float openVolume;
	@DatabaseField
	private float closeVolume;
	@DatabaseField
	private String createTime;
	@DatabaseField
	private String updateTime;
	@DatabaseField
	private int isOpening; // 持仓标志
	@DatabaseField
	private int isClose; // 平仓标志 一般会生成一条记录
	@DatabaseField
	private float openPrice; // 平仓的时候 需要记录建仓的价格

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public String getTreaty() {
		return treaty;
	}

	public void setTreaty(String treaty) {
		this.treaty = treaty;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getTransactionPrice() {
		return transactionPrice;
	}

	public void setTransactionPrice(float transactionPrice) {
		this.transactionPrice = transactionPrice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIsOpening() {
		return isOpening;
	}

	public void setIsOpening(int isOpening) {
		this.isOpening = isOpening;
	}

	public int getIsClose() {
		return isClose;
	}

	public void setIsClose(int isClose) {
		this.isClose = isClose;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public float getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(float openPrice) {
		this.openPrice = openPrice;
	}

//	public float getOpenTransaction() {
//		return openTransaction;
//	}
//
//	public void setOpenTransaction(float openTransaction) {
//		this.openTransaction = openTransaction;
//	}
//
//	public float getCloseTransaction() {
//		return closeTransaction;
//	}
//
//	public void setCloseTransaction(float closeTransaction) {
//		this.closeTransaction = closeTransaction;
//	}

	public float getOpenVolume() {
		return openVolume;
	}

	public void setOpenVolume(float openVolume) {
		this.openVolume = openVolume;
	}

	public float getCloseVolume() {
		return closeVolume;
	}

	public void setCloseVolume(float closeVolume) {
		this.closeVolume = closeVolume;
	}

}
