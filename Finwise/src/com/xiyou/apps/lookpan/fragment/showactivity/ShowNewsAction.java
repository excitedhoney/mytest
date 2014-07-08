package com.xiyou.apps.lookpan.fragment.showactivity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.webkit.WebView;
import android.widget.TextView;

import com.xiyou.apps.lookpan.api.API;
import com.xiyou.apps.lookpan.model.NewInfoBean;
import com.xiyou.apps.lookpan.model.NewInfoList;
import com.xiyou.apps.lookpan.utils.JsonUtil;
import com.xiyou.apps.lookpan.utils.Util;

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
				String jsstr = JsonUtil
						.getJsonstr(API.news_info + id + ".json");
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
			Util.toastTips(context, "网络异常或网络延迟");
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
