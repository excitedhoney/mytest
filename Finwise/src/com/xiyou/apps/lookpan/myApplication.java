package com.xiyou.apps.lookpan;

import java.util.ArrayList;

import android.app.Application;

public class myApplication extends Application {

	private ArrayList<String> zixuanhangqing;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public ArrayList<String> getZixuanhangqing() {
		return zixuanhangqing;
	}

	public void setZixuanhangqing(ArrayList<String> zixuanhangqing) {
		this.zixuanhangqing = zixuanhangqing;
	}

}
