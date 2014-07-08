package com.xiyou.apps.lookpan;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiyou.apps.lookpan.fragment.HangQingFragment;
import com.xiyou.apps.lookpan.fragment.MeFragment;
import com.xiyou.apps.lookpan.fragment.ZhiBo_2_Fragment;
import com.xiyou.apps.lookpan.fragment.ZiXunFragment;

public class MainActivity extends Activity {

	private ImageView tab1;
	private ImageView tab2;
	private ImageView tab3;
	private ImageView tab4;
	private RelativeLayout r1;
	private RelativeLayout r2;
	private RelativeLayout r3;
	private RelativeLayout r4;
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;
	private HangQingFragment mHangQingFragment;
	private MeFragment myMeFragment;
	private ZhiBo_2_Fragment mZhiboFragment;
	private ZiXunFragment mZiXunFragment;
	private String Tag;
	private ActionBar mActionBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mActionBar = getActionBar();
		mActionBar.setTitle("行情");
		mActionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.title_shang));
		findView();
		setContent();
	}

	@SuppressLint("NewApi")
	private void setContent() {
		mHangQingFragment = new HangQingFragment();
		// myMeFragment = new MeFragment();
		// mZhiboFragment = new ZhiBo_2_Fragment();
		// mZiXunFragment = new ZiXunFragment();
		// getFragmentManager().beginTransaction().add(R.id.content,
		// myMeFragment)
		// .commit();
		// getFragmentManager().beginTransaction()
		// .add(R.id.content, mZhiboFragment).commit();
		// getFragmentManager().beginTransaction()
		// .add(R.id.content, mZiXunFragment).commit();
		Tag = "HangQingFragment";
		getFragmentManager().beginTransaction()
				.add(R.id.content, mHangQingFragment, Tag).commit();
		// getFragmentManager().saveFragmentInstanceState(mHangQingFragment);
		// getFragmentManager().saveFragmentInstanceState(mZhiboFragment);
		// getFragmentManager().saveFragmentInstanceState(mZiXunFragment);
		// getFragmentManager().saveFragmentInstanceState(myMeFragment);

	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	private void findView() {
		tab1 = (ImageView) findViewById(R.id.tab1);
		tab2 = (ImageView) findViewById(R.id.tab2);
		tab3 = (ImageView) findViewById(R.id.tab3);
		tab4 = (ImageView) findViewById(R.id.tab4);
		r1 = (RelativeLayout) findViewById(R.id.r1);
		r2 = (RelativeLayout) findViewById(R.id.r2);
		r3 = (RelativeLayout) findViewById(R.id.r3);
		r4 = (RelativeLayout) findViewById(R.id.r4);
		tv1 = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textView2);
		tv3 = (TextView) findViewById(R.id.textView3);
		tv4 = (TextView) findViewById(R.id.textView4);
		r1.setOnClickListener(myOnClickListener());
		r2.setOnClickListener(myOnClickListener());
		r3.setOnClickListener(myOnClickListener());
		r4.setOnClickListener(myOnClickListener());
	}

	private OnClickListener myOnClickListener() {
		return new OnClickListener() {
			@SuppressLint({ "NewApi", "CommitTransaction" })
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.r1:
					mActionBar.setTitle("行情");
					tab1.setImageResource(R.drawable.price_pressed);
					tab2.setImageResource(R.drawable.xinwen_1);
					tab3.setImageResource(R.drawable.zixun_1);
					tab4.setImageResource(R.drawable.wo_1);
					tv1.setTextColor(MainActivity.this.getResources().getColor(
							R.color.kanpan_chengse));
					tv2.setTextColor(MainActivity.this.getResources().getColor(
							R.color.kanpan_greey));
					tv3.setTextColor(MainActivity.this.getResources().getColor(
							R.color.kanpan_greey));
					tv4.setTextColor(MainActivity.this.getResources().getColor(
							R.color.kanpan_greey));
					if (null == mHangQingFragment) {
						mHangQingFragment = new HangQingFragment();
						getFragmentManager()
								.beginTransaction()
								.hide(getFragmentManager().findFragmentByTag(
										Tag))
								.add(R.id.content, mHangQingFragment,
										"HangQingFragment").commit();
					} else {
						if (!Tag.equals("HangQingFragment")) {
							getFragmentManager()
									.beginTransaction()
									.hide(getFragmentManager()
											.findFragmentByTag(Tag))
									.show(mHangQingFragment).commit();
						}
					}
					Tag = "HangQingFragment";

					break;
				case R.id.r2:
					mActionBar.setTitle("资讯");
					tab1.setImageResource(R.drawable.hangqing_1);
					tab2.setImageResource(R.drawable.information_pressed);
					tab3.setImageResource(R.drawable.zixun_1);
					tab4.setImageResource(R.drawable.wo_1);
					tv1.setTextColor(MainActivity.this.getResources().getColor(
							R.color.kanpan_greey));
					tv2.setTextColor(MainActivity.this.getResources().getColor(
							R.color.kanpan_chengse));
					tv3.setTextColor(MainActivity.this.getResources().getColor(
							R.color.kanpan_greey));
					tv4.setTextColor(MainActivity.this.getResources().getColor(
							R.color.kanpan_greey));
					if (null == mZiXunFragment) {
						mZiXunFragment = new ZiXunFragment();
						getFragmentManager()
								.beginTransaction()
								.hide(getFragmentManager().findFragmentByTag(
										Tag))
								.add(R.id.content, mZiXunFragment,
										"ZiXunFragment").commit();
					} else {
						if (!Tag.equals("ZiXunFragment")) {
							getFragmentManager()
									.beginTransaction()
									.hide(getFragmentManager()
											.findFragmentByTag(Tag))
									.show(mZiXunFragment).commit();
						}
					}

					Tag = "ZiXunFragment";
					break;
				case R.id.r3:
					mActionBar.setTitle("直播");
					tab1.setImageResource(R.drawable.hangqing_1);
					tab2.setImageResource(R.drawable.xinwen_1);
					tab3.setImageResource(R.drawable.live_pressed);
					tab4.setImageResource(R.drawable.wo_1);
					tv1.setTextColor(MainActivity.this.getResources().getColor(
							R.color.kanpan_greey));
					tv2.setTextColor(MainActivity.this.getResources().getColor(
							R.color.kanpan_greey));
					tv3.setTextColor(MainActivity.this.getResources().getColor(
							R.color.kanpan_chengse));
					tv4.setTextColor(MainActivity.this.getResources().getColor(
							R.color.kanpan_greey));
					if (null == mZhiboFragment) {
						mZhiboFragment = new ZhiBo_2_Fragment();
						getFragmentManager()
								.beginTransaction()
								.hide(getFragmentManager().findFragmentByTag(
										Tag))
								.add(R.id.content, mZhiboFragment,
										"ZhiBo_2_Fragment").commit();
					} else {
						if (!Tag.equals("ZhiBo_2_Fragment")) {
							getFragmentManager()
									.beginTransaction()
									.hide(getFragmentManager()
											.findFragmentByTag(Tag))
									.show(mZhiboFragment).commit();
						}
					}

					Tag = "ZhiBo_2_Fragment";
					break;
				case R.id.r4:
					mActionBar.setTitle("我");
					tab1.setImageResource(R.drawable.hangqing_1);
					tab2.setImageResource(R.drawable.xinwen_1);
					tab3.setImageResource(R.drawable.zixun_1);
					tab4.setImageResource(R.drawable.me_pressed);
					tv1.setTextColor(MainActivity.this.getResources().getColor(
							R.color.kanpan_greey));
					tv2.setTextColor(MainActivity.this.getResources().getColor(
							R.color.kanpan_greey));
					tv3.setTextColor(MainActivity.this.getResources().getColor(
							R.color.kanpan_greey));
					tv4.setTextColor(MainActivity.this.getResources().getColor(
							R.color.kanpan_chengse));
					if (null == myMeFragment) {
						myMeFragment = new MeFragment();
						getFragmentManager()
								.beginTransaction()
								.hide(getFragmentManager().findFragmentByTag(
										Tag))
								.add(R.id.content, myMeFragment, "MeFragment")
								.commit();
					} else {
						if (!Tag.equals("MeFragment")) {
							getFragmentManager()
									.beginTransaction()
									.hide(getFragmentManager()
											.findFragmentByTag(Tag))
									.show(myMeFragment).commit();
						}
					}
					Tag = "MeFragment";

					break;

				default:
					break;
				}

			}
		};

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}