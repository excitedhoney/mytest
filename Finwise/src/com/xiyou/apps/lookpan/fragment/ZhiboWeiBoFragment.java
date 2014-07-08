package com.xiyou.apps.lookpan.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.PullToRefreshListViewFragment;
import com.xiyou.apps.lookpan.fragment.showactivity.ShowImageViewActivity;
import com.xiyou.apps.lookpan.model.ZhiBoBean;
import com.xiyou.apps.lookpan.utils.JsonUtil;

public class ZhiboWeiBoFragment extends
		PullToRefreshListViewFragment<ZhiBoBean> {

	private DisplayImageOptions mOptionss;
	private String URL_bulinger = "https://api.weibo.com/2/statuses/user_timeline.json?count=50&access_token=2.00MDwOrBuZwUYC6d7134b1900abF9a";

	// private String URL_live =
	// "https://api.weibo.com/2/statuses/user_timeline.json?access_token=2.00XcpUZFRSnvGB4128973919s3nT6C";
	// private String URL_service =
	// "https://api.weibo.com/2/statuses/user_timeline.json?access_token=2.00_yaGaFLpsOeB3b0a46fb683lDC2C";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mOptionss = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.da)
				.displayer(new RoundedBitmapDisplayer(5))
				.showImageForEmptyUri(R.drawable.xiao).build();
		return inflater.inflate(R.layout.viewpager_weibo, container, false);
	}

	@Override
	protected void onLoadData(boolean isFirstPage) {

		mLoadingProgress.setVisibility(View.VISIBLE);
		mLoadErrorView.setVisibility(View.GONE);
		mListView.setVisibility(View.GONE);

		new AsyncTask<Object, Object, ArrayList<ZhiBoBean>>() {

			@Override
			protected ArrayList<ZhiBoBean> doInBackground(Object... params) {

				return JsonUtil.getZhiBoBean4JSON(URL_bulinger);

			}

			protected void onPostExecute(java.util.ArrayList<ZhiBoBean> result) {
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

			};
		}.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		ZhiBoBean mBean = (ZhiBoBean) parent.getAdapter().getItem(position);

		if (null != mBean.getBigImageURL()
				&& !"".equals(mBean.getBigImageURL())) {
			/**
			 * 方法2：
			 */
			Intent i = new Intent();
			i.setClass(getActivity(), ShowImageViewActivity.class);
			i.putExtra("BigImageURL", mBean.getBigImageURL());
			startActivity(i);
		}
	}

	@SuppressLint("NewApi")
	@Override
	protected View onGetItemView(ZhiBoBean item, int position,
			View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.adapter_item_zhibo,
					parent, false);
			holder = new ViewHolder();
			holder.live_time = (TextView) convertView
					.findViewById(R.id.textView3);
			holder.imageview = (ImageView) convertView
					.findViewById(R.id.imageView123);
			holder.live_content = (TextView) convertView
					.findViewById(R.id.textView1);
			holder.head = (ImageView) convertView.findViewById(R.id.imageView1);
			holder.name = (TextView) convertView.findViewById(R.id.textView2);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.live_time.setText(JsonUtil.DayForChinese(item.getTime()));

		holder.live_content.setText(item.getContent());

		holder.head.setImageResource(R.drawable.a);
		holder.name.setText("布林格");
		if (null != item.getBmiddle_picURL()
				&& !"".equals(item.getBmiddle_picURL())) {
			ImageLoader.getInstance().displayImage(item.getBmiddle_picURL(),
					holder.imageview, mOptionss);
		}

		return convertView;
	}

	private static class ViewHolder {
		ImageView imageview;
		TextView live_time;
		TextView live_content;
		ImageView head;
		TextView name;
	}
}
