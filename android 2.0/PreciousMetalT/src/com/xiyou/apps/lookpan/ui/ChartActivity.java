//package com.xiyou.apps.lookpan.ui;
//import java.util.ArrayList;
//import java.util.List;
//
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.pm.ActivityInfo;
//import android.content.res.Configuration;
//import android.graphics.Color;
//import android.graphics.Matrix;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.util.DisplayMetrics;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.animation.Animation;
//import android.view.animation.TranslateAnimation;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.LinearLayout.LayoutParams;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//import cn.limc.KLineEntity;
//import cn.limc.androidcharts.ParseUtils;
//import cn.limc.androidcharts.entity.LineEntity;
//import cn.limc.androidcharts.view.CrossLineChart;
//import cn.limc.androidcharts.view.LineChart;
//import cn.limc.androidcharts.view.NormUnionCandleStickChart;
//import cn.precious.metal.config.ParamConfig;
//import cn.precious.metal.utils.Utils;
//
//import com.xiyou.apps.lookpan.base.BaseActivity;
//import com.xiyou.apps.lookpan.ui.fragment.news.AboutOptinalNewsFragment;
//import com.xiyou.apps.lookpan.ui.fragment.news.AboutOptinalNoticeFragment;
//import com.xiyou.apps.lookpan.ui.fragment.news.AboutOptinalResearchFragment;
//
//@SuppressLint("ResourceAsColor")
//public class ChartActivity extends BaseActivity implements
//		OnClickListener {
//
//	private final String[] SELECTITEMS = { "MACD指标", "BOLL布林线", "KDJ随机指标",
//			"RSI强弱指标", "SMA均线", "EMA均线" };
//
//	private TextView tvFenshi, tv15Fenzhong, tv30Fenzhong, tvXiaoshi,
//			tv4Xiaoshi, tvDays;
//
//
//	private LinearLayout llAboutOptional;
//
//	private TextView abountNews, aboutNotice, abountResearch;
//	//
//	private ImageView orientationChange, chartNorm;
//
//	private ImageView cursor;
//
//	// 横屏的时候
//	private ImageView oritatioanGline, oritationNorm;
//	private TextView oritationFenshi;
//
//	private LayoutInflater inflater;
//
//	// 横竖屏切换需要显示 隐藏
//	private LinearLayout heandView, heandViewOritation, currentHangiqng,
//			aboutOptionalTitle, aboutOptionalContent, chartFenshiIndicator,
//			chartTools;
//
//	private int currentTag = 0;
//	
//	private int offset = 0;
//	
//	private NormUnionCandleStickChart unionStickChart ;
//	
//	private CrossLineChart crossLineChart ;
//	
//	private LineChart lineChart ;
//	
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_chart);
//		inflater = LayoutInflater.from(this);
//		initDatas();
//		initView();
//		initListener();
//	}
//
//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		// TODO Auto-generated method stub
//		super.onConfigurationChanged(newConfig);
//
//		if (Configuration.ORIENTATION_LANDSCAPE == getResources()
//				.getConfiguration().orientation) {
//			handlerOritationView(true);
//		} else {
//			handlerOritationView(false);
//		}
//	}
//
//	public void initView() {
//		cursor = (ImageView) findViewById(R.id.cursor);
//
//		tvFenshi = (TextView) findViewById(R.id.tv_fenshi);
//		tv15Fenzhong = (TextView) findViewById(R.id.tv_15fenzhong);
//		tv30Fenzhong = (TextView) findViewById(R.id.tv_30fenzhong);
//		tvXiaoshi = (TextView) findViewById(R.id.tv_xiaoshi);
//		tv4Xiaoshi = (TextView) findViewById(R.id.tv_4xiaoshi);
//		tvDays = (TextView) findViewById(R.id.tv_other);
//		//
//		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, Utils.getWindowWidth(this) - Utils.dip2px(this, 48)) ;
//		findViewById(R.id.frag_container).setLayoutParams(params);
//
//		//
//		llAboutOptional = (LinearLayout) findViewById(R.id.ll_about_optional);
//		abountNews = (TextView) findViewById(R.id.tv_about_optional_news);
//		aboutNotice = (TextView) findViewById(R.id.tv_about_optional_notice);
//		abountResearch = (TextView) findViewById(R.id.tv_about_optional_research);
//
//		//
//		orientationChange = (ImageView) findViewById(R.id.iv_orientation);
//		chartNorm = (ImageView) findViewById(R.id.iv_norm);
//		
//		//
//		unionStickChart = (NormUnionCandleStickChart) findViewById(R.id.union_candle_stick) ;
//		lineChart = (LineChart) findViewById(R.id.line_chart) ;
//		crossLineChart = (CrossLineChart) findViewById(R.id.cross_line_chart) ;
//		
//		//
//		heandView = (LinearLayout) findViewById(R.id.heand_view);
//		heandViewOritation = (LinearLayout) findViewById(R.id.heand_view_oritation);
//		currentHangiqng = (LinearLayout) findViewById(R.id.ll_current_hangqing);
//		chartFenshiIndicator = (LinearLayout) findViewById(R.id.ll_tab_indicator);
//		aboutOptionalTitle = (LinearLayout) findViewById(R.id.ll_about_optional);
//		aboutOptionalContent = (LinearLayout) findViewById(R.id.gonggao_frag_container);
//		chartTools = (LinearLayout) findViewById(R.id.ll_chart_tools);
//
//		//
//		oritatioanGline = (ImageView) findViewById(R.id.oritation_gline);
//		oritationFenshi = (TextView) findViewById(R.id.oritation_fenshi);
//		oritationNorm = (ImageView) findViewById(R.id.oritation_norm);
//
//		Matrix matrix = new Matrix();
//		matrix.postTranslate(0, 0);
//		cursor.setImageMatrix(matrix);// 设置动画初始位置
//		offset = Utils.getWindowWidth(this) / 6;
//		showAboutOptoinalNewsFrag();
//		
//		initLineChart();
//		initUnionStickChart(); 
//		
//		lineChart.setVisibility(View.VISIBLE );
//		
//	}
//
//	public void initListener() {
//		tvFenshi.setOnClickListener(this);
//		tv15Fenzhong.setOnClickListener(this);
//		tv30Fenzhong.setOnClickListener(this);
//		tvXiaoshi.setOnClickListener(this);
//		tv4Xiaoshi.setOnClickListener(this);
//		tvDays.setOnClickListener(this);
//
//		abountNews.setOnClickListener(this);
//		aboutNotice.setOnClickListener(this);
//		abountResearch.setOnClickListener(this);
//
//		orientationChange.setOnClickListener(this);
//		chartNorm.setOnClickListener(this);
//
//		// 横屏的title
//		oritatioanGline.setOnClickListener(this);
//		oritationFenshi.setOnClickListener(this);
//		oritationNorm.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.tv_about_optional_news:
//			llAboutOptional.setBackgroundResource(R.drawable.gonggao_zuo);
//			showAboutOptoinalNewsFrag();
//			break;
//		case R.id.tv_about_optional_notice:
//			llAboutOptional.setBackgroundResource(R.drawable.gonggao_zhong);
//			showAboutOptoinalNoticeFrag();
//			break;
//		case R.id.tv_about_optional_research:
//			llAboutOptional.setBackgroundResource(R.drawable.gonggao_zhong_you);
//			showAboutOptoinalResearchFrag();
//			break;
//		case R.id.iv_orientation:
//			turnOriClick(v);
//			break;
//		case R.id.iv_norm:
//			updateChartNorm();
//			break;
//		case R.id.tv_fenshi:
//			if (currentTag != 0) {
//				Animation animation = new TranslateAnimation(offset
//						* currentTag, 0, 0, 0);
//				currentTag = 0;
//				animation.setFillAfter(true);
//				animation.setDuration(300);
//				cursor.startAnimation(animation);
//			}
//			lineChart.setVisibility(View.VISIBLE);
//			unionStickChart.setVisibility(View.GONE);
//			break;
//		case R.id.tv_15fenzhong:
//			if (currentTag != 1) {
//				Animation animation = new TranslateAnimation(offset
//						* currentTag, offset, 0, 0);
//				currentTag = 1;
//				animation.setFillAfter(true);
//				animation.setDuration(300);
//				cursor.startAnimation(animation);
//			}
//			
//			lineChart.setVisibility(View.GONE);
//			unionStickChart.setVisibility(View.VISIBLE);
//			unionStickChart.setStickData(stickDatas);
//			unionStickChart.setButtomNormLineDatas(buttonLines);;
//			unionStickChart.setButtomStickData(buttonStickDatas);
//			unionStickChart.setLinesData(toplines);
//			unionStickChart.setTopNormType(lastTopNorm);
//			break;
//		case R.id.tv_30fenzhong:
//			if (currentTag != 2) {
//				Animation animation = new TranslateAnimation(offset
//						* currentTag, 2 * offset, 0, 0);
//				currentTag = 2;
//				animation.setFillAfter(true);
//				animation.setDuration(300);
//				cursor.startAnimation(animation);
//			}
//			
//			lineChart.setVisibility(View.GONE);
//			unionStickChart.setVisibility(View.VISIBLE);
//			
//			unionStickChart.setStickData(stickDatas);
//			unionStickChart.setButtomNormLineDatas(buttonLines);;
//			unionStickChart.setButtomStickData(buttonStickDatas);
//			unionStickChart.setLinesData(toplines);
//			unionStickChart.setTopNormType(lastTopNorm);
//			
//			break;
//		case R.id.tv_xiaoshi:
//			if (currentTag != 3) {
//				Animation animation = new TranslateAnimation(offset
//						* currentTag, 3 * offset, 0, 0);
//				currentTag = 3;
//				animation.setFillAfter(true);
//				animation.setDuration(300);
//				cursor.startAnimation(animation);
//			}
//
//			break;
//		case R.id.tv_4xiaoshi:
//			if (currentTag != 4) {
//				Animation animation = new TranslateAnimation(offset
//						* currentTag, 4 * offset, 0, 0);
//				currentTag = 4;
//				animation.setFillAfter(true);
//				animation.setDuration(300);
//				cursor.startAnimation(animation);
//			}
//			break;
//		case R.id.tv_other:
//			if (currentTag != 5) {
//				Animation animation = new TranslateAnimation(offset
//						* currentTag, 5 * offset, 0, 0);
//				currentTag = 5;
//				animation.setFillAfter(true);
//				animation.setDuration(300);
//				cursor.startAnimation(animation);
//			}
//
//			break;
//		case R.id.oritation_fenshi:
//			showFenshiPopupWindow();
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		switch (keyCode) {
//		case KeyEvent.KEYCODE_BACK:
//			if (Configuration.ORIENTATION_LANDSCAPE == getResources()
//					.getConfiguration().orientation) {
//				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//			} else {
//				finish();
//			}
//			return true;
//		default:
//			break;
//		}
//
//		return super.onKeyDown(keyCode, event);
//	}
//
//	/**
//	 * rt全屏
//	 * 
//	 * @param v
//	 */
//	public void turnOriClick(View v) {
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		int screenWidth = dm.widthPixels;
//		int screenHeight = dm.heightPixels;
//		if (screenHeight > screenWidth) {
//			try {
//				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//			} catch (Exception e) {
//			}
//		} else if (screenWidth > screenHeight) {
//			try {
//				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//			} catch (Exception e) {
//			}
//		}
//	}
//
//	public void handlerOritationView(boolean isOrientation) {
//		if (isOrientation) {
//			heandView.setVisibility(View.GONE);
//			heandViewOritation.setVisibility(View.VISIBLE);
//			aboutOptionalContent.setVisibility(View.GONE);
//			aboutOptionalTitle.setVisibility(View.GONE);
//			currentHangiqng.setVisibility(View.GONE);
//			chartFenshiIndicator.setVisibility(View.GONE);
//			chartTools.setVisibility(View.GONE);
//		} else {
//			heandView.setVisibility(View.VISIBLE);
//			aboutOptionalContent.setVisibility(View.VISIBLE);
//			aboutOptionalTitle.setVisibility(View.VISIBLE);
//			currentHangiqng.setVisibility(View.VISIBLE);
//			heandViewOritation.setVisibility(View.GONE);
//			chartFenshiIndicator.setVisibility(View.VISIBLE);
//			chartTools.setVisibility(View.VISIBLE);
//		}
//	}
//
//
//	private String mLastAboutOptionalTag = null;
//
//	private void showAboutOptoinalNewsFrag() {
//		if (mLastAboutOptionalTag != AboutOptinalNewsFragment.TAG) {
//			final FragmentTransaction ft = getSupportFragmentManager()
//					.beginTransaction();
//			if (mLastAboutOptionalTag != null) {
//				Fragment fragment = getSupportFragmentManager()
//						.findFragmentByTag(mLastAboutOptionalTag);
//				if (fragment != null && !fragment.isDetached()) {
//					ft.detach(fragment);
//				}
//			}
//			Fragment newFragment = getSupportFragmentManager()
//					.findFragmentByTag(AboutOptinalNewsFragment.TAG);
//			if (newFragment == null) {
//				newFragment = new AboutOptinalNewsFragment();
//				ft.add(R.id.gonggao_frag_container, newFragment,
//						AboutOptinalNewsFragment.TAG);
//			} else {
//				ft.attach(newFragment);
//			}
//			ft.commit();
//			mLastAboutOptionalTag = AboutOptinalNewsFragment.TAG;
//		}
//	}
//
//	private void showAboutOptoinalNoticeFrag() {
//		if (mLastAboutOptionalTag != AboutOptinalNoticeFragment.TAG) {
//			final FragmentTransaction ft = getSupportFragmentManager()
//					.beginTransaction();
//			if (mLastAboutOptionalTag != null) {
//				Fragment fragment = getSupportFragmentManager()
//						.findFragmentByTag(mLastAboutOptionalTag);
//				if (fragment != null && !fragment.isDetached()) {
//					ft.detach(fragment);
//				}
//			}
//			Fragment newFragment = getSupportFragmentManager()
//					.findFragmentByTag(AboutOptinalNoticeFragment.TAG);
//			if (newFragment == null) {
//				newFragment = new AboutOptinalNoticeFragment();
//				ft.add(R.id.gonggao_frag_container, newFragment,
//						AboutOptinalNoticeFragment.TAG);
//			} else {
//				ft.attach(newFragment);
//			}
//			ft.commit();
//			mLastAboutOptionalTag = AboutOptinalNoticeFragment.TAG;
//		}
//	}
//
//	private void showAboutOptoinalResearchFrag() {
//		if (mLastAboutOptionalTag != AboutOptinalResearchFragment.TAG) {
//			final FragmentTransaction ft = getSupportFragmentManager()
//					.beginTransaction();
//			if (mLastAboutOptionalTag != null) {
//				Fragment fragment = getSupportFragmentManager()
//						.findFragmentByTag(mLastAboutOptionalTag);
//				if (fragment != null && !fragment.isDetached()) {
//					ft.detach(fragment);
//				}
//			}
//			Fragment newFragment = getSupportFragmentManager()
//					.findFragmentByTag(AboutOptinalResearchFragment.TAG);
//			if (newFragment == null) {
//				newFragment = new AboutOptinalResearchFragment();
//				ft.add(R.id.gonggao_frag_container, newFragment,
//						AboutOptinalResearchFragment.TAG);
//			} else {
//				ft.attach(newFragment);
//			}
//			ft.commit();
//			mLastAboutOptionalTag = AboutOptinalResearchFragment.TAG;
//		}
//	}
//
//	private String lastButtonNorm = ParamConfig.NORM_MACD;
//
//	private String lastTopNorm;
//
//	public void updateChartNorm() {
//		AlertDialog alert = new AlertDialog.Builder(ChartActivity.this)
//				.setItems(SELECTITEMS, new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						
//						
//						
//					}
//				}).create();
//		alert.setCanceledOnTouchOutside(true);
//		alert.show();
//	}
//
//	/**
//	 * 横屏平时的 下拉view
//	 */
//	private boolean fenshi_menu_display = false;
//	private View mFenshiMenuView;
//	private PopupWindow popupFenshiWindow;
//
//	public void showFenshiPopupWindow() {
//		if (null != popupFenshiWindow && popupFenshiWindow.isShowing()) {
//			fenshi_menu_display = true;
//		}
//		if (!fenshi_menu_display) {
//			mFenshiMenuView = inflater.inflate(R.layout.popup_fenshi, null);
//			popupFenshiWindow = new android.widget.PopupWindow(mFenshiMenuView,
//					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//			// 设置SelectPicPopupWindow弹出窗体可点击
//			popupFenshiWindow.setBackgroundDrawable(new BitmapDrawable());
//			popupFenshiWindow.setOutsideTouchable(true);
//			popupFenshiWindow.showAsDropDown(oritationFenshi);
//			fenshi_menu_display = true;
//		} else {
//			popupFenshiWindow.dismiss();
//			fenshi_menu_display = false;
//		}
//	}
//
//	/**
//	 * 横屏平时的指标 下拉view
//	 */
//	private boolean norm_menu_display = false;
//	private View mNormMenuView;
//	private PopupWindow popupNormWindow;
//
//	public void showNormPopupWindow() {
//		if (null != popupFenshiWindow && popupFenshiWindow.isShowing()) {
//			norm_menu_display = true;
//		}
//		if (!norm_menu_display) {
//			mNormMenuView = inflater.inflate(R.layout.popup_norm, null);
//			popupNormWindow = new android.widget.PopupWindow(mNormMenuView,
//					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//			// 设置SelectPicPopupWindow弹出窗体可点击
//			popupNormWindow.setBackgroundDrawable(new BitmapDrawable());
//			popupNormWindow.setOutsideTouchable(true);
//			popupNormWindow.showAsDropDown(oritationNorm);
//			norm_menu_display = true;
//		} else {
//			popupNormWindow.dismiss();
//			norm_menu_display = false;
//		}
//	}
//
//	public void initFenshiPopupView(View mFenshiMenuView) {
//		if(mFenshiMenuView == null)
//			return  ;
//		
//		TextView ormtationa15Minute = (TextView) mFenshiMenuView.findViewById(R.id.oritation_norm_15_minute) ;
//		TextView ormtationa30Minute = (TextView) mFenshiMenuView.findViewById(R.id.oritation_norm_30_minute) ;
//		TextView ormtationa1Hours = (TextView) mFenshiMenuView.findViewById(R.id.oritation_norm_1_hours) ;
//		TextView ormtationa4Hours = (TextView) mFenshiMenuView.findViewById(R.id.oritation_norm_4_hours) ;
//		TextView ormtationaDays = (TextView) mFenshiMenuView.findViewById(R.id.oritation_norm_days) ;
//		
//		ormtationa15Minute.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				oritationFenshi.setText("15M");
//			}
//		});
//		ormtationa30Minute.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				oritationFenshi.setText("30M");
//			}
//		});
//		ormtationa1Hours.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				oritationFenshi.setText("1H");
//			}
//		});
//		ormtationa4Hours.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				oritationFenshi.setText("4H");
//			}
//		});
//		ormtationaDays.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				oritationFenshi.setText("1D");
//			}
//		});
//	}
//
//	public void initNormPopupView(View mNormMenuView) {
//		if(mNormMenuView == null)
//			return  ;
//		
//		TextView normBoll = (TextView) mNormMenuView.findViewById(R.id.popup_norm_boll) ;
//		TextView normEma = (TextView) mNormMenuView.findViewById(R.id.popup_norm_ema) ;
//		TextView normKdj = (TextView) mNormMenuView.findViewById(R.id.popup_norm_kdj) ;
//		TextView normMacd = (TextView) mNormMenuView.findViewById(R.id.popup_norm_macd) ;
//		TextView normRsi = (TextView) mNormMenuView.findViewById(R.id.popup_norm_rsi) ;
//		TextView normSma = (TextView) mNormMenuView.findViewById(R.id.popup_norm_sma) ;
//		
//		normBoll.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				oritationFenshi.setText("15M");
//			}
//		});
//		normEma.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				oritationFenshi.setText("30M");
//			}
//		});
//		normKdj.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				oritationFenshi.setText("1H");
//			}
//		});
//		normMacd.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				oritationFenshi.setText("4H");
//			}
//		});
//		normRsi.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//			}
//		});
//		normSma.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//			}
//		});
//	}
//
//	
//	public void showFragByTagsIdAndTag(int currentTag,String normType){
//		switch (currentTag) {
//		case 0:
//			
//			break;
//		case 1:
//			
//			break;
//		case 2:
//			
//			break;
//		case 3:
//			
//			break;
//		case 4:
//			
//			break;
//		case 5:
//			
//			break;
//
//		default:
//			break;
//		}
//	}
//	
//	public boolean isTopNorm(String normType){
//		if(ParamConfig.NORM_BOLL.equalsIgnoreCase(normType) || ParamConfig.NORM_EMA.equalsIgnoreCase(normType) || ParamConfig.NORM_SMA.equalsIgnoreCase(normType))
//			return true ;
//		return false ;
//	}
//	
//	
//	
//	
//	/**
//	 * 
//	 *  ===========
//	 */
//	
//	private List<KLineEntity> stickDatas;
//
//	private String topNorm;
//
//	private String buttonNorm = ParamConfig.NORM_MACD;
//	
//	
//	private List<LineEntity<KLineEntity>> toplines;
//
//	private List<LineEntity<KLineEntity>> buttonLines;
//
//	private List<KLineEntity> buttonStickDatas;
//
//	public void initDatas() {
//		// 获取数据源
//		List<KLineEntity> list = ParseUtils
//				.getKLineDatas(getResources().openRawResource(
//						R.raw.demo)) ;
//		List<KLineEntity> kLineEntities = new  ArrayList<KLineEntity>();
//		
//		
//		if (list == null || list.size() < 20)
//			return;
//		
//		for (int i = list.size() -1 ; i >= 0; i--) {
//			kLineEntities.add(list.get(i)) ;  //   日期排序
//		}
//		
//		stickDatas = list.subList(20, list.size());
//
//		if (ParamConfig.NORM_KDJ.equalsIgnoreCase(buttonNorm)) {
//			buttonLines = ParseUtils.getKDJLinesDatas(kLineEntities, 10);
//		} else if (ParamConfig.NORM_RSI.equalsIgnoreCase(buttonNorm)) {
//			buttonLines = ParseUtils.getRsiLineDatas(kLineEntities, 6, 12, 24);
//		} else {// macd
//			buttonLines = ParseUtils.getMacdLineDatas(kLineEntities, 12, 26, 9);
//			buttonStickDatas = ParseUtils.getMaceStickDatas(kLineEntities, 12,
//					26, 9);
//		}
//
//		if (ParamConfig.NORM_BOLL.equalsIgnoreCase(topNorm)) {
//			toplines = ParseUtils.boll(kLineEntities, 20, 2);
//		} else if (ParamConfig.NORM_EMA.equalsIgnoreCase(topNorm)) {
//			toplines = new ArrayList<LineEntity<KLineEntity>>();
//			toplines.add(ParseUtils.getEMALineData(kLineEntities, 10));
//		} else if (ParamConfig.NORM_SMA.equalsIgnoreCase(topNorm)) {
//			toplines = ParseUtils.getSimpleMaLineData(kLineEntities, 5, 10, 20);
//		}
//	}
//	
//	
//	public void initUnionStickChart() {
//		unionStickChart.setBorderColor(Color.LTGRAY);
//		unionStickChart.setLongitudeFontSize(14);
//		unionStickChart.setLongitudeFontColor(Color.WHITE);
//		unionStickChart.setLatitudeColor(Color.GRAY);
//		unionStickChart.setLatitudeFontColor(Color.WHITE);
//		unionStickChart.setLongitudeColor(Color.GRAY);
//		unionStickChart.setDisplayLongitudeTitle(true);
//		unionStickChart.setDisplayLatitudeTitle(true);
//		unionStickChart.setDisplayLatitude(true);
//		unionStickChart.setDisplayLongitude(true);
//		unionStickChart.setLatitudeNum(5);
//		unionStickChart.setLongitudeNum(5);
//		unionStickChart.setAxisYTitleQuadrantWidth(50);
//		unionStickChart.setAxisXTitleQuadrantHeight(20);
//		unionStickChart.setBackgroundColor(Color.BLACK);
//		unionStickChart.setButtomLatitudeNum(3);
//
//		unionStickChart.setStickData(stickDatas);
//
//		if (ParamConfig.NORM_MACD.equalsIgnoreCase(buttonNorm)) {
//			unionStickChart.setButtomStickData(buttonStickDatas);
//		}
//		unionStickChart.setButtomNormLineDatas(buttonLines);
//		unionStickChart.setLinesData(toplines);
//	}
//	
//	
//	public void initLineChart() {
//		//初始化数据
//		List<LineEntity<KLineEntity>> lines = new ArrayList<LineEntity<KLineEntity>>();
//		LineEntity<KLineEntity> line1 = new LineEntity<KLineEntity>() ;
//		line1.setTitle("Line Test");
//		line1.setLineColor(Color.WHITE);
//		List<KLineEntity> klineList = ParseUtils.getKLineDatas(getResources().openRawResource(R.raw.line)) ;
//		line1.setLineData(klineList);
//		lines.add(line1);
//		
//		
//		
//		lineChart.setAxisXColor(Color.LTGRAY);
//		lineChart.setAxisYColor(Color.LTGRAY);
//		lineChart.setBorderColor(Color.LTGRAY);
//		lineChart.setLongitudeFontSize(14);
//		lineChart.setLongitudeFontColor(Color.WHITE);
//		lineChart.setLatitudeColor(Color.GRAY);
//		lineChart.setLatitudeFontColor(Color.WHITE);
//		lineChart.setLongitudeColor(Color.GRAY);
//		lineChart.setDisplayLongitudeTitle(true);
//		lineChart.setDisplayLatitudeTitle(true);
//		lineChart.setDisplayLatitude(true);
//		lineChart.setDisplayLongitude(true);
//		lineChart.setLatitudeNum(5);
//		lineChart.setLongitudeNum(5);
//		lineChart.setDataQuadrantPaddingTop(0);
//		lineChart.setDataQuadrantPaddingBottom(0);
//		lineChart.setDataQuadrantPaddingLeft(0);
//		lineChart.setDataQuadrantPaddingRight(0);
//		lineChart.setAxisYTitleQuadrantWidth(50);
//		lineChart.setAxisXTitleQuadrantHeight(20);
//	}
//	
// }
