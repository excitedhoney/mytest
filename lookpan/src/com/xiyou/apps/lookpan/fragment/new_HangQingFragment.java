package com.xiyou.apps.lookpan.fragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.PullToRefreshListViewFragment;
import com.xiyou.apps.lookpan.fragment.showactivity.ShowHangQingActivity;
import com.xiyou.apps.lookpan.model.Hangqing_WaiHuiBean;
import com.xiyou.apps.lookpan.utils.JsonUtil;
import com.xiyou.apps.lookpan.utils.Util;

public class new_HangQingFragment extends
		PullToRefreshListViewFragment<Hangqing_WaiHuiBean> {

	protected String mUri;
	private Timer mtimer;
	private TimerTask mTimeTask;

	public static new_HangQingFragment newInstance(String uri) {
		new_HangQingFragment mFragment = new new_HangQingFragment();
		Bundle args = new Bundle();
		args.putString("uri", uri);
		mFragment.setArguments(args);
		return mFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		mUri = args.getString("uri");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.viewpager_1, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setAutoRefresh();
	}

	protected void setAutoRefresh() {

		if (mtimer == null) {
			mtimer = new Timer();

			mTimeTask = new TimerTask() {
				@Override
				public void run() {
					onLoadData(true);
				}
			};
			mtimer.schedule(mTimeTask, 0, 5000);
		}
	}

	public void stopRefresh() {
		if (mtimer != null) {
			mtimer.cancel(); // �ر��Զ�ˢ��
		}
		mtimer = null; // ��ٱ��������
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopRefresh();
	}

	@Override
	public void onResume() {
		super.onResume();
		setAutoRefresh();
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

	@Override
	protected void onLoadData(boolean isFirstPage) {

		new AsyncTask<Object, Object, ArrayList<Hangqing_WaiHuiBean>>() {
			@Override
			protected ArrayList<Hangqing_WaiHuiBean> doInBackground(
					Object... params) {
				return JsonUtil.getHangQing_WaihHuiBeanList2JSON_new(mUri);
			}

			@Override
			protected void onPostExecute(ArrayList<Hangqing_WaiHuiBean> result) {
				super.onPostExecute(result);
				if (null != result) {
					mAdapter.clear();
					mAdapter.addAll(result);
					mListView.setAdapter(mAdapter);
					mPullToRefreshLayout.setRefreshComplete();
				} else {
					Util.toastTips(getActivity(), "网络延时或者异常");
					mPullToRefreshLayout.setRefreshComplete();
				}
			}

		}.execute();

	}

	@Override
	protected View onGetItemView(Hangqing_WaiHuiBean item, int position,
			View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(
					R.layout.adapter_item_hangqing, parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name123);
			holder.price = (TextView) convertView.findViewById(R.id.shouyilv);
			holder.shou = (TextView) convertView.findViewById(R.id.textView3);
			holder.kai = (TextView) convertView.findViewById(R.id.textView5);
			holder.gao = (TextView) convertView.findViewById(R.id.textView7);
			holder.di = (TextView) convertView.findViewById(R.id.textView9);
			holder.fudu = (TextView) convertView.findViewById(R.id.textView2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(item.getTitle());
		holder.price.setText(item.getPrice());
		holder.shou.setText(item.getPrevClose());
		holder.kai.setText(item.getOpen());
		holder.gao.setText(item.getDayRangeHigh());
		holder.di.setText(item.getDayRangeLow());
		holder.fudu.setText(item.getFudu() + "/" + item.getFudu_baifenbi());

		if (0 < Float.valueOf(item.getFudu())) {
			holder.price.setTextColor(getActivity().getResources().getColor(
					R.color.kanpan_red));
			holder.fudu.setTextColor(getActivity().getResources().getColor(
					R.color.kanpan_red));
		} else {
			holder.price.setTextColor(getActivity().getResources().getColor(
					R.color.kanpan_green));
			holder.fudu.setTextColor(getActivity().getResources().getColor(
					R.color.kanpan_green));
		}

		return convertView;
	}

	private static class ViewHolder {
		TextView name;
		TextView price;
		TextView fudu;
		TextView shou;
		TextView kai;
		TextView gao;
		TextView di;

	}
}