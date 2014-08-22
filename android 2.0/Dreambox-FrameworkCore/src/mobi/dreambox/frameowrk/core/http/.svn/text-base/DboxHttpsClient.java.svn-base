/** 
* 
 * Copyright (c) 1995-2012 Wonders Information Co.,Ltd. 
 * 1518 Lianhang Rd,Shanghai 201112.P.R.C.
 * All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Wonders Group.
 * (Social Security Department). You shall not disclose such
 * Confidential Information and shall use it only in accordance with 
 * the terms of the license agreement you entered into with Wonders Group. 
 *
 * Distributable under GNU LGPL license by gnu.org
 */

package mobi.dreambox.frameowrk.core.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import mobi.dreambox.frameowrk.core.constant.ErrorCodeConstants;
import mobi.dreambox.frameowrk.core.util.StringUtil;


/**<p>
 * Title: w3studio_framework_http
 * </p>
 * <p>
 * Description: 调用网络服务的客户端类
 * </p>
 * 
 * @author caven
 * @version $Revision$ May 17, 2012
 * @author (lastest modification by $Author$)
 * @since 20100620
 */
public class DboxHttpsClient{
	private Class respClass;
	public DboxHttpsClient(Class respClass){
		this.respClass = respClass;
	}
	
	/**
	* @param args
	*/
	private static class TrustAnyTrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] chain, String authType)
	throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType)
	throws CertificateException {
	}

	public X509Certificate[] getAcceptedIssuers() {
	return new X509Certificate[] {};
	}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
	public boolean verify(String hostname, SSLSession session) {
	return true;
	}
	}
	
	
	
	/***
	 * <p>
	 * Description:[方法功能中文描述]
	 * </p>
	 * 
	 * @param url
	 * @param headerProperties
	 * @param bodyProperties
	 * @param encode
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 */
	public DboxHttpResponse get(String url,Map<String,String> headerProperties,String encode,int connectTimeout,int readTimeout) throws DboxHTTPClientException{
//		url = urlEncode(url, encode);
		DboxHttpResponse resp=null;
		HttpsURLConnection conn  = null;
		InputStream in = null;
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
			new java.security.SecureRandom());
			URL reqUrl = new URL(StringUtil.utf8ToUnicode(url));
			conn = (HttpsURLConnection) reqUrl.openConnection();
			if(connectTimeout!=-1)
				conn.setConnectTimeout(connectTimeout);
			if(readTimeout!=-1)
				conn.setReadTimeout(readTimeout);
			setHttpRequestHeader(conn,headerProperties);
			conn.setRequestMethod("GET");
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setRequestProperty("Accept", "application/json");
			int status = conn.getResponseCode();
			if (status != 200) {
				resp = getRespFailInstance(conn);
			}else{
				resp = getRespInstance(conn);
			}
		} catch (MalformedURLException e) {
			throw new DboxHTTPClientException(ErrorCodeConstants.HTTP_URL_ERROR,e.getMessage());
		} catch (SocketTimeoutException e) {
			throw new DboxHTTPClientException(ErrorCodeConstants.HTTP_SOCKET_TIMEOUT,e.getMessage());
		}catch (IOException e) {
			throw new DboxHTTPClientException(ErrorCodeConstants.HTTP_IO_ERROR,e.getMessage());
		}
		catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			releaseConnection(conn, in, null);
		}
		return resp;
		
	}
	
	
	
	/***
	 * <p>
	 * Description:[方法功能中文描述]
	 * </p>
	 * 
	 * @param url
	 * @param headerProperties
	 * @param postObj
	 * @param encode
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 * @throws DboxHTTPClientException 
	 */
	public DboxHttpResponse post(String url,Map<String,String> headerProperties,Object postObj,String encode,int connectTimeout,int readTimeout) throws DboxHTTPClientException{
		DboxHttpResponse resp=null;
		HttpURLConnection conn  = null;
		try {
			URL reqUrl = new URL(url);
			conn = (HttpURLConnection) reqUrl.openConnection();
			if(connectTimeout!=-1)
				conn.setConnectTimeout(connectTimeout);
			if(readTimeout!=-1)
				conn.setReadTimeout(readTimeout);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			setHttpRequestHeader(conn,headerProperties);
			conn.setRequestMethod("POST");
			if(postObj!=null){
				conn.getOutputStream().write(postObjToByte(postObj));
				conn.getOutputStream().flush();
				conn.getOutputStream().close();
			}
			if (conn.getResponseCode()!=-1 && conn.getResponseCode() != 200) {
				resp = getRespFailInstance(conn);
			}else{
				resp = getRespInstance(conn);
			}
			resp.setRespHeader(conn.getHeaderFields());
		} catch (MalformedURLException e) {
			throw new DboxHTTPClientException(ErrorCodeConstants.HTTP_URL_ERROR,e.getMessage());
		} catch (SocketTimeoutException e) {
			throw new DboxHTTPClientException(ErrorCodeConstants.HTTP_SOCKET_TIMEOUT,e.getMessage());
		}catch (IOException e) {
			e.printStackTrace();
			throw new DboxHTTPClientException(ErrorCodeConstants.HTTP_IO_ERROR,e.getMessage());
		} finally{
			releaseConnection(conn, null, null);
		}
		return resp;
	}

	public DboxHttpResponse put(String url,Map<String,String> headerProperties,Object putObj,String encode,int connectTimeout,int readTimeout) throws DboxHTTPClientException{
		DboxHttpResponse resp=null;
		HttpURLConnection conn = null;
		URL reqUrl;
		try {
			reqUrl = new URL(StringUtil.utf8ToUnicode(url));
			conn = (HttpURLConnection) reqUrl.openConnection();
			if(connectTimeout!=-1)
				conn.setConnectTimeout(connectTimeout);
			if(readTimeout!=-1)
				conn.setReadTimeout(readTimeout);
			conn.setRequestMethod("PUT");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			setHttpRequestHeader(conn,headerProperties);
			conn.getOutputStream().write(postObjToByte(putObj));
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			
			int status = conn.getResponseCode();
			if (status != 200) {
				resp = getRespFailInstance(conn);
			}else{
				resp = getRespInstance(conn);
			}
			resp.setRespHeader(conn.getHeaderFields());
		} catch (MalformedURLException e) {
			throw new DboxHTTPClientException(ErrorCodeConstants.HTTP_URL_ERROR,e.getMessage());
		} catch (SocketTimeoutException e) {
			throw new DboxHTTPClientException(ErrorCodeConstants.HTTP_SOCKET_TIMEOUT,e.getMessage());
		}catch (IOException e) {
			throw new DboxHTTPClientException(ErrorCodeConstants.HTTP_IO_ERROR,e.getMessage());
		} finally{
			releaseConnection(conn, null, null);
		}
		return resp;
	}
	public DboxHttpResponse delete(String url,Map<String,String> headerProperties,Map<String,String> bodyProperties,String encode,int connectTimeout,int readTimeout){
		DboxHttpResponse resp=null;
		return resp;
	}
	
	public void get(String url,Map<String,String> headerProperties,Map<String,String> bodyProperties,String encode,IDboxHttpClientCallBack callBackFun,int connectTimeout,int readTimeout){
		DboxHttpResponse resp=null;
	}
	public void post(String url,Map<String,String> headerProperties,Map<String,String> bodyProperties,String encode,IDboxHttpClientCallBack callBackFun,int connectTimeout,int readTimeout){
		DboxHttpResponse resp=null;
	}
	public void put(String url,Map<String,String> headerProperties,Map<String,String> bodyProperties,String encode,IDboxHttpClientCallBack callBackFun,int connectTimeout,int readTimeout){
		DboxHttpResponse resp=null;
	}
	public void delete(String url,Map<String,String> headerProperties,Map<String,String> bodyProperties,String encode,IDboxHttpClientCallBack callBackFun,int connectTimeout,int readTimeout){
		DboxHttpResponse resp=null;
	}
	/***
	 * <p>
	 * Description:url编码
	 * </p>
	 * 
	 * @param url
	 * @param encode
	 * @return
	 * @throws DboxHTTPClientException
	 */
	private String urlEncode(String url,String encode) throws DboxHTTPClientException{
		if(encode!=null){
			try {
				return URLEncoder.encode(url, encode);
			}
			catch (UnsupportedEncodingException e) {
				throw new DboxHTTPClientException(ErrorCodeConstants.HTTP_URL_ENCODE_ERROR, e.getMessage());
			}
		}else{
			return url;
		}
	}
	
	/** <p>
	 * Description:设置请求头属性
	 * </p>
	 * 
	 * @param conn
	 * @param headerProperties
	 */
	private void setHttpRequestHeader(HttpURLConnection conn, Map<String, String> headerProperties) {
		if(headerProperties!=null){
			for(Iterator<Map.Entry<String, String>> iter = headerProperties.entrySet().iterator();iter.hasNext();){
			     Map.Entry<String,String> element = iter.next(); 
			     conn.setRequestProperty(element.getKey(), element.getValue());
			}
		}
	}
	
	/** <p>
	 * Description:将请求之后的流转成返回对象
	 * </p>
	 * 
	 * @param conn
	 * @return
	 * @throws DboxHTTPClientException 
	 */
	private DboxHttpResponse getRespInstance(HttpURLConnection conn) throws DboxHTTPClientException {
		Constructor con;
		DboxHttpResponse resp =null;
		try {
			con = respClass.getConstructor(InputStream.class);
			resp = (DboxHttpResponse) con.newInstance(conn.getInputStream());
			resp.setHttpStatus(conn.getResponseCode());
		}
		catch (Exception e) {
			throw new DboxHTTPClientException(ErrorCodeConstants.HTTP_CONVERT_RESPONSE_DATA, e.getMessage());
		}
		return resp;
	}
	
	private DboxHttpResponse getRespFailInstance(HttpURLConnection conn) throws DboxHTTPClientException {
		Constructor con;
		DboxHttpResponse resp =null;
		try {
			con = respClass.getConstructor();
			resp = (DboxHttpResponse) con.newInstance();
			resp.setHttpStatus(conn.getResponseCode());
		}
		catch (Exception e) {
			throw new DboxHTTPClientException(ErrorCodeConstants.HTTP_CONVERT_RESPONSE_DATA, e.getMessage());
		}
		return resp;
	}
	
	/** <p>
	 * Description:需要post的对象转成byte
	 * </p>
	 * 
	 * @param postObj
	 * @return
	 */
	private byte[] postObjToByte(Object postObj) {
		if(postObj instanceof String){
			return ((String)postObj).getBytes();
		}
		return null;
	}
	
	/**
	 * 释放HTTP连接
	 * 
	 * @param conn
	 * @param in
	 * @param out
	 */
	private void releaseConnection(HttpURLConnection conn, InputStream in, OutputStream out) {
		try {
			if (in != null) {
				in.close();
				in = null;
			}
			if (out != null) {
				out.close();
				out = null;
			}
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new RuntimeException("Release resource failed.");
		}
	}
	
}
