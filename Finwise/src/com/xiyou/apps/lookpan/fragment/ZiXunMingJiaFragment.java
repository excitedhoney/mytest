package com.xiyou.apps.lookpan.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.PullToRefreshListViewFragment;
import com.xiyou.apps.lookpan.fragment.showactivity.ShowMingJiaActivity;
import com.xiyou.apps.lookpan.model.MingJiaBean;
import com.xiyou.apps.lookpan.utils.JsonUtil;

public class ZiXunMingJiaFragment extends
		PullToRefreshListViewFragment<MingJiaBean> {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(getActivity(), ShowMingJiaActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		MingJiaBean mNewsBean = (MingJiaBean) parent.getAdapter().getItem(
				position);
		Bundle bundle = new Bundle();
		bundle.putString("id", mNewsBean.getArticle_id() + "");
		intent.putExtras(bundle);
		startActivity(intent);

	}

	@Override
	protected void onLoadData(boolean isFirstPage) {

		new AsyncTask<Object, Object, ArrayList<MingJiaBean>>() {
			@Override
			protected ArrayList<MingJiaBean> doInBackground(Object... params) {
				return JsonUtil
						.getMingJiaBean2JSON("http://www.06866.com/api/special_reports.json");
			}

			protected void onPostExecute(java.util.ArrayList<MingJiaBean> result) {
				if (null != result) {
					mAdapter.clear();
					mAdapter.addAll(result);
				}
			};

		}.execute();

	}

	@Override
	protected View onGetItemView(MingJiaBean item, int position,
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
		// String url = item.getImage_url();
		// url = url.replaceAll("http://\\w+.wallstreetcn.com",
		// "http://thumbnail.wallstreetcn.com/thumb");
		// url = url.replaceAll(".jpg", ",c_fill,h_200,w_300.jpg");
		ImageLoader.getInstance().displayImage(item.getImage_url(),
				holder.icon, mOptions);
		holder.title.setText(item.getArticle_title());
		holder.title.setTextColor(Color.BLACK);
		holder.creattime.setText(item.getAuthor());
		return convertView;
	}

	private static class ViewHolder {
		TextView title;
		ImageView icon;
		TextView creattime;
	}

}
