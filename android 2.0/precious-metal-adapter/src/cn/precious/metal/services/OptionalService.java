package cn.precious.metal.services;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobi.dreambox.frameowrk.core.http.DboxHTTPClientException;
import mobi.dreambox.frameowrk.core.http.DboxHttpClient;
import mobi.dreambox.frameowrk.core.http.DboxStringHttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.content.Context;
import android.util.Log;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.config.AndroidAPIConfig;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.dao.OptionalDao;
import cn.precious.metal.entity.Optional;
import cn.precious.metal.entity.response.OptionalResponseContent;
import cn.precious.metal.exception.ErrorCodeConst;
import cn.precious.metal.utils.Utils;

public class OptionalService extends AbstractKjsService {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public OptionalService(Context context) {
		super(context);
	}

	/**
	 * 获取所有的天津所的 自选行情
	 */
	public List<Optional> getTjsOptionals(boolean isFirst)
			throws ServiceException {
		List<Optional> list;
		OptionalResponseContent resp = (OptionalResponseContent) callGetApi(
				AndroidAPIConfig.TJS_OPTIONAL, null,
				OptionalResponseContent.class);
		if (resp != null) {
			OptionalDao dao = new OptionalDao(context);
			list = resp.getResults();
			if (list != null && !list.isEmpty()) {
				int size = list.size();
				if (size > 10) {
					size = 10;
				}
				Optional option = null;
				for (int i = 0; i < size; i++) {
					option = list.get(i);
					option.setOptional(true);
					option.setType("tg");
					option.setAdd_time(sdf.format(new Date()));
					dao.addOrUpdateOptional(option);
				}
				if (isFirst)
					AppSetting.getInstance(context).setHaveInitTJSOptionalData(
							true);
			}
			return list;
		} else
			return null;
	}

	/**
	 * 获取所有的上交所的 自选行情
	 * 
	 */
	public List<Optional> getOptionalsByType(String type)
			throws ServiceException {
		List<Optional> list;
		OptionalResponseContent resp = null;
		
		String respJson = null ;
		
		if ("sg".equals(type)) {
			resp = (OptionalResponseContent) callGetApi(AndroidAPIConfig.SJS_OPTIONAL, null,OptionalResponseContent.class);
		} else if ("tg".equalsIgnoreCase(type)) {
			resp = (OptionalResponseContent) callGetApi(AndroidAPIConfig.TJS_OPTIONAL, null, OptionalResponseContent.class);
		} else if ("commodity".equalsIgnoreCase(type)) {
			respJson = (String) callGetApi(AndroidAPIConfig.COMMODITY_OPTIONAL, null,String.class);
		} else if ("forex".equalsIgnoreCase(type)) {
			respJson = (String) callGetApi(AndroidAPIConfig.FOREX_OPTIONAL, null,String.class);
		} else if ("indice".equalsIgnoreCase(type)) {
			respJson = (String) callGetApi(AndroidAPIConfig.INDICE_OPTIONAL, null, String.class);
		}
		
		if(respJson != null) {
			respJson = "{ \"results\":"  + respJson.trim() + "}" ;
			
			resp = (OptionalResponseContent)processResponseObj(OptionalResponseContent.class, respJson) ;
		}

		if (resp != null) {
			OptionalDao dao = new OptionalDao(context);
			list = resp.getResults();
			if (list != null && !list.isEmpty()) {
				Optional option = null;
				for (int i = 0; i < list.size(); i++) {
					option = list.get(i);
					option.setType(type);
					option.setAdd_time(sdf.format(new Date()));
					dao.addOrUpdateOptional(option);
				}
				if ("sg".equals(type)) {
					AppSetting.getInstance(context).setHaveInitSJSOptionalData(
							true);
				} else if ("tg".equalsIgnoreCase(type)) {
					AppSetting.getInstance(context).setHaveInitTJSOptionalData(
							true);
				} else if("forex".equalsIgnoreCase(type)) {
					AppSetting.getInstance(context).setHaveInitForexOptionalData(true);
				}else if("indice".equals(type)) {
					AppSetting.getInstance(context).setHaveInitIndiceOptionalData(true);
				}else if("commodity".equalsIgnoreCase(type)) {
					AppSetting.getInstance(context).setHaveInitCommodityOptionalData(true);
				}
			}
			return list;
		} else
			return null;
	}

	
	protected Object processResponseObj(Class classes, String respJsonStr)
			throws ServiceException {
		if (classes.getName().equals(String.class.getName())) {
			return respJsonStr;
		}
		if (respJsonStr == null || "".equals(respJsonStr))
			return null;
		if (respJsonStr != null) {
			Log.i(classes.getName(), respJsonStr);
			if (respJsonStr.trim().startsWith("{")) {
				try {
					return new Gson().fromJson(respJsonStr, classes);
				} catch (Exception e) {
					// TODO: handle exception
					return null;
				}
			} else {
				throw new ServiceException(
						ErrorCodeConst.CODE_JSON_FORMAT_ERROR, "json解析异常");
			}
		} else
			return null;
	}
	
	
	
	
	
