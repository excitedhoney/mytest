package cn.precious.metal.ui;

import java.util.ArrayList;
import java.util.List;

import widget.LazyViewPager;
import widget.MyFragmentPagerAdapter;
import widget.TabPageIndicator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import cn.precious.metal.R;
import cn.precious.metal.base.BaseActivity;
import cn.precious.metal.ui.fragment.ETFReportFragment;

public class ETFReportActivity extends BaseActivity{
	
	public static final String TAG = "ETFReportActivity" ;
	
	private static final String[] CONTENT = new String[] {"黄金ETF持仓", "白银ETF持仓"};

	LazyViewPager mViewPager;

	private ETFragPageAdapter mETFragPageAdapter;

	private List<Fragment> fragments = new ArrayList<Fragment>();

	private TabPageIndicator indicator;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_etf_report);
		initView();
	}

	
	public void initView() {
		initFragments();
		mViewPager = (LazyViewPager) findViewById(R.id.viewPager);
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		mETFragPageAdapter = new ETFragPageAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mETFragPageAdapter);
		mViewPager.setOnPageChangeListener(pageChangerListener);
		indicator.setViewPager(mViewPager);
		
	}

	public void initFragments(){
		fragments.add(ETFReportFragment.newInstance(ETFReportFragment.ETF_TYPE_GOLDE));
		fragments.add(ETFReportFragment.newInstance(ETFReportFragment.ETF_TYPE_SLIVER));
	}
	
	
	public void back(View view) {
		finish();
	}

	private LazyViewPager.OnPageChangeListener pageChangerListener = new LazyViewPager.OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
		}
	};

	class ETFragPageAdapter extends MyFragmentPagerAdapter {

		public ETFragPageAdapter(FragmentManager fm) {
			super(fm, TAG);
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

		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length];
		}
	}
}
