package cn.precious.metal.services;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import mobi.dreambox.frameowrk.core.http.DboxHTTPClientException;
import mobi.dreambox.frameowrk.core.http.DboxHttpClient;
import mobi.dreambox.frameowrk.core.http.DboxStringHttpResponse;
import android.content.Context;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.config.AndroidAPIConfig;
import cn.precious.metal.exception.ErrorCodeConst;
import cn.precious.metal.utils.Utils;

public class UserService {
	private Context context ;
	
	public UserService(Context context) {
		this.context = context ;
	}
	
	
	
	private String callApiCommon(String apiUrl, Map<String, String> reqHeader,
			String requestType) throws ServiceException {
		
		if (!Utils.checkNetWork(context))
			if (!Utils.checkNetWork(context))
				throw new ServiceException(
						ErrorCodeConst.CODE_CONNECT_NETWORK_FAIL,
						"网络连接失败，请检查你的网络");
		
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
	
	
	public String getCode(String phoneNumber) throws ServiceException{
		String response = null ;
		try {
			response = callApiCommon(AndroidAPIConfig.GET_CODE + phoneNumber , null, "get") ;
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw e ;
		}
		if(response != null) {
			return response ;
		}
		return null ;
	}
	
	public String register(String phoneNumber,String password) throws ServiceException{
		
		Map<String, String> paraMap = new HashMap<String, String>() ;
		paraMap.put("mobile", phoneNumber) ;
		paraMap.put("password", password) ;
		StringBuffer sb = new StringBuffer();
		int i = 0;
		if (paraMap != null) {
			for (Map.Entry<String, String> entry : paraMap.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				if (i == 0)
					sb.append("?");
				else
					sb.append("&");
				sb.append(key + "=" + value);
				i++;
			}
		}
		
		
		String response = null ;
		try {
			response = callApiCommon(AndroidAPIConfig.REGISTER_API  + sb.toString() , null, "get") ;
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw e ;
		}
		if(response != null) {
			JSONObject obj = null ;
			try {
				obj = new JSONObject(response);
				return  obj.getString("error") ;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null ;
	}
	
	
	public String login(String phoneNumber,String password) throws ServiceException{
		
		Map<String, String> paraMap = new HashMap<String, String>() ;
		paraMap.put("mobile", phoneNumber) ;
		paraMap.put("password", password) ;
		StringBuffer sb = new StringBuffer();
		int i = 0;
		if (paraMap != null) {
			for (Map.Entry<String, String> entry : paraMap.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				if (i == 0)
					sb.append("?");
				else
					sb.append("&");
				sb.append(key + "=" + value);
				i++;
			}
		}
		
		
		String response = null ;
		try {
			response = callApiCommon(AndroidAPIConfig.LOGIN_API  + sb.toString() , null, "get") ;
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw e ;
		}
		if(response != null) {
			JSONObject obj = null ;
			try {
				obj = new JSONObject(response);
				return  obj.getString("error") ;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null ;
	}
	
}
