package com.xiyou.apps.lookpan.ui;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.entity.Optional;
import cn.precious.metal.services.OptionalService;
import cn.precious.metal.utils.Utils;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.BaseActivity;
import com.xiyou.apps.lookpan.listener.KLineFragmentChangeListener;
import com.xiyou.apps.lookpan.tools.ExcepUtils;
import com.xiyou.apps.lookpan.ui.fragment.chart.FenshiChartFragment;
import com.xiyou.apps.lookpan.ui.fragment.chart.KLineFragment;

@SuppressLint("ResourceAsColor")
public class PortOptionalActivity extends BaseActivity implements
		OnClickListener, KLineFragmentChangeListener {

	public static final String TAB_15_MINUTE = "TAB_15_MINUTE";
	// public static final String TAB_30_MINUTE = "TAB_30_MINUTE";
	public static final String TAB_1_HOUR = "TAB_1_HOUR";
	public static final String TAB_4_HOUR = "TAB_4_HOUR";
	public static final String TAB_DAYS = "TAB_DAYS";

	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	private TextView tvFenshi, tv15Fenzhong, tvXiaoshi, tv4Xiaoshi, tvDays;

	private String mLastTabTag = null;

	private ImageView orientationChange, orientationNorm, oritationaGlineType;

	private ImageView cursor;
	// 横屏的时候
	private ImageView oritatioanGline, oritationNorm;

	private LinearLayout op_ri;
	// op_hua, op_zhi;

	private TextView oritationFenshi;

	private LayoutInflater inflater;

	// 横竖屏切换需要显示 隐藏

	private LinearLayout heandViewOritation, currentHangiqng,
			chartFenshiIndicator, chartTools, llOritationSaveGline;

	private RelativeLayout heandView;

	private TextView ivGlineSave;

	public static int currentTag = 0;

	private int offset = 0;

	private int portCurrentTag = 0;

	private TextView title, oritationTitle, oritationTitleRate;

	private TextView tvPrice, tvChangeAndRate, tvTime, tvBuyPrice, tvSalePrice,
			tvOpen, tvZuoshou, tvHigh, tvLow; // 竖屏的时候

	private ImageView addNotice;

	private ImageView gline, norm;

	private int statusHeight = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_port_optional);
		inflater = LayoutInflater.from(this);

		statusHeight = Utils.getStatusHeight(this);

		initView();
		initListener();

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;

		if (screenWidth > screenHeight) {
			handlerOritationView(true);
		} else {
			handlerOritationView(false);
		}

		if (currentOptional != null) {
			showFenshiFrag(currentOptional.getTreaty(),
					currentOptional.getType(), "15");
			fragmentChange(0);
		}
		startTimer();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		threadRunable = true;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		threadRunable = false;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (refreshTask != null && refreshTask.getStatus() == Status.RUNNING) {
			refreshTask.cancel(true);
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

		offset = Utils.getWindowWidth(this) / 5;

		if (Configuration.ORIENTATION_LANDSCAPE == getResources()
				.getConfiguration().orientation) {
			portCurrentTag = currentTag;
			handlerOritationView(true);
		} else {
			handlerOritationView(false);
			if (portCurrentTag != currentTag) {
				tranAnim(portCurrentTag, currentTag);
			}
		}
	}

	public void back(View view) {
		// TODO Auto-generated method stub
		finish();
	}

	public void refresh(View v) {
		initHangqing(true);
	}

	public void ortationBack(View view) {
		// TODO Auto-generated method stub
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	public void tranAnim(int lastTag, int position) {
		if (lastTag != position) {
			Animation animation = new TranslateAnimation(offset * lastTag,
					position * offset, 0, 0);
			currentTag = position;
			animation.setFillAfter(true);
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}
	}

	public void initView() {
		gline = (ImageView) findViewById(R.id.iv_gline);
		norm = (ImageView) findViewById(R.id.iv_norm);

		cursor = (ImageView) findViewById(R.id.cursor);

		tvFenshi = (TextView) findViewById(R.id.tv_fenshi);
		tv15Fenzhong = (TextView) findViewById(R.id.tv_15fenzhong);
		tvXiaoshi = (TextView) findViewById(R.id.tv_xiaoshi);
		tv4Xiaoshi = (TextView) findViewById(R.id.tv_4xiaoshi);
		tvDays = (TextView) findViewById(R.id.tv_other);
		op_ri = (LinearLayout) findViewById(R.id.op_ri);
		// op_hua = (LinearLayout) findViewById(R.id.op_hua);
		// op_zhi = (LinearLayout) findViewById(R.id.op_zhi);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				Utils.getWindowHeight(this) - Utils.dip2px(this, 226.5f)
						- statusHeight);
		findViewById(R.id.frag_container).setLayoutParams(params);

		//
		orientationChange = (ImageView) findViewById(R.id.iv_orientation);
		oritationaGlineType = (ImageView) findViewById(R.id.oritationa_gline_type);

		//
		heandView = (RelativeLayout) findViewById(R.id.heand_view);
		heandViewOritation = (LinearLayout) findViewById(R.id.heand_view_oritation);
		currentHangiqng = (LinearLayout) findViewById(R.id.ll_current_hangqing);
		chartFenshiIndicator = (LinearLayout) findViewById(R.id.ll_tab_indicator);
		chartTools = (LinearLayout) findViewById(R.id.ll_chart_tools);

		llOritationSaveGline = (LinearLayout) findViewById(R.id.ll_oritation_save_gline);
		ivGlineSave = (TextView) findViewById(R.id.iv_gline_save);

		//
		oritationFenshi = (TextView) findViewById(R.id.oritation_fenshi);

		addNotice = (ImageView) findViewById(R.id.iv_add_notice);

		Matrix matrix = new Matrix();
		matrix.postTranslate(0, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置

		offset = Utils.getWindowWidth(this) / 5;

		title = (TextView) findViewById(R.id.title1);
		oritationTitle = (TextView) findViewById(R.id.oritation_title);
		oritationTitleRate = (TextView) findViewById(R.id.oritation_title_rate);

		tvPrice = (TextView) findViewById(R.id.tv_price);
		tvChangeAndRate = (TextView) findViewById(R.id.tv_change_and_rate);
		tvTime = (TextView) findViewById(R.id.tv_time);
		tvBuyPrice = (TextView) findViewById(R.id.tv_buy_price);
		tvSalePrice = (TextView) findViewById(R.id.tv_sale_price);
		tvOpen = (TextView) findViewById(R.id.tv_open);
		tvZuoshou = (TextView) findViewById(R.id.tv_zuoshou);
		tvHigh = (TextView) findViewById(R.id.tv_high);
		tvLow = (TextView) findViewById(R.id.tv_low);

		initHangqing(true);
	}

	private Optional currentOptional;

	public void initHangqing(boolean isLoad) {

		String symbol = getIntent().getStringExtra("symbol");
		if (symbol == null || "".equalsIgnoreCase(symbol))
			return;
		if (isLoad)
			currentOptional = new OptionalService(this)
					.getOptionalBySymbol(symbol);

		if (currentOptional == null)
			return;

		title.setText(currentOptional.getTitle());
		oritationTitle.setText(currentOptional.getTitle() + " "
				+ currentOptional.getSellone());

		double currentPrice = 0;
		double zuoshou = 0;
		String change = null;
		String changeRate = null;
		if (!"".equals(currentOptional.getSellone())
				&& null != currentOptional.getSellone()
				&& !"".equals(currentOptional.getClosed())
				&& null != currentOptional.getClosed()) {
			currentPrice = Double.parseDouble(currentOptional.getSellone());
			zuoshou = Double.parseDouble(currentOptional.getClosed());
			change = Utils.getBigDecimal(currentPrice - zuoshou);
			changeRate = Utils.getBigDecimal((currentPrice - zuoshou) * 100
					/ currentPrice);

		}
		tvPrice.setText(currentOptional.getSellone());
		tvChangeAndRate.setText(change + "    " + changeRate + "%");
		oritationTitleRate.setText(change + "/" + changeRate + "%");

		tvTime.setText(currentOptional.getAdd_time());
		tvBuyPrice.setText(currentOptional.getBuyone());
		tvSalePrice.setText(currentOptional.getSellone());
		tvOpen.setText(currentOptional.getOpening());
		tvZuoshou.setText(currentOptional.getClosed());
		tvHigh.setText(currentOptional.getHighest());
		tvLow.setText(currentOptional.getLowest());

		if ((currentPrice - zuoshou) < 0) {
			tvPrice.setTextColor(Color.GREEN);
			tvChangeAndRate.setTextColor(Color.GREEN);
		} else {
			tvPrice.setTextColor(Color.RED);
			tvChangeAndRate.setTextColor(Color.RED);
		}
	}

	public void initListener() {
		tvFenshi.setOnClickListener(this);
		tv15Fenzhong.setOnClickListener(this);
		tvXiaoshi.setOnClickListener(this);
		tv4Xiaoshi.setOnClickListener(this);
		tvDays.setOnClickListener(this);
		orientationChange.setOnClickListener(this);
		// 横屏的title
		op_ri.setOnClickListener(this);
		// op_zhi.setOnClickListener(this);
		// op_hua.setOnClickListener(this);// 画 黄金分割线 水平线
		//
		ivGlineSave.setOnClickListener(this);
		//
		addNotice.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_orientation:
			turnOriClick(v);
			break;
		case R.id.iv_norm:
			break;
		case R.id.tv_fenshi:
			tranAnim(currentTag, 0);
			fragmentChange(0);

			if (currentOptional != null)
				showFenshiFrag(currentOptional.getTreaty(),
						currentOptional.getType(), "15");
			break;
		case R.id.tv_15fenzhong:
			tranAnim(currentTag, 1);
			fragmentChange(1);
			if (currentOptional != null)
				showKlineFrag(currentOptional.getTreaty(),
						currentOptional.getType(), "15", TAB_15_MINUTE);
			break;
		case R.id.tv_xiaoshi:
			tranAnim(currentTag, 2);
			fragmentChange(2);
			if (currentOptional != null)
				showKlineFrag(currentOptional.getTreaty(),
						currentOptional.getType(), "1h", TAB_1_HOUR);

			break;
		case R.id.tv_4xiaoshi:
			tranAnim(currentTag, 3);
			fragmentChange(3);
			if (currentOptional != null)
				showKlineFrag(currentOptional.getTreaty(),
						currentOptional.getType(), "4h", TAB_4_HOUR);
			break;
		case R.id.tv_other:
			tranAnim(currentTag, 4);
			if (currentOptional != null)
				showKlineFrag(currentOptional.getTreaty(),
						currentOptional.getType(), "1d", TAB_DAYS);
			break;
		case R.id.op_ri:
			showFenshiPopupWindow();
			break;
		case R.id.op_hua:
			llOritationSaveGline.setVisibility(View.VISIBLE);
			heandViewOritation.setVisibility(View.GONE);
			break;

		case R.id.iv_gline_save:
			llOritationSaveGline.setVisibility(View.GONE);
			heandViewOritation.setVisibility(View.VISIBLE);
			break;
		case R.id.iv_add_notice:
			if (AppSetting.getInstance(PortOptionalActivity.this).isLoginOn()) {
				if (currentOptional != null) {
					Intent it = new Intent(PortOptionalActivity.this,
							RemindActivity.class);
					it.putExtra("symbol", currentOptional.getTreaty());
					it.putExtra("name", currentOptional.getTitle());
					it.putExtra("BuyPrice",
							Double.parseDouble(currentOptional.getBuyone()));
					it.putExtra("SalePrice",
							Double.parseDouble(currentOptional.getSellone()));
					it.putExtra("CurrentPrice",
							Double.parseDouble(currentOptional.getSellone()));
					startActivity(it);
				}
			} else {

				DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						if (currentOptional != null) {
							Intent it = new Intent(PortOptionalActivity.this,
									LoginActivity.class);
							it.putExtra("flag", "rect_remind");
							it.putExtra("symbol", currentOptional.getTreaty());
							it.putExtra("name", currentOptional.getTitle());
							it.putExtra("BuyPrice", Double.parseDouble(""
									.equals(currentOptional.getBuyone()) ? "0"
									: currentOptional.getBuyone()));
							it.putExtra("SalePrice", Double.parseDouble(""
									.equals(currentOptional.getSellone()) ? "0"
									: currentOptional.getSellone()));
							it.putExtra("CurrentPrice", Double.parseDouble(""
									.equals(currentOptional.getSellone()) ? "0"
									: currentOptional.getSellone()));
							startActivity(it);
						}
					}
				};

				Utils.showCusAlertDialog(PortOptionalActivity.this, "提示信息",
						"本地提醒需要登录后才能使用", "确定", "取消", listener, null);
			}

			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (Configuration.ORIENTATION_LANDSCAPE == getResources()
					.getConfiguration().orientation) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			} else {
				finish();
			}
			return true;
		default:
			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * rt全屏
	 * 
	 * @param v
	 */
	public void turnOriClick(View v) {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		if (screenHeight > screenWidth) {
			try {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			} catch (Exception e) {
			}
		} else if (screenWidth > screenHeight) {
			try {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			} catch (Exception e) {
			}
		}
	}

	public void handlerOritationView(boolean isOrientation) {
		if (isOrientation) {

			full(true);

			heandView.setVisibility(View.GONE);
			heandViewOritation.setVisibility(View.VISIBLE);
			currentHangiqng.setVisibility(View.GONE);
			chartFenshiIndicator.setVisibility(View.GONE);
			chartTools.setVisibility(View.GONE);

			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					Utils.getWindowHeight(this) - Utils.dip2px(this, 48f));
			findViewById(R.id.frag_container).setLayoutParams(params);

			switch (currentTag) {
			case 1:
				oritationFenshi.setText("15M");
				break;
			case 2:
				oritationFenshi.setText("1H");
				break;
			case 3:
				oritationFenshi.setText("4H");
				break;
			case 4:
				oritationFenshi.setText("日线");
				break;

			default:
				break;
			}

		} else {

			full(false);

			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					Utils.getWindowHeight(this) - Utils.dip2px(this, 237f)
							- statusHeight);
			findViewById(R.id.frag_container).setLayoutParams(params);

			llOritationSaveGline.setVisibility(View.GONE);
			heandView.setVisibility(View.VISIBLE);
			currentHangiqng.setVisibility(View.VISIBLE);
			heandViewOritation.setVisibility(View.GONE);
			chartFenshiIndicator.setVisibility(View.VISIBLE);
			chartTools.setVisibility(View.VISIBLE);
		}
	}

	// tag 是时间节点
	private void showFenshiFrag(String treaty, String type, String interval) {
		if (mLastTabTag != FenshiChartFragment.TAG) {
			final FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			if (mLastTabTag != null) {
				Fragment fragment = getSupportFragmentManager()
						.findFragmentByTag(mLastTabTag);
				if (fragment != null && !fragment.isDetached()) {
					ft.detach(fragment);
				}
			}
			Fragment newFragment = getSupportFragmentManager()
					.findFragmentByTag(FenshiChartFragment.TAG);
			if (newFragment == null) {
				newFragment = FenshiChartFragment.newInstance(treaty, type,
						interval);
				ft.add(R.id.frag_container, newFragment,
						FenshiChartFragment.TAG);
			} else {
				ft.attach(newFragment);
			}
			ft.addToBackStack(FenshiChartFragment.TAG) ;
			ft.commit();
			mLastTabTag = FenshiChartFragment.TAG;
		}
	}

	public void showKlineFrag(String treaty, String type, String interval,
			String tabTag) {
		if (mLastTabTag != tabTag) {
			final FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			if (mLastTabTag != null) {
				Fragment fragment = getSupportFragmentManager()
						.findFragmentByTag(mLastTabTag);
				if (fragment != null && !fragment.isDetached()) {
					ft.detach(fragment);
				}
			}
			Fragment newFragment = getSupportFragmentManager()
					.findFragmentByTag(tabTag);

			
			if (newFragment == null) {
				newFragment = KLineFragment.newInstance(treaty, type, interval);
				ft.add(R.id.frag_container, newFragment, tabTag);
			} else {
				ft.attach(newFragment);
			}
			ft.addToBackStack(tabTag) ;
			ft.commit();
			mLastTabTag = tabTag;
		}
	}

	/**
	 * 横屏平时的指标 下拉view
	 */
	private View mFenshiMenuView;
	private PopupWindow popupFenshiWindow;

	public void showFenshiPopupWindow() {

		if (mFenshiMenuView == null) {
			mFenshiMenuView = inflater.inflate(R.layout.popup_fenshi, null);
			initFenshiPopupView(mFenshiMenuView);
		}

		if (popupFenshiWindow == null) {
			popupFenshiWindow = Utils.newBasicPopupWindow(this);
			popupFenshiWindow.setContentView(mFenshiMenuView);
		}

		popupFenshiWindow.showAsDropDown(op_ri);
	}

	public void initFenshiPopupView(View mFenshiMenuView) {
		if (mFenshiMenuView == null)
			return;
		RadioButton oritation15M = (RadioButton) mFenshiMenuView
				.findViewById(R.id.oritation_norm_15_minute);
		// RadioButton oritation30M = (RadioButton) mFenshiMenuView
		// .findViewById(R.id.oritation_norm_30_minute);
		RadioButton oritation1H = (RadioButton) mFenshiMenuView
				.findViewById(R.id.oritation_norm_1_hours);
		RadioButton oritation4H = (RadioButton) mFenshiMenuView
				.findViewById(R.id.oritation_norm_4_hours);
		RadioButton oritationDays = (RadioButton) mFenshiMenuView
				.findViewById(R.id.oritation_norm_days);

		switch (currentTag) {
		case 1:
			oritation15M.setChecked(true);

			break;
		// case 2:
		// oritation30M.setChecked(true);
		// break;
		case 3:
			oritation1H.setChecked(true);
			break;
		case 4:
			oritation4H.setChecked(true);
			break;
		case 5:
			oritationDays.setChecked(true);
			break;

		default:
			break;
		}

		oritation15M.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oritationFenshi.setText("15M");
				currentTag = 1;
				fragmentChange(1);

				if (currentOptional != null)
					showKlineFrag(currentOptional.getTreaty(),
							currentOptional.getType(), "15", TAB_15_MINUTE);
			}
		});
		// oritation30M.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// oritationFenshi.setText("30M");
		// currentTag = 2;
		// showKlineFrag(null, TAB_30_MINUTE);
		// }
		// });
		oritation1H.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oritationFenshi.setText("1H");
				currentTag = 2;
				fragmentChange(2);
				if (currentOptional != null)
					showKlineFrag(currentOptional.getTreaty(),
							currentOptional.getType(), "1h", TAB_1_HOUR);
			}
		});
		oritation4H.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oritationFenshi.setText("4H");
				currentTag = 3;
				fragmentChange(3);
				if (currentOptional != null)
					showKlineFrag(currentOptional.getTreaty(),
							currentOptional.getType(), "4h", TAB_4_HOUR);
			}
		});
		oritationDays.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oritationFenshi.setText("每天");
				currentTag = 4;
				fragmentChange(4);
				if (currentOptional != null)
					showKlineFrag(currentOptional.getTreaty(),
							currentOptional.getType(), "1d", TAB_DAYS);
			}
		});

	}

	@Override
	public void fragmentChange(int currentTag) {
		// TODO Auto-generated method stub
		// if (currentTag == 0) {
		// norm.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// showCusToast("分时线不可用该功能,请切换到其他周期K线");
		// }
		// });
		// }
	}

	/**
	 * 每隔两分钟刷新一次专家的列表
	 */
	public boolean threadRunable = true;

	public Thread refreshOptionalThread;

	private int REFRESH_DELAY = 20 * 1000;

	private int loadCount = 0;

	private RefreshPriceBySymbolTask refreshTask;

	private Runnable priceRunnable = new Runnable() {

		@Override
		public void run() {
			while (threadRunable) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (loadCount > 0) {
							refreshTask = new RefreshPriceBySymbolTask();
							refreshTask.execute("");
						}
					}
				});
				loadCount++;
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
		if (refreshOptionalThread == null) {
			refreshOptionalThread = new Thread(priceRunnable);
		}
		refreshOptionalThread.start();

	}

	class RefreshPriceBySymbolTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			OptionalService hService = new OptionalService(
					PortOptionalActivity.this);
			try {
				Optional op = hService.getOptionalPriceByTradey(getIntent()
						.getStringExtra("symbol"));
				if (op != null) {
					currentOptional = op;
					return SUCCESS;
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(PortOptionalActivity.this,
						null, e);
				return ERROR;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (SUCCESS.equalsIgnoreCase(result)) {
				initHangqing(false);
			} else if (ERROR.equalsIgnoreCase(result)) {

			} else {
			}
		}
	}

	private void full(boolean enable) {
		if (enable) {
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			getWindow().setAttributes(lp);
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		} else {
			WindowManager.LayoutParams attr = getWindow().getAttributes();
			attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().setAttributes(attr);
			getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
	}

}
