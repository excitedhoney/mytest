package com.xiyou.apps.lookpan.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.mm.sdk.platformtools.Log;
import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.model.News_Top_ImageBean;

public class ImageAdapter extends BaseAdapter {
 
	Context context;
	ArrayList<News_Top_ImageBean> drawables;
	ArrayList<String> titles;
	LayoutInflater inflater;
	private String[] imageUrls;
	private DisplayImageOptions options;
	private int mHeight;

	public ImageAdapter(Context context,
			ArrayList<News_Top_ImageBean> drawables, ArrayList<String> titles,
			int height) {
		Log.i("tag", "进入gouzhaoqi");
		this.context = context;
		this.drawables = drawables;
		this.titles = titles;
		inflater = LayoutInflater.from(context);
		mHeight = height;
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this.context).threadPoolSize(5)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCacheSize(1000000)
				// 10 Mb
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// .enableLogging() // Not necessary in common
				.build();
		ImageLoader.getInstance().init(config);
		options = new DisplayImageOptions.Builder()
		// .showImageForEmptyUri(R.drawable.logo)
		// .showStubImage(R.drawable.logo_short)
				.build();
		// 添加urls
		imageUrls = new String[drawables.size()];
		for (int i = 0; i < drawables.size(); i++) {
			String url = drawables.get(i).getFile_managed_file_usage_uri();
			if (url != null) {
				url = url.replaceAll("http://\\w+.wallstreetcn.com",
						"http://thumbnail.wallstreetcn.com/thumb");
				url = url.replaceAll(".jpg", ",c_fill,h_200,w_300.jpg");
			}
			imageUrls[i] = url;
		}
	}

	public void clearDrawables() {
		drawables.clear();
	}

	public Context getContext() {
		return context;
	}

	public ArrayList<News_Top_ImageBean> getDrawables() {
		return drawables;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		return drawables.get(position % drawables.size());
	}

	@Override
	public long getItemId(int position) {
		return Long.parseLong(drawables.get(position % drawables.size())
				.getNid());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		position = position % drawables.size();
		Log.i("tag", "进入getView");
		Log.i("tag", "mhight:的值:" + mHeight);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater
					.inflate(R.layout.adapter_item_autoplay, null);
			Log.i("tag", "mhight:的值:" + mHeight);
			holder.pic = (ImageView) convertView
					.findViewById(R.id.autoplay_pic);
			holder.pic.setScaleType(ImageView.ScaleType.CENTER_CROP);
			holder.pic.getLayoutParams().height = mHeight;
			holder.pic.setLayoutParams(holder.pic.getLayoutParams());

			holder.text = (TextView) convertView
					.findViewById(R.id.autoplay_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Log.i("tag", "mhight:的值:" + mHeight);
		Log.i("tag", "height:的值:" + holder.pic.getLayoutParams().height);
		Log.i("tag", "width:的值:" + holder.pic.getLayoutParams().width);

		ImageLoader.getInstance().displayImage(
				imageUrls[position % drawables.size()], holder.pic, options);
		holder.text.setText(drawables.get(position).getNode_title());
		return convertView;
	}

	private class ViewHolder {
		TextView text;
		ImageView pic;
	}
}
