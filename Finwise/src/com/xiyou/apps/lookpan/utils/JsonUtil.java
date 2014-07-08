package com.xiyou.apps.lookpan.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;
import com.google.common.reflect.TypeToken;
import com.xiyou.apps.lookpan.model.CalendarBean;
import com.xiyou.apps.lookpan.model.Hangqing_WaiHuiBean;
import com.xiyou.apps.lookpan.model.LivesBean;
import com.xiyou.apps.lookpan.model.MingJiaBean;
import com.xiyou.apps.lookpan.model.MingJiaInfoBean;
import com.xiyou.apps.lookpan.model.NewsBean;
import com.xiyou.apps.lookpan.model.News_AmBean;
import com.xiyou.apps.lookpan.model.News_ChinaBean;
import com.xiyou.apps.lookpan.model.News_HotBean;
import com.xiyou.apps.lookpan.model.News_Top_ImageBean;
import com.xiyou.apps.lookpan.model.ZhiBoBean;

public class JsonUtil {
	String jsonstr = new String();
	static final int CONNECTTIMEOUT = 5 * 1000;
	static final int READTIMEOUT = 5 * 1000;
	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	static final JsonFactory JSON_FACTORY = new JacksonFactory();
	static final DecimalFormat df = new DecimalFormat("0.00");
	static final DecimalFormat d1f = new DecimalFormat("#0.00%");

