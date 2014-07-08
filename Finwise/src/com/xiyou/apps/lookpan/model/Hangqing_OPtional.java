package com.xiyou.apps.lookpan.model;

/**
 * 自选股
 * 
 * @author leslie
 * 
 */

public class Hangqing_OPtional {
	private int localId;
	private int drag;
	private int top;
	private boolean isOptional; // 是否是自选股
	private boolean isHostory; //
	// =======

	private String id;
	private String symbol; // 简写编号
	private String status;
	private String title;
	private String subTitle;
	private String enTitle;
	private String type; // 类型 （商品 外汇 贵金属）
	private String tag; // 标签
	private String priority;
	private String orderNumber;
	private String url;
	private double prevClose;
	private String prevCloseTime;
	private double open;
	private String openTime;
	private String currency;
	private String numberFormat;
	private double average;
	private String standardDeviation;
	private String breakingNumber;
	private double dayRangeHigh;
	private double dayRangeLow;
	private String dayRangeHighTime;
	private String dayRangeLowTime;
	private double diff;
	private String diffPercent;
	private String diffUpdateTime;
	private String showInterval;
	private String description;
	private String ext;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getEnTitle() {
		return enTitle;
	}

	public void setEnTitle(String enTitle) {
		this.enTitle = enTitle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getLocalId() {
		return localId;
	}

	public void setLocalId(int localId) {
		this.localId = localId;
	}

	public int getDrag() {
		return drag;
	}

	public void setDrag(int drag) {
		this.drag = drag;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public boolean isOptional() {
		return isOptional;
	}

	public void setOptional(boolean isOptional) {
		this.isOptional = isOptional;
	}

	public boolean isHostory() {
		return isHostory;
	}

	public void setHostory(boolean isHostory) {
		this.isHostory = isHostory;
	}

	public double getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(double prevClose) {
		this.prevClose = prevClose;
	}

	public String getPrevCloseTime() {
		return prevCloseTime;
	}

	public void setPrevCloseTime(String prevCloseTime) {
		this.prevCloseTime = prevCloseTime;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getNumberFormat() {
		return numberFormat;
	}

	public void setNumberFormat(String numberFormat) {
		this.numberFormat = numberFormat;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public String getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(String standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public String getBreakingNumber() {
		return breakingNumber;
	}

	public void setBreakingNumber(String breakingNumber) {
		this.breakingNumber = breakingNumber;
	}

	public double getDayRangeHigh() {
		return dayRangeHigh;
	}

	public void setDayRangeHigh(double dayRangeHigh) {
		this.dayRangeHigh = dayRangeHigh;
	}

	public double getDayRangeLow() {
		return dayRangeLow;
	}

	public void setDayRangeLow(double dayRangeLow) {
		this.dayRangeLow = dayRangeLow;
	}

	public String getDayRangeHighTime() {
		return dayRangeHighTime;
	}

	public void setDayRangeHighTime(String dayRangeHighTime) {
		this.dayRangeHighTime = dayRangeHighTime;
	}

	public String getDayRangeLowTime() {
		return dayRangeLowTime;
	}

	public void setDayRangeLowTime(String dayRangeLowTime) {
		this.dayRangeLowTime = dayRangeLowTime;
	}

	public double getDiff() {
		return diff;
	}

	public void setDiff(double diff) {
		this.diff = diff;
	}

	public String getDiffPercent() {
		return diffPercent;
	}

	public void setDiffPercent(String diffPercent) {
		this.diffPercent = diffPercent;
	}

	public String getDiffUpdateTime() {
		return diffUpdateTime;
	}

	public void setDiffUpdateTime(String diffUpdateTime) {
		this.diffUpdateTime = diffUpdateTime;
	}

	public String getShowInterval() {
		return showInterval;
	}

	public void setShowInterval(String showInterval) {
		this.showInterval = showInterval;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
