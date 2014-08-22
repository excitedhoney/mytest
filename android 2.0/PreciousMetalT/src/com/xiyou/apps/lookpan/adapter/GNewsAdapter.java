package com.xiyou.apps.lookpan.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.precious.metal.entity.NewsInfo;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xiyou.apps.lookpan.R;

public class GNewsAdapter extends BaseAdapter {

	private List<NewsInfo> items = new ArrayList<NewsInfo>();

	private Context context;

	private LayoutInflater inflater;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",
			Locale.getDefault());

	private ImageLoader mImageLoader;

	public GNewsAdapter(Context context, List<NewsInfo> list) {
		this.context = context;
		if (list != null) {
			items.addAll(list);
		}
		inflater = LayoutInflater.from(context);
		initImageLoader(context);
	}

	public void setItems(List<NewsInfo> list) {
		items.clear();
		if (list != null) {
			items.addAll(list);
		}
		notifyDataSetChanged();
	}

	private DisplayImageOptions options;

	public void initImageLoader(Context cont) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				cont).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging()
				.build();

		mImageLoader = ImageLoader.getInstance();
		mImageLoader.init(config);

		options = new DisplayImageOptions.Builder().cacheInMemory()
				.cacheOnDisc().build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public NewsInfo getItem(int position) {
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
		ViewHolder holder;
		if (view == null) {
			view = inflater.inflate(R.layout.item_global_news, null);
			holder = new ViewHolder();
			holder.title = (TextView) view.findViewById(R.id.title);
			holder.date = (TextView) view.findViewById(R.id.date);
			holder.icon = (ImageView) view.findViewById(R.id.icon);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		NewsInfo info = items.get(position);
		holder.title.setText(info.getNode_title());
		Date compare = new Date(info.getNode_created() * 1000l);
		holder.date.setText(sdf.format(compare));
		String url = info.getFile_managed_file_usage_uri();
		url = url.replaceAll("http://\\w+.wallstreetcn.com",
				"http://thumbnail.wallstreetcn.com/thumb");
		url = url.replaceAll(".jpg", ",c_fill,h_200,w_300.jpg");
		mImageLoader.displayImage(url, holder.icon, options);
		return view;
	}

	class ViewHolder {
		TextView title;
		TextView date;
		ImageView icon;

	}

}
