package cn.precious.metal.tools;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import cn.precious.metal.ui.CalendarBean;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class AndroidUtils {

	String jsonstr = new String();
	static final int CONNECTTIMEOUT = 5 * 1000;
	static final int READTIMEOUT = 5 * 1000;

	public static DisplayImageOptions getImageLoaderOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory().cacheOnDisc()
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();

		return options;
	}

	@SuppressLint("ShowToast")
	public static void toastTips(Context c, String msg) {
		Toast.makeText(c, msg, 500).show();
	}
	/**
	 * 
	 * @param day
	 * @param Yestday
	 * @param conteny
	 * @return ArrayList<CalendarBean>
	 */
	public static ArrayList<CalendarBean> getCalendar2JSON(String day,
			String Yestday, ArrayList<String> country,
			ArrayList<String> importance) {
		ArrayList<CalendarBean> list = new ArrayList<CalendarBean>();
		ArrayList<CalendarBean> list2 = new ArrayList<CalendarBean>();
		ArrayList<CalendarBean> list3 = new ArrayList<CalendarBean>();
		ArrayList<CalendarBean> list4 = new ArrayList<CalendarBean>();

		try {
			JSONObject temp_ = new JSONObject(
					AndroidUtils
							.getJsonstr("http://api.markets.wallstreetcn.com/v1/calendar.json?start="
									+ Yestday
									+ "-23%3A59%3A00&end="
									+ day
									+ "-00%3A00%3A00"));
			JSONArray data = temp_.getJSONArray("results");
			for (int i = 0; i < data.length(); i++) {
				JSONObject obj = data.getJSONObject(i);
				CalendarBean mCalendarBean = new CalendarBean();
				mCalendarBean.setPrevious(obj.getString("previous"));
				mCalendarBean.setForecast(obj.getString("forecast"));
				mCalendarBean.setActual(obj.getString("actual"));
				mCalendarBean.setCountry(obj.getString("country"));
				mCalendarBean.setImportance(obj.getString("importance"));
				mCalendarBean.setTitle(obj.getString("title"));
				mCalendarBean.setLocalDateTime(obj.getString("localDateTime"));
				mCalendarBean.setCalendarType(obj.getString("calendarType"));
				list.add(mCalendarBean);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (country.size() > 0 && list.size() > 0 && importance.size() <= 0) {

			for (int j = 0; j < country.size(); j++) {
				for (int i = 0; i < list.size(); i++) {

					if (country.get(j).equals(list.get(i).getCountry())) {
						list2.add(list.get(i));
					}
				}
			}
			return list2;
		} else if (importance.size() > 0 && country.size() > 0) {
			for (int j = 0; j < importance.size(); j++) {
				for (int i = 0; i < country.size(); i++) {
					for (int k = 0; k < list.size(); k++) {

						if (importance.get(j).equals(
								list.get(k).getImportance())
								&& country.get(i).equals(
										list.get(k).getCountry())) {
							list3.add(list.get(k));
						}
					}
				}
			}
			return list3;
		} else if (importance.size() > 0 && list.size() > 0
				&& country.size() <= 0) {

			for (int j = 0; j < importance.size(); j++) {
				for (int i = 0; i < list.size(); i++) {

					if (importance.get(j).equals(list.get(i).getImportance())) {
						list4.add(list.get(i));
					}
				}
			}
			return list4;
		} else {
			return list;
		}
	}

	/**
	 * 获取JSON字符串
	 * 
	 * @param path
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */
	public static String getJsonstr(String path) throws Exception {
		URL url = new URL(path);
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 利用HttpURLConnection对象,我们可以从网络中获取网页数据.
			conn.setConnectTimeout(CONNECTTIMEOUT); // 单位是毫秒，设置超时时间为5秒
			conn.setRequestMethod("GET"); // HttpURLConnection是通过HTTP协议请求path路径的，所以需要设置请求方式,可以不设置，因为默认为GET
			conn.setReadTimeout(READTIMEOUT);// 单位是毫秒，设置连接时间为5秒
			if (conn.getResponseCode() == 200) {// 判断请求码是否是200码，否则失败
				InputStream is = conn.getInputStream(); // 获取输入流
				BufferedInputStream bis = new BufferedInputStream(is);
				byte[] data = readStream(bis); // 把输入流转换成字符数组
				String json = new String(data); // 把字符数组转换成字符串
				conn.disconnect();
				return json;
			} else {
				return "";
			}
		} catch (ProtocolException protocolException) {
			throw new Exception(protocolException);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public static byte[] readStream(InputStream inputStream) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			bout.write(buffer, 0, len);
		}
		bout.close();
		inputStream.close();
		return bout.toByteArray();
	}

	/**
	 * 获得 ListView 高度
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
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

}
