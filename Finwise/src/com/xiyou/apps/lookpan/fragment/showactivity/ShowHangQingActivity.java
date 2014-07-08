package com.xiyou.apps.lookpan.fragment.showactivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.db.BaseDataBase;
import com.xiyou.apps.lookpan.model.Hangqing_WaiHuiBean;
import com.xiyou.apps.lookpan.utils.JsonUtil;
import com.xiyou.apps.lookpan.utils.Util;
import com.xiyou.apps.view.ProgressWebView;

@SuppressLint({ "SetJavaScriptEnabled", "SimpleDateFormat" })
public class ShowHangQingActivity extends Activity {

	private TextView NowPrice;
	private TextView FuDuBaiFenBi;
	private TextView Fudu;
	private TextView YesterdayClose;
	private TextView TodayOpen;
	private TextView HighPrice;
	private TextView LowPrice;
	private ProgressWebView K_WebView;
	private TextView time;
	private TextView zixuan;
	private Bundle mBundle;
	private int high, wigh;
	private ActionBar mActionBar;
	private Timer mtimer;
	private TimerTask mTimeTask;

	private SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private TextView _1m, _5m, _15m, _4h, rik, yuek;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_hangqing_info);
		findView();
		initWebView("5");
		initClick();
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.title_shang));
		mActionBar.setTitle(mBundle.getString("title"));

		setAutoRefresh();

	}

	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setAutoRefresh() {

		if (mtimer == null) {
			mtimer = new Timer();

			mTimeTask = new TimerTask() {
				@Override
				public void run() {
					getdata();
				}
			};
			mtimer.schedule(mTimeTask, 0, 5000);
		}
	}

	public void stopRefresh() {
		if (mtimer != null) {
			mtimer.cancel();
			// 关闭自动刷新
		}

		mtimer = null; // 销毁便于再生成
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopRefresh();
	}

	private void getdata() {
		new AsyncTask<Object, Object, Hangqing_WaiHuiBean>() {
			@Override
			protected Hangqing_WaiHuiBean doInBackground(Object... params) {
				if (!"".equals(mBundle.getString("symbol"))) {

					return JsonUtil
							.getHangQing_WaihHuiBeanList2JSON_new1("http://api.markets.wallstreetcn.com/v1/quote.json/"
									+ mBundle.getString("symbol"));
				} else {
					return null;
				}

			}

			@Override
			protected void onPostExecute(Hangqing_WaiHuiBean result) {
				super.onPostExecute(result);
				if (null != result) {
					NowPrice.setText(result.getPrice());
					Fudu.setText(result.getFudu());
					FuDuBaiFenBi.setText(result.getFudu_baifenbi());
					YesterdayClose.setText(result.getPrevClose());
					TodayOpen.setText(result.getOpen());
					HighPrice.setText(result.getDayRangeHigh());
					LowPrice.setText(result.getDayRangeLow());
					time.setText(formatter.format(new Date(System
							.currentTimeMillis())));
				} else {
					Util.toastTips(ShowHangQingActivity.this, "网络异常！");
				}
			}
		}.execute();
	}

	@SuppressLint("HandlerLeak")
	Handler myhandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				initWebView(msg.obj.toString());
				_1m.setTextColor(getResources().getColor(R.color.white));
				_1m.setBackgroundColor(getResources().getColor(R.color.blue));

				_5m.setTextColor(getResources().getColor(R.color.black));
				_5m.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				_15m.setTextColor(getResources().getColor(R.color.black));
				_15m.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				_4h.setTextColor(getResources().getColor(R.color.black));
				_4h.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				rik.setTextColor(getResources().getColor(R.color.black));
				rik.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				yuek.setTextColor(getResources().getColor(R.color.black));
				yuek.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));

				break;
			case 2:

				initWebView(msg.obj.toString());

				_1m.setTextColor(getResources().getColor(R.color.black));
				_1m.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));

				_5m.setTextColor(getResources().getColor(R.color.white));
				_5m.setBackgroundColor(getResources().getColor(R.color.blue));
				_15m.setTextColor(getResources().getColor(R.color.black));
				_15m.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				_4h.setTextColor(getResources().getColor(R.color.black));
				_4h.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				rik.setTextColor(getResources().getColor(R.color.black));
				rik.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				yuek.setTextColor(getResources().getColor(R.color.black));
				yuek.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));

				break;
			case 3:
				initWebView(msg.obj.toString());
				_1m.setTextColor(getResources().getColor(R.color.black));
				_1m.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));

				_5m.setTextColor(getResources().getColor(R.color.black));
				_5m.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				_15m.setTextColor(getResources().getColor(R.color.white));
				_15m.setBackgroundColor(getResources().getColor(R.color.blue));
				_4h.setTextColor(getResources().getColor(R.color.black));
				_4h.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				rik.setTextColor(getResources().getColor(R.color.black));
				rik.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				yuek.setTextColor(getResources().getColor(R.color.black));
				yuek.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));

				break;
			case 4:
				initWebView(msg.obj.toString());
				_1m.setTextColor(getResources().getColor(R.color.black));
				_1m.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));

				_5m.setTextColor(getResources().getColor(R.color.black));
				_5m.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				_15m.setTextColor(getResources().getColor(R.color.black));
				_15m.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				_4h.setTextColor(getResources().getColor(R.color.white));
				_4h.setBackgroundColor(getResources().getColor(R.color.blue));
				rik.setTextColor(getResources().getColor(R.color.black));
				rik.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				yuek.setTextColor(getResources().getColor(R.color.black));
				yuek.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));

				break;
			case 5:
				initWebView(msg.obj.toString());
				_1m.setTextColor(getResources().getColor(R.color.black));
				_1m.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				_5m.setTextColor(getResources().getColor(R.color.black));
				_5m.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				_15m.setTextColor(getResources().getColor(R.color.black));
				_15m.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				_4h.setTextColor(getResources().getColor(R.color.black));
				_4h.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				rik.setTextColor(getResources().getColor(R.color.white));
				rik.setBackgroundColor(getResources().getColor(R.color.blue));
				yuek.setTextColor(getResources().getColor(R.color.black));
				yuek.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));

				break;
			case 6:

				initWebView(msg.obj.toString());
				_1m.setTextColor(getResources().getColor(R.color.black));
				_1m.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));

				_5m.setTextColor(getResources().getColor(R.color.black));
				_5m.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				_15m.setTextColor(getResources().getColor(R.color.black));
				_15m.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				_4h.setTextColor(getResources().getColor(R.color.black));
				_4h.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				rik.setTextColor(getResources().getColor(R.color.black));
				rik.setBackgroundColor(getResources().getColor(
						R.color.show_hangqing_blue));
				yuek.setTextColor(getResources().getColor(R.color.white));
				yuek.setBackgroundColor(getResources().getColor(R.color.blue));
				break;
			case 7:
				BaseDataBase.getInstance(ShowHangQingActivity.this).open();
				if (zixuan.getText().equals("取消自选")) {
					BaseDataBase.getInstance(ShowHangQingActivity.this)
							.DelectSingleZiXuanChannel((String) msg.obj);
					Util.toastTips(ShowHangQingActivity.this, "取消自选成功!");
					zixuan.setText("添加自选");
				} else {
					BaseDataBase.getInstance(ShowHangQingActivity.this)
							.inserSingleZiXuanChannel((String) msg.obj);
					Util.toastTips(ShowHangQingActivity.this, "添加自选成功!");
					zixuan.setText("取消自选");
				}
				BaseDataBase.getInstance(ShowHangQingActivity.this).close();
				break;

			default:
				break;
			}
		}
	};

	private void initClick() {

		zixuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = myhandler.obtainMessage();
				if (!"".equals(mBundle.getString("symbol"))) {
					msg.what = 7;
					msg.obj = mBundle.getString("symbol");
					msg.sendToTarget();
				}

			}
		});
		_1m.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = myhandler.obtainMessage();
				msg.what = 1;
				msg.obj = "1";
				msg.sendToTarget();
			}
		});
		_5m.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = myhandler.obtainMessage();
				msg.what = 2;
				msg.obj = "5";
				msg.sendToTarget();
			}
		});
		_15m.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = myhandler.obtainMessage();
				msg.what = 3;
				msg.obj = "15";
				msg.sendToTarget();
			}
		});
		_4h.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = myhandler.obtainMessage();
				msg.what = 4;
				msg.obj = "4h";
				msg.sendToTarget();
			}
		});
		rik.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = myhandler.obtainMessage();
				msg.what = 5;
				msg.obj = "1d";
				msg.sendToTarget();
			}
		});
		yuek.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = myhandler.obtainMessage();
				msg.what = 6;
				msg.obj = "mn";
				msg.sendToTarget();
			}
		});

	}

	@SuppressWarnings("deprecation")
	private void initWebView(String time) {

		K_WebView.setDrawingCacheEnabled(true);
		K_WebView.getSettings().setDefaultTextEncodingName("UTF-8");
		K_WebView.getSettings().setJavaScriptEnabled(true);// 开启JavaScriptEnabled
		K_WebView.setWebViewClient(new MyWebViewClient());
		K_WebView.getSettings().setBuiltInZoomControls(false);
		K_WebView.getSettings().setJavaScriptEnabled(true);
		K_WebView.getSettings().setRenderPriority(RenderPriority.HIGH);
		K_WebView.getSettings().setBlockNetworkImage(true);
		K_WebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		K_WebView.getSettings().setAllowFileAccess(true);
		K_WebView.getSettings().setAppCacheEnabled(true);
		K_WebView.getSettings().setSaveFormData(false);
		K_WebView.getSettings().setLoadsImagesAutomatically(true);
		String str = "http://markets.wallstreetcn.com/embed/chart?symbol="
				+ mBundle.getString("symbol") + "&width=" + wigh + "&height="
				+ high + "&type=candle&rows=50&interval=" + time;
		K_WebView.loadUrl(str);
	}

	private void findView() {
		mBundle = getIntent().getExtras();
		NowPrice = (TextView) findViewById(R.id.textView3);
		FuDuBaiFenBi = (TextView) findViewById(R.id.textView2);
		Fudu = (TextView) findViewById(R.id.textView4);
		YesterdayClose = (TextView) findViewById(R.id.textView8);
		TodayOpen = (TextView) findViewById(R.id.textView10);
		HighPrice = (TextView) findViewById(R.id.textView11);
		LowPrice = (TextView) findViewById(R.id.textView12);
		K_WebView = (ProgressWebView) findViewById(R.id.webView);
		_1m = (TextView) findViewById(R.id.t1);
		_5m = (TextView) findViewById(R.id.TextView01);
		_15m = (TextView) findViewById(R.id.TextView02);
		_4h = (TextView) findViewById(R.id.TextView03);
		rik = (TextView) findViewById(R.id.TextView04);
		yuek = (TextView) findViewById(R.id.TextView05);

		zixuan = (TextView) findViewById(R.id.zixuan);
		if (mBundle.getString("symbol").equals("XAGUSD")
				|| mBundle.getString("symbol").equals("XAUUSD")
				|| mBundle.getString("symbol").equals("USDollarIndex")) {
			zixuan.setVisibility(View.INVISIBLE);
		}
		DisplayMetrics metric = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(metric);
		high = K_WebView.getHeight() - 10;
		wigh = K_WebView.getWidth() - 10;

		time = (TextView) findViewById(R.id.time);

		if (!"".equals(mBundle.getString("symbol"))) {

			NowPrice.setText(mBundle.getString("price"));
			FuDuBaiFenBi.setText(mBundle.getString("Fudubaifenbi"));
			Fudu.setText(mBundle.getString("Fudu"));
			char[] ca = mBundle.getString("Fudu").toCharArray();
			char strr1 = ca[0];
			String str1 = strr1 + "";
			if (str1.equals("-")
					|| Float.parseFloat(mBundle.getString("Fudu")) == 0) {

				FuDuBaiFenBi.setTextColor(this.getResources().getColor(
						R.color.kanpan_green));
				Fudu.setTextColor(this.getResources().getColor(
						R.color.kanpan_green));
				NowPrice.setTextColor(this.getResources().getColor(
						R.color.kanpan_green));
			}

			YesterdayClose.setText(mBundle.getString("PrevClose"));
			TodayOpen.setText(mBundle.getString("open"));
			HighPrice.setText(mBundle.getString("DayRangeHigh"));
			LowPrice.setText(mBundle.getString("DayRangeLow"));
			BaseDataBase.getInstance(this).open();
			ArrayList<String> list = BaseDataBase.getInstance(this)
					.selectZiXuanChannel();
			for (String str : list) {
				if (str.equals(mBundle.getString("symbol"))) {
					zixuan.setText("取消自选");
					break;
				}
			}
		} else {

			NowPrice.setText("N/A");
			FuDuBaiFenBi.setText("N/A");
			Fudu.setText("N/A");
			YesterdayClose.setText("N/A");
			TodayOpen.setText("N/A");
			HighPrice.setText("N/A");
			LowPrice.setText("N/A");

		}
		time.setText(formatter.format(new Date(System.currentTimeMillis())));

	}

	/**
	 * @author yang
	 */
	private class MyWebViewClient extends WebViewClient {

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			String errorHtml = "<html><body><h3>Network Connection Failure!</h3></body></html>";
			view.loadData(errorHtml, "text/html", "UTF-8");

		}
	}
}
