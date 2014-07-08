package com.xiyou.apps.lookpan.fragment.showactivity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.fragment.ETFReportFragment;
import com.xiyou.apps.view.LazyViewPager;
import com.xiyou.apps.view.TabPageIndicator;

public class ShowETFActivity extends Activity {

	public static final String TAG = "ETFReportActivity";

	protected ActionBar mActionBar;
	LazyViewPager mViewPager;
	private TextView BLG_TextView;
	private TextView ZBZ_TextView;
	private View BLG_view;
	private View ZBZ_view;

	private ETFragPageAdapter mETFragPageAdapter;

	private List<Fragment> fragments = new ArrayList<Fragment>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_etf_report);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.title_shang));
		mActionBar.setTitle("ETF持仓");
		initView();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void initView() {
		initFragments();
		mViewPager = (LazyViewPager) findViewById(R.id.viewPager);
		mETFragPageAdapter = new ETFragPageAdapter(getFragmentManager());
		mViewPager.setAdapter(mETFragPageAdapter);
		mViewPager.setOnPageChangeListener(pageChangerListener);
		BLG_TextView = (TextView) findViewById(R.id.textView1);
		ZBZ_TextView = (TextView) findViewById(R.id.textView2);
		BLG_view = findViewById(R.id.view1);
		ZBZ_view = findViewById(R.id.view2);

		BLG_TextView.setOnClickListener(mClickListener());
		ZBZ_TextView.setOnClickListener(mClickListener());

	}

	public void initFragments() {
		fragments.add(ETFReportFragment
				.newInstance(ETFReportFragment.ETF_TYPE_GOLDE));
		fragments.add(ETFReportFragment
				.newInstance(ETFReportFragment.ETF_TYPE_SLIVER));
	}

	private LazyViewPager.OnPageChangeListener pageChangerListener = new LazyViewPager.OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			switch (position) {
			case 0:
				BLG_TextView.setTextColor(ShowETFActivity.this.getResources()
						.getColor(R.color.kanpan_chengse));
				ZBZ_TextView.setTextColor(ShowETFActivity.this.getResources()
						.getColor(R.color.black));
				BLG_view.setBackgroundColor(ShowETFActivity.this.getResources()
						.getColor(R.color.kanpan_chengse));
				ZBZ_view.setBackgroundColor(ShowETFActivity.this.getResources()
						.getColor(android.R.color.transparent));
				break;
			case 1:
				BLG_TextView.setTextColor(ShowETFActivity.this.getResources()
						.getColor(R.color.black));
				ZBZ_TextView.setTextColor(ShowETFActivity.this.getResources()
						.getColor(R.color.kanpan_chengse));
				BLG_view.setBackgroundColor(ShowETFActivity.this.getResources()
						.getColor(android.R.color.transparent));
				ZBZ_view.setBackgroundColor(ShowETFActivity.this.getResources()
						.getColor(R.color.kanpan_chengse));

			default:
				break;
			}
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

	private OnClickListener mClickListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {

				switch (v.getId()) {
				case R.id.textView1:
					BLG_TextView.setTextColor(ShowETFActivity.this
							.getResources().getColor(R.color.kanpan_chengse));
					ZBZ_TextView.setTextColor(ShowETFActivity.this
							.getResources().getColor(R.color.black));
					BLG_view.setBackgroundColor(ShowETFActivity.this
							.getResources().getColor(R.color.kanpan_chengse));
					ZBZ_view.setBackgroundColor(ShowETFActivity.this
							.getResources().getColor(
									android.R.color.transparent));
					mViewPager.setCurrentItem(0);
					break;
				case R.id.textView2:
					BLG_TextView.setTextColor(ShowETFActivity.this
							.getResources().getColor(R.color.black));
					ZBZ_TextView.setTextColor(ShowETFActivity.this
							.getResources().getColor(R.color.kanpan_chengse));
					BLG_view.setBackgroundColor(ShowETFActivity.this
							.getResources().getColor(
									android.R.color.transparent));
					ZBZ_view.setBackgroundColor(ShowETFActivity.this
							.getResources().getColor(R.color.kanpan_chengse));
					mViewPager.setCurrentItem(1);
					break;

				default:
					break;
				}
			}
		};

	}

	class ETFragPageAdapter extends MyFragmentPagerAdapter {

		public ETFragPageAdapter(FragmentManager fm) {
			super(fm, "ETFReportActivity");
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
}
