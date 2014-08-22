package cn.precious.metal.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.umeng.message.PushAgent;

import cn.precious.metal.R;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.context.SystemContext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class LoadingActivity extends Activity {
	private static int SHOW_TIME = 3000;
	private Timer timer;
	private TimerTask task;
	private long startTime, endTime;
	private String open;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loading);
		startTime = System.currentTimeMillis();

		SystemContext.getInstance(LoadingActivity.this);

		if (AppSetting.getInstance(this).isTuisongFlagFlag()) {
			PushAgent mPushAgent = PushAgent.getInstance(this);
			mPushAgent.enable();
			mPushAgent.onAppStart();
		}    

		
		
		goHomepage();
	}

	private void goHomepage() {
		endTime = System.currentTimeMillis();
		long remainTime = endTime - startTime;
		if (remainTime < SHOW_TIME) {
			timer = new Timer();
			task = new TimerTask() {
				@Override
				public void run() {
					LoadingActivity.this.finish();
					startActivity(new Intent(getApplicationContext(),
							MainActivity.class));
				}
			};
			timer.schedule(task, SHOW_TIME - remainTime);
		}
	}

	public void onResume() {
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}

}
