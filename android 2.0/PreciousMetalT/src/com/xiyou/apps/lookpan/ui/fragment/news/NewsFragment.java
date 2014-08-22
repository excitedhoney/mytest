package com.xiyou.apps.lookpan.ui.fragment.news;

import java.util.ArrayList;
import java.util.List;

import widget.LazyViewPager;
import widget.MyFragmentPagerAdapter;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.precious.metal.utils.Utils;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.BaseFragment;
import com.xiyou.apps.lookpan.ui.fragment.news.live.ZiXunNewsFragment;

public class NewsFragment extends BaseFragment implements OnClickListener {

	public static final String TAG = "NewsFragment";

	LazyViewPager mLazyViewPager;

	private ImageView cursor;// 动画图片

	private MyFragPageAdapter mMyFragPageAdapter;

	private List<Fragment> fragments = new ArrayList<Fragment>();

	private LinearLayout globalNews, macroHot, industryAnaly, themeAnaly;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_news, null);
		initFragments();
		initView(view);
		return view;
	}

	public void initFragments() {
		fragments.add(new GlobalNewsFragment());
		Bundle mBundle = new Bundle();
		mBundle.putString("url", "1");
		fragments.add(MacroHotFragment.getInstance(mBundle));
		Bundle mBundle1 = new Bundle();
		mBundle1.putString("url", "2");
		fragments.add(MacroHotFragment.getInstance(mBundle1));
		fragments.add(new ZiXunNewsFragment());
	}

	public void initView(View view) {
		mLazyViewPager = (LazyViewPager) view.findViewById(R.id.viewPager);
		cursor = (ImageView) view.findViewById(R.id.cursor);

		mMyFragPageAdapter = new MyFragPageAdapter(getFragmentManager(), TAG);
		mLazyViewPager.setAdapter(mMyFragPageAdapter);
		mLazyViewPager.setOnPageChangeListener(pageChangerListener);
		initAnimView();

		globalNews = (LinearLayout) view.findViewById(R.id.global_news);
		macroHot = (LinearLayout) view.findViewById(R.id.macro_hot);
		industryAnaly = (LinearLayout) view.findViewById(R.id.industry_analy);
		themeAnaly = (LinearLayout) view.findViewById(R.id.theme_analy);

		globalNews.setOnClickListener(this);
		macroHot.setOnClickListener(this);
		industryAnaly.setOnClickListener(this);
		themeAnaly.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.global_news:
			mLazyViewPager.setCurrentItem(0);
			break;
		case R.id.macro_hot:
			mLazyViewPager.setCurrentItem(1);
			break;
		case R.id.industry_analy:
			mLazyViewPager.setCurrentItem(2);
			break;
		case R.id.theme_analy:
			mLazyViewPager.setCurrentItem(3);
			break;

		default:
			break;
		}
	}

	// 初始化动画
	private void initAnimView() {
		Matrix matrix = new Matrix();
		matrix.postTranslate(0, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}

	private int currentIndex = 0;

	private LazyViewPager.OnPageChangeListener pageChangerListener = new LazyViewPager.OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {

			// TODO Auto-generated method stub
			int one = Utils.getWindowWidth(getActivity()) / 4;

			Animation animation = new TranslateAnimation(one * currentIndex,
					one * position, 0, 0);
			currentIndex = position;
			animation.setFillAfter(true);
			animation.setDuration(300);
			cursor.startAnimation(animation);

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			// mLazyViewPager
			// .requestDisallowInterceptTouchEvent(state !=
			// ViewPager.SCROLL_STATE_IDLE);
		}
	};

	class MyFragPageAdapter extends MyFragmentPagerAdapter {

		public MyFragPageAdapter(FragmentManager fm, String uniqueFlag) {
			super(fm, uniqueFlag);
			// TODO Auto-generated constructor stub
		}

		public void setItem(List<Fragment> list) {
			fragments.clear();
			if (list != null)
				fragments.addAll(list);
			notifyDataSetChanged();
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			return super.instantiateItem(container, position);
		}
	}

	public void updateTabTitleColor(int position) {

	}

}
