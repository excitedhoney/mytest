package cn.precious.metal.services;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobi.dreambox.frameowrk.core.http.DboxHTTPClientException;
import mobi.dreambox.frameowrk.core.http.DboxHttpClient;
import mobi.dreambox.frameowrk.core.http.DboxStringHttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.config.AndroidAPIConfig;
import cn.precious.metal.entity.LivesBean;
import cn.precious.metal.entity.NewsInfo;
import cn.precious.metal.entity.TaxonomyVocabulary;
import cn.precious.metal.exception.ErrorCodeConst;
import cn.precious.metal.utils.Utils;

public class NewsService extends AbstractKjsService {

	public NewsService(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private String callApiCommon(String apiUrl, Map<String, String> reqHeader,
			String requestType) throws ServiceException {

		if (!Utils.checkNetWork(context))
			if (!Utils.checkNetWork(context))
				throw new ServiceException(
						ErrorCodeConst.CODE_CONNECT_NETWORK_FAIL,
						"网络连接失败，请检查你的网络");
		DboxHttpClient httpClient = new DboxHttpClient(
				DboxStringHttpResponse.class);
		Map<String, String> reqHeaderMap = new HashMap<String, String>();
		if (reqHeader != null) {
			for (Map.Entry<String, String> entry : reqHeader.entrySet()) {
				String key = entry.getKey().toString();
				if (entry.getValue() != null) {
					String value = entry.getValue().toString();
					if (!value.equals(""))
						reqHeaderMap.put(key, URLEncoder.encode(value));
				}
			}
		}
		DboxStringHttpResponse resp = null;
		try {
			if (requestType.equals("post")) {
				resp = (DboxStringHttpResponse) httpClient.post(apiUrl,
						reqHeaderMap, null, "utf-8", 10000, 10000);
			} else if (requestType.equals("get")) {
				resp = (DboxStringHttpResponse) httpClient.get(apiUrl,
						reqHeaderMap, "utf-8", 10000, 10000);
			}
		} catch (DboxHTTPClientException e) {
			// TODO Auto-generated catch block
			throw new ServiceException(e.getErrCode(), e.getMessage());
		}

		if (resp != null && resp.getHttpStatus() == 200) {
			return resp.getResponseData();
		} else {
			return null;
		}
	}

	// 头条新闻
	public List<NewsInfo> headLineNews() throws ServiceException {

		String responseStr = callApiCommon(AndroidAPIConfig.HUAERJIE_NEWS,
				null, "get");

		if (responseStr == null)
			return null;

		List<NewsInfo> list = new ArrayList<NewsInfo>();
		NewsInfo news;
		try {
			JSONArray jsonArray = new JSONArray(responseStr);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				String node_title = jsonObject.getString("node_title");
				int nid = jsonObject.getInt("nid");
				long node_created = jsonObject.getLong("node_created");
				String file_managed_file_usage_uri = jsonObject
						.getString("file_managed_file_usage_uri");

				String taxonomy_vocabulary_6JsonArray = jsonObject
						.getString("taxonomy_vocabulary_6");

				JSONArray ja = new JSONArray(taxonomy_vocabulary_6JsonArray);
				List<TaxonomyVocabulary> taxonomy_vocabulary_6 = new ArrayList<TaxonomyVocabulary>();
				if (ja != null && ja.length() > 0) {
					for (int j = 0; j < ja.length(); j++) {
						JSONObject object = (JSONObject) ja.get(j);
						TaxonomyVocabulary tv = new TaxonomyVocabulary();
						tv.setTid(object.getString("tid"));
						taxonomy_vocabulary_6.add(tv);
					}
				}

				news = new NewsInfo();
				news.setFile_managed_file_usage_uri(file_managed_file_usage_uri);
				news.setNid(nid);
				news.setNode_created(node_created);
				news.setNode_title(node_title);
				news.setTaxonomy_vocabulary_6(taxonomy_vocabulary_6);
				list.add(news);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 头条新闻
	public List<NewsInfo> topHeadLineNews() throws ServiceException {

		String responseStr = callApiCommon(AndroidAPIConfig.TOP_HUAERJIE_NEWS,
				null, "get");

		if (responseStr == null)
			return null;

		List<NewsInfo> list = new ArrayList<NewsInfo>();
		NewsInfo news;
		try {
			JSONArray jsonArray = new JSONArray(responseStr);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				String node_title = jsonObject.getString("node_title");
				int nid = jsonObject.getInt("nid");
				long node_created = jsonObject.getLong("node_created");
				String file_managed_file_usage_uri = jsonObject
						.getString("file_managed_file_usage_uri");

				news = new NewsInfo();
				news.setFile_managed_file_usage_uri(file_managed_file_usage_uri);
				news.setNid(nid);
				news.setNode_created(node_created);
				news.setNode_title(node_title);
				list.add(news);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 时时新闻

	public List<LivesBean> LiveNews() throws ServiceException {

		String responseStr = callApiCommon(AndroidAPIConfig.HUAERJIE_LIVE,
				null, "get");

		if (responseStr == null)
			return null;

		List<LivesBean> list = new ArrayList<LivesBean>();
		try {
			JSONArray jsonArray = new JSONArray(responseStr);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 宏观 热点
	public List<NewsInfo> macroscopicHot(String ulr) throws ServiceException {
		String responseStr;
		if ("1".equals(ulr)) {
			responseStr = callApiCommon(AndroidAPIConfig.HUAERJIE_MACRO_HOT,
					null, "get");
		} else {
			responseStr = callApiCommon(AndroidAPIConfig.HUAERJIE_American,
					null, "get");
		}

		if (responseStr == null)
			return null;

		List<NewsInfo> list = new ArrayList<NewsInfo>();
		NewsInfo news;
		try {
			JSONArray jsonArray = new JSONArray(responseStr);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				String node_title = jsonObject.getString("node_title");
				int nid = jsonObject.getInt("nid");
				long node_created = jsonObject.getLong("node_created");
				String file_managed_file_usage_uri = jsonObject
						.getString("file_managed_file_usage_uri");

				String taxonomy_vocabulary_6JsonArray = jsonObject
						.getString("taxonomy_vocabulary_6");

				JSONArray ja = new JSONArray(taxonomy_vocabulary_6JsonArray);
				List<TaxonomyVocabulary> taxonomy_vocabulary_6 = new ArrayList<TaxonomyVocabulary>();
				if (ja != null && ja.length() > 0) {
					for (int j = 0; j < ja.length(); j++) {
						JSONObject object = (JSONObject) ja.get(j);
						TaxonomyVocabulary tv = new TaxonomyVocabulary();
						tv.setTid(object.getString("tid"));
						taxonomy_vocabulary_6.add(tv);
					}
				}

				news = new NewsInfo();
				news.setFile_managed_file_usage_uri(file_managed_file_usage_uri);
				news.setNid(nid);
				news.setNode_created(node_created);
				news.setNode_title(node_title);
				news.setTaxonomy_vocabulary_6(taxonomy_vocabulary_6);
				list.add(news);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public String getDetailNewInfo(String url) throws ServiceException {
		try {
			return callApiCommon(url, null, "get");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}

}
