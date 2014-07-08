package com.xiyou.apps.lookpan.fragment.showactivity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.fragment.ZhiBoFragment;

public class ShowZhiboActivity extends Activity {

	ZhiBoFragment mZhiBoFragment;
	ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_zhibo_2);
		mActionBar = getActionBar();

		mActionBar.setTitle("直播嘉宾");

		mActionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.title_shang));
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mZhiBoFragment = new ZhiBoFragment();
		getFragmentManager().beginTransaction()
				.add(R.id.content, mZhiBoFragment, "10").commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
