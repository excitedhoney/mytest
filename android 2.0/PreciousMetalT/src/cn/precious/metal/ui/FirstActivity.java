package cn.precious.metal.ui;

import com.umeng.message.PushAgent;

import android.content.Intent;
import android.os.Bundle;
import cn.precious.metal.base.BaseActivity;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.context.SystemContext;
import cn.precious.metal.ui.alarm.AlarmService;
import cn.precious.metal.utils.Utils;

public class FirstActivity extends BaseActivity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SystemContext.getInstance(FirstActivity.this);
		
		if(AppSetting.getInstance(this).isTuisongFlagFlag()) {
			PushAgent mPushAgent = PushAgent.getInstance(this);
			mPushAgent.enable();
			mPushAgent.onAppStart();
		}
		
		
		
//		if (AppSetting.getInstance(this).isNeedGuide()) {
//			Intent intent = new Intent(this, GuideActivity.class);
//			startActivity(intent);
//			this.finish();
//		} else {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			this.finish();
//		}
	}
}
