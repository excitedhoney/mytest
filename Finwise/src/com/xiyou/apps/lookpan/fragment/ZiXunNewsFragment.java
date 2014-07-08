package com.xiyou.apps.lookpan.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.PullToRefreshListViewFragment;
import com.xiyou.apps.lookpan.fragment.showactivity.ShowLiveActivity;
import com.xiyou.apps.lookpan.model.LivesBean;
import com.xiyou.apps.lookpan.utils.JsonUtil;
import com.xiyou.apps.lookpan.utils.Util;

public class ZiXunNewsFragment extends PullToRefreshListViewFragment<LivesBean> {
	public static final String TAG = "ZiXunNews";
	private static final String URL = "http://api.wallstreetcn.com/apiv1/livenews-list-v2.json";
	// private static final String COUNT_URL =
	// "http://api.wallstreetcn.com/apiv1/livenews-count?nid=";
	SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm",
			Locale.getDefault());
	ArrayList<LivesBean> mLivesBeans;

	public static Fragment newInstance(FragmentManager fm) {
		Fragment fragment = fm.findFragmentByTag(TAG);
		if (fragment == null) {
			fragment = new ZiXunNewsFragment();
		}
		return fragment;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), ShowLiveActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		LivesBean mLivesBean = (LivesBean) parent.getAdapter()
				.getItem(position);
		Bundle bundle = new Bundle();
		Date compare = new Date(
				Long.parseLong(mLivesBean.getNode_created()) * 1000l);
		bundle.putString("node_icon", mLivesBean.getNode_icon() + "");
		bundle.putString("time", time.format(compare) + "");
		bundle.putString("content", mLivesBean.getNode_content() + "");
		bundle.putString("laiyuan", mLivesBean.getSource() + "");
		bundle.putString("lianjie", mLivesBean.getSourcelink() + "");
		intent.putExtras(bundle);
		startActivity(intent);

	}

	@Override
	protected void onLoadData(final boolean isFirstPage) {

		mLoadingProgress.setVisibility(View.VISIBLE);
		mLoadErrorView.setVisibility(View.GONE);
		mListView.setVisibility(View.GONE);

		new AsyncTask<Object, Object, ArrayList<LivesBean>>() {
			@Override
			protected ArrayList<LivesBean> doInBackground(Object... params) {
				mLivesBeans = JsonUtil.getLiveBeanList2JSON_new(URL);
				return mLivesBeans;
			}

			@Override
			protected void onPostExecute(ArrayList<LivesBean> result) {
				mLoadingProgress.setVisibility(View.GONE);
				if (null != result) {
					if (isFirstPage) {
						mAdapter.clear();
					}
					mListView.setVisibility(View.VISIBLE);
					mAdapter.addAll(result);
					mListView.setAdapter(mAdapter);
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
	protected View onGetItemView(LivesBean item, int position,
			View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.adapter_item_live,
					parent, false);
			holder = new ViewHolder();
			holder.live_time = (TextView) convertView
					.findViewById(R.id.live_time);
			holder.live_icon = (ImageView) convertView
					.findViewById(R.id.node_icon);
			holder.live_content = (TextView) convertView
					.findViewById(R.id.live_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (item.getNode_icon().equals("折线")) {
			holder.live_icon.setImageResource(R.drawable.live_zhexian);
		} else if (item.getNode_icon().equals("柱状")) {
			holder.live_icon.setImageResource(R.drawable.cheng);
		} else if (item.getNode_icon().equals("提醒")) {
			holder.live_icon.setImageResource(R.drawable.live_tixing);
		} else if (item.getNode_icon().equals("传言")) {
			holder.live_icon.setImageResource(R.drawable.hong);
		} else if (item.getNode_icon().equals("警告")) {
			holder.live_icon.setImageResource(R.drawable.live_zhongbang);
		} else {
			holder.live_icon.setImageResource(R.drawable.lan);
		}
		if (item.getNode_color().equals("红色")) {

			holder.live_content.setTextColor(getActivity().getResources()
					.getColor(R.color.red));
		} else {
			holder.live_content.setTextColor(getActivity().getResources()
					.getColor(R.color.black));
		}
		if (item.getNode_format().equals("加粗")) {
			holder.live_content.setTypeface(Typeface
					.defaultFromStyle(Typeface.BOLD));
		} else {
			holder.live_content.setTypeface(Typeface
					.defaultFromStyle(Typeface.NORMAL));
		}

		holder.live_content
				.setText(Util.filterHtml_new(item.getNode_content()));
		Date compare = new Date(Long.parseLong(item.getNode_created()) * 1000l);
		holder.live_time.setText(time.format(compare));

		return convertView;
	}

	private static class ViewHolder {
		ImageView live_icon;
		TextView live_time;
		TextView live_content;
	}

}
