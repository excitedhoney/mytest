package com.venus.carpapa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.venus.carpapa.R;


public class WelcomeActivity extends BaseActivity{
	private static final long WELCOME_DISPLAY_TIME = 2000;// 欢迎界面显示时间

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		new Handler().postDelayed(new Runnable() {
			public void run() {

				startActivity(new Intent(WelcomeActivity.this,
						LoginActivity.class));

//				WelcomeActivity.this.overridePendingTransition(R.anim.inside,
//						R.anim.outside);

				finish();
			}
		}, WELCOME_DISPLAY_TIME);
	}
	
}