	/**
	 * 获得不活动期间的未读直播
	 * 
	 * @param URL
	 * @return
	 */
	public static String getCount2JSON(String URL) {
		String str = "0";
		try {
			JSONObject temp_ = new JSONObject(JsonUtil.getJsonstr(URL));
			JSONArray data = temp_.getJSONArray("results");
			for (int i = 0; i < data.length(); i++) {
				str = data.getJSONObject(i).getString("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;

	}

	/**
	 * 获得06866的专家数据
	 */

	public static ArrayList<MingJiaBean> getMingJiaBean2JSON(String URL) {

		ArrayList<MingJiaBean> list = new ArrayList<MingJiaBean>();

		try {

			JSONObject temp_ = new JSONObject(JsonUtil.getJsonstr(URL));
			JSONArray arr = temp_.getJSONArray("results");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = (JSONObject) arr.get(i);
				MingJiaBean mingJiaBean = new MingJiaBean();
				mingJiaBean.setArticle_id(obj.getString("article_id"));
				mingJiaBean.setArticle_title(obj.getString("article_title"));
				mingJiaBean.setAuthor(obj.getString("author"));
				mingJiaBean.setImage_url(obj.getString("image_url"));
				mingJiaBean.setArticle_date(obj.getString("article_date"));
				list.add(mingJiaBean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	/**
	 * 获得06866的专家数据 详情
	 */

	public static MingJiaInfoBean getMingJiaBeanInfo2JSON(String Url, String id) {
		MingJiaInfoBean MingJiaInfoBean = new MingJiaInfoBean();
		try {
			// Ulr06866专家数据 http://www.06866.com/api/special_report?id=
			JSONObject temp_ = new JSONObject(JsonUtil.getJsonstr(Url + id));
			JSONArray arr = temp_.getJSONArray("results");
			if (arr.length() > 0) {
				JSONObject obj = (JSONObject) arr.get(0);
				MingJiaInfoBean.setArticle_id(obj.getString("article_id"));
				MingJiaInfoBean
						.setArticle_title(obj.getString("article_title"));
				MingJiaInfoBean.setAuthor(obj.getString("author"));
				MingJiaInfoBean.setImage_url(obj.getString("image_url"));
				MingJiaInfoBean.setContent(obj.getString("content"));
				MingJiaInfoBean.setArticle_date(obj.getString("article_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return MingJiaInfoBean;

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
					JsonUtil.getJsonstr("http://api.markets.wallstreetcn.com/v1/calendar.json?start="
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
	 * 获得NewsBean信息
	 * 
	 * @param URL
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<NewsBean> getNewsBeanList2JSON(String URL) {
		ArrayList<NewsBean> list = new ArrayList<NewsBean>();
		HttpRequestFactory requestFactory = HTTP_TRANSPORT
				.createRequestFactory(new HttpRequestInitializer() {
					@Override
					public void initialize(HttpRequest request) {
						request.setParser(new JsonObjectParser(JSON_FACTORY));
					}
				});
		DailyMotionUrl url = new DailyMotionUrl(URL);
		url.fields = "node_title,nid,node_created,file_managed_file_usage_uri";
		HttpRequest request;
		try {
			request = requestFactory.buildGetRequest(url);
			list = (ArrayList<NewsBean>) request.execute().parseAs(
					new TypeToken<List<NewsBean>>() {
						private static final long serialVersionUID = 1L;
					}.getType());

			return list;

		} catch (Exception e) {
			try {
				throw new Exception(e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 获得微博信息
	 * 
	 * @param url
	 * @return
	 */
	public static ArrayList<ZhiBoBean> getZhiBoBean4JSON(String url) {

		ArrayList<ZhiBoBean> list = new ArrayList<ZhiBoBean>();

		try {
			JSONObject temp_ = new JSONObject(JsonUtil.getJsonstr(url));
			JSONArray data = temp_.getJSONArray("statuses");
			for (int i = 0; i < data.length(); i++) {
				JSONObject obj = data.getJSONObject(i);
				ZhiBoBean mzhibobean = new ZhiBoBean();
				mzhibobean.setContent(obj.getString("text"));
				mzhibobean.setTime(obj.getString("created_at"));

				JSONArray ja = obj.getJSONArray("pic_urls");

				if (ja.length() > 0) {
					mzhibobean.setBigImageURL(obj.getString("original_pic"));
					mzhibobean.setBmiddle_picURL(obj.getString("bmiddle_pic"));
				}
				list.add(mzhibobean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * 获得hangqing_waihuiBean信息
	 * 
	 * @param URL
	 * @return
	 */

	public static ArrayList<Hangqing_WaiHuiBean> getHangQing_WaihHuiBeanList2JSON_new(
			String URL) {

		ArrayList<Hangqing_WaiHuiBean> list = new ArrayList<Hangqing_WaiHuiBean>();
		try {
			JSONObject temp_ = new JSONObject(JsonUtil.getJsonstr(URL));
			JSONArray data = temp_.getJSONArray("results");
			for (int i = 0; i < data.length(); i++) {
				JSONObject obj = data.getJSONObject(i);
				Hangqing_WaiHuiBean mHangqing_WaiHuiBean = new Hangqing_WaiHuiBean();
				mHangqing_WaiHuiBean.setId(obj.getString("id"));
				mHangqing_WaiHuiBean.setSymbol(obj.getString("symbol"));
				mHangqing_WaiHuiBean.setStatus(obj.getString("status"));
				mHangqing_WaiHuiBean.setTitle(obj.getString("title"));
				mHangqing_WaiHuiBean.setSubTitle(obj.getString("subTitle"));
				mHangqing_WaiHuiBean.setEnTitle(obj.getString("enTitle"));
				mHangqing_WaiHuiBean.setType(obj.getString("type"));
				mHangqing_WaiHuiBean.setPriority(obj.getString("priority"));
				mHangqing_WaiHuiBean.setOrderNumber(obj
						.getString("orderNumber"));
				mHangqing_WaiHuiBean.setUrl(obj.getString("url"));
				mHangqing_WaiHuiBean.setPrevClose(obj.getString("prevClose"));
				mHangqing_WaiHuiBean.setPrevCloseTime(obj
						.getString("prevCloseTime"));
				mHangqing_WaiHuiBean.setOpen(obj.getString("open"));
				mHangqing_WaiHuiBean.setOpenTime(obj.getString("openTime"));
				mHangqing_WaiHuiBean.setCurrency(obj.getString("currency"));
				mHangqing_WaiHuiBean.setNumberFormat(obj
						.getString("numberFormat"));
				mHangqing_WaiHuiBean.setAverage(obj.getString("average"));
				mHangqing_WaiHuiBean.setStandardDeviation(obj
						.getString("standardDeviation"));
				mHangqing_WaiHuiBean.setBreakingNumber(obj
						.getString("breakingNumber"));
				mHangqing_WaiHuiBean.setDayRangeHigh(obj
						.getString("dayRangeHigh"));
				mHangqing_WaiHuiBean.setDayRangeLow(obj
						.getString("dayRangeLow"));
				mHangqing_WaiHuiBean.setDayRangeHighTime(obj
						.getString("dayRangeHighTime"));
				mHangqing_WaiHuiBean.setDayRangeLowTime(obj
						.getString("dayRangeLowTime"));
				mHangqing_WaiHuiBean.setDiff(obj.getString("diff"));
				mHangqing_WaiHuiBean.setDiffPercent(obj
						.getString("diffPercent"));
				mHangqing_WaiHuiBean.setDiffUpdateTime(obj
						.getString("diffUpdateTime"));
				mHangqing_WaiHuiBean.setShowInterval(obj
						.getString("showInterval"));
				mHangqing_WaiHuiBean.setDescription(obj
						.getString("description"));
				mHangqing_WaiHuiBean.setExt(obj.getString("ext"));
				list.add(mHangqing_WaiHuiBean);
			}
			if (list.size() > 0) {
				ArrayList<String> list_Sysmol = new ArrayList<String>();
				for (int i = 0; i < list.size(); i++) {
					list_Sysmol.add(list.get(i).getSymbol());
				}
				HashMap<String, String> map = getPrice_new(list_Sysmol);
				HashMap<String, String> map1 = getBid_new(list_Sysmol);
				HashMap<String, String> map2 = getAsk_new(list_Sysmol);
				String str = "";
				for (int i = 0; i < list.size(); i++) {
					str = map.get(list.get(i).getSymbol());
					list.get(i).setPrice(str);
					list.get(i).setAsk(map2.get(list.get(i).getSymbol()));
					list.get(i).setBid(map1.get(list.get(i).getSymbol()));
					if (null != str && str != "" && str.length() >= 1) {
						try {
							Float i_ = Float.parseFloat(str)
									- Float.parseFloat(list.get(i)
											.getPrevClose());
							list.get(i).setFudu(df.format(i_) + "");
							list.get(i).setFudu_baifenbi(
									d1f.format(i_
											/ Float.parseFloat(list.get(i)
													.getPrevClose()))
											+ "");
						} catch (Exception e) {
							e.printStackTrace();
							list.get(i).setFudu("0.00");
							list.get(i).setFudu_baifenbi("0.00%");
						}
					} else {
						list.get(i).setFudu("0.00");
						list.get(i).setFudu_baifenbi("0.00%");
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * 获得单个hangqing_waihuiBean信息
	 * 
	 * @param URL
	 * @return
	 */

	public static Hangqing_WaiHuiBean getHangQing_WaihHuiBeanList2JSON_new1(
			String URL) {
		Hangqing_WaiHuiBean mHangqing_WaiHuiBean = new Hangqing_WaiHuiBean();
		try {
			JSONObject temp_ = new JSONObject(JsonUtil.getJsonstr(URL));
			JSONObject obj = temp_.getJSONObject("results");
			mHangqing_WaiHuiBean.setId(obj.getString("id"));
			String symbol = obj.getString("symbol");
			String prevClose = obj.getString("prevClose");
			String price = getPrice(symbol);
			mHangqing_WaiHuiBean.setPrice(price);
			if (!price.equals("")) {
				Float i_ = Float.parseFloat(price)
						- Float.parseFloat(prevClose);
				mHangqing_WaiHuiBean.setFudu(df.format(i_) + "");
				mHangqing_WaiHuiBean.setFudu_baifenbi(d1f.format(i_
						/ Float.parseFloat(prevClose))
						+ "");
			} else {
				mHangqing_WaiHuiBean.setFudu("0.00");
				mHangqing_WaiHuiBean.setFudu_baifenbi("0.00%");
			}

			mHangqing_WaiHuiBean.setSymbol(symbol);
			mHangqing_WaiHuiBean.setStatus(obj.getString("status"));
			mHangqing_WaiHuiBean.setTitle(obj.getString("title"));
			mHangqing_WaiHuiBean.setSubTitle(obj.getString("subTitle"));
			mHangqing_WaiHuiBean.setEnTitle(obj.getString("enTitle"));
			mHangqing_WaiHuiBean.setType(obj.getString("type"));
			mHangqing_WaiHuiBean.setPriority(obj.getString("priority"));
			mHangqing_WaiHuiBean.setOrderNumber(obj.getString("orderNumber"));
			mHangqing_WaiHuiBean.setUrl(obj.getString("url"));
			mHangqing_WaiHuiBean.setPrevClose(prevClose);
			mHangqing_WaiHuiBean.setPrevCloseTime(obj
					.getString("prevCloseTime"));
			mHangqing_WaiHuiBean.setOpen(obj.getString("open"));
			mHangqing_WaiHuiBean.setOpenTime(obj.getString("openTime"));
			mHangqing_WaiHuiBean.setCurrency(obj.getString("currency"));
			mHangqing_WaiHuiBean.setNumberFormat(obj.getString("numberFormat"));
			mHangqing_WaiHuiBean.setAverage(obj.getString("average"));
			mHangqing_WaiHuiBean.setStandardDeviation(obj
					.getString("standardDeviation"));
			mHangqing_WaiHuiBean.setBreakingNumber(obj
					.getString("breakingNumber"));
			mHangqing_WaiHuiBean.setDayRangeHigh(obj.getString("dayRangeHigh"));
			mHangqing_WaiHuiBean.setDayRangeLow(obj.getString("dayRangeLow"));
			mHangqing_WaiHuiBean.setDayRangeHighTime(obj
					.getString("dayRangeHighTime"));
			mHangqing_WaiHuiBean.setDayRangeLowTime(obj
					.getString("dayRangeLowTime"));
			mHangqing_WaiHuiBean.setDiff(obj.getString("diff"));
			mHangqing_WaiHuiBean.setDiffPercent(obj.getString("diffPercent"));
			mHangqing_WaiHuiBean.setDiffUpdateTime(obj
					.getString("diffUpdateTime"));
			mHangqing_WaiHuiBean.setShowInterval(obj.getString("showInterval"));
			mHangqing_WaiHuiBean.setDescription(obj.getString("description"));
			mHangqing_WaiHuiBean.setExt(obj.getString("ext"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mHangqing_WaiHuiBean;

	}

	private static String getPrice(String str) {
		String strr = "";
		JSONObject json;
		try {
			json = new JSONObject(
					JsonUtil.getJsonstr("http://api.markets.wallstreetcn.com/v1/price.json?symbol="
							+ str));
			JSONArray JSONObject_ = json.getJSONArray("results");
			for (int i = 0; i < JSONObject_.length(); i++) {
				JSONObject jsons = JSONObject_.getJSONObject(i);
				strr = jsons.getString("price");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strr;
	}

	private static HashMap<String, String> getPrice_new(ArrayList<String> list) {
		StringBuffer strr = new StringBuffer();
		JSONObject json;
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size() - 1; i++) {
			strr.append(list.get(i) + "_");
		}
		strr.append(list.get(list.size() - 1));
		if (list.size() > 0) {
			try {
				json = new JSONObject(
						JsonUtil.getJsonstr("http://api.markets.wallstreetcn.com/v1/price.json?symbol="
								+ strr.toString()));
				if (json.length() > 0) {
					JSONArray JSONObject_ = json.getJSONArray("results");
					for (int i = 0; i < JSONObject_.length(); i++) {
						JSONObject jsons = JSONObject_.getJSONObject(i);
						map.put(jsons.getString("symbol"),
								jsons.getString("price"));
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	private static HashMap<String, String> getBid_new(ArrayList<String> list) {
		StringBuffer strr = new StringBuffer();
		JSONObject json;
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size() - 1; i++) {
			strr.append(list.get(i) + "_");
		}
		strr.append(list.get(list.size() - 1));
		if (list.size() > 0) {
			try {
				json = new JSONObject(
						JsonUtil.getJsonstr("http://api.markets.wallstreetcn.com/v1/price.json?symbol="
								+ strr.toString()));
				if (json.length() > 0) {
					JSONArray JSONObject_ = json.getJSONArray("results");
					for (int i = 0; i < JSONObject_.length(); i++) {
						JSONObject jsons = JSONObject_.getJSONObject(i);
						map.put(jsons.getString("symbol"),
								jsons.getString("bid"));
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	private static HashMap<String, String> getAsk_new(ArrayList<String> list) {
		StringBuffer strr = new StringBuffer();
		JSONObject json;
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size() - 1; i++) {
			strr.append(list.get(i) + "_");
		}
		strr.append(list.get(list.size() - 1));
		if (list.size() > 0) {
			try {
				json = new JSONObject(
						JsonUtil.getJsonstr("http://api.markets.wallstreetcn.com/v1/price.json?symbol="
								+ strr.toString()));
				if (json.length() > 0) {
					JSONArray JSONObject_ = json.getJSONArray("results");
					for (int i = 0; i < JSONObject_.length(); i++) {
						JSONObject jsons = JSONObject_.getJSONObject(i);
						map.put(jsons.getString("symbol"),
								jsons.getString("ask"));
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public static ArrayList<LivesBean> getLiveBeanList2JSON_new(String URL) {

		ArrayList<LivesBean> list = new ArrayList<LivesBean>();
		try {
			String temp = JsonUtil.getJsonstr(URL);
			JSONArray data = new JSONArray(temp);
			for (int i = 0; i < data.length(); i++) {
				JSONObject obj = data.getJSONObject(i);
				LivesBean mLivesBean = new LivesBean();
				mLivesBean.setNid(obj.getString("nid"));
				mLivesBean.setNode_title(obj.getString("node_title"));

				mLivesBean.setNode_created(obj.getString("node_created"));
				mLivesBean.setNode_content(obj.getString("node_content"));
				if (!obj.isNull("node_color")) {
					mLivesBean.setNode_color(obj.getString("node_color"));
				}
				if (!obj.isNull("node_icon")) {
					mLivesBean.setNode_icon(obj.getString("node_icon"));
				}
				if (!obj.isNull("node_format")) {
					mLivesBean.setNode_format(obj.getString("node_format"));
				}
				if (!obj.isNull("source")) {
					mLivesBean.setSource(obj.getString("source"));
				}
				if (!obj.isNull("sourcelink")) {
					mLivesBean.setSourcelink(obj.getString("sourcelink"));
				}
				list.add(mLivesBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	/**
	 * 获得News_AM_Bean信息
	 * 
	 * @param URL
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<News_AmBean> getNews_Am_BeanList2JSON(String URL) {

		ArrayList<News_AmBean> list = null;

		HttpRequestFactory requestFactory = HTTP_TRANSPORT
				.createRequestFactory(new HttpRequestInitializer() {
					@Override
					public void initialize(HttpRequest request) {
						request.setParser(new JsonObjectParser(JSON_FACTORY));
					}
				});
		DailyMotionUrl url = new DailyMotionUrl(URL);
		url.fields = "node_title,nid,node_created,file_managed_file_usage_uri";
		HttpRequest request;
		try {
			request = requestFactory.buildGetRequest(url);
			list = (ArrayList<News_AmBean>) request.execute().parseAs(
					new TypeToken<List<News_AmBean>>() {
						private static final long serialVersionUID = 1L;
					}.getType());
			return list;

		} catch (Exception e) {
			try {
				throw new Exception(e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 获得News_Recommend_Bean信息
	 * 
	 * @param URL
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<News_HotBean> getNews_Recommend_Bean_List2JSON(
			String URL) {

		ArrayList<News_HotBean> list = null;

		HttpRequestFactory requestFactory = HTTP_TRANSPORT
				.createRequestFactory(new HttpRequestInitializer() {
					@Override
					public void initialize(HttpRequest request) {
						request.setParser(new JsonObjectParser(JSON_FACTORY));
					}
				});
		DailyMotionUrl url = new DailyMotionUrl(URL);
		url.fields = "node_title,nid,node_created,file_managed_field_data_upload_uri";
		HttpRequest request;
		try {
			request = requestFactory.buildGetRequest(url);
			list = (ArrayList<News_HotBean>) request.execute().parseAs(
					new TypeToken<List<News_HotBean>>() {
						private static final long serialVersionUID = 1L;
					}.getType());
			return list;

		} catch (Exception e) {
			try {
				throw new Exception(e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 获得News_China信息
	 * 
	 * @param URL
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<News_ChinaBean> getNews_China_BeanList2JSON(
			String URL) {

		ArrayList<News_ChinaBean> list = null;

		HttpRequestFactory requestFactory = HTTP_TRANSPORT
				.createRequestFactory(new HttpRequestInitializer() {
					@Override
					public void initialize(HttpRequest request) {
						request.setParser(new JsonObjectParser(JSON_FACTORY));
					}
				});
		DailyMotionUrl url = new DailyMotionUrl(URL);
		url.fields = "node_title,nid,node_created,file_managed_file_usage_uri";
		HttpRequest request;
		try {
			request = requestFactory.buildGetRequest(url);
			list = (ArrayList<News_ChinaBean>) request.execute().parseAs(
					new TypeToken<List<News_ChinaBean>>() {
						private static final long serialVersionUID = 1L;
					}.getType());
			return list;

		} catch (Exception e) {
			try {
				throw new Exception(e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 获得New页的TOP 滑动图片
	 * 
	 * @param Url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<News_Top_ImageBean> getNews_Top_ImageBean2JSON(
			String Url) {
		ArrayList<News_Top_ImageBean> list = null;

		HttpRequestFactory requestFactory = HTTP_TRANSPORT
				.createRequestFactory(new HttpRequestInitializer() {

					@Override
					public void initialize(HttpRequest request) {
						request.setParser(new JsonObjectParser(JSON_FACTORY));
					}
				});
		DailyMotionUrl url = new DailyMotionUrl(Url);
		url.fields = "node_title,nid,node_created,file_managed_file_usage_uri";
		HttpRequest request;
		try {
			request = requestFactory.buildGetRequest(url);
			list = (ArrayList<News_Top_ImageBean>) request.execute().parseAs(
					new TypeToken<List<News_Top_ImageBean>>() {
						private static final long serialVersionUID = 1L;
					}.getType());
			return list;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 取得 日历 数据 CalendarBean
	 * 
	 * @param Url
	 * @return
	 */
	public static ArrayList<CalendarBean> getCalendarBean2JSON(String Url) {
		ArrayList<CalendarBean> list = new ArrayList<CalendarBean>();
		try {
			JSONObject temp_ = new JSONObject(JsonUtil.getJsonstr(Url));
			JSONArray obj = temp_.getJSONArray("results");
			for (int i = 0; i < obj.length(); i++) {
				JSONObject temp = (JSONObject) obj.get(i);
				CalendarBean c = new CalendarBean();
				c.setId(temp.getString("id"));
				c.setEventRowId(temp.getString("eventRowId"));
				c.setTimestamp(temp.getString("timestamp"));
				c.setLocalDateTime(temp.getString("localDateTime"));
				c.setImportance(temp.getString("importance"));
				c.setTitle(temp.getString("title"));
				c.setForecast(temp.getString("forecast"));
				c.setActual(temp.getString("actual"));
				c.setPrevious(temp.getString("previous"));
				c.setCategory_id(temp.getString("category_id"));
				c.setRelatedAssets(temp.getString("relatedAssets"));
				c.setRemark(temp.getString("remark"));
				c.setMark(temp.getString("mark"));
				c.setCalendarType(temp.getString("calendarType"));
				c.setCountry(temp.getString("country"));
				c.setCurrency(temp.getString("currency"));
				c.setEvent_attr_id(temp.getString("event_attr_id"));
				c.setDescription(temp.getString("description"));
				list.add(c);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
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
			throw new SojexException.UnknownException(
					protocolException.getMessage(), protocolException);

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
	 * @param url
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */

	public static String getJsonfromfile(String url) throws Exception {

		HttpClient client = new DefaultHttpClient();
		HttpGet request;
		String str = "";
		request = new HttpGet(new URI(url));

		try {
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				str = EntityUtils.toString(response.getEntity(), "utf-8");
				if (str != null) {
					return str;
				}
				return str;

			}
		} catch (ProtocolException protocolException) {
			throw new SojexException.UnknownException(
					protocolException.getMessage(), protocolException);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return str;
	}

	/**
	 * @param str
	 * @param param
	 * 
	 * @return
	 * 
	 * @throws JSONException
	 */
	public static JSONArray getJsonarr(String str, String param)
			throws JSONException {
		JSONObject jsonObject = new JSONObject(str);
		JSONArray jsonArray = jsonObject.getJSONArray(param);

		return jsonArray;
	}

	public static JSONArray getJsonarr(JSONObject jsobj, String param)
			throws JSONException {
		JSONArray jsonArray = jsobj.getJSONArray(param);

		return jsonArray;
	}

	/**
	 * @param str
	 * 
	 * @return
	 * 
	 * @throws JSONException
	 *             get the JSONArray from str
	 */
	public static JSONArray getJsonarr(String str) throws JSONException {
		JSONArray jsonArray = new JSONArray(str);
		return jsonArray;
	}

	public static JSONObject getJsonobj(String str, String param)
			throws JSONException {
		JSONObject jsonObject = new JSONObject(str);
		return jsonObject.getJSONObject(param);
	}

	public static JSONObject getJsonobj(JSONObject jsoby, String param)
			throws JSONException {
		return jsoby.getJSONObject(param);
	}

	/**
	 * @param jsonArray
	 * @param params
	 */
	public static List<Map<String, Object>> getData(JSONArray jsonArray,
			String[] params) throws JSONException {
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < jsonArray.length(); i++) {

			Map<String, Object> listItem = new HashMap<String, Object>();
			JSONObject jso = jsonArray.getJSONObject(i);
			for (String s : params) {
				listItem.put(s, jso.getString(s));
			}

			listItems.add(listItem);
		}
		return listItems;
	}

	public static List<Map<String, Object>> getData(JSONObject jso,
			String[] params) throws JSONException {
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

		Map<String, Object> listItem = new HashMap<String, Object>();
		for (String s : params) {
			listItem.put(s, jso.getString(s));
		}

		listItems.add(listItem);
		return listItems;
	}

	public static List<Map<String, Object>> getData(String str, String[] params)
			throws JSONException {
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		JSONObject jso = new JSONObject(str);
		Map<String, Object> listItem = new HashMap<String, Object>();
		for (String s : params) {
			listItem.put(s, jso.getString(s));
		}
		listItems.add(listItem);
		return listItems;
	}

	public static String DayForChinese(String time) {
		String[] c = time.split(" ");
		// String week = c[0];
		String month = c[1];
		String day = c[2];
		String atime = c[3];
		String year = c[5];

		return year + "-" + month(month) + "-" + day + "	"
				+ atime.split(":")[0] + ":" + atime.split(":")[1];

	}

	public static String DateForMonth(String time) {
		String[] c = time.split(" ");
		// String week = c[0];
		String month = c[1];
		String day = c[2];
		// String atime = c[3];
		// String year = c[5];
		return month(month) + "-" + day;
	}

	public static String week(String str) {
		if ("Mon".equals(str)) {
			return "星期一";
		} else if ("Tue".equals(str)) {
			return "星期二";
		} else if ("Wed".equals(str)) {
			return "星期三";
		} else if ("Thu".equals(str)) {
			return "星期四";

		} else if ("Fri".equals(str)) {
			return "星期五";
		} else if ("Sat".equals(str)) {
			return "星期六";
		} else if ("Sun".equals(str)) {
			return "星期日";
		}
		return str;
	}

	public static String month(String str) {
		if ("Jan".equals(str)) {
			return "01";
		} else if ("Feb".equals(str)) {
			return "02";
		} else if ("Mar".equals(str)) {
			return "03";
		} else if ("Apr".equals(str)) {
			return "04";
		} else if ("May".equals(str)) {
			return "05";
		} else if ("Jun".equals(str)) {
			return "06";
		} else if ("Jul".equals(str)) {
			return "07";
		} else if ("Aug".equals(str)) {
			return "08";
		} else if ("Sep".equals(str)) {
			return "09";
		} else if ("Oct".equals(str)) {
			return "10";
		} else if ("Nov".equals(str)) {
			return "11";
		} else if ("Dec".equals(str)) {
			return "12";
		}
		return str;
	}

	public static class DailyMotionUrl extends GenericUrl {
		public DailyMotionUrl(String encodedUrl) {
			super(encodedUrl);
		}

		@Key
		public String fields;
	}
	/**
	 * 转化时间
	 */

}
