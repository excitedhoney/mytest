package com.xiyou.apps.lookpan.fragment.showactivity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xiyou.apps.lookpan.MainActivity;
import com.xiyou.apps.lookpan.R;

public class LoadingDaoHangActivity extends Activity {

	private int currentPageScrollStatus;
	private ViewPager mViewPager;
	private ArrayList<View> viewlist;
	private int pos = 0;
	private int maxPos = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_daohang);
		mViewPager = (ViewPager) findViewById(R.id.viewpager1);
		viewlist = new ArrayList<View>();
		viewlist.add(getLayoutInflater().inflate(R.layout.item_1, null));
		viewlist.add(getLayoutInflater().inflate(R.layout.item_2, null));
		viewlist.add(getLayoutInflater().inflate(R.layout.item_3, null));
		mViewPager.setAdapter(new MyPagerAdapter());
		mViewPager.setOnPageChangeListener(new mOnpagerChangeLister());

	}

	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(viewlist.get(position));
		}

		@Override
		public int getCount() {
			return viewlist.size();
		}

		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(viewlist.get(position));
			return viewlist.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	private class mOnpagerChangeLister implements
			ViewPager.OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			currentPageScrollStatus = arg0;

		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			if (pos == maxPos) {
				// 已经在最后一页还想往右划
				if (positionOffsetPixels == 0 && currentPageScrollStatus == 1) {
					LoadingDaoHangActivity.this.finish();
					startActivity(new Intent().setClass(
							LoadingDaoHangActivity.this, MainActivity.class));

				}
			}

		}

		@Override
		public void onPageSelected(int arg0) {

		}

	}
}
