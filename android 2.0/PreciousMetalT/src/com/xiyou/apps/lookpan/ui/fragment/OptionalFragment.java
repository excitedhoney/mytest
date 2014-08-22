package com.xiyou.apps.lookpan.ui.fragment;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.entity.Optional;
import cn.precious.metal.services.OptionalService;
import cn.precious.metal.utils.Utils;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.R.color;
import com.xiyou.apps.lookpan.adapter.OptionalAdapter;
import com.xiyou.apps.lookpan.adapter.OptionalAdapter.DiffOrPercentChangeListener;
import com.xiyou.apps.lookpan.base.BaseFragment;
import com.xiyou.apps.lookpan.pulltorefresh.PullToRefreshBase;
import com.xiyou.apps.lookpan.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.xiyou.apps.lookpan.pulltorefresh.PullToRefreshListView;
import com.xiyou.apps.lookpan.tools.ExcepUtils;
import com.xiyou.apps.lookpan.ui.AddOptionalActivity;
import com.xiyou.apps.lookpan.ui.PortOptionalActivity;

/**
 * 自选行情
 * 
 * @author mac
 * 
 */

public class OptionalFragment extends BaseFragment implements
		OnRefreshListener<ListView> {

	private PullToRefreshListView mPullRefreshListView;

	private ListView listView;

	private OptionalAdapter mOptionalAdapter;

	private List<Optional> options;

	// top 四个自选行情

	private TextView symbol, symbol1, symbol2;

	private TextView price, price1, price2;

	private TextView diffAndPerc, diffAndPerc1, diffAndPerc2;

	private RefreshPriceTask refreshTask;

	private TextView zdf;

	private LinearLayout llKjs, llKjs1, llKjs2;

	private ProgressBar mProgressBar;

	private HashMap<String, Double> getMap = new HashMap<String, Double>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_optional, null);
		initView(view);
		if (!AppSetting.getInstance(getActivity()).isHaveInitTJSOptionalData()) { // 第一次
			new OptionsTask().execute("");
		}
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		REFRESH_DELAY = AppSetting.getInstance(getActivity()).getRefreshTime();

		initData(true);
		threadRunable = true;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		threadRunable = false;
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		if (refreshTask != null && refreshTask.getStatus() == Status.RUNNING) {
			refreshTask.cancel(true);
		}
	}

	public void initView(View view) {

		getActivity().getLayoutInflater();
		View v = LayoutInflater.from(getActivity()).inflate(
				R.layout.option_add, null);
		mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setOnRefreshListener(this);
		mOptionalAdapter = new OptionalAdapter(getActivity(), options);
		mOptionalAdapter.listener = changeListener;
		listView = mPullRefreshListView.getRefreshableView();
		listView.addFooterView(v);
		listView.setAdapter(mOptionalAdapter);

		long currentTime = System.currentTimeMillis();
		mPullRefreshListView.setLastUpdatedLabel("" + currentTime);
		AppSetting.getInstance(getActivity()).setOptionalRefreshTime(
				currentTime);
		mPullRefreshListView.doPullRefreshing(true, 10);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == mOptionalAdapter.getCount()) {
					Intent intent = new Intent(getActivity(),
							AddOptionalActivity.class);
					startActivity(intent);
				} else {
					Optional optional = mOptionalAdapter.getItem(arg2);
					if (optional != null) {
						Intent intent = new Intent(getActivity(),
								PortOptionalActivity.class);
						intent.putExtra("symbol", optional.getTreaty());
						startActivity(intent);
					}
				}
			}
		});

		symbol = (TextView) view.findViewById(R.id.symbol);
		symbol1 = (TextView) view.findViewById(R.id.symbol1);
		symbol2 = (TextView) view.findViewById(R.id.symbol2);

		price = (TextView) view.findViewById(R.id.price);
		price1 = (TextView) view.findViewById(R.id.price1);
		price2 = (TextView) view.findViewById(R.id.price2);

		diffAndPerc = (TextView) view.findViewById(R.id.diff_and_diffpre);
		diffAndPerc1 = (TextView) view.findViewById(R.id.diff_and_diffpre1);
		diffAndPerc2 = (TextView) view.findViewById(R.id.diff_and_diffpre2);

		zdf = (TextView) view.findViewById(R.id.zhang_die_fu);

		llKjs = (LinearLayout) view.findViewById(R.id.ll_kjs1);
		llKjs1 = (LinearLayout) view.findViewById(R.id.ll_kjs2);
		llKjs2 = (LinearLayout) view.findViewById(R.id.ll_kjs3);

		llKjs.setOnClickListener(myOnClickListener());
		llKjs1.setOnClickListener(myOnClickListener());
		llKjs2.setOnClickListener(myOnClickListener());

		startTimer();

	}

	private DiffOrPercentChangeListener changeListener = new DiffOrPercentChangeListener() {

		@Override
		public void change(int count) {
			// TODO Auto-generated method stub
			switch (count % 2) {
			case 0:
				zdf.setText("涨跌幅");
				break;
			case 1:
				zdf.setText("涨跌值");
				break;
			default:
				break;
			}
		}
	};

	public void initData(boolean isLoad) {

		if (options != null && !options.isEmpty()) {
			if (getMap.isEmpty()) {
				if (options.size() >= 1) {
					getMap.put(options.get(0).getTreaty(),
							Double.parseDouble(options.get(0).getNewest()));
				}

				if (options.size() >= 2) {
					getMap.put(options.get(1).getTreaty(),
							Double.parseDouble(options.get(1).getNewest()));
				}
				if (options.size() >= 3) {
					getMap.put(options.get(2).getTreaty(),
							Double.parseDouble(options.get(2).getNewest()));
				}
			}
		}

		if (isLoad)
			options = new OptionalService(getActivity()).queryAllMyOptional();
		if (options != null && options.size() > 3) {
			mOptionalAdapter.setItems(options.subList(3, options.size()));
		} else {
			mOptionalAdapter.setItems(null);
		}

		initTopOptional();

	}

	private AnimationDrawable cusRedAnimationDrawable;

	private AnimationDrawable cusGreenAnimationDrawable;

	public double getDiff(String newsPrice, String closePrice) {
		try {
			return Double.parseDouble(newsPrice)
					- Double.parseDouble(closePrice);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}

	public String getDiffPrecent(String newsPrice, String closePrice) {
		try {
			return Utils.getBigDecimal((getDiff(newsPrice, closePrice) * 100)
					/ Double.parseDouble(newsPrice));

		} catch (Exception e) {
			return "0.0";
		}
	}

	private void checkChange(View v, String getTreaty, Double newsPrice) {

		Iterator<?> iter = getMap.entrySet().iterator();
		while (iter.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			if (key.equals(getTreaty)) {
				Log.i("tag", "Val与现值:" + val + "==" + newsPrice);
				if (val == null) {

				} else {
					if (newsPrice > (Double) val) {
						v.setBackgroundResource(R.anim.shansuo_red);
						cusRedAnimationDrawable = (AnimationDrawable) v
								.getBackground();
						cusRedAnimationDrawable.stop();
						cusRedAnimationDrawable.start();
						getMap.put(getTreaty, newsPrice);
					} else if (newsPrice < (Double) val) {
						v.setBackgroundResource(R.anim.shansuo_green);
						cusGreenAnimationDrawable = (AnimationDrawable) v
								.getBackground();
						cusGreenAnimationDrawable.stop();
						cusGreenAnimationDrawable.start();
						getMap.put(getTreaty, newsPrice);
					}

				}
				break;
			}
		}

	}

	public void initTopOptional() {
		if (options == null || options.isEmpty())
			return;
		if (options.size() > 0) {
			symbol.setText(options.get(0).getTitle());
			price.setText(options.get(0).getSellone());

			if (Float.valueOf(Utils.getBigDecimal(getDiff(options.get(0)
					.getSellone(), options.get(0).getClosed()))) > 0) {
				price.setTextColor(getActivity().getResources().getColor(
						color.red));
				diffAndPerc.setTextColor(getActivity().getResources().getColor(
						color.red));

			} else {
				price.setTextColor(getActivity().getResources().getColor(
						color.green));
				diffAndPerc.setTextColor(getActivity().getResources().getColor(
						color.green));
			}
			diffAndPerc.setText(Utils.getBigDecimal(getDiff(options.get(0)
					.getSellone(), options.get(0).getClosed()))
					+ " / "
					+ getDiffPrecent(options.get(0).getSellone(), options
							.get(0).getClosed()) + "%");

			checkChange(llKjs, options.get(0).getTreaty(),
					Double.parseDouble(options.get(0).getNewest()));

		}

		if (options.size() > 1) {
			symbol1.setText(options.get(1).getTitle());
			price1.setText(options.get(1).getSellone());
			if (Float.valueOf(Utils.getBigDecimal(getDiff(options.get(1)
					.getSellone(), options.get(1).getClosed()))) > 0) {
				price1.setTextColor(getActivity().getResources().getColor(
						color.red));
				diffAndPerc1.setTextColor(getActivity().getResources()
						.getColor(color.red));

			} else {
				price1.setTextColor(getActivity().getResources().getColor(
						color.green));
				diffAndPerc1.setTextColor(getActivity().getResources()
						.getColor(color.green));
			}
			diffAndPerc1.setText(Utils.getBigDecimal(getDiff(options.get(1)
					.getSellone(), options.get(1).getClosed()))
					+ " / "
					+ getDiffPrecent(options.get(1).getSellone(), options
							.get(1).getClosed()) + "%");
			checkChange(llKjs1, options.get(1).getTreaty(),
					Double.parseDouble(options.get(1).getNewest()));
		}

		if (options.size() > 2) {
			symbol2.setText(options.get(2).getTitle());
			price2.setText(options.get(2).getSellone());

			if (Float.valueOf(Utils.getBigDecimal(getDiff(options.get(2)
					.getSellone(), options.get(2).getClosed()))) > 0) {
				price2.setTextColor(getActivity().getResources().getColor(
						color.red));
				diffAndPerc2.setTextColor(getActivity().getResources()
						.getColor(color.red));

			} else {
				price2.setTextColor(getActivity().getResources().getColor(
						color.green));
				diffAndPerc2.setTextColor(getActivity().getResources()
						.getColor(color.green));
			}
			diffAndPerc2.setText(Utils.getBigDecimal(getDiff(options.get(2)
					.getSellone(), options.get(2).getClosed()))
					+ " / "
					+ getDiffPrecent(options.get(2).getSellone(), options
							.get(2).getClosed()) + "%");

			checkChange(llKjs2, options.get(2).getTreaty(),
					Double.parseDouble(options.get(2).getNewest()));
		}
	}

	private OnClickListener myOnClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				switch (arg0.getId()) {
				case R.id.ll_kjs1:
					if (options.size() > 0) {
						Intent intent = new Intent(getActivity(),
								PortOptionalActivity.class);
						intent.putExtra("symbol", options.get(0).getTreaty());
						startActivity(intent);
					}
					break;

				case R.id.ll_kjs2:
					if (options.size() > 1) {
						Intent intent = new Intent(getActivity(),
								PortOptionalActivity.class);
						intent.putExtra("symbol", options.get(1).getTreaty());
						startActivity(intent);
					}
					break;
				case R.id.ll_kjs3:
					if (options.size() > 2) {
						Intent intent = new Intent(getActivity(),
								PortOptionalActivity.class);
						intent.putExtra("symbol", options.get(2).getTreaty());
						startActivity(intent);
					}
					break;
				default:
					break;
				}

			}
		};

	}

	/**
	 * 每隔两分钟刷新一次数据
	 */
	public boolean threadRunable = true;
	public Thread refreshThread;
	private int REFRESH_DELAY = 5 * 1000;

	private int loadCount = 0;

	private Runnable loadLatestPriceRunnable = new Runnable() {

		@Override
		public void run() {
			while (threadRunable) {
				if (listView.getVisibility() == View.VISIBLE) {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if (loadCount > 0) {
								refreshTask = new RefreshPriceTask(false);
								refreshTask.execute("");
							}
						}
					});
					loadCount++;
				}
				try {
					Thread.sleep(REFRESH_DELAY);
				} catch (Exception e) {
				}

			}
		}
	};

	/**
	 * 刷新价格的定时器
	 * 
	 */
	private void startTimer() {
		threadRunable = true;
		if (refreshThread == null) {
			refreshThread = new Thread(loadLatestPriceRunnable);
		}

		refreshThread.start();

	}

	class RefreshPriceTask extends AsyncTask<String, Void, String> {

		private boolean isPullRefresh;

		public RefreshPriceTask(boolean isPullRefresh) {
			this.isPullRefresh = isPullRefresh;
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			OptionalService hService = new OptionalService(getActivity());
			try {
				options = hService.getOptionalsPriceByTradey(options);
				if (options != null && !options.isEmpty()) {
					return SUCCESS;
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(getActivity(), null, e);
				return ERROR;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (isPullRefresh)
				mPullRefreshListView.onPullDownRefreshComplete();
			if (SUCCESS.equalsIgnoreCase(result)) {
				initData(false);
			} else if (ERROR.equalsIgnoreCase(result)) {

			} else {
			}
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		mPullRefreshListView.setLastUpdatedLabel(""
				+ sdf.format(new Date(AppSetting.getInstance(getActivity())
						.getOptionalRefreshTime())));

		refreshTask = new RefreshPriceTask(true);
		refreshTask.execute("");
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub

	}

	class OptionsTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mProgressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			OptionalService hService = new OptionalService(getActivity());
			try {
				options = hService.getTjsOptionals(true);
				if (options != null && !options.isEmpty()) {
					return SUCCESS;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(getActivity(), null, e);
				return ERROR;
			}
			return FAIL;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mProgressBar.setVisibility(View.GONE);
			if (SUCCESS.equalsIgnoreCase(result)) {
				initData(false);
			}
		}

	}

}
