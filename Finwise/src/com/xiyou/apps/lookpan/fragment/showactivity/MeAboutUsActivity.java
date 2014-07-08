package com.xiyou.apps.lookpan.fragment.showactivity;

import android.os.Bundle;

import com.xiyou.apps.lookpan.R;

public class MeAboutUsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.me_aboutus);
		mActionBar.setTitle("关于我们");
	}

}
