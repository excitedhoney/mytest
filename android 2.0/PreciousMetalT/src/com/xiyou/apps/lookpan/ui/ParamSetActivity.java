package com.xiyou.apps.lookpan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.BaseActivity;

public class ParamSetActivity extends BaseActivity implements OnClickListener {

	private LinearLayout llSma, llEma, llBool;

	private LinearLayout llMacd, llKdi, llRsi;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_param_set);
		initView();
		initListener();
	}

	public void back(View v) {
		finish();
	}

	public void initView() {
		llSma = (LinearLayout) findViewById(R.id.ll_sma);
		llEma = (LinearLayout) findViewById(R.id.ll_ema);
		llBool = (LinearLayout) findViewById(R.id.ll_bool);

		llMacd = (LinearLayout) findViewById(R.id.ll_macd);
		llKdi = (LinearLayout) findViewById(R.id.ll_kdi);
		llRsi = (LinearLayout) findViewById(R.id.ll_rsi);

	}

	public void initListener() {
		llSma.setOnClickListener(this);
		llEma.setOnClickListener(this);
		llBool.setOnClickListener(this);

		llMacd.setOnClickListener(this);
		llKdi.setOnClickListener(this);
		llRsi.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_sma:
			Intent itSma = new Intent(ParamSetActivity.this,
					KlineNormSetActiviy.class);
			itSma.putExtra("norm", "sma");
			startActivity(itSma);
			break;
		case R.id.ll_ema:
			Intent itEma = new Intent(ParamSetActivity.this,
					KlineNormSetActiviy.class);
			itEma.putExtra("norm", "ema");
			startActivity(itEma);
			break;
		case R.id.ll_bool:
			Intent itBool = new Intent(ParamSetActivity.this,
					KlineNormSetActiviy.class);
			itBool.putExtra("norm", "bool");
			startActivity(itBool);
			break;
		case R.id.ll_macd:
			Intent itMacd = new Intent(ParamSetActivity.this,
					KlineNormSetActiviy.class);
			itMacd.putExtra("norm", "macd");
			startActivity(itMacd);
			break;
		case R.id.ll_kdi:
			Intent itKdi = new Intent(ParamSetActivity.this,
					KlineNormSetActiviy.class);
			itKdi.putExtra("norm", "kdj");
			startActivity(itKdi);
			break;
		case R.id.ll_rsi:
			Intent itRsi = new Intent(ParamSetActivity.this,
					KlineNormSetActiviy.class);
			itRsi.putExtra("norm", "rsi");
			startActivity(itRsi);
			break;

		default:
			break;
		}
	}

}
