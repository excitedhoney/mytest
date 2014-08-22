package cn.precious.metal.ui;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import cn.precious.metal.R;
import cn.precious.metal.base.TitleActivity;
import cn.precious.metal.ui.alarm.AlarmService;
import cn.precious.metal.ui.fragment.MeFragment;
import cn.precious.metal.ui.fragment.OptionalFragment;
import cn.precious.metal.ui.fragment.StrategyFragment;
import cn.precious.metal.ui.fragment.news.NewsFragment;
import cn.precious.metal.utils.Utils;

public class MainActivity extends TitleActivity {

	public static final String MARKET = "MARKET";

	public static final String NEWS = "NEWS";

	public static final String LIVING = "LIVING";

	public static final String ME = "ME";

	private String currentTag;

	private View marketTab;

	private View newsTab;

	private View livingTab;

	private View meTab;

	TabHost mTabHost;

	TabManager mTabManager;

	TabWidget mTabWidger;

	private Map<String, View> tabWidgetViews = new HashMap<String, View>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (!Utils.isServiceRunning(getApplicationContext(),
				"cn.precious.metal.ui.alarm.AlarmService")) {
			Intent show = new Intent(this, AlarmService.class);
			startService(show);
		}

		initWidgetContent();
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabWidger = (TabWidget) findViewById(android.R.id.tabs);
		mTabHost.setup();
		mTabManager = new TabManager(this, mTabHost, R.id.frag_container);
		// 行情
		mTabManager.addTab(mTabHost.newTabSpec(MARKET).setIndicator(marketTab),
				OptionalFragment.class, null);
		// 咨询
		mTabManager.addTab(mTabHost.newTabSpec(NEWS).setIndicator(newsTab),
				NewsFragment.class, null);
		// 直播
		mTabManager.addTab(mTabHost.newTabSpec(LIVING).setIndicator(livingTab),
				StrategyFragment.class, null);

