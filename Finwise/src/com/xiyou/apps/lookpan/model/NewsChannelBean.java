package com.xiyou.apps.lookpan.model;

import com.xiyou.apps.lookpan.api.API;

public class NewsChannelBean {

	private String newsChannelName;
	private String isChecked;

	public String getNewsChannelName() {
		return newsChannelName;
	}

	public void setNewsChannelName(String newsChannelName) {
		this.newsChannelName = newsChannelName;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	@Override
	public String toString() {
		return "NewsChannelBean [newsChannelName=" + newsChannelName
				+ ", isChecked=" + isChecked + "]";
	}

	public String getUri(String name) {
		if ("American".equals(name)) {
			return API.news_more_USA;
		} else if ("China".equals(name)) {
			return API.news_more_china;
		} else if ("Recommend".equals(name)) {
			return API.news_more_recommend;
		} else if ("Hot".equals(name)) {
			return API.news_more_hot;
		} else if ("Europe".equals(name)) {
			return API.news_more_europe;
		} else if ("Economy".equals(name)) {
			return API.news_more_finance;
		} else if ("Market".equals(name)) {
			return API.news_more_market;
		} else if ("CentralBank".equals(name)) {
			return API.news_more_CentralBank;
		} else if ("Company".equals(name)) {
			return API.news_more_company;
		}
		return "";
	}

}
