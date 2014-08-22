package cn.precious.metal.ui.fragment.chart;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import cn.precious.metal.R;
import cn.precious.metal.base.BaseFragment;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.config.ParamConfig;
import cn.precious.metal.services.KlineService;
import cn.precious.metal.tools.ExcepUtils;
import cn.precious.metal.utils.Utils;
import cn.precious.metal.view.KLineEntity;
import cn.precious.metal.view.ParseUtils;
import cn.precious.metal.view.entity.LineEntity;
import cn.precious.metal.view.view.CrossLineChart;
import cn.precious.metal.view.view.GLineToolsChart;
import cn.precious.metal.view.view.GoldLineChart;
import cn.precious.metal.view.view.NormUnionCandleStickChart;

public class KLineFragment extends BaseFragment {

	private final String[] SELECTITEMS = { "MACD指标", "BOLL布林线", "KDJ随机指标",
			"RSI强弱指标", "SMA均线", "EMA均线" };

	public static final String TAG = "KLineFragment";

	private NormUnionCandleStickChart unionCandleStickChart;

	private CrossLineChart crossLineChart;

	private GLineToolsChart gLineToolsChart;

	private List<KLineEntity> stickDatas;

	private Activity mActivity;

	private LinearLayout llOritationSaveGline, heandViewOritation;

	// 横屏的时候
	private ImageView  oritationGline,  oritationDeleteAll, oritationDelete;
	
//	private ImageView oritationNorm ;
	
//	private ImageView oritationaGlineType ;
	
	private LinearLayout op_hua ,op_zhi;

	private GoldLineChart goldLineChart;


