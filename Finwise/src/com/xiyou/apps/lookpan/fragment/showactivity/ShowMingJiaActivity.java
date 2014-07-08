package com.xiyou.apps.lookpan.fragment.showactivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.model.MingJiaInfoBean;
import com.xiyou.apps.lookpan.utils.JsonUtil;
import com.xiyou.apps.lookpan.utils.Util;

public class ShowMingJiaActivity extends BaseActivity {

	private String id;
	private TextView title, auto, time, content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_mingjia);
		id = (String) getIntent().getExtras().get("id");
		title = (TextView) findViewById(R.id.textView1);
		auto = (TextView) findViewById(R.id.textView2);
		time = (TextView) findViewById(R.id.textView3);
		content = (TextView) findViewById(R.id.textView4);

		new AsyncTask<Object, Object, MingJiaInfoBean>() {

			@Override
			protected MingJiaInfoBean doInBackground(Object... params) {
				return JsonUtil.getMingJiaBeanInfo2JSON(
						"http://www.06866.com/api/special_report?id=", id);
			}

			protected void onPostExecute(MingJiaInfoBean result) {
				if (null != result) {
					title.setText(result.getArticle_title());
					auto.setText(result.getAuthor());
					time.setText(result.getArticle_date());
					content.setText(result.getContent());
				} else {
					Util.toastTips(ShowMingJiaActivity.this, "网络异常，等重试！");
				}

			};

		}.execute();
	}

}
