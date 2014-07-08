package com.xiyou.apps.lookpan.model;

public class ZhiBoBean {

	private String content;

	private String time;

	private String bigImageURL;

	private String bmiddle_picURL;

	public String getBmiddle_picURL() {
		return bmiddle_picURL;
	}

	public void setBmiddle_picURL(String bmiddle_picURL) {
		this.bmiddle_picURL = bmiddle_picURL;
	}

	public String getBigImageURL() {
		return bigImageURL;
	}

	public void setBigImageURL(String bigImageURL) {
		this.bigImageURL = bigImageURL;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "ZhiBoBean [content=" + content + ", time=" + time
				+ ", bigImageURL=" + bigImageURL + "]";
	}

}
