package com.venus.carpapa.util;





import com.google.gson.Gson;

/**
 *
 *
 */
public class ReqJsonUtil {
	
	
	/**
	 * 
	 * @param request
	 * @param cla
	 * @return
	 */
	public static Object changeToObject(String jsonContent, Class cla) {
		Object object = null;
		try {
			
			
			Gson gson = new Gson();
			object = gson.fromJson(jsonContent, cla);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
}
