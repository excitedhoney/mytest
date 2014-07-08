package com.venus.carpapa.act.shigu;

import com.venus.carpapa.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ShiguActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.act_shigu);
	}
	public void back(View v) {
		this.finish();
	}
}
