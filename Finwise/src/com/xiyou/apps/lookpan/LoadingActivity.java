package com.xiyou.apps.lookpan;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.baidu.mobstat.StatService;
import com.umeng.message.PushAgent;
import com.umeng.update.UmengUpdateAgent;
import com.xiyou.apps.lookpan.db.BaseDataBase;
import com.xiyou.apps.lookpan.fragment.showactivity.LoadingDaoHangActivity;
import com.xiyou.apps.lookpan.utils.Util;

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
		PushAgent mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable();
		UmengUpdateAgent.update(this);
		startTime = System.currentTimeMillis();
		try {
			BaseDataBase.getInstance(this).open();
			if (BaseDataBase.getInstance(this).SelectLoading().size() < 1) {
				BaseDataBase.getInstance(this).insertLoading(
						Util.getVersionCode(this) + "");
				open = "open";
			} else {
				if (BaseDataBase.getInstance(this).SelectLoading().get(0)
						.equals("close")
						|| BaseDataBase.getInstance(this).SelectLoading()
								.get(0).equals("open")) {
					open = "open";
					BaseDataBase.getInstance(this).Update(
							Util.getVersionCode(this) + "");
				} else {
					if (Util.getVersionCode(this) > Integer
							.getInteger(BaseDataBase.getInstance(this)
									.SelectLoading().get(0))) {
						open = "open";
						BaseDataBase.getInstance(this).Update(
								Util.getVersionCode(this) + "");
					} else {
						open = "close";
					}

				}
			}
		} catch (Exception E) {
			E.printStackTrace();
		} finally {
			BaseDataBase.getInstance(this).close();
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
					{
						if ("open".equals(open)) {
							LoadingActivity.this.finish();
							startActivity(new Intent(getApplicationContext(),
									LoadingDaoHangActivity.class));
						} else {
							LoadingActivity.this.finish();
							startActivity(new Intent(getApplicationContext(),
									MainActivity.class));
						}
					}
				}
			};
			timer.schedule(task, SHOW_TIME - remainTime);
		}
	}

	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

}
