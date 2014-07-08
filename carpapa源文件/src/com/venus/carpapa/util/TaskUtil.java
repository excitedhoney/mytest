package com.venus.carpapa.util;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.venus.carpapa.BaseActivity;





import android.app.Dialog;
import android.os.Handler;
import android.os.Message;


public class TaskUtil extends AjaxCallBack{
	private BaseActivity context;
	String tag=TaskUtil.class.getName();
	
	private int type=0;
	FinalHttp fh = null;
	Dialog dialog;
	String JSESSIONID=null;
	
	
	public TaskUtil(BaseActivity context)
	{
		this.context=context;
		fh = new FinalHttp();
		fh.configTimeout(90*60*1000);
	}	
		
	
	
	Handler loginhand=new Handler(){

		@Override
		public void handleMessage(Message msg) 
		{
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			loginRet((String)msg.obj);
		}
		
	};
	
	public void loginRet(String session)
	{
		context.taskLogin(session);
	}
	
	
	
	public void post(AjaxParams params,String url){
		//Header[] headers={new BasicHeader("Cookie","JSESSIONID="+spm.getSessionid())};		
		
		//fh.post(url,headers, params,"text/html", this);
		fh.post(url,params,this);
	}
	public void get(String url){
		Logger.d(tag, "url:"+url);
		fh.get(url, this);
		//fh.get(url,headers,null, this);
		
	}
	
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
//		if(dialog!=null){
//			dialog.show();
//		}
		
	}

	@Override
	public void onFailure(Throwable t, String strMsg) {
		// TODO Auto-generated method stub
//		if(dialog!=null){
//			dialog.cancel();
//		}		
//DialogUtil.toast(context, context.getString(R.string.request_data_error)+strMsg);
		context.canelDialog();
		Logger.e(tag, "onFailure");
	}

	@Override
	public void onSuccess(Object t) {
		// TODO Auto-generated method stub
//		if(dialog!=null){
//			dialog.cancel();
//		}
		Logger.d(tag, "context:"+context);
		//Logger.d(tag, "t="+t);
		if(type==0)
		{
			context.taskCallBack((String)t);
		}
		else
		{
			context.taskCallBack((String)t, type);
			
		}
		
	}

}
