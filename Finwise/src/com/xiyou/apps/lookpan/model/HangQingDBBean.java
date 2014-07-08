package com.xiyou.apps.lookpan.model;

public class HangQingDBBean {

	private String symbol;
	private String title;
	private String isNeed;
	private String subTitle;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsNeed() {
		return isNeed;
	}

	public void setIsNeed(String isNeed) {
		this.isNeed = isNeed;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	@Override
	public String toString() {
		return "HangQingDBBean [symbol=" + symbol + ", title=" + title
				+ ", isNeed=" + isNeed + ", subTitle=" + subTitle + "]";
	}

}
