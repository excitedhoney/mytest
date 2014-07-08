package com.venus.carpapa.util;

import java.util.Iterator;
import java.util.Map;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.util.Log;

/**
 * HttpUtil.java
 * <p>
 * 调用webservice Copyright: Copyright(c) 2013
 * 
 * @author Gavin_Feng
 *         </p>
 * 
 */
public class HttpUtil {
	// public static String
	// URL_WEBSERVICE_ENDPOINT="http://test.yunjiazheng.com/api/index.php/app2/mobile?ws=1";
	// public static String URL_WEBSERVICE_NameSpace="urn:AppControllerwsdl";
	// public static String URL_WEBSERVICE_GET_CODE="getCode";
	// public static String URL_WEBSERVICE_LOGIN="login";
	// public static String URL_WEBSERVICE_LOGOUT="logout";
	// public static String URL_WEBSERVICE_SET_ORDER="setOrder";
	// public static String URL_WEBSERVICE_GET_ORDER_DETAIL="getOrderDetail";
	// public static String URL_WEBSERVICE_CANCEL_ORDER="cancelOrder";
	// public static String URL_WEBSERVICE_PAY_ORDER="payOrder";
	// public static String URL_WEBSERVICE_CONFIRM_ORDER="confirmOrder";
	// public static String URL_WEBSERVICE_IS_SERVICE="isService";
	// public static String
	// URL_WEBSERVICE_IS_SERVICE_BY_ADDR="isServiceByAddress";
	//

	public static String URL_WEBSERVICE_ENDPOINT = "http://jiumaixinxi.vicp.cc:8080/carpapa/services/Login?wsdl";
	public static String URL_WEBSERVICE_NameSpace = "http://jiumaixinxi.vicp.cc:8080/carpapa/services/Login";

	public static String ORDER_URL_WEBSERVICE_ENDPOINT = "http://jiumaixinxi.vicp.cc:8080/carpapa/services/CarOrder";
	public static String ORDER_URL_WEBSERVICE_NameSpace = "http://jiumaixinxi.vicp.cc:8080/carpapa/services/CarOrder";

	public static String URL_WEBSERVICE_LOGIN = "login";
	public static String ORDER_URL_WEBSERVICE_LOGIN = "listPage";
	// public static String URL_WEBSERVICE_GET_CODE="getCode";
	// public static String URL_WEBSERVICE_LOGOUT="logout";
	// public static String URL_WEBSERVICE_SET_ORDER="setOrder";
	// public static String URL_WEBSERVICE_GET_ORDER_DETAIL="getOrderDetail";
	// public static String URL_WEBSERVICE_CANCEL_ORDER="cancelOrder";
	// public static String URL_WEBSERVICE_PAY_ORDER="payOrder";
	// public static String URL_WEBSERVICE_CONFIRM_ORDER="confirmOrder";
	// public static String URL_WEBSERVICE_IS_SERVICE="isService";
	// public static String
	// URL_WEBSERVICE_IS_SERVICE_BY_ADDR="isServiceByAddress";

	private static boolean DEBUG = true;
	static String tag = HttpUtil.class.getName();

