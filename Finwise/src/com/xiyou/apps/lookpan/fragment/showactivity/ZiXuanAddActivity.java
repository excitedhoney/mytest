package com.xiyou.apps.lookpan.fragment.showactivity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.baidu.mobstat.StatService;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMSsoHandler;
import com.xiyou.apps.lookpan.MainActivity;
import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.fragment.Add_AFragment;

public class ZiXuanAddActivity extends Activity {

	private static final String FrA_Url = "1";
	private static final String FrB_Url = "2";
	private static final String FrC_Url = "3";
	private static final String FrD_Url = "4";
	private static final String FrE_Url = "5";
	private ActionBar mActionBar;
	final UMSocialService mController = UMServiceFactory.getUMSocialService(
			"com.umeng.share", RequestType.SOCIAL);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_zixuan);
		mActionBar = getActionBar();
		mActionBar.setTitle("编辑自选");
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		ActionBar.Tab tabA = mActionBar.newTab().setText("津贵所");
		ActionBar.Tab tabB = mActionBar.newTab().setText("上金所");
		ActionBar.Tab tabC = mActionBar.newTab().setText("外汇");
		ActionBar.Tab tabD = mActionBar.newTab().setText("商品");
		ActionBar.Tab tabE = mActionBar.newTab().setText("股指");

		tabA.setTabListener(new MyTabsListener<Add_AFragment>(this, FrA_Url,
				Add_AFragment.class));

		tabB.setTabListener(new MyTabsListener<Add_AFragment>(this, FrB_Url,
				Add_AFragment.class));

		tabC.setTabListener(new MyTabsListener<Add_AFragment>(this, FrC_Url,
				Add_AFragment.class));
		tabD.setTabListener(new MyTabsListener<Add_AFragment>(this, FrD_Url,
				Add_AFragment.class));

		tabE.setTabListener(new MyTabsListener<Add_AFragment>(this, FrE_Url,
				Add_AFragment.class));

		mActionBar.addTab(tabA);
		mActionBar.addTab(tabB);
		mActionBar.addTab(tabC);
		mActionBar.addTab(tabD);
		mActionBar.addTab(tabE);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.news_other, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			Intent i = new Intent();
			i.setClass(ZiXuanAddActivity.this, MainActivity.class);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}

	protected class MyTabsListener<T extends Fragment> implements
			ActionBar.TabListener {
		private Fragment fragment;

		private final Activity mActivity;
		private final String mTag;
		private final Class<T> mClass;

		public MyTabsListener(Activity activity, String tag, Class<T> clz) {
			mActivity = activity;
			mTag = tag;
			mClass = clz;
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// Check if the fragment is already initialized
			if (fragment == null) {
				// If not, instantiate and add it to the activity
				Bundle b = new Bundle();
				b.putString("tag", mTag);
				fragment = Add_AFragment.instantiate(mActivity,
						mClass.getName(), b);
				ft.add(android.R.id.content, fragment, mTag);
			} else {
				// If it exists, simply attach it in order to show it
				ft.attach(fragment);
			}
		}

		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {

		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (fragment != null) {
				// Detach the fragment, because another one is being attached
				ft.detach(fragment);
			}
		}

	}

}