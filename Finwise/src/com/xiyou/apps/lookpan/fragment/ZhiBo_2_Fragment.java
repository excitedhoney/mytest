package com.xiyou.apps.lookpan.fragment;

import com.xiyou.apps.lookpan.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ZhiBo_2_Fragment extends Fragment {

	private ViewPager mViewPager;
	private TextView BLG_TextView;
	private TextView ZBZ_TextView;
	private View BLG_view;
	private View ZBZ_view;
	private FragmentAdapter mFragmentAdapter;
	private static String TAG = "ZhiBo_2_Fragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_zhibo1, container, false);
	}

	@SuppressLint("NewApi")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager12);
		BLG_TextView = (TextView) view.findViewById(R.id.textView1);
		ZBZ_TextView = (TextView) view.findViewById(R.id.textView2);
		BLG_view = view.findViewById(R.id.view1);
		ZBZ_view = view.findViewById(R.id.view2);
		mFragmentAdapter = new FragmentAdapter(getFragmentManager());
		mViewPager.setAdapter(mFragmentAdapter);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		BLG_TextView.setOnClickListener(mClickListener());
		ZBZ_TextView.setOnClickListener(mClickListener());
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				switch (arg0) {
				case 0:
					BLG_TextView.setTextColor(getActivity().getResources()
							.getColor(R.color.kanpan_chengse));
					ZBZ_TextView.setTextColor(getActivity().getResources()
							.getColor(R.color.black));
					BLG_view.setBackgroundColor(getActivity().getResources()
							.getColor(R.color.kanpan_chengse));
					ZBZ_view.setBackgroundColor(getActivity().getResources()
							.getColor(android.R.color.transparent));
					break;
				case 1:
					BLG_TextView.setTextColor(getActivity().getResources()
							.getColor(R.color.black));
					ZBZ_TextView.setTextColor(getActivity().getResources()
							.getColor(R.color.kanpan_chengse));
					BLG_view.setBackgroundColor(getActivity().getResources()
							.getColor(android.R.color.transparent));
					ZBZ_view.setBackgroundColor(getActivity().getResources()
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
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private OnClickListener mClickListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {

				switch (v.getId()) {
				case R.id.textView1:
					BLG_TextView.setTextColor(getActivity().getResources()
							.getColor(R.color.kanpan_chengse));
					ZBZ_TextView.setTextColor(getActivity().getResources()
							.getColor(R.color.black));
					BLG_view.setBackgroundColor(getActivity().getResources()
							.getColor(R.color.kanpan_chengse));
					ZBZ_view.setBackgroundColor(getActivity().getResources()
							.getColor(android.R.color.transparent));
					mViewPager.setCurrentItem(0);
					break;
				case R.id.textView2:
					BLG_TextView.setTextColor(getActivity().getResources()
							.getColor(R.color.black));
					ZBZ_TextView.setTextColor(getActivity().getResources()
							.getColor(R.color.kanpan_chengse));
					BLG_view.setBackgroundColor(getActivity().getResources()
							.getColor(android.R.color.transparent));
					ZBZ_view.setBackgroundColor(getActivity().getResources()
							.getColor(R.color.kanpan_chengse));
					mViewPager.setCurrentItem(1);
					break;

				default:
					break;
				}
			}
		};

	}

	class FragmentAdapter extends FragmentStatePagerAdapter {

		public FragmentAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 0:
				return new ZhiBoFragment();
			case 1:
				return new ZhiboWeiBoFragment();
			default:
				return new ZhiBoFragment();
			}

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}

	}
}
