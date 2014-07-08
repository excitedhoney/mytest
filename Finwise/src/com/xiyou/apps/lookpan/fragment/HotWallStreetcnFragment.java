package com.xiyou.apps.lookpan.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.api.API;
import com.xiyou.apps.lookpan.base.PullToRefreshListViewFragment;
import com.xiyou.apps.lookpan.fragment.showactivity.ShowNewsActivity;
import com.xiyou.apps.lookpan.model.News_HotBean;
import com.xiyou.apps.lookpan.utils.JsonUtil;

public class HotWallStreetcnFragment extends
		PullToRefreshListViewFragment<News_HotBean> {

	public static final String TAG = "HotWallStreetcn";

	SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm",
			Locale.getDefault());

	public static Fragment newInstance(FragmentManager fm) {
		Fragment fragment = fm.findFragmentByTag(TAG);
		if (fragment == null) {
			fragment = new HotWallStreetcnFragment();
		}
		return fragment;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), ShowNewsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		News_HotBean mNewsBean = (News_HotBean) parent.getAdapter().getItem(
				position);
		mNewsBean.getNid();
		Bundle bundle = new Bundle();
		bundle.putString("id", mNewsBean.getNid() + "");
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	protected void onLoadData(boolean isFirstPage) {

		mLoadingProgress.setVisibility(View.VISIBLE);
		mLoadErrorView.setVisibility(View.GONE);
		mListView.setVisibility(View.GONE);

		new AsyncTask<Object, Object, ArrayList<News_HotBean>>() {
			@Override
			protected ArrayList<News_HotBean> doInBackground(Object... params) {
				return JsonUtil
						.getNews_Recommend_Bean_List2JSON(API.news_more_hot);
			}

			protected void onPostExecute(
					java.util.ArrayList<News_HotBean> result) {
				mLoadingProgress.setVisibility(View.GONE);
				if (null != result) {
					mListView.setVisibility(View.VISIBLE);
					mAdapter.clear();
					mAdapter.addAll(result);
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
	protected View onGetItemView(News_HotBean item, int position,
			View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.list_item_news,
					parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView
					.findViewById(android.R.id.title);
			holder.icon = (ImageView) convertView
					.findViewById(android.R.id.icon);
			holder.creattime = (TextView) convertView
					.findViewById(R.id.itemnews_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String url = item.getFile_managed_field_data_upload_uri();
		url = url.replaceAll("http://\\w+.wallstreetcn.com",
				"http://thumbnail.wallstreetcn.com/thumb");
		url = url.replaceAll(".jpg", ",c_fill,h_200,w_300.jpg");
		ImageLoader.getInstance().displayImage(url, holder.icon, mOptions);
		holder.title.setText(item.getNode_title());
		holder.title.setTextColor(Color.BLACK);
		Date compare = new Date(Long.parseLong(item.getNode_created()) * 1000l);
		holder.creattime.setText(time.format(compare));
		return convertView;
	}

	private static class ViewHolder {
		TextView title;
		ImageView icon;
		TextView creattime;
	}

}
