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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.adapter.ImageAdapter;
import com.xiyou.apps.lookpan.base.PullToRefreshListViewFragment;
import com.xiyou.apps.lookpan.fragment.showactivity.ShowNewsActivity;
import com.xiyou.apps.lookpan.model.NewsBean;
import com.xiyou.apps.lookpan.model.News_Top_ImageBean;
import com.xiyou.apps.lookpan.utils.JsonUtil;
import com.xiyou.apps.lookpan.utils.Util;
import com.xiyou.apps.view.AutoPlayGallery;

public class ZiXunWallStreetcnFragment extends
		PullToRefreshListViewFragment<NewsBean> {
	public static final String TAG = "ZiXunWallStreetcn";
	private AutoPlayGallery mAutoPlayGallery;
	private int height;
	private ImageAdapter mImageAdapter;
	SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm",
			Locale.getDefault());
	private int width;

	public static Fragment newInstance(FragmentManager fm) {
		Fragment fragment = fm.findFragmentByTag(TAG);
		if (fragment == null) {
			fragment = new ZiXunWallStreetcnFragment();
		}
		return fragment;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mAutoPlayGallery = (AutoPlayGallery) LayoutInflater.from(getActivity())
				.inflate(R.layout.adapter_item_auto, null);
		DisplayMetrics metric = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
		float density = metric.density;
		width = (int) (metric.widthPixels / density);
		height = (int) (width * 0.8);
		mListView.addHeaderView(mAutoPlayGallery);

	}

	@Override
	public void onLoadData(final boolean isFirstPage) {

		mLoadingProgress.setVisibility(View.VISIBLE);
		mLoadErrorView.setVisibility(View.GONE);
		mListView.setVisibility(View.GONE);

		new AsyncTask<Object, Object, ArrayList<News_Top_ImageBean>>() {
			@Override
			protected ArrayList<News_Top_ImageBean> doInBackground(
					Object... params) {
				ArrayList<News_Top_ImageBean> news = null;
				try {
					ArrayList<News_Top_ImageBean> old_news = new ArrayList<News_Top_ImageBean>();
					news = new ArrayList<News_Top_ImageBean>();
					old_news = JsonUtil
							.getNews_Top_ImageBean2JSON("http://api.wallstreetcn.com/apiv1/topnews-list.json");
					for (int i = 0; i < 5; i++) {
						news.add(old_news.get(i));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return news;
			}

			@Override
			protected void onPostExecute(ArrayList<News_Top_ImageBean> result) {

				if (result != null) {
					ArrayList<String> titles = new ArrayList<String>();
					for (int i = 0; i < result.size(); i++) {
						titles.add(result.get(i)
								.getFile_managed_file_usage_uri());
					}
					if (null != getActivity()) {
						Log.i("tag", "ImageAdapter1");
						mImageAdapter = new ImageAdapter(getActivity(), result,
								titles, height);
						Log.i("tag", "ImageAdapter2");
						mAutoPlayGallery.setAdapter(mImageAdapter);
						Log.i("tag", "ImageAdapter3");
						mImageAdapter.notifyDataSetChanged();
					}

				} else {
					Util.toastTips(getActivity(), "网络延时或者异常");

				}
			};
		}.execute();

		new AsyncTask<Object, Object, ArrayList<NewsBean>>() {

			@Override
			protected ArrayList<NewsBean> doInBackground(Object... params) {
				return JsonUtil
						.getNewsBeanList2JSON("HTTP://api.wallstreetcn.com/apiv1/news-list.json"
								+ (isFirstPage ? "" : "?page=" + ++mPage));
			}

			@Override
			protected void onPostExecute(ArrayList<NewsBean> result) {
				if (null != result) {
					mLoadingProgress.setVisibility(View.GONE);
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
			};
		}.execute();
	}

	@Override
	protected View onGetItemView(NewsBean item, int position, View convertView,
			ViewGroup parent) {
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
		String url = item.getFile_managed_file_usage_uri();
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), ShowNewsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		NewsBean mNewsBean = (NewsBean) parent.getAdapter().getItem(position);
		mNewsBean.getNid();
		Bundle bundle = new Bundle();
		bundle.putString("id", mNewsBean.getNid() + "");
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private static class ViewHolder {
		TextView title;
		ImageView icon;
		TextView creattime;
	}

}
