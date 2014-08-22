package com.xiyou.apps.lookpan.tools;

import android.util.Log;
import android.webkit.JavascriptInterface;


public class JsObject {

	public static final String JS_NAME = "jsObj";
	
	private onJsListener jsistener;
	
	
	public JsObject() {
		
	}
	
	
	@JavascriptInterface
	public void fullImage(String imageUrl) {
		Log.e("ImageURL", "" + imageUrl) ;
		if(jsistener != null) {
			jsistener.intentTo(imageUrl);
		} 
	}
	
	
	public interface onJsListener {
		void intentTo(String imageUrl) ;
	}


	public void setJsistener(onJsListener jsistener) {
		this.jsistener = jsistener;
	}
	
	
	
}
