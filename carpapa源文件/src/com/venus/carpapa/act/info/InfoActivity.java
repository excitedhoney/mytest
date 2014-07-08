package com.venus.carpapa.act.info;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.venus.carpapa.R;

public class InfoActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_task_info);
	}
	public void back(View v) {
		this.finish();
	}
	public void date(View v) {
		this.finish();
	}
}
