package com.xiyou.apps.lookpan.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.BaseActivity;

public class FullImageActivity extends BaseActivity{
	
	private ImageView  fullImage ;
	
	private ImageLoader mImageLoader ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_image);
		
		initImageLoader(this);
		
		initView(); 
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			MemoryCacheAware<String, Bitmap> cache = mImageLoader
					.getMemoryCache();
			cache.clear();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public void back(View view) {
		finish();
	}
	
	public void initImageLoader(Context cont) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				cont).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging()
				.build();

		mImageLoader = ImageLoader.getInstance();
		mImageLoader.init(config);
		
		initOption();

	}
	
	private DisplayImageOptions options;
	

	public void initOption() {
		options = new DisplayImageOptions.Builder().cacheInMemory()
				.cacheOnDisc().build();
	}
	
	
	public void initView () {
		fullImage = (ImageView) findViewById(R.id.fullImage) ;
		
		mImageLoader.displayImage(getIntent().getStringExtra("url"), fullImage , options); 
	}
}
