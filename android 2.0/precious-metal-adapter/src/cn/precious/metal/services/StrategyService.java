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
import org.json.JSONObject;

import android.content.Context;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.config.AndroidAPIConfig;
import cn.precious.metal.entity.DetailStrategy;
import cn.precious.metal.entity.Strategy;
import cn.precious.metal.exception.ErrorCodeConst;
import cn.precious.metal.utils.Utils;

public class StrategyService {
		
	private Context context ;
	
	public StrategyService(Context context) {
		this.context = context ;
	}
	
	
	
	private String callApiCommon(String apiUrl, Map<String, String> reqHeader,
			String requestType) throws ServiceException {

		if (!Utils.checkNetWork(context))
			if (!Utils.checkNetWork(context))
				throw new ServiceException(
						ErrorCodeConst.CODE_CONNECT_NETWORK_FAIL, "网络连接失败，请检查你的网络");
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
	
	public List<Strategy> getStrategys (String page)  throws ServiceException{
		String response  = callApiCommon(AndroidAPIConfig.CELUO_LIST+"?page=" + page, null, "get") ;
		List<Strategy> list = null ;
		try {
			if(response != null && !"".equals(response.trim())){
				list = new ArrayList<Strategy>() ;
				Strategy entity = null ;
				JSONArray jsonArr = new JSONArray(response) ;
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject jsonObj  = jsonArr.getJSONObject(i) ;
					entity = new Strategy() ;
					entity.setArticle_id(jsonObj.getString("article_id"));
					entity.setAuthor(jsonObj.getString("author"));
					entity.setAuthor_pic(jsonObj.getString("author_pic"));
					entity.setArticle_title(jsonObj.getString("article_title"));
					entity.setImage_url(jsonObj.getString("image_url"));
					entity.setSummary(jsonObj.getString("summary"));
					list.add(entity) ;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list ;
		
	}
	
	
	public DetailStrategy  getDetailStrategyById (String id)  throws ServiceException{
		String response  = callApiCommon(AndroidAPIConfig.DETAIL_CELUO+"?id=" + id, null, "get") ;
		DetailStrategy entity = null ;
		try {
			if(response != null && !"".equals(response.trim())){
				JSONObject jsonObj = new JSONObject(response) ;
					entity = new DetailStrategy() ;
					entity.setId(jsonObj.getString("id"));
					entity.setAuthor(jsonObj.getString("author"));
					entity.setArticle_title(jsonObj.getString("article_title"));
					entity.setArticle_content(jsonObj.getString("article_content"));
					entity.setArticle_date(jsonObj.getString("article_date"));
					entity.setImage_url(jsonObj.getString("image_url"));
					entity.setHits(jsonObj.getString("hits"));
					entity.setLast_modify_date(jsonObj.getString("last_modify_date"));
					entity.setSummary(jsonObj.getString("summary"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  entity ;
	}
	
}
