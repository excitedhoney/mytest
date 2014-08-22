package com.xiyou.apps.lookpan.task;

import cn.precious.metal.config.API;
import cn.precious.metal.entity.NewInfoBean;
import cn.precious.metal.entity.NewInfoList;
import cn.precious.metal.services.NewsService;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;


public class ShowNewsAction extends AsyncTask<Object, Object, NewInfoBean> {
	NewInfoBean data = null;
	WebView webView = null;
	TextView title = null;
	TextView time = null;
	String id;
	Activity activity = null;
	Context context = null;

	public ShowNewsAction(String i, Activity act) {
		id = i;
		this.activity = act;
		this.context = activity.getApplicationContext();
	}

	@Override
	protected NewInfoBean doInBackground(Object... params) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (!isCancelled()) {
			try {
				String jsstr = new NewsService(context).getDetailNewInfo(API.news_info + id + ".json") ;
				data = new NewInfoList(jsstr).getNewInfoBean();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}// end !isCanclled
		return data;

	}

	@Override
	protected void onPostExecute(NewInfoBean result) {
		if (result == null) {
			Toast.makeText(context, "网络异常或网络延迟", 1000).show() ;
			Message msg = new Message();
			msg.what = 20;

			msg.obj = result;
			handleMessage(msg);
			return;
		}
		Message msg = new Message();
		msg.what = 30;
		msg.obj = result;
		// 设置bean的body值
		handleMessage(msg);

	}

	public boolean handleMessage(Message msg) {
		return false;
	}

}
