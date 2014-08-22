package com.xiyou.apps.lookpan.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.config.API;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.entity.DetailStrategy;
import cn.precious.metal.services.StrategyService;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.BaseActivity;
import com.xiyou.apps.lookpan.tools.ExcepUtils;
import com.xiyou.apps.lookpan.tools.JsObject;

@SuppressLint("SetJavaScriptEnabled")
public class CopyDetailStrategyActivity extends BaseActivity {
	private WebView webview;

	private DetailStrategy strategy;

	private TextView title;

	private JsObject jsObj;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_copy_detail_strategy);
		jsObj = new JsObject();
		jsObj.setJsistener(new JsObject.onJsListener() {

			@Override
			public void intentTo(final String imageUrl) {
				// TODO Auto-generated method stub

				new Handler().post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Intent intent = new Intent(
								CopyDetailStrategyActivity.this,
								FullImageActivity.class);
						intent.putExtra("url", imageUrl);
						startActivity(intent);
					}
				});

			}
		});

		initView();

		new DetStraTask().execute(new String[] { getIntent().getStringExtra(
				"id") });
	}

	public void back(View view) {
		// if(webview!=null&&webview.canGoBack()){
		// webview.goBack();
		// }

		finish();
	}

	public void initView() {
		title = (TextView) findViewById(R.id.title);
		title.setText(getIntent().getStringExtra("antor"));
		webview = (WebView) findViewById(R.id.web_view);
		webview.setDrawingCacheEnabled(true);
		webview.setBackgroundColor(Color.TRANSPARENT);
		webview.getSettings().setDefaultTextEncodingName("UTF-8");

		webview.getSettings().setJavaScriptEnabled(true);

		webview.addJavascriptInterface(jsObj, JsObject.JS_NAME);
		webview.setWebViewClient(new MyWebViewClient());

		// webview.setWebViewClient(new WebViewClient(){
		// @Override
		// public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// webview.loadUrl(url);
		// showNetLoadingProgressDialog("加载中...");
		// return true;
		// }
		//
		// @Override
		// public void onPageFinished(WebView view, String url) {
		// super.onPageFinished(view, url);
		// hideNetLoadingProgressDialog();
		// }
		//
		// @Override
		// public void onReceivedError(WebView view, int errorCode,
		// String description, String failingUrl) {
		// super.onReceivedError(view, errorCode, description, failingUrl);
		// // webview.loadUrl("");
		// }
		// });
	}

	/**
	 * @author yang
	 */
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

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
			webview.getSettings().setBlockNetworkImage(false);

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

	public void initContent() {
		if (strategy != null) {
			LoadData(strategy);
		}
	}

	private void LoadData(DetailStrategy strategy) {

		if (strategy == null)
			return;
		String body = strategy.getArticle_content();

		Log.i("tag", body);
		StringBuilder sb = new StringBuilder();

		if (!AppSetting.getInstance(this).isBigTextFont()) {
			sb.append(API.img);
		} else {
			sb.append(API.imgBig);
		}

		sb.append(API.javascript);
		sb.append("<h4 style='color:white'>" + strategy.getArticle_title()
				+ "</br>" + "</h4>");
		sb.append("<h5><font  color='white'>");
		sb.append("文 / " + strategy.getAuthor());
		sb.append("    " + strategy.getArticle_date() + "</font></h5>");

		sb.append(body);
		sb.append("</br>"); // 网页底部留白

		webview.loadDataWithBaseURL(null, matchPic(sb.toString()), "text/html",
				"utf-8", null);
	}

	// public String matchP (String s) {
	// if(s == null )
	// return "" ;
	// Pattern p = Pattern.compile("<p[^>]+.*?>");
	// Matcher m = p.matcher(s);
	// while(m.find()){
	// String find = m.group() ;
	// s = s.replace(find, "<p style='color:black;'>") ;
	// }
	// return s;
	// }

	public String matchPic(String s) {
		if (s == null)
			return "";
		Pattern p = Pattern
				.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");// <img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
		Matcher m = p.matcher(s);
		while (m.find()) {
			String find = m.group();
			String newFind = find.replace("/>",
					" onclick=\"javascript:intentTo(this.src) ;\"  />");
			s = s.replace(find, newFind);
		}
		return s;
	}

	// private View winView;
	//
	// private ImageView back ;
	//
	// private PopupWindow mPopupWindows;
	//
	// public void showFullImage(String imageUrl, Context cont, View view) {
	// winView = LayoutInflater.from(cont).inflate(R.layout.full_image_layout,
	// null);
	//
	// back = (ImageView) winView.findViewById(R.id.back_image) ;
	//
	//
	//
	// back.setOnClickListener(new View.OnClickListener() {
	//
	// @Override
	// public void onClick(View arg0) {
	// // TODO Auto-generated method stub
	// mPopupWindows.dismiss();
	// winView = null;
	// System.gc();
	// }
	// });
	//
	// mPopupWindows = new PopupWindow(winView,
	// ViewGroup.LayoutParams.FILL_PARENT,
	// ViewGroup.LayoutParams.FILL_PARENT);
	// mPopupWindows.setFocusable(true);
	// mPopupWindows.setTouchable(true);
	// mPopupWindows.setBackgroundDrawable(new ColorDrawable(getResources()
	// .getColor(R.color.black)));
	// mPopupWindows.update();
	// mPopupWindows.showAtLocation(view.findViewById(R.id.art_image),
	// Gravity.CENTER, 0, 0);
	// mPopupWindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
	//
	// @Override
	// public void onDismiss() {
	// // TODO Auto-generated method stub
	// winView = null;
	// System.gc();
	// }
	// });
	// ImageView imageView = (ImageView) winView.findViewById(R.id.fullImage);
	// mImageLoader.displayImage(imageUrl, imageView,options);
	// }

	class DetStraTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showNetLoadingProgressDialog("加载中...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			StrategyService service = new StrategyService(
					CopyDetailStrategyActivity.this);
			try {
				strategy = service.getDetailStrategyById(params[0]);
				if (strategy != null) {
					return SUCCESS;
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(
						CopyDetailStrategyActivity.this, null, e);
				return "error";
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			hideNetLoadingProgressDialog();
			if (SUCCESS.equals(result)) {
				initContent();
			}
		}
	}
}
