package cn.precious.metal.services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import mobi.dreambox.frameowrk.core.constant.ErrorCodeConstants;
import mobi.dreambox.frameowrk.core.http.DboxHTTPClientException;
import mobi.dreambox.frameowrk.core.http.DboxHttpClient;
import mobi.dreambox.frameowrk.core.http.DboxStringHttpResponse;
import mobi.dreambox.frameowrk.core.util.StringUtil;
import android.content.Context;
import android.util.Log;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.exception.ErrorCodeConst;
import cn.precious.metal.utils.Utils;

import com.google.gson.Gson;

public abstract class AbstractKjsService {


	protected  Context context;

	protected AbstractKjsService(Context context) {
		this.context = context;
	}

	protected Object callPostApi(String apiUrl, Map<String, String> reqHeader,
			Class classes) throws ServiceException {
		if (!Utils.checkNetWork(context))
			throw new ServiceException(
					ErrorCodeConst.CODE_CONNECT_NETWORK_FAIL, "网络连接失败，请检查你的网络");
		String respJsonStr = callApiCommon(apiUrl.trim(), reqHeader, "post");
		return processResponseObj(classes, respJsonStr);
	}

	protected Object callGetApi(String apiUrl, Map<String, String> paraMap,
			Class classes) throws ServiceException {
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
		String respJsonStr;
		respJsonStr = callApiCommon(apiUrl + sb.toString(), null, "get");
		return processResponseObj(classes, respJsonStr);
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

	private void resp(org.json.JSONObject respObj) {
		// TODO Auto-generated method stub
	}


	public String uploadFile(File soundQuestionFile, String url,
			Map<String, String> reqHeader) throws ServiceException {
		if (!Utils.checkNetWork(context))
			if (!Utils.checkNetWork(context))
				throw new ServiceException(
						ErrorCodeConst.CODE_CONNECT_NETWORK_FAIL, "网络连接失败，请检查你的网络");

		String respString = null;
		HttpURLConnection conn = null;
		BufferedInputStream fin = null;
		BufferedOutputStream out = null;
		URL reqUrl;
		try {
			reqUrl = new URL(url);
			conn = (HttpURLConnection) reqUrl.openConnection();
			conn.setConnectTimeout(60000);
			Set<String> key = reqHeader.keySet();
			Iterator it = key.iterator();
			while (it.hasNext()) {
				String next = (String) it.next();
				conn.setRequestProperty(next, reqHeader.get(next));
			}
			conn.setRequestMethod("POST");
			// conn.setUseCaches(true);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// conn.setChunkedStreamingMode(1024*128);
			out = new BufferedOutputStream(conn.getOutputStream());
			fin = new BufferedInputStream(new FileInputStream(
					(File) soundQuestionFile));
			byte[] buf = new byte[1024 * 128];
			int len = -1;
			while ((len = fin.read(buf)) != -1) {
				out.write(buf, 0, len);
				out.flush();
			}
			respString = StringUtil.toString(conn.getInputStream());
		} catch (SocketTimeoutException e) {
			throw new ServiceException(ErrorCodeConstants.HTTP_SOCKET_TIMEOUT,
					e.getMessage());
		} catch (IOException e) {
			throw new ServiceException(ErrorCodeConstants.HTTP_IO_ERROR,
					e.getMessage());
		} finally {
			try {
				if (fin != null) {
					fin.close();
					fin = null;
				}
				if (out != null) {
					out = null;
				}
				if (conn != null) {
					conn.disconnect();
					conn = null;
				}
			} catch (IOException ioe) {
				throw new ServiceException(ErrorCodeConstants.HTTP_IO_ERROR,
						ioe.getMessage());
			}
		}
		return respString;
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
}
