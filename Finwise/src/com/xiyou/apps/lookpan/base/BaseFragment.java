package com.xiyou.apps.lookpan.base;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xiyou.apps.lookpan.R;

public abstract class BaseFragment extends Fragment {

	protected DisplayImageOptions mOptions;
	protected ActionBar mActionBar;
	protected static LayoutInflater mLayoutInflater;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getActivity()).threadPoolSize(5)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCacheSize(1000000)
				// 10 Mb
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).build();
		ImageLoader.getInstance().init(config);
		mOptions = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.xiao).cacheInMemory().cacheOnDisc()
				.displayer(new RoundedBitmapDisplayer(5))
				.showImageForEmptyUri(R.drawable.xiao).build();
		mLayoutInflater = LayoutInflater.from(getActivity());
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mActionBar = getActivity().getActionBar();
	}

}
