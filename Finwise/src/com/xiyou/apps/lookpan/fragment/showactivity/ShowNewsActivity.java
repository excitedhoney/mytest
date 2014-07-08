package com.xiyou.apps.lookpan.fragment.showactivity;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.api.API;
import com.xiyou.apps.lookpan.model.NewInfoBean;

public class ShowNewsActivity extends BaseActivity {
	private WebView mWebView;
	private String mId;
	private String mUrl;
	private NewInfoBean mNewInfoBean;
	private final int CHECK_HEIGHT = 1000;
	public static Handler handler = new Handler();
	private int newid;
	Boolean flag = false;
	HashMap<Integer, String> matchtype = null;

	protected ImageView mLoadErrorView;
	protected LinearLayout mLoadingProgress;
	protected RelativeLayout mTopLayoutView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_info);
		InitWebView();
		InitHander();
		Bundle bd = this.getIntent().getExtras();
		mId = bd.getString("id");
		mUrl = bd.getString("url");

		mLoadErrorView = (ImageView) findViewById(R.id.load_error);
		mLoadingProgress = (LinearLayout) findViewById(R.id.loading_progress);
		mTopLayoutView = (RelativeLayout) findViewById(R.id.top_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.news_share, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart() {
		super.onStart();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		if (!flag) {
			if (null != ShowNewsActivity.this) {
				mLoadingProgress.setVisibility(View.VISIBLE);
				mLoadErrorView.setVisibility(View.GONE);
				mWebView.setVisibility(View.GONE);
				new ShowNewsAction(mId, ShowNewsActivity.this) {
					@Override
					public boolean handleMessage(Message msg) {
						handler.handleMessage(msg);
						return super.handleMessage(msg);
					}
				}.execute();
			}
		}
	}

	/**
	 * 初始化hander
	 */
	@SuppressLint("HandlerLeak")
	private void InitHander() {

		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (msg.what == 30) {
					mLoadingProgress.setVisibility(View.GONE);
					mWebView.setVisibility(View.VISIBLE);
					LoadData(msg);
				} // end if(msg.what==MessageAPI.SHOW_NEW_SUCCESS)
				if (msg.what == 20) {
					mWebView.setVisibility(View.GONE);
					mLoadErrorView.setVisibility(View.VISIBLE);
					mTopLayoutView.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							if (null != ShowNewsActivity.this) {
								mLoadingProgress.setVisibility(View.VISIBLE);
								mLoadErrorView.setVisibility(View.GONE);
								mWebView.setVisibility(View.GONE);
								new ShowNewsAction(mId, ShowNewsActivity.this) {
									@Override
									public boolean handleMessage(Message msg) {
										handler.handleMessage(msg);
										return super.handleMessage(msg);
									}
								}.execute();
							}
						}
					});

				}
				if (msg.what == CHECK_HEIGHT) {
					// checkHeight();
				}

			}

		};
	}

	private StringBuilder LoadData(android.os.Message msg) {
		mNewInfoBean = (NewInfoBean) msg.obj;
		mNewInfoBean.setFile_managed_file_usage_uri(mUrl);
		StringBuilder sb = new StringBuilder();
		sb.append(API.img);

		String title = mNewInfoBean.getTitle();
		if (title.length() > 15) {
			title = title.substring(0, 15) + "...";
		}

		sb.append("<h2>" + title + "</br>" + "</h2>");
		sb.append("<h5><font  color='Gray'>");
		if (null != mNewInfoBean.getTaxonomy_vocabulary_2()) {
			sb.append(API.IMG_TAG);
			sb.append(" ");
			for (int i = 0; i < mNewInfoBean.getTaxonomy_vocabulary_2().size(); i++) {
				String s = mNewInfoBean.getTaxonomy_vocabulary_2().get(i);
				sb.append(s);
			}

		}
		if (null != mNewInfoBean.getName()) {
			sb.append("</br> " + API.IMG_PEN + "文 / " + mNewInfoBean.getName());
		}
		sb.append("    " + mNewInfoBean.getCreated() + "</font></h5>");

		String body = mNewInfoBean.getBody();
		body = body.replaceAll("href=\"/", "href=\"" + API.base);
		body = body.replaceAll("src=\"/", "src=\"" + API.base);
		body = body.replaceAll("style=\".+px\"", "");
		sb.append(body);

		sb.append("</br></br></br>"); // 网页底部留白
		if (mNewInfoBean.getField_related() != null) {
			sb.append("<tap>相关新闻</tap></br></br>");
			// 设置分割栏
			sb.append("<HR style='FILTER: alpha(opacity=100,finishopacity=0,style=1)' width='100%' color=#41607D SIZE=1>");
			for (NewInfoBean bean : mNewInfoBean.getField_related()) {
				sb.append("<a href=\"http://wallstreetcn.com/node/"
						+ bean.getNid() + "\" >" + bean.getTitle()
						+ "</a></br>");
				sb.append("<HR style='FILTER: alpha(opacity=100,finishopacity=0,style=1)' width='100%' color=#41607D SIZE=1>");
			}
		}

		mNewInfoBean.setBody(sb.toString());
		if (null != mUrl) {
		}
		mWebView.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8",
				null);
		return sb;
	}

	;

	/**
	 * 初始化webview
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void InitWebView() {
		// wv.getSettings().
		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.setDrawingCacheEnabled(true);
		mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
		mWebView.getSettings().setJavaScriptEnabled(true);// 开启JavaScriptEnabled
		mWebView.setWebViewClient(new MyWebViewClient());// 屏蔽超链接
	}

	// 注入js函数监听
	private void addImageClickListner() {
		mWebView.loadUrl("javascript:(function(){"
				+ "var objs = document.getElementsByTagName(\"img\"); "
				+ "for(var i=0;i<objs.length;i++)  " + "{"
				+ "    objs[i].onclick=function()  " + "    {  "
				+ "        pop_window.imagelistner.openImage(this.src);  "
				+ "    }  " + "}" + "})()");
	}

	/**
	 * @author yang
	 */
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// view.loadUrl(url);
			if (url.matches(API.url_news_link + "\\w+")
					|| url.matches(API.url_live_link + "\\w+")) {
				Intent intent = new Intent(ShowNewsActivity.this,
						ShowNewsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Bundle bundle = new Bundle();
				mId = url.substring(url.lastIndexOf("/"));
				bundle.putString("id", mId);
				bundle.putInt("newid", newid);
				intent.putExtras(bundle);
				// ScreenShot.shoot(ShowNewsActivity.this);
				startActivity(intent);
				return true;
			}
			/*
			 * if (!url.matches(".+.jpe*g")) { Intent intent = new
			 * Intent(ShowNewsActivity.this, ShowNewsLinkActivity.class);
			 * intent.putExtra("url", url);
			 * 
			 * ScreenShot.shoot(ShowNewsActivity.this); startActivity(intent);
			 * overridePendingTransition(R.anim.push_right_in,
			 * R.anim.push_left_out); return true; }
			 */
			return true;
		}

		@SuppressLint("SetJavaScriptEnabled")
		@Override
		public void onPageFinished(WebView view, String url) {

			view.getSettings().setJavaScriptEnabled(true);

			super.onPageFinished(view, url);
			// html加载完成之后，添加监听图片的点击js函数
			addImageClickListner();
			mWebView.getSettings().setBlockNetworkImage(false);
			flag = true;

		}

		@SuppressLint("SetJavaScriptEnabled")
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			view.getSettings().setJavaScriptEnabled(true);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);

		}
	}
}
