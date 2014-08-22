package com.xiyou.apps.lookpan.ui;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import cn.precious.metal.config.API;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.entity.NewInfoBean;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.BaseActivity;
import com.xiyou.apps.lookpan.task.ShowNewsAction;

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

	private ImageView chageFontSize;
	private TextView title;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_news);
		initView();
	}

	public void initView() {
		InitWebView();
		title = (TextView) findViewById(R.id.title);
		chageFontSize = (ImageView) findViewById(R.id.change_font_size);
		title.setText("资讯");
		mId = getIntent().getStringExtra("id");
		mUrl = getIntent().getStringExtra("url");
		if (mUrl != null) {
			mUrl = mUrl.replaceAll("http://\\w+.wallstreetcn.com",
					"http://thumbnail.wallstreetcn.com/thumb");
			mUrl = mUrl.replaceAll(".jpg", ",c_fill,h_200,w_300.jpg");
		}

		InitHander();
		chageFontSize.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!AppSetting.getInstance(ShowNewsActivity.this).isBigTextFont()) {
					chageFontSize.setBackgroundResource(R.drawable.ziti_da);
					AppSetting.getInstance(ShowNewsActivity.this).setBigTextFont(true); 
					LoadData(null, true);
				} else {
					chageFontSize.setBackgroundResource(R.drawable.ziti_xiao);
					AppSetting.getInstance(ShowNewsActivity.this).setBigTextFont(false); 
					LoadData(null, false);
				}
			}
		});
	}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (AppSetting.getInstance(ShowNewsActivity.this).isBigTextFont()) {
			chageFontSize.setBackgroundResource(R.drawable.ziti_da);
		}else{
			chageFontSize.setBackgroundResource(R.drawable.ziti_xiao);
		}
	}

	public void back(View view) {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onStart() {
		super.onStart();
		if (!flag) {
			if (null != ShowNewsActivity.this) {

				showNetLoadingProgressDialog("加载中...");

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
				hideNetLoadingProgressDialog();
				if (msg.what == 30) {
					LoadData((NewInfoBean) msg.obj, AppSetting.getInstance(ShowNewsActivity.this).isBigTextFont());
				} 
				if (msg.what == 20) {
					mWebView.setVisibility(View.GONE);
				}
				if (msg.what == CHECK_HEIGHT) {
					// checkHeight();
				}

			}

		};
	}

	private void LoadData(NewInfoBean info, boolean isLargeFontSize) {
		if (info != null) {
			mNewInfoBean = info;
			mNewInfoBean.setFile_managed_file_usage_uri(mUrl);
			String body = mNewInfoBean.getBody();
			body = body.replaceAll("href=\"/", "href=\"" + API.base);
			body = body.replaceAll("src=\"/", "src=\"" + API.base);
			body = body.replaceAll("style=\".+px\"", "");
			body = body.replaceAll("<p>", "<p style='color:white;'>");
			mNewInfoBean.setBody(body);
		}
		StringBuilder sb = new StringBuilder();

		if (!isLargeFontSize) {
			sb.append(API.img);
		} else {
			sb.append(API.imgBig);
		}
		
		if(mNewInfoBean == null){
			return  ;
		}
		
		String title = mNewInfoBean.getTitle();

		sb.append("<h2 style='color:White'>" + title + "</br>" + "</h2>");
		sb.append("<h5><font  color='White'>");
//		if (null != mNewInfoBean.getTaxonomy_vocabulary_2()) {
//			sb.append(API.IMG_TAG);
//			sb.append(" ");
			// for (int i = 0; i <
			// mNewInfoBean.getTaxonomy_vocabulary_2().size(); i++) {
			// String s = mNewInfoBean.getTaxonomy_vocabulary_2().get(i);
			// sb.append(s);
			// }
//		}
		if (null != mNewInfoBean.getName()) {
			sb.append("</br> " + API.IMG_PEN + "文 / " + mNewInfoBean.getName());
		}
		sb.append("    " + mNewInfoBean.getCreated() + "</font></h5>");
		String body = mNewInfoBean.getBody();

		sb.append(body);
		// sb.append("</br></br></br>"); // 网页底部留白
		sb.append("</br>"); // 网页底部留白
		if (mNewInfoBean.getField_related() != null) {
			sb.append("<tap style='color:#3a80d2'  >相关新闻</tap></br></br>");
			// 设置分割栏
			// sb.append("<HR style='FILTER: alpha(opacity=100,finishopacity=0,style=1)' width='100%' color=white SIZE=1>");
			for (NewInfoBean bean : mNewInfoBean.getField_related()) {
				sb.append("<a style='color:#3a80d2'  href=\"http://wallstreetcn.com/node/"
						+ bean.getNid()
						+ "\" >"
						+ bean.getTitle()
						+ "</a></br>");
				// sb.append("<HR style='FILTER: alpha(opacity=100,finishopacity=0,style=1)' width='100%' color=white SIZE=1>");
			}
		}
		
		if(AppSetting.getInstance(ShowNewsActivity.this).isShowNewsPic()) {
			mWebView.loadDataWithBaseURL(null,sb.toString(), "text/html", "utf-8",
					null);
		}else{
			mWebView.loadDataWithBaseURL(null, matchPic(sb.toString()), "text/html", "utf-8",
					null);
		}
	}
	
		public String matchPic (String s) {
			if(s == null )
				return "" ;
			 Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");//<img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
		     Matcher m = p.matcher(s);
		     while(m.find()){
		    	String  find = m.group() ;
		    	s = s.replace(find, "") ;
		     }
		     return  s; 
		}
	
	

	/**
	 * 初始化webview
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void InitWebView() {
		// wv.getSettings().
		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.setDrawingCacheEnabled(true);
		mWebView.setBackgroundColor(0);
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
				finish();
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
