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
import cn.precious.metal.entity.ETFReport;
import cn.precious.metal.exception.ErrorCodeConst;
import cn.precious.metal.utils.Utils;

public class ETFReportService extends AbstractKjsService{

	public ETFReportService(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
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
	
	
	public List<ETFReport> goldReposts() throws ServiceException{
		  
		String responseStr = callApiCommon(AndroidAPIConfig.ETF_REPORT_GOLD, null, "get") ;
		
		if(responseStr == null)
			return null ;
		
		List<ETFReport> list = new ArrayList<ETFReport>() ;
		ETFReport report ;
		try {
			JSONArray jsonArray = new JSONArray(responseStr) ;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i) ;
				report = new ETFReport(jsonObject.getString("time"), jsonObject.getDouble("val"), jsonObject.getString("unit"), jsonObject.getDouble("change")) ;
				list.add(report) ;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list ;
	}
	
	public List<ETFReport> silverReposts() throws ServiceException{
		
		String responseStr = callApiCommon(AndroidAPIConfig.ETF_REPORT_SILVER, null, "get") ;
		
		if(responseStr == null)
			return null ;
		
		List<ETFReport> list = new ArrayList<ETFReport>() ;
		ETFReport report ;
		try {
			JSONArray jsonArray = new JSONArray(responseStr) ;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i) ;
				report = new ETFReport(jsonObject.getString("time"), Double.parseDouble(jsonObject.getString("val").replaceAll(",", "")), jsonObject.getString("unit"), jsonObject.getDouble("change")) ;
				list.add(report) ;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list ;
	}
	

}
