package com.xiyou.apps.lookpan.ui.fragment.news.live;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.BaseActivity;


public class ShowLiveActivity extends BaseActivity {

	private TextView title;
	private TextView time;
	private TextView content;
	private TextView laiyuan;
	private TextView lianjie;
	private ImageView node_icon;
	private String str_node_icon;
	private String str_time;
	private String str_content;
	private String str_laiyuan;
	private String str_lianjie;

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_live_info);
		findView();
		Bundle bd = this.getIntent().getExtras();
		str_node_icon = bd.getString("node_icon");
		str_time = bd.getString("time");
		str_content = bd.getString("content");
		str_laiyuan = bd.getString("laiyuan");
		str_lianjie = bd.getString("lianjie");
		getData();
	}

	
	public void back(View view) {
		finish(); 
	}

	private void getData() {
		if (null != str_node_icon) {
			if (str_node_icon.equals("折线")) {
				node_icon.setImageResource(R.drawable.live_zhexian);
			} else if (str_node_icon.equals("柱状")) {
				node_icon.setImageResource(R.drawable.live_zhuzhuang);
			} else if (str_node_icon.equals("提醒")) {
				node_icon.setImageResource(R.drawable.live_tixing);
			} else if (str_node_icon.equals("传言")) {
				node_icon.setImageResource(R.drawable.live_chuanyan);
			} else if (str_node_icon.equals("警告")) {
				node_icon.setImageResource(R.drawable.live_zhongbang);
			} else {
				node_icon.setImageResource(R.drawable.live_putong);
			}
		} else {
//			Util.toastTips(this, "网络连接异常或者超时!");
		}

		title.setText(getIntent().getStringExtra("title"));
		time.setText(str_time);
		content.setText(Html.fromHtml(str_content));

		if (null != str_laiyuan && "".equals(str_laiyuan)) {
			laiyuan.setText("信息来源:" + str_laiyuan);
		}
		if (null != str_lianjie && "".equals(str_lianjie)) {
			lianjie.setText(str_lianjie);
		}
	}

	private void findView() {
		title = (TextView) findViewById(R.id.title);
		time = (TextView) findViewById(R.id.time);
		content = (TextView) findViewById(R.id.live_content);
		laiyuan = (TextView) findViewById(R.id.live_laiyuan);
		lianjie = (TextView) findViewById(R.id.live_lianjie);
		node_icon = (ImageView) findViewById(R.id.node_icon);
	}

}
