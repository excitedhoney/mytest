package com.venus.carpapa.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String getDate2() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
		Date d=new Date();
		d.setDate(d.getDate()+1);
		return sdf.format(d);

	}
	public static String getDate3() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
		Date d=new Date();
		d.setDate(d.getDate()+2);
		return sdf.format(d);

	}
	public static String getDate4() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
		Date d=new Date();
		d.setDate(d.getDate()+3);
		return sdf.format(d);

	}
	public static String getDate5() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
		Date d=new Date();
		d.setDate(d.getDate()+4);
		return sdf.format(d);

	}
	public static String getDate6() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		Date d=new Date();
		d.setDate(d.getDate()+5);
		return sdf.format(d);

	}
	public static String getNow() {
		Date date = new Date();
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String timeStr = sdf.format(date);
		return timeStr;
	}
	public static String getYear() {
		Date date = new Date();
		String pattern = "yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String timeStr = sdf.format(date);
		return timeStr;
	}
	public static int getMM() {
		Date date = new Date();
		String pattern = "dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String timeStr = sdf.format(date);
		return Integer.parseInt(timeStr);
	}
	public static String getNow(long t) {
		Date date = new Date(t);
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String timeStr = sdf.format(date);
		return timeStr;
	}
}