		mTabManager.addTab(mTabHost.newTabSpec(ME).setIndicator(meTab),
				MeFragment.class, null);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		mTabHost.setCurrentTabByTag(currentTag);

	}

	public void initWidgetContent() {
		marketTab = (View) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, null);
		TextView text0 = (TextView) marketTab.findViewById(R.id.title);
		TextView text0eng = (TextView) marketTab.findViewById(R.id.eng_title);
		text0.setText("行情");
		text0eng.setText("Quotations");
		tabWidgetViews.put(MARKET, marketTab);

		newsTab = (View) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, null);
		TextView text4 = (TextView) newsTab.findViewById(R.id.title);
		TextView text4eng = (TextView) newsTab.findViewById(R.id.eng_title);
		text4.setText("资讯");
		text4eng.setText("News");
		tabWidgetViews.put(NEWS, newsTab);

		livingTab = (View) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, null);
		TextView text1 = (TextView) livingTab.findViewById(R.id.title);
		TextView text1eng = (TextView) livingTab.findViewById(R.id.eng_title);
		text1.setText("策略");
		text1eng.setText("Strategies");
		tabWidgetViews.put(LIVING, livingTab);

		meTab = (View) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, null);
		TextView text2 = (TextView) meTab.findViewById(R.id.title);
		TextView text2eng = (TextView) meTab.findViewById(R.id.eng_title);
		text2.setText("我");
		text2eng.setText("Me");
		tabWidgetViews.put(ME, meTab);

	}

	@Override
	public void onClick(View v) {
		if (getRightLayoutId2() == v.getId()) {
			intentTo(this, SearchActivity.class);
		} else if (getRightLayoutId() == v.getId()) {
			intentTo(this, EditOptionalActivity.class);
		}
		// else if (getLeftLayoutId() == v.getId()) {
		// if (MARKET.equalsIgnoreCase(currentTag)) {
		// intentTo(this, EditOptionalActivity.class);
		// }
		// }
	}

	/**
	 * This is a helper class that implements a generic mechanism for
	 * associating fragments with the tabs in a tab host. It relies on a trick.
	 * Normally a tab host has a simple API for supplying a View or Intent that
	 * each tab will show. This is not sufficient for switching between
	 * fragments. So instead we make the content part of the tab host 0dp high
	 * (it is not shown) and the TabManager supplies its own dummy view to show
	 * as the tab content. It listens to changes in tabs, and takes care of
	 * switch to the correct fragment shown in a separate content area whenever
	 * the selected tab changes.
	 */
	public class TabManager implements TabHost.OnTabChangeListener {
		private final FragmentActivity mActivity;
		private final TabHost mTabHost;
		private final int mContainerId;
		private HashMap<String, TabInfo> mTabs;
		TabInfo mLastTab;

		class TabInfo {
			private String tag;
			private Class<?> clss;
			private Bundle args;
			private Fragment fragment;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabManager(FragmentActivity activity, TabHost tabHost,
				int containerId) {
			mTabs = new HashMap<String, TabInfo>();
			mActivity = activity;
			mTabHost = tabHost;
			mContainerId = containerId;
			mTabHost.setOnTabChangedListener(this);
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
			tabSpec.setContent(new DummyTabFactory(mActivity));

			String tag = tabSpec.getTag();
			// Check to see if we already have a fragment for this tab, probably
			// from a previously saved state. If so, deactivate it, because our
			// initial state is that a tab isn't shown.
			TabInfo info = new TabInfo(tag, clss, args);

			info.fragment = mActivity.getSupportFragmentManager()
					.findFragmentByTag(tag);
			// if (info.fragment != null && !info.fragment.isDetached()) {
			// FragmentTransaction ft = mActivity.getSupportFragmentManager()
			// .beginTransaction();
			// ft.detach(info.fragment);
			// ft.commit();
			// }

			mTabs.put(tag, info);
			mTabHost.addTab(tabSpec);
		}

		@Override
		public void onTabChanged(String tabId) {

			currentTag = tabId;

			initLeftTitle(tabId);
			TabInfo newTab = mTabs.get(tabId);
			if (mLastTab != newTab) {
				FragmentTransaction ft = mActivity.getSupportFragmentManager()
						.beginTransaction();
				if (mLastTab != null) {
					Fragment fragment = mLastTab.fragment;
					if (fragment != null) {
						ft.hide(fragment);
					}
				}
				if (newTab != null) {
					if (newTab.fragment == null) {
						newTab.fragment = Fragment.instantiate(mActivity,
								newTab.clss.getName(), newTab.args);
						ft.add(mContainerId, newTab.fragment, newTab.tag);
					} else {
						ft.show(newTab.fragment);
					}
				}
				mLastTab = newTab;
				ft.commit();
				mActivity.getSupportFragmentManager()
						.executePendingTransactions();
			}
		}

	}

	public void initLeftTitle(String tag) {
		if (MARKET.equalsIgnoreCase(tag)) {
			getTitleText().setText("行情");
		} else if (LIVING.equalsIgnoreCase(tag)) {
			getTitleText().setText("策略");
		} else if (NEWS.equalsIgnoreCase(tag)) {
			getTitleText().setText("资讯");
		} else if (ME.equalsIgnoreCase(tag)) {
			getTitleText().setText("我");
		}

		if (MARKET.equalsIgnoreCase(tag)) {
			// getLeftImageView().setVisibility(View.VISIBLE);
			// getLeftImageView().setClickable(false);

			getLeftLayout().setVisibility(View.GONE);
			getRightLayout2().setVisibility(View.VISIBLE);
			getRightLayout().setVisibility(View.VISIBLE);

			// getLeftImageView().setBackgroundResource(
			// R.drawable.bg_edit_optional);
			getRightImageView2().setBackgroundResource(R.drawable.bg_search);
			getRightImageView().setBackgroundResource(
					R.drawable.bg_edit_optional);
		} else {
			getLeftImageView().setVisibility(View.GONE);
			getRightLayout2().setVisibility(View.GONE);
			getRightLayout().setVisibility(View.GONE);
		}
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(MainActivity.this, "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
