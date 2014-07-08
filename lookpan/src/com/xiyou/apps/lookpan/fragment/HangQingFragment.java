package com.xiyou.apps.lookpan.fragment;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.model.Hangqing_WaiHuiBean;
import com.xiyou.apps.lookpan.utils.JsonUtil;

@SuppressLint("NewApi")
public class HangQingFragment extends Fragment {

	private PagerSlidingTabStrip mPagerSlidingTabStrip;
	private ViewPager mViewPager;
	private static final String WAI_HUI_URL = "http://api.markets.wallstreetcn.com/v1/quotes.json?channel=forex";
	private static final String SHANG_PIN_URL = "http://api.markets.wallstreetcn.com/v1/quotes.json?channel=commodity";
	private static final String GU_ZHI_URL = "http://api.markets.wallstreetcn.com/v1/quotes.json?channel=indice";
	private static final String ZHAI_QUAN_URL = "http://api.m arkets.wallstreetcn.com/v1/quotes.json?channel=bond";
	private TextView goldprice;
	private TextView goldfudi;
	private TextView baipingprice;
	private TextView baipingfudi;
	private TextView dollorprice;
	private TextView dollorfudu;
	private myFragmentAdapter mfragmentAdapter;
	private Timer mtimer;
	private TimerTask mTimeTask;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_hangqing, null, false);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		mPagerSlidingTabStrip = (PagerSlidingTabStrip) view
				.findViewById(R.id.tabs);
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		goldprice = (TextView) view.findViewById(R.id.price2);
		goldfudi = (TextView) view.findViewById(R.id.textView2);
		baipingprice = (TextView) view.findViewById(R.id.price1);
		baipingfudi = (TextView) view.findViewById(R.id.textView1);
		dollorprice = (TextView) view.findViewById(R.id.price3);
		dollorfudu = (TextView) view.findViewById(R.id.textView3);
		mfragmentAdapter = new myFragmentAdapter(getFragmentManager());

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mViewPager.setAdapter(mfragmentAdapter);
		mPagerSlidingTabStrip.setViewPager(mViewPager);
		mPagerSlidingTabStrip.setIndicatorColorResource(R.color.kanpan_chengse);
		get3info();
		setAutoRefresh();

	}

	private void setAutoRefresh() {

		if (mtimer == null) {
			mtimer = new Timer();

			mTimeTask = new TimerTask() {
				@Override
				public void run() {
					get3info();
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

	private void get3info() {
		new AsyncTask<Object, Object, Hangqing_WaiHuiBean>() {

			@Override
			protected Hangqing_WaiHuiBean doInBackground(Object... params) {

				return JsonUtil
						.getHangQing_WaihHuiBeanList2JSON_new1("http://api.markets.wallstreetcn.com/v1/quote.json/XAUUSD");
			}

			@Override
			protected void onPostExecute(Hangqing_WaiHuiBean result) {

				super.onPostExecute(result);
				if (null != result) {
					goldprice.setText(result.getPrice());
					goldfudi.setText(result.getFudu() + "/"
							+ result.getFudu_baifenbi());

					if (null != result.getFudu() && result.getFudu() != ""
							&& result.getFudu().length() >= 1) {
						if (0 >= Float.valueOf(result.getFudu())) {
							goldprice.setTextColor(getActivity().getResources()
									.getColor(R.color.kanpan_green));
							goldfudi.setTextColor(getActivity().getResources()
									.getColor(R.color.kanpan_green));
						} else {
							if (null != getActivity()) {
								goldprice.setTextColor(getActivity()
										.getResources().getColor(
												R.color.kanpan_red));
								goldfudi.setTextColor(getActivity()
										.getResources().getColor(
												R.color.kanpan_red));
							}

						}
					}
				}

			}
		}.execute();
		new AsyncTask<Object, Object, Hangqing_WaiHuiBean>() {

			@Override
			protected Hangqing_WaiHuiBean doInBackground(Object... params) {

				return JsonUtil
						.getHangQing_WaihHuiBeanList2JSON_new1("http://api.markets.wallstreetcn.com/v1/quote.json/USDollarIndex");
			}

			@Override
			protected void onPostExecute(Hangqing_WaiHuiBean result) {

				super.onPostExecute(result);
				if (null != result) {

					dollorprice.setText(result.getPrice());
					dollorfudu.setText(result.getFudu() + "/"
							+ result.getFudu_baifenbi());

					if (null != result.getFudu() && result.getFudu() != ""
							&& result.getFudu().length() >= 1) {
						if (0 >= Float.valueOf(result.getFudu())) {
							dollorprice.setTextColor(getActivity()
									.getResources().getColor(
											R.color.kanpan_green));
							dollorfudu.setTextColor(getActivity()
									.getResources().getColor(
											R.color.kanpan_green));

						} else {
							if (null != getActivity()) {
								dollorprice.setTextColor(getActivity()
										.getResources().getColor(
												R.color.kanpan_red));
								dollorfudu.setTextColor(getActivity()
										.getResources().getColor(
												R.color.kanpan_red));
							}
						}
					}
				}
			}
		}.execute();
		new AsyncTask<Object, Object, Hangqing_WaiHuiBean>() {

			@Override
			protected Hangqing_WaiHuiBean doInBackground(Object... params) {

				return JsonUtil
						.getHangQing_WaihHuiBeanList2JSON_new1("http://api.markets.wallstreetcn.com/v1/quote.json/XAGUSD");
			}

			@Override
			protected void onPostExecute(Hangqing_WaiHuiBean result) {

				super.onPostExecute(result);
				if (null != result) {
					baipingprice.setText(result.getPrice());
					baipingfudi.setText(result.getFudu() + "/"
							+ result.getFudu_baifenbi());

					if (null != result.getFudu() && result.getFudu() != ""
							&& result.getFudu().length() >= 1) {
						if (0 >= Float.valueOf(result.getFudu())) {
							baipingprice.setTextColor(getActivity()
									.getResources().getColor(
											R.color.kanpan_green));
							baipingfudi.setTextColor(getActivity()
									.getResources().getColor(
											R.color.kanpan_green));
						} else {
							if (null != getActivity()) {
								baipingprice.setTextColor(getActivity()
										.getResources().getColor(
												R.color.kanpan_red));
								baipingfudi.setTextColor(getActivity()
										.getResources().getColor(
												R.color.kanpan_red));
							}
						}
					}
				}

			}
		}.execute();
	}

	class myFragmentAdapter extends FragmentPagerAdapter {

		public myFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.hangqing_bar_zixuan);
			case 1:
				return getString(R.string.hangqing_bar_waihui);
			case 2:
				return getString(R.string.hangqing_bar_shangping);
			case 3:
				return getString(R.string.hangqing_bar_guzhi);
			case 4:
				return getString(R.string.hangqing_bar_zhaiquan);
			default:
				return null;
			}
		}

		@Override
		public Fragment getItem(int arg0) {
			switch (arg0) {
			case 0:
				return new new_ZiXuanHangQingFragment();
			case 1:
				return new_HangQingFragment.newInstance(WAI_HUI_URL);
			case 2:
				return new_HangQingFragment.newInstance(SHANG_PIN_URL);
			case 3:
				return new_HangQingFragment.newInstance(GU_ZHI_URL);
			case 4:
				return new_HangQingFragment.newInstance(ZHAI_QUAN_URL);
			default:
				return null;
			}

		}
	}

}