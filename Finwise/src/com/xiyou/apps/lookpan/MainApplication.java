package com.xiyou.apps.lookpan;

import java.util.ArrayList;

import android.app.Application;

public class MainApplication extends Application {

	private ArrayList<String> countrylist;
	private ArrayList<String> importList;
	private String today;
	private String yesteday;

	@Override
	public void onCreate() {
		super.onCreate();
		countrylist = new ArrayList<String>();
		importList = new ArrayList<String>();
	}

	public ArrayList<String> getCountrylist() {
		return countrylist;
	}

	public void setCountrylist(ArrayList<String> countrylist) {
		this.countrylist = countrylist;
	}

	public ArrayList<String> getImportList() {
		return importList;
	}

	public void setImportList(ArrayList<String> importList) {
		this.importList = importList;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public String getYesteday() {
		return yesteday;
	}

	public void setYesteday(String yesteday) {
		this.yesteday = yesteday;
	}

}
