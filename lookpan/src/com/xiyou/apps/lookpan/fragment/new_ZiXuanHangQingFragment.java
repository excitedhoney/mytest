package com.xiyou.apps.lookpan.fragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.PullToRefreshListViewFragment;
import com.xiyou.apps.lookpan.db.BaseDataBase;
import com.xiyou.apps.lookpan.fragment.showactivity.ShowHangQingActivity;
import com.xiyou.apps.lookpan.model.HangQingDBBean;
import com.xiyou.apps.lookpan.model.Hangqing_WaiHuiBean;
import com.xiyou.apps.lookpan.utils.JsonUtil;
import com.xiyou.apps.lookpan.utils.Util;

public class new_ZiXuanHangQingFragment extends
		PullToRefreshListViewFragment<Hangqing_WaiHuiBean> {
	private static ArrayList<HangQingDBBean> mlist_DB;
	private Timer mtimer;
	private TimerTask mTimeTask;

	@Override
	public void onResume() {
		super.onResume();
		setAutoRefresh();
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
			mtimer.cancel(); //
		}
		mtimer = null; //
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopRefresh();
	}

	@Override
	protected void onLoadData(boolean isFirstPage) {

		new AsyncTask<Object, Object, ArrayList<Hangqing_WaiHuiBean>>() {
			@Override
			protected ArrayList<Hangqing_WaiHuiBean> doInBackground(
					Object... params) {
				return JsonUtil
						.getHangQing_WaihHuiBeanList2JSON_new("http://api.markets.wallstreetcn.com/v1/quotes.json");
			}

			@Override
			protected void onPostExecute(ArrayList<Hangqing_WaiHuiBean> result) {
				super.onPostExecute(result);
				if (null != result) {
					Log.i("tag", "11111111");
					mAdapter.clear();
					mAdapter.addAll(getZiXuan(getData(result)));
					mListView.setAdapter(mAdapter);
					mPullToRefreshLayout.setRefreshComplete();
				} else {
					Util.toastTips(getActivity(), "网络不稳或者异常");
					mPullToRefreshLayout.setRefreshComplete();
				}
			}

		}.execute();

	}

	public ArrayList<Hangqing_WaiHuiBean> getZiXuan(
			ArrayList<Hangqing_WaiHuiBean> result) {
		ArrayList<String> list;
		ArrayList<Hangqing_WaiHuiBean> newList = new ArrayList<Hangqing_WaiHuiBean>();
		try {
			BaseDataBase.getInstance(getActivity()).open();
			list = BaseDataBase.getInstance(getActivity())
					.selectZiXuanChannel();
			if (null == list || list.size() < 1) {
				ArrayList<String> strList = new ArrayList<String>();
				strList.add("十年期美债");
				strList.add("黄金");
				strList.add("布伦特石油");
				strList.add("欧元兑美元");
				strList.add("美元兑日元");
				strList.add("上证指数");
				strList.add("标普500");

				BaseDataBase.getInstance(getActivity()).inserZiXuanChannel(
						strList);

			}
			list = BaseDataBase.getInstance(getActivity())
					.selectZiXuanChannel();
			for (Hangqing_WaiHuiBean hangqing : result) {
				for (String str : list) {
					if (hangqing.getTitle().equals(str)) {
						newList.add(hangqing);
					}
				}
			}
			return newList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDataBase.getInstance(getActivity()).close();
		}

		return newList;

	}

	/**
	 * �������
	 * 
	 * @param ArrayList
	 *            <Hangqing_WaiHuiBean>
	 * @return ArrayList<Hangqing_WaiHuiBean>
	 */
	public ArrayList<Hangqing_WaiHuiBean> getData(
			ArrayList<Hangqing_WaiHuiBean> list) {
		ArrayList<Hangqing_WaiHuiBean> newList = list;
		if (null == mlist_DB) {
		} else {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getSymbol().equals("US10Year")) {
					newList.get(i).setTitle("美国10年期");
				}

				for (int j = 0; j < mlist_DB.size(); j++) {
					if (list.get(i).getSymbol()
							.equals(mlist_DB.get(j).getSymbol())) {
						if (mlist_DB.get(j).getIsNeed().equals("0")) {
							newList.remove(i);
						} else {
							newList.get(i).setTitle(mlist_DB.get(j).getTitle());
						}
					}
				}
			}
		}
		return newList;

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

}