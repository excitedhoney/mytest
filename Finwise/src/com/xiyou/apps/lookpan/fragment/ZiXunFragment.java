package com.xiyou.apps.lookpan.fragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.xiyou.apps.lookpan.R;

@SuppressLint("NewApi")
public class ZiXunFragment extends Fragment {

	private PagerSlidingTabStrip mPagerSlidingTabStrip;
	private ViewPager mViewPager;
	private myFragmentAdapter mFragmentAdapter;
	private ActionBar mActionBar;
	private View mView;
	private static String TAG = "ZiXunFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("tag", "onCreateView");
		mView = inflater.inflate(R.layout.fragment_zixun, null, false);
		if (mView != null) {
			ViewGroup parent = (ViewGroup) mView.getParent();
			if (parent != null) {
				parent.removeView(mView);
			}
			return mView;
		}
		return mView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		mPagerSlidingTabStrip = (PagerSlidingTabStrip) view
				.findViewById(R.id.tabs);
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager1);

		Log.i("tag", "onViewCreated");

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mFragmentAdapter = new myFragmentAdapter(getFragmentManager());
		mViewPager.setAdapter(mFragmentAdapter);
		mPagerSlidingTabStrip.setViewPager(mViewPager);
		mPagerSlidingTabStrip.setIndicatorColorResource(R.color.kanpan_chengse);
		mActionBar = getActivity().getActionBar();
		mActionBar.setTitle("资讯");
		Log.i("tag", "onActivityCreated");
	}

	class myFragmentAdapter extends FragmentStatePagerAdapter {

		public myFragmentAdapter(FragmentManager fm) {
			super(fm);
			Log.i("tag", "myFragmentAdapter的构造器");
		}

		@Override
		public Fragment getItem(int arg0) {
			switch (arg0) {
			case 0:
				// return ZiXunNewsFragment.newInstance(getFragmentManager());
				Log.i("tag", "1");
				return new ZiXunNewsFragment();
			case 1:
				// return ZiXunWallStreetcnFragment
				// .newInstance(getFragmentManager());
				Log.i("tag", "2");
				return new ZiXunWallStreetcnFragment();
			case 2:
				// return HotWallStreetcnFragment
				// .newInstance(getFragmentManager());
				Log.i("tag", "3");
				return new HotWallStreetcnFragment();
			case 3:
				// return TuiJianWallStreetcnFragment
				// .newInstance(getFragmentManager());
				Log.i("tag", "4");
				return new TuiJianWallStreetcnFragment();
			default:
				Log.i("tag", "5");
				return new ZiXunNewsFragment();
			}

		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "实时新闻";
			case 1:
				return "华尔街见闻";
			case 2:
				return "见闻最热";
			case 3:
				return "见闻推荐";

			default:
				return "实时新闻";
			}
		}

		@Override
		public int getCount() {
			return 4;
		}

	}

}
