package com.xiyou.apps.lookpan.fragment.showactivity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.model.MingJiaInfoBean;
import com.xiyou.apps.lookpan.utils.JsonUtil;
import com.xiyou.apps.lookpan.utils.Util;

public class ShowGongGaoInfoActivity extends Activity {

	private String id;
	private TextView title, content, autor, time;
	private ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.show_gonggao_info);
		id = (String) getIntent().getExtras().get("id");
		mActionBar = getActionBar();
		mActionBar.setTitle("公告");
		mActionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.title_shang));
		mActionBar.setDisplayHomeAsUpEnabled(true);

		title = (TextView) findViewById(R.id.textView1);
		content = (TextView) findViewById(R.id.textView2);
		autor = (TextView) findViewById(R.id.textView3);
		time = (TextView) findViewById(R.id.textView4);

		new AsyncTask<Object, Object, MingJiaInfoBean>() {

			@Override
			protected MingJiaInfoBean doInBackground(Object... params) {
				return JsonUtil.getMingJiaBeanInfo2JSON(
						"http://www.06866.com/api/gonggao?id=", id);
			}

			protected void onPostExecute(MingJiaInfoBean result) {
				if (null != result) {
					title.setText(result.getArticle_title());
					autor.setText(result.getAuthor());
					time.setText(result.getArticle_date());
					content.setText(Html.fromHtml(result.getContent()));
				} else {
					Util.toastTips(ShowGongGaoInfoActivity.this, "网络异常，等重试！");
				}

			};

		}.execute();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
