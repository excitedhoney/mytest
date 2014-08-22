package com.xiyou.apps.lookpan.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.precious.metal.entity.Strategy;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiyou.apps.lookpan.R;

public class StrategyAdapter extends BaseAdapter {

	private Context context;

	private LayoutInflater mInflater;

	private List<Strategy> items = new ArrayList<Strategy>();

	private ImageLoader mImageLoader;

	public StrategyAdapter(Context context, List<Strategy> list,
			ImageLoader mImageLoader) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.mImageLoader = mImageLoader;

		initOption();

		if (list != null) {
			items.addAll(list);
		}
	}

	private DisplayImageOptions options;

	public void initOption() {
		options = new DisplayImageOptions.Builder().cacheInMemory()
				.cacheOnDisc().build();
	}

	public void setItems(List<Strategy> list, boolean isClear) {
		if (isClear)
			items.clear();
		if (list != null) {
			items.addAll(list);
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Strategy getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		ViewHolder holder = null;
		if (view == null) {
			view = mInflater.inflate(R.layout.item_strategy, null);
			holder = new ViewHolder();
			holder.userIcon = (ImageView) view.findViewById(R.id.user_icon);
			holder.image = (ImageView) view.findViewById(R.id.image);

			holder.title = (TextView) view.findViewById(R.id.title);
			holder.content = (TextView) view.findViewById(R.id.content);
			holder.name = (TextView) view.findViewById(R.id.user_name);
			holder.time = (TextView) view.findViewById(R.id.time);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		Strategy s = items.get(position);

		holder.userIcon.setTag(s.getAuthor_pic() + "_" + position);

		if (holder.userIcon.getTag().equals(s.getAuthor_pic() + "_" + position)) {
			mImageLoader.displayImage(s.getAuthor_pic(), holder.userIcon,
					options);
		}
		holder.image.setTag(s.getImage_url() + "_" + position);

		if (holder.image.getTag().equals(s.getImage_url() + "_" + position)) {
			mImageLoader.displayImage(s.getImage_url(), holder.image, options);
		}
		// holder.time.setText(s.get);
		holder.content.setText(s.getSummary());
		holder.name.setText(s.getAuthor());
		holder.title.setText(s.getArticle_title());
		Log.i("tag", s.toString());
		return view;
	}

	class ViewHolder {
		ImageView userIcon;
		ImageView image;
		TextView title;
		TextView content;
		TextView name;
		TextView time;
	}

}
