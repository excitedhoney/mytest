//package cn.precious.metal.ui.fragment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import widget.MyFragmentPagerAdapter;
//import widget.TabPageIndicator;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import cn.precious.metal.R;
//import cn.precious.metal.base.BaseFragment;
//
//public class MarketFragment extends BaseFragment {
//	
//	public static final String TAG = "MarketFragment" ;
//	
//	private static final String[] CONTENT = new String[] {"自选", "国内纸金", "国内现货", "国际现货", "全球外汇", "国内股指ָ","全球指数"};
//
//	ViewPager mViewPager;
//
//	private MyFragPageAdapter mMyFragPageAdapter;
//
//	private List<Fragment> fragments = new ArrayList<Fragment>();
//
//	private TabPageIndicator indicator;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		View view = inflater.inflate(R.layout.fragment_market, null);
//		initFragments();
//		initView(view);
//		return view;
//	}
//
//	public void initFragments() {
//		fragments.add(new NewsFragment());
//		fragments.add(new NewsFragment());
//		fragments.add(new NewsFragment());
//		fragments.add(new NewsFragment());
//		fragments.add(new NewsFragment());
//		fragments.add(new NewsFragment());
//		fragments.add(new NewsFragment());
//	}
//
//	public void initView(View view) {
//		mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
//		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
//		mMyFragPageAdapter = new MyFragPageAdapter(getChildFragmentManager());
//		mViewPager.setAdapter(mMyFragPageAdapter);
//		mViewPager.setOnPageChangeListener(pageChangerListener);
//		indicator.setViewPager(mViewPager,0);
//	}
//
//
//	private ViewPager.OnPageChangeListener pageChangerListener = new ViewPager.OnPageChangeListener() {
//
//		@Override
//		public void onPageSelected(int position) {
//			// TODO Auto-generated method stub
//		}
//
//		@Override
//		public void onPageScrolled(int arg0, float arg1, int arg2) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onPageScrollStateChanged(int state) {
//			// TODO Auto-generated method stub
//		}
//	};
//
//	class MyFragPageAdapter extends MyFragmentPagerAdapter {
//
//		public MyFragPageAdapter(FragmentManager fm) {
//			super(fm, TAG);
//			// TODO Auto-generated constructor stub
//		}
//
//		public void setItem(List<Fragment> list) {
//			fragments.clear();
//			if (list != null)
//				fragments.addAll(list);
//			notifyDataSetChanged();
//		}
//
//		@Override
//		public Fragment getItem(int arg0) {
//			return fragments.get(arg0);
//		}
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return fragments.size();
//		}
//
//		@Override
//		public Object instantiateItem(ViewGroup container, int position) {
//			// TODO Auto-generated method stub
//			return super.instantiateItem(container, position);
//		}
//
//		@Override
//		public CharSequence getPageTitle(int position) {
//			return CONTENT[position % CONTENT.length];
//		}
//	}
//
//}
