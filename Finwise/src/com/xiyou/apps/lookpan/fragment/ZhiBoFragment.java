package com.xiyou.apps.lookpan.fragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.view.PullScrollView;

public class ZhiBoFragment extends Fragment {

	private ActionBar mActionBar;
	private zhibo_1_Fragment mZhibo_1_Fragment;
	private ZhiboWeiBoFragment mZhiboWeiBoFragment;
	// private TextView zhuye;
	// private TextView zhibo;
	private String Tag;
	private ScrollView mPullScrollView;
	private ImageView mImageView;
	private Context mContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_zhibo, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		// zhuye = (TextView) view.findViewById(R.id.textView3);
		// zhibo = (TextView) view.findViewById(R.id.textView5);
		mPullScrollView = (ScrollView) view.findViewById(R.id.pullscrollview);
		mImageView = (ImageView) view.findViewById(R.id.img_bigShopLogo);
		mImageView.setImageResource(R.drawable.q_);
		// mPullScrollView.init(mImageView);
		// mPullScrollView.setOnTurnListener(ZhiBoFragment.this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActionBar = getActivity().getActionBar();
		mActionBar.setTitle("直播");

		setContent();
		mContext = getActivity();
		// zhuye.setOnClickListener(getClickListener());
		// zhibo.setOnClickListener(getClickListener());
		// mPullScrollView.setOnTurnListener(this);
		// ((Object) mPullScrollView).setHeader(mImageView);
	}

	private void setContent() {
		// TODO Auto-generated method stub
		mZhibo_1_Fragment = new zhibo_1_Fragment();
		getFragmentManager().beginTransaction()
				.add(R.id.content1, mZhibo_1_Fragment, "10").commit();
		Tag = "10";
	}

}