	protected String callGetApi(String apiUrl, Map<String, String> paraMap)
			throws ServiceException {
		if (!Utils.checkNetWork(context))
			throw new ServiceException(
					ErrorCodeConst.CODE_CONNECT_NETWORK_FAIL, "网络连接失败，请检查你的网络");

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
		return callApiCommon(apiUrl + sb.toString(), null, "get");
	}

	public List<Optional> getOptionalsPriceByTradey(List<Optional> optionals)
			throws ServiceException {
		if (optionals == null || optionals.isEmpty())
			return null;
		StringBuffer sb = new StringBuffer();

		boolean isOptionalHaveId = true;

		for (int i = 0; i < optionals.size(); i++) {
			if (optionals.get(i).getLocalId() <= 0) {
				isOptionalHaveId = false;
			}
			sb.append(optionals.get(i).getTreaty());
			if (i != optionals.size() - 1)
				sb.append("_");
		}
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("treaty", sb.toString());
		String resp = callGetApi(AndroidAPIConfig.GET_TREATY_DATE, reqParam);

		if (resp != null && !"".equals(resp)) {
			try {
				JSONArray arr = new JSONArray(resp);
				OptionalDao dao = new OptionalDao(context);
				List<Optional> list = new ArrayList<Optional>();
				for (int i = 0; i < arr.length(); i++) {
					JSONObject obj = arr.getJSONObject(i);
					String treaty = obj.getString("treaty");
					if (isOptionalHaveId) {
						for (int j = 0; j < optionals.size(); j++) {
							if (optionals.get(j).getTreaty().equals(treaty)) {
								optionals.get(j).setAdd_time(
										obj.getString("add_time"));
								optionals.get(j).setNewest(
										obj.getString("newest"));
								optionals.get(j).setOpening(
										obj.getString("opening"));
								optionals.get(j).setHighest(
										obj.getString("highest"));
								optionals.get(j).setLowest(
										obj.getString("lowest"));

								optionals.get(j).setBuyone(
										obj.getString("buyone"));
								optionals.get(j).setSellone(
										obj.getString("sellone"));
								optionals.get(j).setBuyquantity(
										obj.getString("buyquantity"));
								optionals.get(j).setSellquantity(
										obj.getString("sellquantity"));
								optionals.get(j).setVolume(
										obj.getString("volume"));
								optionals.get(j).setVolume(
										obj.getString("volume"));
								optionals.get(j).setPrice(
										obj.getString("price"));
								optionals.get(j).setLastsettle(
										obj.getString("lastsettle"));
								optionals.get(j).setClosed(
										obj.getString("closed"));
								optionals.get(j).setAdd_time(
										obj.getString("add_time"));
								break;
							}
						}
					} else {
						Optional optiona = new Optional();
						optiona.setTitle(obj.getString("title"));
						optiona.setTreaty(obj.getString("treaty"));
						optiona.setAdd_time(obj.getString("add_time"));
						optiona.setNewest(obj.getString("newest"));
						optiona.setOpening(obj.getString("opening"));
						optiona.setHighest(obj.getString("highest"));
						optiona.setLowest(obj.getString("lowest"));

						optiona.setBuyone(obj.getString("buyone"));
						optiona.setSellone(obj.getString("sellone"));
						optiona.setBuyquantity(obj.getString("buyquantity"));
						optiona.setSellquantity(obj.getString("sellquantity"));
						optiona.setVolume(obj.getString("volume"));
						optiona.setVolume(obj.getString("volume"));
						optiona.setPrice(obj.getString("price"));
						optiona.setLastsettle(obj.getString("lastsettle"));
						optiona.setClosed(obj.getString("closed"));

						dao.addOrUpdateOptional(optiona);

						list.add(optiona);

					}
				}

				if (isOptionalHaveId) {
					boolean flag = dao.updateOptionalList(optionals);
					if (flag)
						return optionals;
				} else {
					return list;
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	public Optional getOptionalPriceByTradey(String treaty)
			throws ServiceException {
		if (treaty == null || "".equals(treaty))
			return null;
		Optional optiona = null ;

		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put("treaty", treaty);

		String resp = callGetApi(AndroidAPIConfig.GET_TREATY_DATE, reqParam);
		
		if (resp != null && !"".equals(resp)) {
			try {
				JSONArray arr = new JSONArray(resp);
				OptionalDao dao = new OptionalDao(context);
			if(arr != null && arr.length() >= 1) {
					JSONObject obj = arr.getJSONObject(0);
					optiona = new Optional();
					optiona.setTitle(obj.getString("title"));
					optiona.setTreaty(obj.getString("treaty"));
					optiona.setAdd_time(obj.getString("add_time"));
					optiona.setNewest(obj.getString("newest"));
					optiona.setOpening(obj.getString("opening"));
					optiona.setHighest(obj.getString("highest"));
					optiona.setLowest(obj.getString("lowest"));

					optiona.setBuyone(obj.getString("buyone"));
					optiona.setSellone(obj.getString("sellone"));
					optiona.setBuyquantity(obj.getString("buyquantity"));
					optiona.setSellquantity(obj.getString("sellquantity"));
					optiona.setVolume(obj.getString("volume"));
					optiona.setVolume(obj.getString("volume"));
					optiona.setPrice(obj.getString("price"));
					optiona.setLastsettle(obj.getString("lastsettle"));
					optiona.setClosed(obj.getString("closed"));
					dao.addOrUpdateOptional(optiona);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return optiona;

	}

	// /**
	// * 获取对用tag的行情列表
	// */
	// public List<Hangqing_OPtional> getOptionals() throws ServiceException {
	// Map<String, String> headerMap = new HashMap<String, String>();
	// List<Hangqing_OPtional> list;
	// headerMap.put("channel", ParamConfig.SHANG_PIN);
	// OptionalResponseContent resp = (OptionalResponseContent) callGetApi(
	// AndroidAPIConfig.HUAERJIE_HANGQING_OPTIONAL, headerMap,
	// OptionalResponseContent.class);
	// if (resp != null) {
	// OptionalDao dao = new OptionalDao(context);
	// list = resp.getResults();
	// boolean isLarge = false;
	// if (list != null && !list.isEmpty()) {
	// int size = list.size();
	//
	// if (size > 10) {
	// size = 10;
	// isLarge = true;
	// }
	// for (int i = 0; i < size; i++) {
	// Hangqing_OPtional optioanl = list.get(i) ;
	// optioanl.setOptional(true);
	// if (!dao.isExits(optioanl))
	// dao.addOptional(optioanl);
	// }
	// AppSetting.getInstance(context).setCommodityLoadFromDb(true);
	// }
	// return list ;
	// } else
	// return null;
	// }

	// public List<Optional> queryOptionalsFromDb(String tags,
	// boolean isChannel) {
	// return new OptionalDao(context)
	// .queryOptionalsByCatalog(tags, isChannel);
	// }

	// 查出所有自选
	public List<Optional> queryAllMyOptional() {
		return new OptionalDao(context).queryAllMyOptionals();
	}

	public boolean  updateDrag(List<Optional> list) {
		if (list == null || list.isEmpty())
			return false;
		int count = 0  ;
		int size = list.size();
		OptionalDao dao = new OptionalDao(context);
		for (int i = 0; i < list.size(); i++) {
			Optional optional = list.get(i);
			optional.setDrag(size - i);
			if(dao.updateOptional(optional)) {
				count ++ ;
			}
		}
		return count == size ;
	}

//	public boolean updateTop(List<Optional> list) {
//		if (list == null || list.isEmpty())
//			return false;
//		int size = list.size();
//		int count = 0;
//		boolean flag = false;
//		OptionalDao dao = new OptionalDao(context);
//		for (int i = 0; i < list.size(); i++) {
//			Optional optional = list.get(i);
//			optional.setTop(size - i);
//			flag = dao.updateOptional(optional);
//			if (flag) {
//				count++;
//			}
//		}
//		return size == count;
//	}
	
	
	public boolean updateTop(Optional op,int size) {
		if (op == null  )
			return false;
		OptionalDao dao = new OptionalDao(context);
		op.setTop(size);
		return  dao.updateOptional(op) ;
	}

	public boolean deleteOptionals(List<Optional> list) {
		return new OptionalDao(context).deleteOptionalList(list);
	}

	// 通过关键字查询 自选

	public List<Optional> queryOptionalsByKeyword(String keyword) {
		return new OptionalDao(context).queryOptionalsByKeyword(keyword);
	}

	public int getOptionalNumber() {
		return new OptionalDao(context).getIsOptionalNumber();
	}

	public Optional getOptionalBySymbol(String symbol) {
		return new OptionalDao(context).queryOptionalsBySymbol(symbol);
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
						reqHeaderMap, null, "utf-8", 5000, 5000);
			} else if (requestType.equals("get")) {
				resp = (DboxStringHttpResponse) httpClient.get(apiUrl,
						reqHeaderMap, "utf-8", 5000, 5000);
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
}