	private ImageView changeOritation;
	

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity = activity;

	}

	public static KLineFragment newInstance(String treaty, String type,
			String interval) {
		KLineFragment fragment = new KLineFragment();
		Bundle b = new Bundle();
		b.putString("treaty", treaty);
		b.putString("type", type);
		b.putString("interval", interval);
		fragment.setArguments(b);
		return fragment;
	}

	
	private GetKlineDataTask task ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_norm_chart, null);
		inflater = LayoutInflater.from(mActivity);
		initView(view);
		initListener();
		task = new GetKlineDataTask() ;
		task.execute(new String[] {
				getArguments().getString("treaty"),
				getArguments().getString("type"),
				getArguments().getString("interval") });
		return view;
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(task != null && task.getStatus() == Status.RUNNING) {
			task.cancel(true);
		}
		
	}

	public void initView(View view) {

		unionCandleStickChart = (NormUnionCandleStickChart) view
				.findViewById(R.id.norm_union_candle_stick_chart);
		crossLineChart = (CrossLineChart) view
				.findViewById(R.id.cross_line_chart);
		gLineToolsChart = (GLineToolsChart) view
				.findViewById(R.id.gline_tools_chart);
		goldLineChart = (GoldLineChart) view.findViewById(R.id.gold_line_chart);
		unionCandleStickChart.setmCrossLineChart(crossLineChart);

		
		
		
//		oritationNorm = (ImageView) mActivity.findViewById(R.id.oritation_norm);
		op_zhi = (LinearLayout) mActivity.findViewById(R.id.op_zhi);
		oritationGline = (ImageView) mActivity
				.findViewById(R.id.oritation_gline);// 十字线
		llOritationSaveGline = (LinearLayout) mActivity
				.findViewById(R.id.ll_oritation_save_gline);
		oritationDelete = (ImageView) mActivity
				.findViewById(R.id.iv_gline_delete);// 删除
		heandViewOritation = (LinearLayout) mActivity
				.findViewById(R.id.heand_view_oritation);
//		oritationaGlineType = (ImageView) mActivity
//				.findViewById(R.id.oritationa_gline_type);// 徒手画线
		
		op_hua = (LinearLayout) mActivity.findViewById(R.id.op_hua) ;
		
		oritationDeleteAll = (ImageView) mActivity
				.findViewById(R.id.oritationa_gline_delete_all);// 删除所有
		changeOritation = (ImageView) mActivity
				.findViewById(R.id.iv_orientation);// 方向发生变化

	}

	public void initListener() {
		unionCandleStickChart
				.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						unionCandleStickChart.invalidate();
						unionCandleStickChart.notifyEventAll(unionCandleStickChart);
						
						crossLineChart.setVisibility(View.VISIBLE);
						crossLineChart.setDrawOffset(unionCandleStickChart
								.getDrawOffset());
						crossLineChart.setStickWidth(unionCandleStickChart
								.getStickWidth());
						crossLineChart.setGlineType(CrossLineChart.CLOSE_LINE_TYPE);
						
						crossLineChart.setShowBorderText(true);
						
						crossLineChart.invalidate();
						crossLineChart.notifyEventAll(crossLineChart);
						return true;
					}
				});

		crossLineChart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!crossLineChart.isNear()) {
					crossLineChart.setVisibility(View.GONE);
				}
				crossLineChart.setNear(false);
			}
		});

		oritationDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (GLineToolsChart.STRAIGHT_LINE_TYPE
						.equalsIgnoreCase(currentGlineType)) {
					gLineToolsChart.setGlineEntity(null);
					gLineToolsChart.setVisibility(View.GONE);
				} else if (GLineToolsChart.GOLD_LINE_TYPE
						.equalsIgnoreCase(currentGlineType)) {
					goldLineChart.setmGoldLineEntity(null);
					goldLineChart.setVisibility(View.GONE);
				}
				currentGlineType = null;
				llOritationSaveGline.setVisibility(View.GONE);
				heandViewOritation.setVisibility(View.VISIBLE);
			}
		});

		oritationGline.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showCrossLinePopupWindow();
			}
		});
		
		
		
		
		op_zhi.setOnClickListener( // 横屏是title的切换指标
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showNormPopupWindow();
					}
				});
		// 竖屏的时候 切换指标
		mActivity.findViewById(R.id.iv_norm).setOnClickListener(
				changeNormListener);

		mActivity.findViewById(R.id.iv_gline_save)
				.setOnClickListener(glineSave);

		op_hua.setOnClickListener(freeHandGlineListener);

		oritationDeleteAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (unionCandleStickChart.isHaveGline()) {
					unionCandleStickChart.clearAllGlineLines();
				} else {
					Toast.makeText(getActivity(), "你还没有自定义指标线", 1000).show();
				}
			}
		});

		// 横竖屏切换
		changeOritation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				unionCandleStickChart.invalidate();
				unionCandleStickChart.notifyEventAll(unionCandleStickChart);

				turnOriClick(v);

			}
		});

	}

	private View.OnClickListener freeHandGlineListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			showGlinePopupWindow();
		}
	};

	private View.OnClickListener glineSave = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (GLineToolsChart.STRAIGHT_LINE_TYPE
					.equalsIgnoreCase(currentGlineType)) {
				unionCandleStickChart
						.addLines(gLineToolsChart.getGlineEntity());
				gLineToolsChart.setGlineEntity(null);
				gLineToolsChart.setVisibility(View.GONE);
			} else if (GLineToolsChart.GOLD_LINE_TYPE
					.equalsIgnoreCase(currentGlineType)) {
				unionCandleStickChart.addLines(goldLineChart
						.getmGoldLineEntity());
				goldLineChart.setmGoldLineEntity(null);
				goldLineChart.setVisibility(View.GONE);
			}
			currentGlineType = null;
			llOritationSaveGline.setVisibility(View.GONE);
			heandViewOritation.setVisibility(View.VISIBLE);
		}
	};
	private View.OnClickListener changeNormListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			updateChartNorm();
		}
	};

	private String lastButtonNorm = ParamConfig.NORM_MACD;
	private String lastTopNorm;

	public void updateChartNorm() {
		AlertDialog alert = new AlertDialog.Builder(mActivity).setItems(
				SELECTITEMS, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							if (lastButtonNorm == ParamConfig.NORM_MACD)
								break;
							buttonLines = ParseUtils.getMacdLineDatas(
									kLineEntities, AppSetting.getInstance(getActivity()).getMacdTParam1(), AppSetting.getInstance(getActivity()).getMacdTParam2(), AppSetting.getInstance(getActivity()).getMacdKParam());
							buttonStickDatas = ParseUtils.getMaceStickDatas(
									kLineEntities, AppSetting.getInstance(getActivity()).getMacdTParam1(), AppSetting.getInstance(getActivity()).getMacdTParam2(), AppSetting.getInstance(getActivity()).getMacdKParam());
							unionCandleStickChart
									.setButtomNormLineDatas(buttonLines);
							unionCandleStickChart
									.setButtomStickData(buttonStickDatas);

							unionCandleStickChart
									.setButtomNormType(ParamConfig.NORM_MACD);
							lastButtonNorm = ParamConfig.NORM_MACD;
							break;
						case 1:
							if (lastTopNorm == ParamConfig.NORM_BOLL)
								break;
							toplines = ParseUtils.boll(kLineEntities, AppSetting.getInstance(getActivity()).getBoolTParam(), AppSetting.getInstance(getActivity()).getBoolKParam());
							unionCandleStickChart
									.setTopNormType(ParamConfig.NORM_BOLL);
							unionCandleStickChart.setLinesData(toplines);
							lastTopNorm = ParamConfig.NORM_BOLL;
							break;
						case 2:
							if (lastButtonNorm == ParamConfig.NORM_KDJ)
								break;
							buttonLines = ParseUtils.getKDJLinesDatas(
									kLineEntities, AppSetting.getInstance(getActivity()).getKdjKParam());
							unionCandleStickChart
									.setButtomNormLineDatas(buttonLines);
							unionCandleStickChart
									.setButtomNormType(ParamConfig.NORM_KDJ);
							lastButtonNorm = ParamConfig.NORM_KDJ;
							break;
						case 3:
							if (lastButtonNorm == ParamConfig.NORM_RSI)
								break;
							buttonLines = ParseUtils.getRsiLineDatas(
									kLineEntities, AppSetting.getInstance(getActivity()).getRsiParam1(), AppSetting.getInstance(getActivity()).getRsiParam2(), AppSetting.getInstance(getActivity()).getRsiParam3());
							unionCandleStickChart
									.setButtomNormLineDatas(buttonLines);
							unionCandleStickChart
									.setButtomNormType(ParamConfig.NORM_RSI);
							lastButtonNorm = ParamConfig.NORM_RSI;
							break;
						case 4:
							if (lastTopNorm == ParamConfig.NORM_SMA)
								break;
							toplines = ParseUtils.getSimpleMaLineData(
									kLineEntities, AppSetting.getInstance(getActivity()).getSmaParam1(), AppSetting.getInstance(getActivity()).getSmaParam2(), AppSetting.getInstance(getActivity()).getSmaParam3());
							unionCandleStickChart.setLinesData(toplines);
							unionCandleStickChart
									.setTopNormType(ParamConfig.NORM_SMA);
							lastTopNorm = ParamConfig.NORM_SMA;
							break;
						case 5:
							if (lastTopNorm == ParamConfig.NORM_EMA)
								break;
							toplines = new ArrayList<LineEntity<KLineEntity>>();
							toplines.add(ParseUtils.getEMALineData(
									kLineEntities, AppSetting.getInstance(getActivity()).getEmaParam()));
							unionCandleStickChart.setLinesData(toplines);
							unionCandleStickChart
									.setTopNormType(ParamConfig.NORM_EMA);
							lastTopNorm = ParamConfig.NORM_EMA;
							break;

						default:
							break;
						}

					}
				}).create();
		alert.setCanceledOnTouchOutside(true);
		alert.show();
	}

	public void initUnionStickChart() {

		unionCandleStickChart.setBorderColor(Color.parseColor("#2D2D2D"));
		unionCandleStickChart.setLongitudeFontSize(18);
		unionCandleStickChart.setLongitudeFontColor(Color.WHITE);
		unionCandleStickChart.setLatitudeColor(Color.parseColor("#2D2D2D"));
		unionCandleStickChart.setLatitudeFontColor(Color.WHITE);
		unionCandleStickChart.setLongitudeColor(Color.parseColor("#2D2D2D"));
		unionCandleStickChart.setDisplayLongitudeTitle(true);
		unionCandleStickChart.setDisplayLatitudeTitle(true);
		unionCandleStickChart.setDisplayLatitude(true);
		unionCandleStickChart.setDisplayLongitude(true);
		unionCandleStickChart.setLatitudeNum(5);
		unionCandleStickChart.setLongitudeNum(5);
		unionCandleStickChart.setAxisYTitleQuadrantWidth(80);
		unionCandleStickChart.setAxisXTitleQuadrantHeight(20);
		unionCandleStickChart.setBackgroundColor(Color.parseColor("#060A0B"));
		unionCandleStickChart.setButtomLatitudeNum(3);
		unionCandleStickChart.setmCrossLineChart(crossLineChart);
		unionCandleStickChart.setStickData(stickDatas);

		unionCandleStickChart.setButtomStickData(buttonStickDatas);
		unionCandleStickChart.setButtomNormLineDatas(buttonLines);
		unionCandleStickChart.setLinesData(toplines);

		buttonLines = ParseUtils.getMacdLineDatas(kLineEntities, AppSetting.getInstance(getActivity()).getMacdTParam1(), AppSetting.getInstance(getActivity()).getMacdTParam2(), AppSetting.getInstance(getActivity()).getMacdKParam());
		buttonStickDatas = ParseUtils.getMaceStickDatas(kLineEntities, AppSetting.getInstance(getActivity()).getMacdTParam1(), AppSetting.getInstance(getActivity()).getMacdTParam2(), AppSetting.getInstance(getActivity()).getMacdKParam());
		unionCandleStickChart.setButtomNormLineDatas(buttonLines);
		unionCandleStickChart.setButtomStickData(buttonStickDatas);

	}

	private void initCrossLineChart() {

		crossLineChart.setLatitudeColor(Color.parseColor("#2D2D2D"));
		crossLineChart.setLongitudeColor(Color.parseColor("#2D2D2D"));
		crossLineChart.setBorderColor(Color.parseColor("#2D2D2D"));
		crossLineChart.setLongitudeFontColor(Color.WHITE);
		crossLineChart.setLatitudeFontColor(Color.WHITE);

		crossLineChart.setBackgroundColor(Color.TRANSPARENT);

		crossLineChart.setAxisYTitleQuadrantWidth(80);
		crossLineChart.setAxisXTitleQuadrantHeight(20);
		crossLineChart.setStickData(stickDatas);
	}

	private void initGlineToolsChart() {

		gLineToolsChart.setLatitudeColor(Color.parseColor("#2D2D2D"));
		gLineToolsChart.setLongitudeColor(Color.parseColor("#2D2D2D"));
		gLineToolsChart.setBorderColor(Color.parseColor("#2D2D2D"));
		gLineToolsChart.setLongitudeFontColor(Color.WHITE);
		gLineToolsChart.setLatitudeFontColor(Color.WHITE);

		gLineToolsChart.setBackgroundColor(Color.TRANSPARENT);

		crossLineChart.setAxisYTitleQuadrantWidth(80);
		gLineToolsChart.setAxisXTitleQuadrantHeight(20);
	}

	private void initGoldLineChart() {

		goldLineChart.setLatitudeColor(Color.parseColor("#2D2D2D"));
		goldLineChart.setLongitudeColor(Color.parseColor("#2D2D2D"));
		goldLineChart.setBorderColor(Color.parseColor("#2D2D2D"));
		goldLineChart.setLongitudeFontColor(Color.WHITE);
		goldLineChart.setLatitudeFontColor(Color.WHITE);

		goldLineChart.setBackgroundColor(Color.TRANSPARENT);

		crossLineChart.setAxisYTitleQuadrantWidth(80);
		goldLineChart.setAxisXTitleQuadrantHeight(20);

		goldLineChart.setmUnionCandleStickChart(unionCandleStickChart);
	}

	private List<LineEntity<KLineEntity>> toplines;

	private List<LineEntity<KLineEntity>> buttonLines;

	private List<KLineEntity> buttonStickDatas;

	private List<KLineEntity> kLineEntities;

	private View mNormMenuView;
	private PopupWindow popupNormWindow;

	public void showNormPopupWindow() {
		if (mNormMenuView == null) {
			mNormMenuView = LayoutInflater.from(getActivity()).inflate(
					R.layout.popup_norm, null);
			initNormPopupView(mNormMenuView);
		}
		if (popupNormWindow == null) {
			popupNormWindow = Utils.newBasicPopupWindow(getActivity());
			popupNormWindow.setContentView(mNormMenuView);

		}
		popupNormWindow.showAsDropDown(op_zhi);
	}

	private AppSetting setting;

	public void initNormPopupView(View mNormMenuView) {
		if (mNormMenuView == null)
			return;

		setting = AppSetting.getInstance(getActivity());

		TextView normBoll = (TextView) mNormMenuView
				.findViewById(R.id.popup_norm_boll);
		TextView normEma = (TextView) mNormMenuView
				.findViewById(R.id.popup_norm_ema);
		TextView normKdj = (TextView) mNormMenuView
				.findViewById(R.id.popup_norm_kdj);
		TextView normMacd = (TextView) mNormMenuView
				.findViewById(R.id.popup_norm_macd);
		TextView normRsi = (TextView) mNormMenuView
				.findViewById(R.id.popup_norm_rsi);
		TextView normSma = (TextView) mNormMenuView
				.findViewById(R.id.popup_norm_sma);

		normBoll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (lastTopNorm == ParamConfig.NORM_BOLL)
					return;
				toplines = ParseUtils.boll(kLineEntities,
						setting.getBoolTParam(), setting.getBoolKParam());
				unionCandleStickChart.setLinesData(toplines);
				unionCandleStickChart.setTopNormType(ParamConfig.NORM_BOLL);
				lastTopNorm = ParamConfig.NORM_BOLL;
				
				if(popupNormWindow != null)
					popupNormWindow.dismiss();

			}
		});
		normEma.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (lastTopNorm == ParamConfig.NORM_EMA)
					return;
				toplines = new ArrayList<LineEntity<KLineEntity>>();
				toplines.add(ParseUtils.getEMALineData(kLineEntities,
						setting.getEmaParam()));
				unionCandleStickChart.setLinesData(toplines);
				unionCandleStickChart.setTopNormType(ParamConfig.NORM_EMA);
				lastTopNorm = ParamConfig.NORM_EMA;
				
				if(popupNormWindow != null)
					popupNormWindow.dismiss();
				
			}
		});
		normKdj.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (lastButtonNorm == ParamConfig.NORM_KDJ)
					return;
				buttonLines = ParseUtils.getKDJLinesDatas(kLineEntities,
						setting.getKdjKParam());
				unionCandleStickChart.setButtomNormLineDatas(buttonLines);
				unionCandleStickChart.setButtomNormType(ParamConfig.NORM_KDJ);
				lastButtonNorm = ParamConfig.NORM_KDJ;
				
				if(popupNormWindow != null)
					popupNormWindow.dismiss();
			}
		});
		normMacd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (lastButtonNorm == ParamConfig.NORM_MACD)
					return;
				buttonLines = ParseUtils.getMacdLineDatas(kLineEntities,
						setting.getMacdTParam1(), setting.getMacdTParam2(),
						setting.getMacdKParam());
				buttonStickDatas = ParseUtils.getMaceStickDatas(kLineEntities,
						setting.getMacdTParam1(), setting.getMacdTParam2(),
						setting.getMacdKParam());
				unionCandleStickChart.setButtomStickData(buttonStickDatas);
				unionCandleStickChart.setButtomNormLineDatas(buttonLines);
				unionCandleStickChart.setButtomNormType(ParamConfig.NORM_MACD);
				lastButtonNorm = ParamConfig.NORM_MACD;
				
				if(popupNormWindow != null)
					popupNormWindow.dismiss();
			}
		});
		normRsi.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (lastButtonNorm == ParamConfig.NORM_RSI)
					return;
				buttonLines = ParseUtils.getRsiLineDatas(kLineEntities,
						setting.getRsiParam1(), setting.getRsiParam2(),
						setting.getRsiParam3());
				unionCandleStickChart.setButtomNormLineDatas(buttonLines);
				unionCandleStickChart.setButtomNormType(ParamConfig.NORM_RSI);
				lastButtonNorm = ParamConfig.NORM_RSI;
				
				if(popupNormWindow != null)
					popupNormWindow.dismiss();
			}
		});
		normSma.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (lastTopNorm == ParamConfig.NORM_SMA)
					return;
				toplines = ParseUtils.getSimpleMaLineData(kLineEntities,
						setting.getSmaParam1(), setting.getSmaParam2(),
						setting.getSmaParam3());
				unionCandleStickChart.setLinesData(toplines);
				unionCandleStickChart.setTopNormType(ParamConfig.NORM_SMA);
				lastTopNorm = ParamConfig.NORM_SMA;
				
				if(popupNormWindow != null)
					popupNormWindow.dismiss();
			}
		});
	}

	/**
	 * 横屏平时的指标 下拉view
	 */
	private View mCrossLineMenuView;

	private PopupWindow popupCrossLineWindow;

	public void showCrossLinePopupWindow() {
		if (mCrossLineMenuView == null) {
			mCrossLineMenuView = LayoutInflater.from(getActivity()).inflate(
					R.layout.popup_cross_line, null);

			initCrossLinePopupView(mCrossLineMenuView);
		}

		if (popupCrossLineWindow == null) {
			popupCrossLineWindow = Utils.newBasicPopupWindow(getActivity());
			popupCrossLineWindow.setContentView(mCrossLineMenuView);

		}
		popupCrossLineWindow.showAsDropDown(oritationGline);
	}

	public void initCrossLinePopupView(View mCrossLineMenuView) {
		if (mCrossLineMenuView == null)
			return;

		RadioGroup corssLineType = (RadioGroup) mCrossLineMenuView
				.findViewById(R.id.rg_popup_gline);

		final RadioButton gline = (RadioButton) mCrossLineMenuView
				.findViewById(R.id.popup_gline);

		final RadioButton highGline = (RadioButton) mCrossLineMenuView
				.findViewById(R.id.popup_high_gline);

		final RadioButton lowGline = (RadioButton) mCrossLineMenuView
				.findViewById(R.id.popup_low_gline);

		final RadioButton hideGline = (RadioButton) mCrossLineMenuView
				.findViewById(R.id.popup_hide_gline);

		corssLineType
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						switch (checkedId) {
						case R.id.popup_gline:
							crossLineChart.setVisibility(View.VISIBLE);
							crossLineChart.setDrawOffset(unionCandleStickChart
									.getDrawOffset());
							crossLineChart.setStickWidth(unionCandleStickChart
									.getStickWidth());
							crossLineChart
									.setGlineType(CrossLineChart.NORMAL_CROSS_LINE_TYPE);
							gline.setChecked(true);
							break;
						case R.id.popup_high_gline:
							crossLineChart.setVisibility(View.VISIBLE);
							crossLineChart.setDrawOffset(unionCandleStickChart
									.getDrawOffset());
							crossLineChart.setStickWidth(unionCandleStickChart
									.getStickWidth());
							crossLineChart
									.setGlineType(CrossLineChart.HIGH_CROSS_LINE_TYPE);
							highGline.setChecked(true);
							break;
						case R.id.popup_low_gline:
							crossLineChart.setVisibility(View.VISIBLE);
							crossLineChart.setDrawOffset(unionCandleStickChart
									.getDrawOffset());
							crossLineChart.setStickWidth(unionCandleStickChart
									.getStickWidth());
							crossLineChart
									.setGlineType(CrossLineChart.LOW_CROSS_LINE_TYPE);
							lowGline.setChecked(true);
							break;
						case R.id.popup_hide_gline:
							crossLineChart.setVisibility(View.GONE);
							break;
						default:
							break;
						}
					}
				});

	}

	/**
	 * 横屏平时的指标 下拉view
	 */
	private View mGlineMenuView;

	private PopupWindow popupGlineWindow;

	public void showGlinePopupWindow() {
		if (mGlineMenuView == null) {
			mGlineMenuView = LayoutInflater.from(getActivity()).inflate(
					R.layout.popup_gline, null);
			initGlinePopupView(mGlineMenuView);
		}

		if (popupGlineWindow == null) {
			popupGlineWindow = Utils.newBasicPopupWindow(getActivity());
			popupGlineWindow.setContentView(mGlineMenuView);

		}
		popupGlineWindow.showAsDropDown(op_hua);
	}

	private String currentGlineType;

	public void initGlinePopupView(View mGlineMenuView) {
		if (mGlineMenuView == null)
			return;

		TextView freeHandLine = (TextView) mGlineMenuView
				.findViewById(R.id.popup_free_hand_line);
		TextView goldLine = (TextView) mGlineMenuView
				.findViewById(R.id.popup_gole_line);
		TextView trendLine = (TextView) mGlineMenuView
				.findViewById(R.id.popup_trend_line);

		freeHandLine.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentGlineType = GLineToolsChart.STRAIGHT_LINE_TYPE;
				gLineToolsChart
						.setmUnionCandleStickChart(unionCandleStickChart);
				gLineToolsChart.setVisibility(View.VISIBLE);
				goldLineChart.setVisibility(View.GONE);
				llOritationSaveGline.setVisibility(View.VISIBLE);
				heandViewOritation.setVisibility(View.GONE);

			}
		});

		trendLine.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentGlineType = GLineToolsChart.GOLD_LINE_TYPE;
				goldLineChart.setVisibility(View.VISIBLE);
				goldLineChart.setShowHorLine(false);

				gLineToolsChart.setVisibility(View.GONE);
				llOritationSaveGline.setVisibility(View.VISIBLE);
				heandViewOritation.setVisibility(View.GONE);
			}
		});

		goldLine.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentGlineType = GLineToolsChart.GOLD_LINE_TYPE;
				goldLineChart.setVisibility(View.VISIBLE);
				goldLineChart.setShowHorLine(true);

				gLineToolsChart.setVisibility(View.GONE);
				llOritationSaveGline.setVisibility(View.VISIBLE);
				heandViewOritation.setVisibility(View.GONE);
			}
		});

	}

	/**
	 * rt全屏
	 * 
	 * @param v
	 */
	public void turnOriClick(View v) {
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		if (screenHeight > screenWidth) {
			try {
				getActivity().setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			} catch (Exception e) {
			}
		} else if (screenWidth > screenHeight) {
			try {
				getActivity().setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			} catch (Exception e) {
			}
		}
	}

	class GetKlineDataTask extends AsyncTask<String, Void, String> {

		private List<KLineEntity> list;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			KlineService service = new KlineService(getActivity());
			try {
				list = service.getKlineByinterval(params[0], params[1],
						params[2]);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(getActivity(), null, e);
				return "error";
			}

			kLineEntities = new ArrayList<KLineEntity>();
			if (list != null && list.size() > 20) {
				for (int i = list.size() - 1; i >= 0; i--) {
					kLineEntities.add(list.get(i)); // 日期排序
				}
				List<KLineEntity> tempList = list.subList(0, list.size() - 20);
				stickDatas = new ArrayList<KLineEntity>() ;
				stickDatas.add(new KLineEntity()) ;
				stickDatas.add(new KLineEntity()) ;
				stickDatas.add(new KLineEntity()) ;
				stickDatas.add(new KLineEntity()) ;
				stickDatas.add(new KLineEntity()) ;
				
				for (int i = 0; i < tempList.size(); i++) {
					stickDatas.add(tempList.get(i)) ;
				}
				return "success";
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if ("success".equals(result)) {
				initUnionStickChart();
				initCrossLineChart();
				initGlineToolsChart();
				initGoldLineChart();
			} else if ("error".equals(result)) {

			} else {
				Toast.makeText(getActivity(), "获取数据失败", 1000).show();
			}
		}

	}

}
