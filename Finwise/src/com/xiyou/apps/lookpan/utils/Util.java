package com.xiyou.apps.lookpan.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.widget.Toast;

import com.xiyou.apps.lookpan.db.BaseDataBase;
import com.xiyou.apps.lookpan.model.Hangqing_WaiHuiBean;

public class Util {

	/**
	 * Toast
	 * 
	 * @param c
	 *            Context
	 * @param msg
	 *            String
	 */
	@SuppressLint("ShowToast")
	public static void toastTips(Context c, String msg) {
		Toast.makeText(c, msg, 500).show();
	}

	// 获取版本号(内部识别号)

	public static int getVersionCode(Context context)// 获取版本号(内部识别号)
	{
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getSpecifiedDayBefore(String specifiedDay) {// 可以用new
																		// Date().toLocalString()传递参数
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return dayBefore;
	}

	/**
	 * 中英文 对照表
	 * 
	 * @param 英文
	 *            symbol
	 * @return 中文 title
	 */
	public static String getChineseName(String str) {
		if ("USDollarIndex".equals(str)) {
			return "美元指数";
		} else if ("EURUSD".equals(str)) {
			return "欧元/美元";
		} else if ("GBPUSD".equals(str)) {
			return "英镑/美元";
		} else if ("USDCNY".equals(str)) {
			return "美元/人民币";
		} else if ("USDJPY".equals(str)) {
			return "美元/日元";
		} else if ("USDCHF".equals(str)) {
			return "美元/瑞郎";

		} else if ("AUDUSD".equals(str)) {
			return "澳元/美元";
		} else if ("USDHKD".equals(str)) {
			return "美元/港币";
		} else if ("EURJPY".equals(str)) {
			return "欧元/日元";
		} else if ("BTCUSD".equals(str)) {
			return "比特币兑美元";
		} else if ("XAUUSD".equals(str)) {
			return "黄金";
		}

		// ---
		else if ("XAGUSD".equals(str)) {
			return "白银";
		} else if ("Copper".equals(str)) {
			return "铜";
		} else if ("UKOil".equals(str)) {
			return "布伦特石油";
		} else if ("XPTUSD".equals(str)) {
			return "铂";
		} else if ("XPDUSD".equals(str)) {
			return "钯";
		} else if ("NGAS".equals(str)) {
			return "天然气";
		} else if ("CORN".equals(str)) {
			return "玉米";
		} else if ("SOYBEAN".equals(str)) {
			return "大豆";
		} else if ("WHEAT".equals(str)) {
			return "小麦";
		} else if ("SUGAR".equals(str)) {
			return "糖";
		}

		// --
		else if ("000001".equals(str)) {
			return "上证指数";
		} else if ("399001".equals(str)) {
			return "深证成指";
		} else if ("399006".equals(str)) {
			return "创业板指数";
		} else if ("399300".equals(str)) {
			return "沪深300";
		} else if ("HKG33INDEX".equals(str)) {
			return "恒生指数";
		} else if ("SPX500INDEX".equals(str)) {
			return "标普500";
		} else if ("NAS100INDEX".equals(str)) {
			return "纳斯达克";
		} else if ("US30INDEX".equals(str)) {
			return "道琼斯";
		} else if ("JPN225INDEX".equals(str)) {
			return "日经指数";
		} else if ("GER30INDEX".equals(str)) {
			return "德国DAX";
		} else if ("FRA40INDEX".equals(str)) {
			return "法国CAC";
		}
		// --
		else if ("AUTD".equals(str)) {
			return "黄金t+d";
		} else if ("AGTD".equals(str)) {
			return "白银t+d";
		} else if ("MAUTD".equals(str)) {
			return "迷你黄金t+d";
		} else if ("AU9999".equals(str)) {
			return "黄金9999";
		} else if ("AU9995".equals(str)) {
			return "黄金9995";
		} else if ("AU100G".equals(str)) {
			return "100克金条";
		} else if ("PT9995".equals(str)) {
			return "铂金9995";
		}
		// --
		else if ("TTYXAGUSD".equals(str)) {
			return "现货白银";
		} else if ("TTJPD".equals(str)) {
			return "现货钯金";
		} else if ("TTJPT".equals(str)) {
			return "现货铂金";
		} else if ("FRA40INDEX".equals(str)) {
			return "法国CAC";
		} else if ("TTNI".equals(str)) {
			return "现货镍";
		}

		return str;
	}

	/**
	 * 中英文 对照表2
	 * 
	 * @param 中文
	 *            title
	 * @return 英文 symbol
	 */
	public static String getEnglishName(String str) {
		if ("美元指数".equals(str)) {
			return "USDollarIndex";

		} else if ("欧元/美元".equals(str)) {
			return "EURUSD";

		} else if ("英镑/美元".equals(str)) {
			return "GBPUSD";
		} else if ("美元/人民币".equals(str)) {
			return "USDCNY";
		} else if ("美元/日元".equals(str)) {
			return "USDJPY";
		} else if ("美元/瑞郎".equals(str)) {
			return "USDCHF";

		} else if ("澳元/美元".equals(str)) {
			return "AUDUSD";
		} else if ("美元/港币".equals(str)) {
			return "USDHKD";

		} else if ("欧元/日元".equals(str)) {
			return "EURJPY";
		} else if ("比特币兑美元".equals(str)) {
			return "BTCUSD";
		} else if ("黄金".equals(str)) {
			return "XAUUSD";
		}

		// ---
		else if ("白银".equals(str)) {
			return "XAGUSD";
		} else if ("铜".equals(str)) {
			return "Copper";
		} else if ("布伦特石油".equals(str)) {
			return "UKOil";
		} else if ("铂".equals(str)) {
			return "XPTUSD";
		} else if ("钯".equals(str)) {
			return "XPDUSD";
		} else if ("天然气".equals(str)) {
			return "NGAS";
		} else if ("玉米".equals(str)) {
			return "CORN";
		} else if ("大豆".equals(str)) {
			return "SOYBEAN";
		} else if ("小麦".equals(str)) {
			return "WHEAT";
		} else if ("糖".equals(str)) {
			return "SUGAR";
		}

		// --
		else if ("上证指数".equals(str)) {
			return "000001";
		} else if ("深证成指".equals(str)) {
			return "399001";
		} else if ("创业板指数".equals(str)) {
			return "399006";
		} else if ("沪深300".equals(str)) {
			return "399300";
		} else if ("恒生指数".equals(str)) {
			return "HKG33INDEX";
		} else if ("标普500".equals(str)) {
			return "SPX500INDEX";
		} else if ("纳斯达克".equals(str)) {
			return "NAS100INDEX";
		} else if ("道琼斯".equals(str)) {
			return "US30INDEX";
		} else if ("日经指数".equals(str)) {
			return "JPN225INDEX";
		} else if ("德国DAX".equals(str)) {
			return "GER30INDEX";
		} else if ("法国CAC".equals(str)) {
			return "FRA40INDEX";
		}
		// --
		else if ("黄金t+d".equals(str)) {
			return "AUTD";
		} else if ("白银t+d".equals(str)) {
			return "AGTD";
		} else if ("迷你黄金t+d".equals(str)) {
			return "MAUTD";
		} else if ("黄金9999".equals(str)) {
			return "AU9999";
		} else if ("黄金9995".equals(str)) {
			return "AU9995";
		} else if ("100克金条".equals(str)) {
			return "AU100G";
		} else if ("铂金9995".equals(str)) {
			return "PT9995";
		}
		// --
		else if ("现货白银".equals(str)) {
			return "TTYXAGUSD";
		} else if ("现货钯金".equals(str)) {
			return "TTJPD";
		} else if ("现货铂金".equals(str)) {
			return "TTJPT";
		} else if ("法国CAC".equals(str)) {
			return "FRA40INDEX";
		} else if ("现货镍".equals(str)) {
			return "TTNI";
		}

		return str;
	}

	/**
	 * 获得自选信息
	 * 
	 * @param context
	 * @param result
	 * @return
	 */

	public static ArrayList<Hangqing_WaiHuiBean> getZiXuan(Context context,
			ArrayList<Hangqing_WaiHuiBean> result) {
		ArrayList<String> list;

		ArrayList<Hangqing_WaiHuiBean> newList = new ArrayList<Hangqing_WaiHuiBean>();
		try {
			BaseDataBase.getInstance(context).open();
			list = BaseDataBase.getInstance(context).selectZiXuanChannel();
			Log.i("tag", list.toString());
			if (null == list || list.size() < 1) {
				ArrayList<String> strList = new ArrayList<String>();
				strList.add("");
				strList.add("TTYXAGUSD");
				strList.add("AUTD");
				strList.add("AGTD");
				strList.add("Copper");
				strList.add("EURUSD");
				strList.add("000001");
				BaseDataBase.getInstance(context).inserZiXuanChannel(strList);
				list = BaseDataBase.getInstance(context).selectZiXuanChannel();
			}
			for (String str : list) {
				for (Hangqing_WaiHuiBean hangqing : result) {
					if (str.equals(hangqing.getSymbol())) {
						newList.add(hangqing);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDataBase.getInstance(context).close();
		}
		return newList;

	}

	/**
	 * 
	 * @return true:
	 * 
	 * @see use isServiceTime()
	 * @deprecated
	 */
	public static boolean isWeekEnd() {
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.setTime(new Date(System.currentTimeMillis()));
		int i = myCalendar.get(Calendar.DAY_OF_WEEK);
		return i == 1 || i == 7;
	}

	/**
	 * � *
	 * 
	 * @return
	 */
	public static boolean isServiceTime() {
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.setTime(new Date(System.currentTimeMillis()));
		int time = myCalendar.get(Calendar.HOUR_OF_DAY);
		int day = myCalendar.get(Calendar.DAY_OF_WEEK);
		// 7 is Saturday ,1 is Sunday, 2 is Monday
		// Service time is from Monday 7'am to Saturday 4'am
		if (day == 7 || day == 1 || day == 2) {
			if ((day == 7 && time <= 4) || (day == 2 && time >= 7)) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	/**
	 * 获得指定日期
	 * 
	 * @param specifiedDay
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date getSpecifiedDayAfter(Date date, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + i);
		return c.getTime();
	}

	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * 
	 * 
	 * @param day
	 *            yyyyMMdd
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getTheDayOfWeek(String day) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar myCalendar = Calendar.getInstance();
		try {
			myCalendar.setTime(df.parse(day));
			int mDay = myCalendar.get(Calendar.DAY_OF_WEEK);
			String dayOfWeek = "";
			switch (mDay) {
			case 1:
				dayOfWeek = "星期天";
				break;
			case 2:
				dayOfWeek = "星期一";
				break;
			case 3:
				dayOfWeek = "星期二";
				break;
			case 4:
				dayOfWeek = "星期三";
				break;
			case 5:
				dayOfWeek = "星期四";
				break;
			case 6:
				dayOfWeek = "星期五";
				break;
			case 7:
				dayOfWeek = "星期六";
				break;
			}
			return dayOfWeek;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String filterHtml_new(String str) {

		String strr = str;
		char[] cah = strr.toCharArray();
		for (int i = 0; i < cah.length; i++) {
		}
		if (strr.contains("\n")) {
			strr = strr.replace("\n", "");
		}
		if (strr.contains("<p>")) {
			strr = strr.replace("<p>", "");
		}
		if (strr.contains("</p>")) {
			strr = strr.replace("</p>", "");
		}
		if (strr.contains("<br/>")) {
			strr = strr.replace("<br/>", "");
		}
		if (strr.contains("<div>")) {
			strr = strr.replace("<div>", "");
		}
		if (strr.contains("</div>")) {
			strr = strr.replace("</div>", "");
		}
		if (strr.contains("&nbsp;")) {
			strr = strr.replace("&nbsp;", "");
		}
		if (strr.contains(" ")) {
			strr = strr.replace(" ", "");
		}
		if (strr.contains("&mdash;")) {
			strr = strr.replace("&mdash;", "--");
		}
		if (strr.contains("&ldquo;")) {
			strr = strr.replace("&ldquo;", "“");
		}
		if (strr.contains("&rdquo;")) {
			strr = strr.replace("&ldquo;", "”");
		}
		if (strr.contains("<br/>")) {
			strr = strr.replace("<br/>", "");
		}
		if (strr.contains("<ahref=") || strr.contains("<a href=")) {

			strr = strr.split("<u>")[0];

		}

		return strr;

	}

	@SuppressLint("SimpleDateFormat")
	public static String getDate(String style) {
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat(style);
		return sf.format(date);
	}

	/**
	 * 时间排序
	 * 
	 * @param now
	 * @param compare
	 * 
	 * @return
	 * 
	 * @see now>compare
	 */
	public static String compareDate(Date now, Date compare) {
		long l = now.getTime() - compare.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		// long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min *
		// 60);// 秒
		StringBuilder sb = new StringBuilder();
		if (day > 0) {
			sb = new StringBuilder();
			sb.append(day + "天前");
			return sb.toString();
		}
		{
			if (hour > 0) {
				sb.append(hour + "小时");
			}
		}
		if (min > 0) {
			sb.append(min + "分前");
		}
		return sb.toString();
	}

}
