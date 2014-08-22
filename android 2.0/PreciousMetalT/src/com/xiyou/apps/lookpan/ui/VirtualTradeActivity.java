package com.xiyou.apps.lookpan.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.dao.OptionalDao;
import cn.precious.metal.dao.VirtualTradeDao;
import cn.precious.metal.entity.Optional;
import cn.precious.metal.entity.VirtualTrade;
import cn.precious.metal.utils.Utils;
import cn.precious.metal.view.entity.DateValueEntity;
import cn.precious.metal.view.entity.LineEntity;
import cn.precious.metal.view.view.GridChart;
import cn.precious.metal.view.view.TradeProfitView;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.adapter.TradeHostoryAdapter;
import com.xiyou.apps.lookpan.adapter.VirtualTradeAdapter;
import com.xiyou.apps.lookpan.base.BaseActivity;

/**
 * 虚拟交易
 * 
 * @author mac
 * 
 */
public class VirtualTradeActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout llOpenPosition, llCloseOut;

	private LinearLayout opening, close;

	private ListView openingListview, hostoryListView;

	private ImageView openArrow, closeArrow;

	private TextView tvTotaRate, tvTotalValue, tvBalance, tvOopeningValue,
			tvOpeningNumber;

	private VirtualTradeAdapter tradeAdapter;

	private TradeHostoryAdapter hostoryAdapter;

	private List<VirtualTrade> openingTrades;

	private List<VirtualTrade> hostoryTrades;

	private VirtualTradeDao tradeDao;

	private OptionalDao optionalDao;

	private boolean isShowOpen = false;
	private boolean isShowHostory = false;

	private TradeProfitView profitView;

	private TextView tip;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_virtual_trade);
		tradeDao = new VirtualTradeDao(this);
		optionalDao = new OptionalDao(this);
		initView();
		initListen();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new InifAssetTask().execute("");
	}

	public void back(View view) {
		finish();
	}

	public void initView() {
		llOpenPosition = (LinearLayout) findViewById(R.id.ll_open_position);
		llCloseOut = (LinearLayout) findViewById(R.id.ll_close_out);

		opening = (LinearLayout) findViewById(R.id.ll_opening);
		openArrow = (ImageView) findViewById(R.id.iv_open_arraw);
		close = (LinearLayout) findViewById(R.id.ll_close);
		closeArrow = (ImageView) findViewById(R.id.iv_close_arrow);

		openingListview = (ListView) findViewById(R.id.openingListview);
		hostoryListView = (ListView) findViewById(R.id.closeListview);

		tvTotaRate = (TextView) findViewById(R.id.tv_total_rate);
		tvTotalValue = (TextView) findViewById(R.id.tv_total_value);
		tvBalance = (TextView) findViewById(R.id.tv_balance);
		tvOopeningValue = (TextView) findViewById(R.id.tv_opening_value);
		tvOpeningNumber = (TextView) findViewById(R.id.opening_num);

		tradeAdapter = new VirtualTradeAdapter(VirtualTradeActivity.this,
				openingTrades);
		openingListview.setAdapter(tradeAdapter);

		hostoryAdapter = new TradeHostoryAdapter(VirtualTradeActivity.this,
				hostoryTrades);
		hostoryListView.setAdapter(hostoryAdapter);

		profitView = (TradeProfitView) findViewById(R.id.profit_view);
		tip = (TextView) findViewById(R.id.tip);
	}

	public void initListen() {
		llOpenPosition.setOnClickListener(this);
		llCloseOut.setOnClickListener(this);

		opening.setOnClickListener(this);
		close.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_open_position:
			Intent intentOpen = new Intent(VirtualTradeActivity.this,
					OpenPositionActivity.class);
			startActivity(intentOpen);
			break;
		case R.id.ll_close_out:
			Intent intentClose = new Intent(VirtualTradeActivity.this,
					CloseOutActivity.class);
			startActivity(intentClose);
			break;
		case R.id.ll_opening:
			openingTrades = tradeDao.queryOpeningTrades();
			if (isShowOpen) {
				isShowOpen = false;
				openArrow.setBackgroundResource(R.drawable.arrow_down);
				openingListview.setVisibility(View.GONE);
			} else {
				isShowOpen = true;
				openArrow.setBackgroundResource(R.drawable.arrow_up);
				openingListview.setVisibility(View.VISIBLE);
			}
			tradeAdapter.setItems(openingTrades);
			Utils.setListViewHeightBasedOnChildren(VirtualTradeActivity.this,
					openingListview, true);
			break;
		case R.id.ll_close:
			hostoryTrades = tradeDao.queryTrades();
			if (isShowHostory) {
				isShowHostory = false;
				closeArrow.setBackgroundResource(R.drawable.arrow_down);
				hostoryListView.setVisibility(View.GONE);
			} else {
				isShowHostory = true;
				closeArrow.setBackgroundResource(R.drawable.arrow_up);
				hostoryListView.setVisibility(View.VISIBLE);
			}
			hostoryAdapter.setItems(hostoryTrades);
			Utils.setListViewHeightBasedOnChildren(VirtualTradeActivity.this,
					hostoryListView, false);
			break;
		default:
			break;
		}
	}

	class InifAssetTask extends AsyncTask<String, Void, String> {

		float sumProfit;

		float openingValue;

		private List<VirtualTrade> list = new ArrayList<VirtualTrade>();

		List<DateValueEntity> valueList = new ArrayList<DateValueEntity>();

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			list = tradeDao.queryOpeningTrades();
			if (list != null && !list.isEmpty()) {
				for (VirtualTrade virtualTrade : list) {
					Optional optional = optionalDao
							.queryOptionalsBySymbol(virtualTrade.getTreaty());
					if (virtualTrade.getOrientation() == 1) {
						openingValue += Float.parseFloat(optional.getBuyone())
								* virtualTrade.getCloseVolume();
					} else {
						openingValue += Float.parseFloat(optional.getSellone())
								* virtualTrade.getCloseVolume();
					}

				}
			}

			List<VirtualTrade> closeList = tradeDao.queryCloseTrades();

			if (closeList != null && !closeList.isEmpty()) {
				float value = 0;
				DateValueEntity valueEntity;
				for (int i = 0; i < closeList.size(); i++) {
					VirtualTrade trade = closeList.get(i);
					if (trade.getOrientation() == 1) {
						value = Utils.getFloatDecimal((trade
								.getTransactionPrice() - trade.getOpenPrice())
								* trade.getCloseVolume());
						sumProfit += value;
					} else {
						value = Utils
								.getFloatDecimal((trade.getOpenPrice() - trade
										.getTransactionPrice())
										* trade.getCloseVolume());
						sumProfit += value;
					}
					valueEntity = new DateValueEntity(value,
							trade.getCreateTime());
					valueList.add(valueEntity);
				}

				return "success";
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			tvOpeningNumber.setText(Html.fromHtml("持仓数("
					+ "<font text-color=\"blue\">" + "" + list.size()
					+ "</font>" + ")"));
			if (sumProfit >= 0) {
				tvTotaRate.setTextColor(Color.RED);
				tvTotaRate.setText("+" + (sumProfit / 10000) * 100 + "%");
			} else {
				tvTotaRate.setTextColor(Color.GREEN);
				tvTotaRate.setText(getBigDecimal((sumProfit / 10000) * 100)
						+ "%");
			}

			tvTotalValue
					.setText(""
							+ Utils.getFloatDecimal((AppSetting.getInstance(
									VirtualTradeActivity.this)
									.getVirtualBalance() + openingValue)));
			tvBalance.setText(Utils.getFloatDecimal(AppSetting.getInstance(
					VirtualTradeActivity.this).getVirtualBalance())
					+ "");

			tvOopeningValue.setText(Utils.getFloatDecimal(openingValue) + "");

			tip.setVisibility(View.GONE);

			if ("success".equals(result)) {
				initChatData(valueList);
			} else {
				tip.setVisibility(View.VISIBLE);
			}

		}

		private String getBigDecimal(double targer) {
			BigDecimal bigDecimal = new BigDecimal(targer);
			return String.valueOf(bigDecimal.setScale(4,
					BigDecimal.ROUND_HALF_UP).doubleValue());
		}
	}

	public void initChatData(List<DateValueEntity> list) {
		if (list == null || list.isEmpty())
			return;
		LineEntity<DateValueEntity> lineEntity = new LineEntity<DateValueEntity>();
		lineEntity.setLineData(list);

		List<LineEntity<DateValueEntity>> linesData = new ArrayList<LineEntity<DateValueEntity>>();
		linesData.add(lineEntity);
		profitView.setLinesData(linesData);
		profitView.setLatitudeFontSize(4);
		profitView.setLongitudeFontSize(2);
		profitView.setBorderColor(Color.LTGRAY);
		profitView.setLongitudeFontSize(22);
		profitView.setLatitudeFontSize(22);
		profitView.setLongitudeFontColor(Color.WHITE);
		profitView.setLatitudeColor(Color.GRAY);
		profitView.setLatitudeFontColor(Color.WHITE);
		profitView.setLongitudeColor(Color.GRAY);
		profitView.setDisplayLongitudeTitle(true);
		profitView.setDisplayLatitudeTitle(true);
		profitView.setDisplayLatitude(true);
		profitView.setDisplayLongitude(true);
		profitView.setAxisYTitleQuadrantWidth(70);
		profitView.setAxisXTitleQuadrantHeight(20);
		profitView.setAxisYPosition(GridChart.AXIS_Y_POSITION_LEFT);
	}
}
