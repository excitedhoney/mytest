package com.xiyou.apps.lookpan.fragment.showactivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiyou.apps.lookpan.MainApplication;
import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.model.CalendarBean;
import com.xiyou.apps.lookpan.utils.JsonUtil;
import com.xiyou.apps.lookpan.utils.Util;

public class showRiLiActivity<T> extends Activity implements OnRefreshListener {

	private PullToRefreshLayout mPullToRefreshLayout;
	private ListView mListView;
	private myadapter mAdapter;
	private TextView t1, t2, t3, t4, t5, t6, t7;
	private TextView w1, w2, w3, w4, w5, w6, w7;
	private String day;
	private String yesteday;
	private ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7;
	private RelativeLayout r1, r2, r3, r4, r5, r6, r7;
	private ArrayList<String> countryList;
	private ArrayList<String> importList;
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("d");
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	private ImageView mLoadErrorView;
	private LinearLayout mLoadingProgress;
	private RelativeLayout mTopLayoutView;
	private ActionBar mActionBar;
	private MainApplication mApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActionBar = getActionBar();
		mActionBar.setTitle("财经日历");
		mActionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.title_shang));
		mActionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.show_rili);
		findview();
		init();

	}

	@Override
	protected void onResume() {
		super.onResume();
		onLoadData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.rili_main, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.action_item_share:
			Intent i = new Intent();
			i.setClass(this, ShowRiLi_2_Activity.class);
			startActivity(i);
			return true;
		case R.id.action_item_rili:
			Intent i1 = new Intent();
			i1.setClass(this, ShowRiLi_3_Activity.class);
			startActivity(i1);
			return true;
		default:

			return super.onOptionsItemSelected(item);
		}
	}

	@SuppressLint("SimpleDateFormat")
	private void init() {
		Date d = new java.util.Date();
		String date = sdf.format(d);
		t1.setText(sdf.format(Util.getSpecifiedDayAfter(d, -3)));
		t2.setText(sdf.format(Util.getSpecifiedDayAfter(d, -2)));
		t3.setText(sdf.format(Util.getSpecifiedDayAfter(d, -1)));
		t4.setText(date.toString());
		t5.setText(sdf.format(Util.getSpecifiedDayAfter(d, +1)));
		t6.setText(sdf.format(Util.getSpecifiedDayAfter(d, +2)));
		t7.setText(sdf.format(Util.getSpecifiedDayAfter(d, +3)));
		w1.setText(Util.getWeekOfDate(Util.getSpecifiedDayAfter(d, -3)));
		w2.setText(Util.getWeekOfDate(Util.getSpecifiedDayAfter(d, -2)));
		w3.setText(Util.getWeekOfDate(Util.getSpecifiedDayAfter(d, -1)));
		w4.setText(Util.getWeekOfDate(d));
		w5.setText(Util.getWeekOfDate(Util.getSpecifiedDayAfter(d, +1)));
		w6.setText(Util.getWeekOfDate(Util.getSpecifiedDayAfter(d, +2)));
		w7.setText(Util.getWeekOfDate(Util.getSpecifiedDayAfter(d, +3)));
		day = sdf1.format(d);
		yesteday = sdf1.format(Util.getSpecifiedDayAfter(d, -1));
		mApplication.setToday(day);
		mApplication.setYesteday(yesteday);
		mAdapter = new myadapter(this);
		mListView.setAdapter(mAdapter);
		onLoadData();

		r1.setOnClickListener(myOnClick());
		r2.setOnClickListener(myOnClick());
		r3.setOnClickListener(myOnClick());
		r4.setOnClickListener(myOnClick());
		r5.setOnClickListener(myOnClick());
		r6.setOnClickListener(myOnClick());
		r7.setOnClickListener(myOnClick());

	}

	private OnClickListener myOnClick() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {

				switch (v.getId()) {
				case R.id.r1:
					day = sdf1.format(Util.getSpecifiedDayAfter(
							new java.util.Date(), -3));
					yesteday = sdf1.format(Util.getSpecifiedDayAfter(
							new java.util.Date(), -4));
					mApplication.setToday(day);
					mApplication.setYesteday(yesteday);
					onLoadData();
					iv1.setImageResource(R.drawable.xuanzhong_cheng);
					iv2.setImageDrawable(null);
					iv3.setImageDrawable(null);
					iv4.setImageDrawable(null);
					iv5.setImageDrawable(null);
					iv6.setImageDrawable(null);
					iv7.setImageDrawable(null);

					break;
				case R.id.r2:
					day = sdf1.format(Util.getSpecifiedDayAfter(
							new java.util.Date(), -2));
					yesteday = sdf1.format(Util.getSpecifiedDayAfter(
							new java.util.Date(), -3));
					mApplication.setToday(day);
					mApplication.setYesteday(yesteday);
					onLoadData();
					iv1.setImageDrawable(null);
					iv2.setImageResource(R.drawable.xuanzhong_cheng);
					iv3.setImageDrawable(null);
					iv4.setImageDrawable(null);
					iv5.setImageDrawable(null);
					iv6.setImageDrawable(null);
					iv7.setImageDrawable(null);

					break;
				case R.id.r3:
					day = sdf1.format(Util.getSpecifiedDayAfter(
							new java.util.Date(), -1));
					yesteday = sdf1.format(Util.getSpecifiedDayAfter(
							new java.util.Date(), -2));
					mApplication.setToday(day);
					mApplication.setYesteday(yesteday);
					onLoadData();
					iv1.setImageDrawable(null);
					iv3.setImageResource(R.drawable.xuanzhong_cheng);
					iv2.setImageDrawable(null);
					iv4.setImageDrawable(null);
					iv5.setImageDrawable(null);
					iv6.setImageDrawable(null);
					iv7.setImageDrawable(null);
					break;
				case R.id.r4:

					day = sdf1.format(new java.util.Date());
					yesteday = sdf1.format(Util.getSpecifiedDayAfter(
							new java.util.Date(), -1));
					mApplication.setToday(day);
					mApplication.setYesteday(yesteday);
					onLoadData();
					iv1.setImageDrawable(null);
					iv4.setImageResource(R.drawable.xuanzhong_cheng);
					iv2.setImageDrawable(null);
					iv3.setImageDrawable(null);
					iv5.setImageDrawable(null);
					iv6.setImageDrawable(null);
					iv7.setImageDrawable(null);
					break;
				case R.id.r5:
					day = sdf1.format(Util.getSpecifiedDayAfter(
							new java.util.Date(), +1));
					yesteday = sdf1.format(new java.util.Date());
					mApplication.setToday(day);
					mApplication.setYesteday(yesteday);
					onLoadData();
					iv1.setImageDrawable(null);
					iv5.setImageResource(R.drawable.xuanzhong_cheng);
					iv2.setImageDrawable(null);
					iv3.setImageDrawable(null);
					iv4.setImageDrawable(null);
					iv6.setImageDrawable(null);
					iv7.setImageDrawable(null);
					break;
				case R.id.r6:
					day = sdf1.format(Util.getSpecifiedDayAfter(
							new java.util.Date(), +2));
					yesteday = sdf1.format(Util.getSpecifiedDayAfter(
							new java.util.Date(), +1));
					mApplication.setToday(day);
					mApplication.setYesteday(yesteday);
					onLoadData();
					iv1.setImageDrawable(null);
					iv6.setImageResource(R.drawable.xuanzhong_cheng);
					iv2.setImageDrawable(null);
					iv3.setImageDrawable(null);
					iv4.setImageDrawable(null);
					iv5.setImageDrawable(null);
					iv7.setImageDrawable(null);
					break;
				case R.id.r7:
					day = sdf1.format(Util.getSpecifiedDayAfter(
							new java.util.Date(), +3));
					yesteday = sdf1.format(Util.getSpecifiedDayAfter(
							new java.util.Date(), +2));
					mApplication.setToday(day);
					mApplication.setYesteday(yesteday);
					onLoadData();
					iv1.setImageDrawable(null);
					iv7.setImageResource(R.drawable.xuanzhong_cheng);
					iv2.setImageDrawable(null);
					iv3.setImageDrawable(null);
					iv4.setImageDrawable(null);
					iv6.setImageDrawable(null);
					iv5.setImageDrawable(null);
					break;

				default:
					break;
				}
			}
		};

	}

	private void onLoadData() {

		mLoadingProgress.setVisibility(View.VISIBLE);
		mLoadErrorView.setVisibility(View.GONE);
		mListView.setVisibility(View.GONE);
		countryList = mApplication.getCountrylist();
		importList = mApplication.getImportList();
		day = mApplication.getToday();
		yesteday = mApplication.getYesteday();
		Log.i("tag", "日历 今天：" + day + "日历 昨天:" + yesteday);
		new AsyncTask<Object, Object, ArrayList<CalendarBean>>() {

			@Override
			protected ArrayList<CalendarBean> doInBackground(Object... params) {
				return JsonUtil.getCalendar2JSON(day, yesteday, countryList,
						importList);
			}

			protected void onPostExecute(
					java.util.ArrayList<CalendarBean> result) {
				mLoadingProgress.setVisibility(View.GONE);
				if (null != result) {
					mListView.setVisibility(View.VISIBLE);
					mAdapter.clear();
					mAdapter.addAll(result);
					mListView.setAdapter(mAdapter);
					mPullToRefreshLayout.setRefreshComplete();
				} else {
					mListView.setVisibility(View.GONE);
					mLoadErrorView.setVisibility(View.VISIBLE);
					mTopLayoutView.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							onLoadData();
						}
					});
				}
			};

		}.execute();

	}

	private void findview() {
		mApplication = (MainApplication) this.getApplication();
		t1 = (TextView) findViewById(R.id.textView12);
		t2 = (TextView) findViewById(R.id.textView22);
		t3 = (TextView) findViewById(R.id.textView32);
		t4 = (TextView) findViewById(R.id.textView42);
		t5 = (TextView) findViewById(R.id.textView52);
		t6 = (TextView) findViewById(R.id.textView62);
		t7 = (TextView) findViewById(R.id.textView72);
		w1 = (TextView) findViewById(R.id.textView11);
		w2 = (TextView) findViewById(R.id.textView21);
		w3 = (TextView) findViewById(R.id.textView31);
		w4 = (TextView) findViewById(R.id.textView41);
		w5 = (TextView) findViewById(R.id.textView51);
		w6 = (TextView) findViewById(R.id.textView61);
		w7 = (TextView) findViewById(R.id.textView71);
		iv1 = (ImageView) findViewById(R.id.imageView11);
		iv2 = (ImageView) findViewById(R.id.imageView21);
		iv3 = (ImageView) findViewById(R.id.imageView31);
		iv4 = (ImageView) findViewById(R.id.imageView41);
		iv5 = (ImageView) findViewById(R.id.imageView51);
		iv6 = (ImageView) findViewById(R.id.imageView61);
		iv7 = (ImageView) findViewById(R.id.imageView71);
		r1 = (RelativeLayout) findViewById(R.id.r1);
		r2 = (RelativeLayout) findViewById(R.id.r2);
		r3 = (RelativeLayout) findViewById(R.id.r3);
		r4 = (RelativeLayout) findViewById(R.id.r4);
		r5 = (RelativeLayout) findViewById(R.id.r5);
		r6 = (RelativeLayout) findViewById(R.id.r6);
		r7 = (RelativeLayout) findViewById(R.id.r7);

		mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);
		mListView = (ListView) mPullToRefreshLayout
				.findViewById(android.R.id.list);
		// Now setup the PullToRefreshLayout
		ActionBarPullToRefresh.from(this)
		// Mark All Children as pullable
				.allChildrenArePullable()
				// Set the OnRefreshListener
				.listener(this)
				// Finally commit the setup to our PullToRefreshLayout
				.setup(mPullToRefreshLayout);

		mLoadErrorView = (ImageView) findViewById(R.id.load_error);
		mLoadingProgress = (LinearLayout) findViewById(R.id.loading_progress);
		mTopLayoutView = (RelativeLayout) findViewById(R.id.top_layout);

	}

	@Override
	public void onRefreshStarted(View view) {
		onLoadData();
	}

	class myadapter extends ArrayAdapter<CalendarBean> {

		public myadapter(Context context) {
			super(context, 0);
		}

		@SuppressWarnings("unchecked")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final ViewHolder holder;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.adapter_item_rili, parent, false);
				holder = new ViewHolder();
				holder.imageview_conutry = (ImageView) convertView
						.findViewById(R.id.imageView1);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.gao = (ImageView) convertView
						.findViewById(R.id.imageView3);
				holder.qianzhi = (TextView) convertView
						.findViewById(R.id.textView2);
				holder.yuzhi = (TextView) convertView
						.findViewById(R.id.textView4);
				holder.jieguo = (TextView) convertView
						.findViewById(R.id.textView6);
				holder.time = (TextView) convertView
						.findViewById(R.id.textView9);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.title.setText(getItem(position).getTitle());
			holder.time.setText(getTime(getItem(position).getLocalDateTime()));
			if ("1".equals(getItem(position).getImportance())) {
				holder.gao.setImageResource(R.drawable.di);
			} else if ("2".equals(getItem(position).getImportance())) {
				holder.gao.setImageResource(R.drawable.zhong);
			} else if ("3".equals(getItem(position).getImportance())) {
				holder.gao.setImageResource(R.drawable.gao);
			}

			if (" ".equals(getItem(position).getPrevious().trim())
					|| getItem(position).getPrevious().trim().endsWith("sp;")
					|| null == getItem(position).getPrevious()
					|| "null" == getItem(position).getPrevious()
					|| "".equals(getItem(position).getPrevious().trim())) {
				holder.qianzhi.setText("N/A");
			} else {
				holder.qianzhi.setText(getItem(position).getPrevious());
			}
			if (" ".equals(getItem(position).getForecast().trim())
					|| getItem(position).getForecast().trim().endsWith("sp;")
					|| null == getItem(position).getForecast()
					|| "null" == getItem(position).getForecast()
					|| "".equals(getItem(position).getPrevious().trim())) {
				holder.yuzhi.setText("N/A");
			} else {
				holder.yuzhi.setText(getItem(position).getForecast());
			}
			if (" ".equals(getItem(position).getActual().trim())
					|| getItem(position).getActual().trim().endsWith("sp;")
					|| null == getItem(position).getActual()
					|| "null" == getItem(position).getActual()
					|| "".equals(getItem(position).getPrevious().trim())) {
				holder.jieguo.setText("N/A");
			} else {
				holder.jieguo.setText(getItem(position).getActual());
			}

			holder.imageview_conutry.setImageResource(getcountry(getItem(
					position).getCountry()));

			return convertView;
		}

		public int getcountry(String country) {

			if ("韩国".equals(country)) {
				return R.drawable.korea;
			}
			if ("英国".equals(country)) {
				return R.drawable.britain;
			}
			if ("加拿大".equals(country)) {
				return R.drawable.canada;
			}
			if ("中国".equals(country)) {
				return R.drawable.china;
			}
			if ("欧元区".equals(country)) {
				return R.drawable.eu;
			}
			if ("法国".equals(country)) {
				return R.drawable.france;
			}
			if ("印度".equals(country)) {
				return R.drawable.india;
			}
			if ("意大利".equals(country)) {
				return R.drawable.italy;
			}
			if ("日本".equals(country)) {
				return R.drawable.japan;
			}
			if ("挪威".equals(country)) {
				return R.drawable.norway;
			}
			if ("瑞士".equals(country)) {
				return R.drawable.switzerland;
			}
			if ("美国".equals(country)) {
				return R.drawable.usa;
			}
			if ("墨西哥".equals(country)) {
				return R.drawable.mexico;
			}
			if ("德国".equals(country)) {
				return R.drawable.germany;
			}
			if ("西班牙".equals(country)) {
				return R.drawable.spain;
			} else {
				return R.drawable.jiazai;
			}

		}

		@SuppressLint("SimpleDateFormat")
		public String getTime(String str) {

			SimpleDateFormat format1 = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");

			try {
				Date date = format1.parse(str);
				String time = format2.format(date);
				return time;
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return " ";

		}

		private class ViewHolder {
			ImageView imageview_conutry;
			TextView title;
			ImageView gao;
			TextView qianzhi;
			TextView yuzhi;
			TextView jieguo;
			TextView time;

		}
	}
}
