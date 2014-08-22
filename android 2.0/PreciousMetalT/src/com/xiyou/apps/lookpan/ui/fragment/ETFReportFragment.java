package com.xiyou.apps.lookpan.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cn.precious.metal.entity.ETFReport;
import cn.precious.metal.services.ETFReportService;
import cn.precious.metal.view.entity.DateValueEntity;
import cn.precious.metal.view.entity.LineEntity;
import cn.precious.metal.view.view.GridChart;
import cn.precious.metal.view.view.SlipAreaChart;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.adapter.ETFReportAdapter;
import com.xiyou.apps.lookpan.base.BaseFragment;
import com.xiyou.apps.lookpan.pulltorefresh.PullToRefreshListView;

/**
 * 黄金 白银ETF报告
 * 
 * @author mac
 * 
 */

public class ETFReportFragment extends BaseFragment {

	public static final String ETF_TYPE_GOLDE = "ETF_TYPE_GOLDE";
	public static final String ETF_TYPE_SLIVER = "ETF_TYPE_SLIVER";

	private PullToRefreshListView mPullToRefreshListView;

	private ListView listView;

	private List<ETFReport> reports = new ArrayList<ETFReport>();

	private ETFReportAdapter reportAdapter;

	private List<DateValueEntity> mDateValueEntities;

	private SlipAreaChart mAreaChart;

	public static ETFReportFragment newInstance(String type) {
		ETFReportFragment fragment = new ETFReportFragment();
		Bundle b = new Bundle();
		b.putString("type", type);
		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_etf_report, null);
		initView(view);
		new ETFReportTask().execute("");
		return view;
	}

	public void initView(View view) {

		mPullToRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.listView);
		mPullToRefreshListView.setPullRefreshEnabled(false);
		mPullToRefreshListView.setPullLoadEnabled(false);
		listView = mPullToRefreshListView.getRefreshableView();
		reportAdapter = new ETFReportAdapter(getActivity(), reports);
		listView.setAdapter(reportAdapter);
		mAreaChart = (SlipAreaChart) view.findViewById(R.id.slip_area);
	}

	private void initSlipAreaChart() {
		List<LineEntity<DateValueEntity>> lines = new ArrayList<LineEntity<DateValueEntity>>();

		// 计算5日均线
		LineEntity<DateValueEntity> MA5 = new LineEntity<DateValueEntity>();
		MA5.setTitle("HIGH");
		MA5.setLineColor(Color.WHITE);
		MA5.setLineData(mDateValueEntities);
		lines.add(MA5);

		mAreaChart.setAxisXColor(Color.LTGRAY);
		mAreaChart.setAxisYColor(Color.LTGRAY);
		mAreaChart.setBorderColor(Color.LTGRAY);
		mAreaChart.setLongitudeFontSize(14);
		mAreaChart.setLongitudeFontColor(Color.WHITE);
		mAreaChart.setLatitudeColor(Color.GRAY);
		mAreaChart.setLatitudeFontColor(Color.WHITE);
		mAreaChart.setLongitudeColor(Color.GRAY);
		mAreaChart.setDisplayLongitudeTitle(true);
		mAreaChart.setDisplayLatitudeTitle(true);
		mAreaChart.setDisplayLatitude(true);
		mAreaChart.setDisplayLongitude(true);
		mAreaChart.setDataQuadrantPaddingTop(0);
		mAreaChart.setDataQuadrantPaddingBottom(0);
		mAreaChart.setDataQuadrantPaddingLeft(0);
		mAreaChart.setDataQuadrantPaddingRight(0);
		mAreaChart.setAxisYTitleQuadrantWidth(40);
		mAreaChart.setBackgroundColor(Color.BLACK);
		mAreaChart.setAxisXTitleQuadrantHeight(20);
		mAreaChart.setAxisXPosition(GridChart.AXIS_X_POSITION_BOTTOM);
		mAreaChart.setAxisYPosition(GridChart.AXIS_Y_POSITION_LEFT);
		mAreaChart.setLinesData(lines);
	}

	class ETFReportTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			ETFReportService reportService = new ETFReportService(getActivity());

			try {
				if (ETF_TYPE_GOLDE
						.equals(getArguments() != null ? getArguments()
								.getString("type") : "")) {
					reports = reportService.goldReposts();
				} else {
					reports = reportService.silverReposts();
				}

				if (reports != null && !reports.isEmpty()) {
					return SUCCESS;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ERROR;
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (SUCCESS.equalsIgnoreCase(result)) {
				reportAdapter.setItems(reports);
				mDateValueEntities = new ArrayList<DateValueEntity>();
				for (int i = 0; i < reports.size(); i++) {
					mDateValueEntities.add(new DateValueEntity(reports.get(i)
							.getVal(), reports.get(i).getTime()));
				}
				initSlipAreaChart();
			}
		}
	}

}