	/**
	 * Login
	 * */
	public static String login(String phone, String pwd) {

		String result = "";
		// 指定WebService的命名空间和调用的方法名
		SoapObject request = new SoapObject(URL_WEBSERVICE_NameSpace,
				URL_WEBSERVICE_LOGIN);
		request.addProperty("userName", phone);
		request.addProperty("password", pwd);
		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);
		HttpTransportSE transport = new HttpTransportSE(URL_WEBSERVICE_ENDPOINT);
		transport.debug = true;
		try {
			// 调用WebService
			transport.call(URL_WEBSERVICE_NameSpace + URL_WEBSERVICE_LOGIN,
					envelope);
			if (envelope.getResponse() != null) {
				SoapObject result1 = (SoapObject) envelope.bodyIn;
				SoapObject detail = (SoapObject) result1
						.getProperty(URL_WEBSERVICE_LOGIN + "Result");
				result = detail.toString();
				// result = envelope.getResponse().toString();

			}
			return result;
		} catch (Exception e) {

			return e.toString();
		}

	}

	public static String getLoading(String phone, String pwd) {
		String result = null;
		// ===============调用参数为对象的接口时模拟对象=======结束==================
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress("http://jiumaixinxi.vicp.cc:8080/carpapa/services/Login?wsdl");
			call.setOperationName("login");
			// ============调用接口传递参数为对象时使用的方法===========开始======
			// QName qn = new QName("urn:CarAssess", "CarSellInfo");
			// //序列化与反序列化CarSellInfo
			// call.registerTypeMapping(CarSellInfo.class, qn,
			// new BeanSerializerFactory(CarSellInfo.class, qn),
			// new BeanDeserializerFactory(CarSellInfo.class, qn));
			//
			// QName qn1 = new QName("urn:CarSellInfo", "TUser");
			// //序列化与反序列化TUser
			// call.registerTypeMapping(TUser.class, qn1,
			// new BeanSerializerFactory(TUser.class, qn1),
			// new BeanDeserializerFactory(TUser.class, qn1));
			// String result=(String)call.invoke(new Object[]{carSellInfo,
			// targetIdList, carpStrList, credpStrList, accipStrList});
			// ============调用接口传递参数为对象时使用的方法===========结束======

//			result = (String) call.invoke(new Object[] { phone, pwd });

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String CarOrder(int userid) {

		String result = "";
		// 指定WebService的命名空间和调用的方法名
		SoapObject request = new SoapObject(ORDER_URL_WEBSERVICE_NameSpace,
				ORDER_URL_WEBSERVICE_LOGIN);
		request.addProperty("userId", userid);

		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);
		HttpTransportSE transport = new HttpTransportSE(
				ORDER_URL_WEBSERVICE_ENDPOINT);
		transport.debug = true;
		try {
			// 调用WebService
			transport.call(ORDER_URL_WEBSERVICE_NameSpace + "#"
					+ ORDER_URL_WEBSERVICE_LOGIN, envelope);
			if (envelope.getResponse() != null) {
				result = envelope.getResponse().toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// public static String getCode(String phone){
	// Logger.d(tag, "getCode:"+phone);
	//
	// String result="";
	// // 指定WebService的命名空间和调用的方法名
	// SoapObject request = new SoapObject(URL_WEBSERVICE_NameSpace,
	// URL_WEBSERVICE_GET_CODE);
	// request.addProperty("mobile", phone);
	// // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
	// SoapSerializationEnvelope envelope = new
	// SoapSerializationEnvelope(SoapEnvelope.VER11);
	// envelope.bodyOut = request;
	// envelope.setOutputSoapObject(request);
	// HttpTransportSE transport = new HttpTransportSE(URL_WEBSERVICE_ENDPOINT);
	// transport.debug=true;
	// try {
	// // 调用WebService
	// transport.call(URL_WEBSERVICE_NameSpace+"#"+URL_WEBSERVICE_GET_CODE,
	// envelope);
	// if (envelope.getResponse() != null) {
	// result=envelope.getResponse().toString();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// Logger.d(tag, "getCode result:"+result);
	// return result;
	// }
	// public static String setOrder(int uid,String username,String orderjson){
	// Logger.d(tag, "uid:"+uid);
	// Logger.d(tag, "username:"+username);
	// Logger.d(tag, "orderjson:"+orderjson);
	// String result="";
	// // 指定WebService的命名空间和调用的方法名
	// SoapObject request = new SoapObject(URL_WEBSERVICE_NameSpace,
	// URL_WEBSERVICE_SET_ORDER);
	// request.addProperty("uid", uid);
	// request.addProperty("username", username);
	// request.addProperty("orderjson", orderjson);
	// // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
	// SoapSerializationEnvelope envelope = new
	// SoapSerializationEnvelope(SoapEnvelope.VER11);
	// envelope.bodyOut = request;
	// envelope.setOutputSoapObject(request);
	// HttpTransportSE transport = new HttpTransportSE(URL_WEBSERVICE_ENDPOINT);
	// transport.debug=true;
	// try {
	// // 调用WebService
	// transport.call(URL_WEBSERVICE_NameSpace+"#"+URL_WEBSERVICE_SET_ORDER,
	// envelope);
	// if (envelope.getResponse() != null) {
	// result=envelope.getResponse().toString();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return result;
	// }
	// public static String logout(String userId, String userPhone) {
	// String result="";
	// // 指定WebService的命名空间和调用的方法名
	// SoapObject request = new SoapObject(URL_WEBSERVICE_NameSpace,
	// URL_WEBSERVICE_LOGOUT);
	// request.addProperty("uid", userId);
	// request.addProperty("username", userPhone);
	// // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
	// SoapSerializationEnvelope envelope = new
	// SoapSerializationEnvelope(SoapEnvelope.VER11);
	// envelope.bodyOut = request;
	// envelope.setOutputSoapObject(request);
	// HttpTransportSE transport = new HttpTransportSE(URL_WEBSERVICE_ENDPOINT);
	// transport.debug=true;
	// try {
	// // 调用WebService
	// transport.call(URL_WEBSERVICE_NameSpace+"#"+URL_WEBSERVICE_LOGOUT,
	// envelope);
	// if (envelope.getResponse() != null) {
	// result=envelope.getResponse().toString();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return result;
	// }

	/**
	 * 调动服务器webservice接口
	 * 
	 * @param methodName
	 *            接口方法名称
	 * @param params
	 *            接口参数
	 * @return
	 * @throws Exception
	 */
	public static Object getInfoFromServer(String methodName,
			Map<String, Object> params) throws Exception {
		Object result = null;
		// 指定WebService的命名空间和调用的方法名
		SoapObject request = new SoapObject(URL_WEBSERVICE_NameSpace,
				methodName);

		for (Iterator<Map.Entry<String, Object>> it = params.entrySet()
				.iterator(); it.hasNext();) {
			Map.Entry<String, Object> m = it.next();
			request.addProperty(m.getKey(), m.getValue());
		}

		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);
		HttpTransportSE transport = new HttpTransportSE(URL_WEBSERVICE_ENDPOINT);
		transport.debug = DEBUG;
		// 调用WebService
		transport.call(URL_WEBSERVICE_NameSpace + "#" + methodName, envelope);
		if (envelope.getResponse() != null) {
			result = envelope.getResponse();
		}
		return result;
	}

}
