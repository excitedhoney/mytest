package com.xiyou.apps.lookpan.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.fragment.showactivity.ShowHangQingActivity;
import com.xiyou.apps.lookpan.fragment.showactivity.ShowPaiXuActivity;
import com.xiyou.apps.lookpan.model.Hangqing_WaiHuiBean;
import com.xiyou.apps.lookpan.utils.JsonUtil;
import com.xiyou.apps.lookpan.utils.Util;

public class HangQingFragment extends Fragment implements OnItemClickListener,
		OnRefreshListener {

	private  static String TAG = "HangQingFragment";
	
	private TextView goldprice;
	private TextView goldfudi;
	private TextView baipingprice;
	private TextView baipingfudi;
	private TextView dollorprice;
	private TextView dollorfudu;
	private Timer mTimer;
	private TimerTask mTimeTask;
	private RelativeLayout huangjing;
	private RelativeLayout baiying;
	private RelativeLayout meiyuan;
	private Hangqing_WaiHuiBean huangjingBean;
	private Hangqing_WaiHuiBean baiyingBean;
	private Hangqing_WaiHuiBean meiyuanBean;
	private Float Float_huang;
	private Float Float_baiyin;
	private Float Float_meiyuan;
	private ArrayAdapter<Hangqing_WaiHuiBean> mAdapter;
	private ImageView mLoadErrorView;
	private LinearLayout mLoadingProgress;
	private RelativeLayout mTopLayoutView;
	private PullToRefreshLayout mPullToRefreshLayout;
	private ListView mListView;
	private ActionBar mActionBar;
	private boolean checked;

	static final DecimalFormat df = new DecimalFormat("0.00");

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_hangqing, null, false);

	}

	@Override
	public void onRefreshStarted(View view) {
		onLoadData(true);
	}

	@Override
	public void onResume() {
		super.onResume();
		onLoadData(true);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		goldprice = (TextView) view.findViewById(R.id.price2);
		goldfudi = (TextView) view.findViewById(R.id.textView2);
		baipingprice = (TextView) view.findViewById(R.id.price1);
		baipingfudi = (TextView) view.findViewById(R.id.textView1);
		dollorprice = (TextView) view.findViewById(R.id.price3);
		dollorfudu = (TextView) view.findViewById(R.id.textView3);
		huangjing = (RelativeLayout) view.findViewById(R.id.rehuangjing);
		baiying = (RelativeLayout) view.findViewById(R.id.RelativeLayout1);
		meiyuan = (RelativeLayout) view.findViewById(R.id.remeiyuan);
		Float_huang = 0.0f;
		Float_baiyin = 0.0f;
		Float_meiyuan = 0.0f;

		mPullToRefreshLayout = (PullToRefreshLayout) view
				.findViewById(R.id.ptr_layout);
		mListView = (ListView) mPullToRefreshLayout
				.findViewById(android.R.id.list);
		// Now setup the PullToRefreshLayout
		ActionBarPullToRefresh.from(getActivity())
		// Mark All Children as pullable
				.allChildrenArePullable()
				// Set the OnRefreshListener
				.listener(this)
				// Finally commit the setup to our PullToRefreshLayout
				.setup(mPullToRefreshLayout);

		mLoadErrorView = (ImageView) view.findViewById(R.id.load_error);
		mLoadingProgress = (LinearLayout) view
				.findViewById(R.id.loading_progress);
		mTopLayoutView = (RelativeLayout) view.findViewById(R.id.top_layout);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.news_main, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_item_add:
			Intent i = new Intent();
			i.setClass(getActivity(), ShowPaiXuActivity.class);
			startActivity(i);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActionBar = getActivity().getActionBar();
		mActionBar.setTitle("行情");

		// 上的图片
		final Drawable drawable_shang = getResources().getDrawable(
				R.drawable.shang);
		// / 这一步必须要做,否则不会显示.
		drawable_shang.setBounds(0, 0, drawable_shang.getMinimumWidth(),
				drawable_shang.getMinimumHeight());
		// 下的图片
		final Drawable drawable_xia = getResources()
				.getDrawable(R.drawable.xia);
		// / 这一步必须要做,否则不会显示.
		drawable_xia.setBounds(0, 0, drawable_xia.getMinimumWidth(),
				drawable_xia.getMinimumHeight());

		get3info();
		huangjing.setOnClickListener(myonClickListener());
		baiying.setOnClickListener(myonClickListener());
		meiyuan.setOnClickListener(myonClickListener());
		setAutoRefresh();

		mAdapter = new ArrayAdapter<Hangqing_WaiHuiBean>(getActivity(), 0) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				return onGetItemView(getItem(position), position, convertView,
						parent);
			}

			private View onGetItemView(final Hangqing_WaiHuiBean item,
					int position, View convertView, ViewGroup parent) {
				final ViewHolder holder;
				if (convertView == null) {
					convertView = LayoutInflater.from(getActivity()).inflate(
							R.layout.adapter_item_hangqing, parent, false);
					holder = new ViewHolder();
					holder.name = (TextView) convertView
							.findViewById(R.id.title);
					holder.symbol = (TextView) convertView
							.findViewById(R.id.symbol);
					holder.mairu = (TextView) convertView
							.findViewById(R.id.selling_rate);
					holder.maichu = (TextView) convertView
							.findViewById(R.id.buying_rate);
					holder.difu = (TextView) convertView
							.findViewById(R.id.change_rate);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				if (item.getTitle().startsWith("上海")
						|| item.getTitle().startsWith("天津")) {
					holder.name.setText(item.getTitle().substring(2));
				} else {
					holder.name.setText(item.getTitle());
				}

				holder.symbol.setText(item.getSymbol());

				if (null != item.getBid() && !"".equals(item.getBid())
						&& null != item.getAsk() && !"".equals(item.getAsk())) {
					if (item.getSymbol().equals("000001")) {

						holder.mairu.setText(df.format(Float.valueOf(item
								.getBid())));
						holder.maichu.setText(df.format(Float.valueOf(item
								.getAsk())));

					} else {

						holder.mairu.setText(item.getBid());
						holder.maichu.setText(item.getAsk());
					}
				}
				if (checked) {
					holder.difu.setText(item.getFudu());
				} else {
					holder.difu.setText(item.getFudu_baifenbi());
				}
				if (0 < Float.valueOf(item.getFudu())) {
					holder.difu.setBackgroundColor(getResources().getColor(
							R.color.red));
				} else {
					holder.difu.setBackgroundColor(getResources().getColor(
							R.color.green_new));
				}
				holder.difu.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (checked) {
							notifyDataSetChanged();
							checked = false;

						} else {
							notifyDataSetChanged();
							checked = true;
						}

					}
				});
				return convertView;
			}
		};

		mListView.setAdapter(mAdapter);
		onLoadData(true);
		mListView.setOnItemClickListener(this);

	}

	private OnClickListener myonClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.rehuangjing:
					Intent intent = new Intent(getActivity(),
							ShowHangQingActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					if (null != huangjingBean) {
						Hangqing_WaiHuiBean mHangqing_WaiHuiBean = huangjingBean;
						Bundle bundle = new Bundle();
						bundle.putString("id", mHangqing_WaiHuiBean.getId()
								+ "");
						bundle.putString("title",
								mHangqing_WaiHuiBean.getTitle() + "");
						bundle.putString("price",
								mHangqing_WaiHuiBean.getPrice() + "");
						bundle.putString("symbol",
								mHangqing_WaiHuiBean.getSymbol() + "");
						bundle.putString("Fudu", mHangqing_WaiHuiBean.getFudu()
								+ "");
						bundle.putString("Fudubaifenbi",
								mHangqing_WaiHuiBean.getFudu_baifenbi() + "");
						bundle.putString("PrevClose",
								mHangqing_WaiHuiBean.getPrevClose() + "");
						bundle.putString("open", mHangqing_WaiHuiBean.getOpen()
								+ "");
						bundle.putString("DayRangeHigh",
								mHangqing_WaiHuiBean.getDayRangeHigh() + "");
						bundle.putString("DayRangeLow",
								mHangqing_WaiHuiBean.getDayRangeLow() + "");
						intent.putExtras(bundle);
						startActivity(intent);
					} else {
						Util.toastTips(getActivity(), "网络不稳定，请稍后~");
					}
					break;
				case R.id.RelativeLayout1:
					Intent intent1 = new Intent(getActivity(),
							ShowHangQingActivity.class);
					intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					if (null != baiyingBean) {
						Hangqing_WaiHuiBean mHangqing_WaiHuiBean = baiyingBean;
						Bundle bundle = new Bundle();
						bundle.putString("id", mHangqing_WaiHuiBean.getId()
								+ "");
						bundle.putString("title",
								mHangqing_WaiHuiBean.getTitle() + "");
						bundle.putString("price",
								mHangqing_WaiHuiBean.getPrice() + "");
						bundle.putString("symbol",
								mHangqing_WaiHuiBean.getSymbol() + "");
						bundle.putString("Fudu", mHangqing_WaiHuiBean.getFudu()
								+ "");
						bundle.putString("Fudubaifenbi",
								mHangqing_WaiHuiBean.getFudu_baifenbi() + "");
						bundle.putString("PrevClose",
								mHangqing_WaiHuiBean.getPrevClose() + "");
						bundle.putString("open", mHangqing_WaiHuiBean.getOpen()
								+ "");
						bundle.putString("DayRangeHigh",
								mHangqing_WaiHuiBean.getDayRangeHigh() + "");
						bundle.putString("DayRangeLow",
								mHangqing_WaiHuiBean.getDayRangeLow() + "");
						intent1.putExtras(bundle);
						startActivity(intent1);
					} else {
						Util.toastTips(getActivity(), "网络不稳定，请稍后~");
					}
					break;
				case R.id.remeiyuan:
					Intent intent2 = new Intent(getActivity(),
							ShowHangQingActivity.class);
					intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					if (null != meiyuanBean) {
						Hangqing_WaiHuiBean mHangqing_WaiHuiBean = meiyuanBean;
						Bundle bundle = new Bundle();
						bundle.putString("id", mHangqing_WaiHuiBean.getId()
								+ "");
						bundle.putString("title",
								mHangqing_WaiHuiBean.getTitle() + "");
						bundle.putString("price",
								mHangqing_WaiHuiBean.getPrice() + "");
						bundle.putString("symbol",
								mHangqing_WaiHuiBean.getSymbol() + "");
						bundle.putString("Fudu", mHangqing_WaiHuiBean.getFudu()
								+ "");
						bundle.putString("Fudubaifenbi",
								mHangqing_WaiHuiBean.getFudu_baifenbi() + "");
						bundle.putString("PrevClose",
								mHangqing_WaiHuiBean.getPrevClose() + "");
						bundle.putString("open", mHangqing_WaiHuiBean.getOpen()
								+ "");
						bundle.putString("DayRangeHigh",
								mHangqing_WaiHuiBean.getDayRangeHigh() + "");
						bundle.putString("DayRangeLow",
								mHangqing_WaiHuiBean.getDayRangeLow() + "");
						intent2.putExtras(bundle);
						startActivity(intent2);
					} else {
						Util.toastTips(getActivity(), "网络不稳定，请稍后~");
					}
					break;
				default:
					break;
				}
			}
		};

	}

	private void setAutoRefresh() {

		if (mTimer == null) {
			mTimer = new Timer();
			mTimeTask = new TimerTask() {
				@Override
				public void run() {
					get3info();
				}
			};
			mTimer.schedule(mTimeTask, 0, 5000);
		}
	}

	@SuppressLint("NewApi")
	private void get3info() {
		new AsyncTask<Object, Object, Hangqing_WaiHuiBean>() {
			@Override
			protected Hangqing_WaiHuiBean doInBackground(Object... params) {
				huangjingBean = JsonUtil
						.getHangQing_WaihHuiBeanList2JSON_new1("http://api.markets.wallstreetcn.com/v1/quote.json/XAUUSD");
				return huangjingBean;
			}

			@Override
			protected void onPostExecute(Hangqing_WaiHuiBean result) {
				super.onPostExecute(result);

				if (null != result) {
					if (null != getActivity() && null != result.getPrice()
							&& result.getPrice() != ""
							&& result.getPrice().length() > 1
							&& null != result.getFudu()
							&& result.getFudu() != ""
							&& result.getFudu().length() > 1
							&& null != result.getFudu_baifenbi()
							&& result.getFudu_baifenbi() != ""
							&& result.getFudu_baifenbi().length() > 1) {

						if (0.0f != Float_huang) {

							if (Float.valueOf(result.getPrice()) > Float_huang) {
								huangjing
										.setBackgroundResource(R.anim.shansuo_red);
								AnimationDrawable anim = (AnimationDrawable) huangjing
										.getBackground();
								anim.stop();
								anim.start();
							} else if (result.getPrice().equals(
									Float_huang.toString())) {
								huangjing.setBackground(null);

							} else {

								huangjing
										.setBackgroundResource(R.anim.shansuo_green);
								AnimationDrawable anim = (AnimationDrawable) huangjing
										.getBackground();
								anim.stop();
								anim.start();
							}
						}
						Float_huang = Float.valueOf(result.getPrice());

						goldprice.setText(result.getPrice());

						goldfudi.setText(result.getFudu() + "/"
								+ result.getFudu_baifenbi());

						if (0 >= Float.valueOf(result.getFudu())) {
							goldprice.setTextColor(getActivity().getResources()
									.getColor(R.color.kanpan_green));
							goldfudi.setTextColor(getActivity().getResources()
									.getColor(R.color.kanpan_green));
						} else {
							goldprice.setTextColor(getActivity().getResources()
									.getColor(R.color.kanpan_red));
							goldfudi.setTextColor(getActivity().getResources()
									.getColor(R.color.kanpan_red));

						}
					}
				}
			}
		}.execute();
		new AsyncTask<Object, Object, Hangqing_WaiHuiBean>() {

			@Override
			protected Hangqing_WaiHuiBean doInBackground(Object... params) {
				meiyuanBean = JsonUtil
						.getHangQing_WaihHuiBeanList2JSON_new1("http://api.markets.wallstreetcn.com/v1/quote.json/USDollarIndex");
				return meiyuanBean;
			}

			@Override
			protected void onPostExecute(Hangqing_WaiHuiBean result) {

				super.onPostExecute(result);
				if (null != result) {

					if (null != getActivity() && null != result.getPrice()
							&& result.getPrice() != ""
							&& result.getPrice().length() > 1
							&& null != result.getFudu()
							&& result.getFudu() != ""
							&& result.getFudu().length() > 1
							&& null != result.getFudu_baifenbi()
							&& result.getFudu_baifenbi() != ""
							&& result.getFudu_baifenbi().length() > 1) {
						if (0.0f != Float_huang) {
							if (Float.valueOf(result.getPrice()) > Float_meiyuan) {

								meiyuan.setBackgroundResource(R.anim.shansuo_red);
								AnimationDrawable anim = (AnimationDrawable) meiyuan
										.getBackground();
								anim.stop();
								anim.start();
							} else if (result.getPrice().equals(
									Float_meiyuan.toString())) {
								meiyuan.setBackground(null);

							} else {
								meiyuan.setBackgroundResource(R.anim.shansuo_green);
								AnimationDrawable anim = (AnimationDrawable) meiyuan
										.getBackground();
								anim.stop();
								anim.start();
							}
						}
						Float_meiyuan = Float.valueOf(result.getPrice());

						dollorprice.setText(result.getPrice());
						dollorfudu.setText(result.getFudu() + "/"
								+ result.getFudu_baifenbi());
						if (0 >= Float.valueOf(result.getFudu())) {
							dollorprice.setTextColor(getActivity()
									.getResources().getColor(
											R.color.kanpan_green));
							dollorfudu.setTextColor(getActivity()
									.getResources().getColor(
											R.color.kanpan_green));
						} else {
							dollorprice.setTextColor(getActivity()
									.getResources()
									.getColor(R.color.kanpan_red));
							dollorfudu.setTextColor(getActivity()
									.getResources()
									.getColor(R.color.kanpan_red));
						}
					}
				}
			}
		}.execute();
		new AsyncTask<Object, Object, Hangqing_WaiHuiBean>() {

			@Override
			protected Hangqing_WaiHuiBean doInBackground(Object... params) {

				baiyingBean = JsonUtil
						.getHangQing_WaihHuiBeanList2JSON_new1("http://api.markets.wallstreetcn.com/v1/quote.json/XAGUSD");
				return baiyingBean;
			}

			@Override
			protected void onPostExecute(Hangqing_WaiHuiBean result) {

				super.onPostExecute(result);
				if (null != result) {

					if (null != getActivity() && null != result.getPrice()
							&& result.getPrice() != ""
							&& result.getPrice().length() > 1
							&& null != result.getFudu()
							&& result.getFudu() != ""
							&& result.getFudu().length() > 1
							&& null != result.getFudu_baifenbi()
							&& result.getFudu_baifenbi() != ""
							&& result.getFudu_baifenbi().length() > 1) {

						if (0.0f != Float_baiyin) {

							if (Float.valueOf(result.getPrice()) > Float_baiyin) {
								baiying.setBackgroundResource(R.anim.shansuo_red);
								AnimationDrawable anim = (AnimationDrawable) baiying
										.getBackground();
								anim.stop();
								anim.start();
							} else if (result.getPrice().equals(
									Float_baiyin.toString())) {
								baiying.setBackground(null);

							} else {
								baiying.setBackgroundResource(R.anim.shansuo_green);
								AnimationDrawable anim = (AnimationDrawable) baiying
										.getBackground();
								anim.stop();
								anim.start();
							}
						}

						Float_baiyin = Float.valueOf(result.getPrice());
						baipingprice.setText(result.getPrice());
						baipingfudi.setText(result.getFudu() + "/"
								+ result.getFudu_baifenbi());
						if (0 >= Float.valueOf(result.getFudu())) {
							baipingprice.setTextColor(getActivity()
									.getResources().getColor(
											R.color.kanpan_green));
							baipingfudi.setTextColor(getActivity()
									.getResources().getColor(
											R.color.kanpan_green));
						} else {
							baipingprice.setTextColor(getActivity()
									.getResources()
									.getColor(R.color.kanpan_red));
							baipingfudi.setTextColor(getActivity()
									.getResources()
									.getColor(R.color.kanpan_red));
						}
					}
				}
			}
		}.execute();
	}

	@SuppressLint("HandlerLeak")
	Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				mLoadingProgress.setVisibility(View.VISIBLE);
				mLoadErrorView.setVisibility(View.GONE);
				mListView.setVisibility(View.GONE);
				break;
			default:
				break;
			}
		}
	};

	protected void onLoadData(boolean isFirstPage) {
		Message ms = myHandler.obtainMessage();
		ms.what = 1;
		ms.sendToTarget();
		new AsyncTask<Object, Object, ArrayList<Hangqing_WaiHuiBean>>() {
			@Override
			protected ArrayList<Hangqing_WaiHuiBean> doInBackground(
					Object... params) {
				return JsonUtil
						.getHangQing_WaihHuiBeanList2JSON_new("http://api.markets.wallstreetcn.com/v1/quotes.json");
			}

			@Override
			protected void onPostExecute(ArrayList<Hangqing_WaiHuiBean> result) {
				if (null != result) {
					mLoadingProgress.setVisibility(View.GONE);
					mListView.setVisibility(View.VISIBLE);
					mAdapter.clear();
					ArrayList<Hangqing_WaiHuiBean> list = Util.getZiXuan(
							getActivity(), result);
					mAdapter.addAll(list);
					mPullToRefreshLayout.setRefreshComplete();
				} else {
					mListView.setVisibility(View.GONE);
					mLoadErrorView.setVisibility(View.VISIBLE);
					mTopLayoutView.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							onLoadData(true);
						}
					});
				}
			}

		}.execute();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(getActivity(), ShowHangQingActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Hangqing_WaiHuiBean mHangqing_WaiHuiBean = (Hangqing_WaiHuiBean) parent
				.getAdapter().getItem(position);
		// mNewsBean.getNid();
		Bundle bundle = new Bundle();
		bundle.putString("id", mHangqing_WaiHuiBean.getId() + "");
		bundle.putString("title", mHangqing_WaiHuiBean.getTitle() + "");
		bundle.putString("price", mHangqing_WaiHuiBean.getPrice() + "");
		bundle.putString("symbol", mHangqing_WaiHuiBean.getSymbol() + "");
		bundle.putString("Fudu", mHangqing_WaiHuiBean.getFudu() + "");
		bundle.putString("Fudubaifenbi",
				mHangqing_WaiHuiBean.getFudu_baifenbi() + "");
		bundle.putString("PrevClose", mHangqing_WaiHuiBean.getPrevClose() + "");
		bundle.putString("open", mHangqing_WaiHuiBean.getOpen() + "");
		bundle.putString("DayRangeHigh", mHangqing_WaiHuiBean.getDayRangeHigh()
				+ "");
		bundle.putString("DayRangeLow", mHangqing_WaiHuiBean.getDayRangeLow()
				+ "");
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private static class ViewHolder {
		TextView name;
		TextView symbol;
		TextView maichu;
		TextView mairu;
		TextView difu;

	}

}