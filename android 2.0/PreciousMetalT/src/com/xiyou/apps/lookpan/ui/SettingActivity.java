package com.xiyou.apps.lookpan.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.context.SystemContext;

import com.umeng.message.PushAgent;
import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.BaseActivity;

public class SettingActivity extends BaseActivity implements OnClickListener{
	
	private  LinearLayout refreshFrequency  ,klineSetting ,remindSetting , clearTrade;
	
	private ImageView highlight ,tuisong , showPic ,textFont;
	
	private TextView refeshTime ;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
		initListener();
	}
	
	public void initView () {
		refreshFrequency = (LinearLayout) findViewById(R.id.refresh_frequency) ;
		klineSetting = (LinearLayout) findViewById(R.id.ll_kline_setting) ;
		remindSetting = (LinearLayout) findViewById(R.id.ll_remind_setting) ;
		clearTrade = (LinearLayout) findViewById(R.id.ll_clear_trade) ;
		
		highlight  = (ImageView) findViewById(R.id.iv_highlight) ;
		tuisong  = (ImageView) findViewById(R.id.iv_tuisong) ;
		showPic  = (ImageView) findViewById(R.id.iv_show_pic) ;
		textFont  = (ImageView) findViewById(R.id.iv_text_font) ;
		
		refeshTime = (TextView) findViewById(R.id.tv_refesh_time) ;
		
		refeshTime.setText("" + (AppSetting.getInstance(SettingActivity.this).getRefreshTime()/1000) + "秒");
		
	}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(AppSetting.getInstance(this).isHighlightFlag()) {
			highlight.setBackgroundResource(R.drawable.switch_on);
		}else{
			highlight.setBackgroundResource(R.drawable.switch_off);
		}
		
		if(AppSetting.getInstance(this).isTuisongFlagFlag()) {
			tuisong.setBackgroundResource(R.drawable.switch_on);
		}else{
			tuisong.setBackgroundResource(R.drawable.switch_off);
		}
		
		if(AppSetting.getInstance(this).isShowNewsPic()) {
			showPic.setBackgroundResource(R.drawable.switch_on);
		}else{
			showPic.setBackgroundResource(R.drawable.switch_off);
		}
		if(AppSetting.getInstance(this).isBigTextFont()) {
			textFont.setBackgroundResource(R.drawable.ziti_da);
		}else{
			textFont.setBackgroundResource(R.drawable.ziti_xiao);
		}
	}
	
	public void initListener () {
		refreshFrequency.setOnClickListener(this);
		klineSetting.setOnClickListener(this);
		remindSetting.setOnClickListener(this);
		clearTrade.setOnClickListener(this);
		
		highlight.setOnClickListener(this);
		tuisong.setOnClickListener(this);
		showPic.setOnClickListener(this);
		textFont.setOnClickListener(this);
	}
	
	public void back(View view) {
		finish();
	}

	private String[] items = {"5秒", "10秒" ,"15秒" ,"30秒" ,"1分钟"};
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.refresh_frequency:
			AlertDialog dialog = new AlertDialog.Builder(SettingActivity.this)
			.setItems(items, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					switch (which) {
					case 0:
						AppSetting.getInstance(SettingActivity.this).setRefreshTime(5000);
						refeshTime.setText("" + (AppSetting.getInstance(SettingActivity.this).getRefreshTime()/1000) + "秒");
						break;
					case 1:
						AppSetting.getInstance(SettingActivity.this).setRefreshTime(10000);
						refeshTime.setText("" + (AppSetting.getInstance(SettingActivity.this).getRefreshTime()/1000) + "秒");
						break;
					case 2:
						AppSetting.getInstance(SettingActivity.this).setRefreshTime(15000);
						refeshTime.setText("" + (AppSetting.getInstance(SettingActivity.this).getRefreshTime()/1000) + "秒");
						break;
					case 3:
						AppSetting.getInstance(SettingActivity.this).setRefreshTime(30000);
						refeshTime.setText("" + (AppSetting.getInstance(SettingActivity.this).getRefreshTime()/1000) + "秒");
						break;
					case 4:
						AppSetting.getInstance(SettingActivity.this).setRefreshTime(60000);
						refeshTime.setText("" + (AppSetting.getInstance(SettingActivity.this).getRefreshTime()/1000) + "秒");
						break;

					default:
						break;
					}
				}
			}).create() ;
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
			
			break;
		case R.id.iv_highlight:
			if(AppSetting.getInstance(SettingActivity.this).isHighlightFlag()) {
				highlight.setBackgroundResource(R.drawable.switch_off);
				setLockPatternEnabled(false);
				AppSetting.getInstance(SettingActivity.this).setHighlightFlag(false);
			}else{
				highlight.setBackgroundResource(R.drawable.switch_on);
				setLockPatternEnabled(true);
				AppSetting.getInstance(SettingActivity.this).setHighlightFlag(true);
			}
			break;
		case R.id.ll_kline_setting:
			
			Intent intent = new Intent(SettingActivity.this, ParamSetActivity.class) ;
			startActivity(intent);
			
			break;
		case R.id.ll_remind_setting:  //消息提醒
			Intent intentRemind = new Intent(SettingActivity.this, RemindActivity.class) ;
			startActivity(intentRemind);
			break;
		case R.id.ll_clear_trade:    //清空模拟交易记录 
			SystemContext.getInstance(SettingActivity.this).getDatabaseHelper().clearTradeTable(); 
			break;
		case R.id.iv_tuisong:
			if(AppSetting.getInstance(SettingActivity.this).isTuisongFlagFlag()) {
				tuisong.setBackgroundResource(R.drawable.switch_off);
				AppSetting.getInstance(SettingActivity.this).setTuisongFlag(false);
				PushAgent.getInstance(this).disable();
			}else{
				tuisong.setBackgroundResource(R.drawable.switch_on);
				AppSetting.getInstance(SettingActivity.this).setTuisongFlag(true);
				PushAgent.getInstance(this).enable();
			}
			break;
		case R.id.iv_show_pic:    
			if(AppSetting.getInstance(SettingActivity.this).isShowNewsPic()){
				AppSetting.getInstance(SettingActivity.this).setShowNewsPic(false);
				showPic.setBackgroundResource(R.drawable.switch_off);
			}else{
				AppSetting.getInstance(SettingActivity.this).setShowNewsPic(true);
				showPic.setBackgroundResource(R.drawable.switch_on);
			}
			break;
		case R.id.iv_text_font:    //清空模拟交易记录 
			if(AppSetting.getInstance(SettingActivity.this).isBigTextFont()){
				AppSetting.getInstance(SettingActivity.this).setBigTextFont(false);
				textFont.setBackgroundResource(R.drawable.ziti_xiao);
			}else{
				AppSetting.getInstance(SettingActivity.this).setBigTextFont(true);
				textFont.setBackgroundResource(R.drawable.ziti_da);
			}
			
			break;

		default:
			break;
		}
	}
	
	

	public void setLockPatternEnabled(boolean enabled) {
		setBoolean(android.provider.Settings.System.LOCK_PATTERN_ENABLED,
				enabled);
	}
	private void setBoolean(String systemSettingKey, boolean enabled) {
		android.provider.Settings.System.putInt(getContentResolver(),
				systemSettingKey, enabled ? 1 : 0);
	}


}
