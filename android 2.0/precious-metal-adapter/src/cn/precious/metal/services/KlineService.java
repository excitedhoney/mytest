package cn.precious.metal.services;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import mobi.dreambox.frameowrk.core.http.DboxHTTPClientException;
import mobi.dreambox.frameowrk.core.http.DboxHttpClient;
import mobi.dreambox.frameowrk.core.http.DboxStringHttpResponse;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.config.AndroidAPIConfig;
import cn.precious.metal.exception.ErrorCodeConst;
import cn.precious.metal.utils.Utils;
import cn.precious.metal.view.KLineEntity;
import android.content.Context;

public class KlineService {
	
	private Context context ;
	
	public KlineService(Context context) {
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
	
	
	public String callGetApi(Map<String, String> paraMap) throws ServiceException {
		if (!Utils.checkNetWork(context))
			throw new ServiceException(
					ErrorCodeConst.CODE_CONNECT_NETWORK_FAIL, "网络连接失败，请检查你的网络");

		
		StringBuffer sb = new StringBuffer();
		int i = 0;
		if (paraMap != null && !paraMap.isEmpty()) {
			for (Map.Entry<String, String> entry : paraMap.entrySet()) {
				String key = entry.getKey().toString();
				String value = "" ;
				try {
					value = entry.getValue().toString();
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (i == 0)
					sb.append("?");
				else
					sb.append("&");
				sb.append(key + "=" + value);
				i++;
			}
		}
		String respJsonStr = callApiCommon(AndroidAPIConfig.KLINE_API + sb.toString(), null, "get");
		return respJsonStr ;
	}
	
	
	
	public List<KLineEntity> getKlineByinterval(String treaty , String type ,String interval) throws ServiceException {
		Map<String, String> paraMap = new HashMap<String, String>() ;
		paraMap.put("treaty", treaty) ;
		paraMap.put("type", type) ;
		paraMap.put("interval", interval) ;
		paraMap.put("limitnumber", "300") ;
		String response  = callGetApi(paraMap);
		List<KLineEntity> list = null ;
		try {
			if(response != null && !"".equals(response.trim())){
				list = new ArrayList<KLineEntity>() ;
				System.out.println(response);
				KLineEntity entity = null ;
				JSONArray jsonArr = new JSONArray(response) ;
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject jsonObj  = jsonArr.getJSONObject(i) ;
					entity = new KLineEntity() ;
					entity.setHigh(Double.parseDouble(jsonObj.getString("highest")));
					entity.setLow(Double.parseDouble(jsonObj.getString("lowest")));
					entity.setOpen(Double.parseDouble(jsonObj.getString("start_opening")));
					entity.setZuoShou(Double.parseDouble(jsonObj.getString("end_opening")));
					entity.setDate(jsonObj.getString("time"));
					list.add(entity) ;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list ;
	}
	
	
	
}
