package com.xiyou.apps.lookpan.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.precious.metal.config.AppSetting;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.BaseActivity;

public class KlineNormSetActiviy extends BaseActivity{ 
	
	private TextView  tvParam1 ,tvParam2 ,tvParam3 ;
	
	private EditText  etParam1 ,etParam2 ,etParam3 ;
	
	private TextView title ,paramDesc ;
	
	private Button btnSet ;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_norm_set);
		initView();
		initFrame();
	}
	
	public void back(View v) {
		finish();
	}
	
	public void initView () {
		tvParam1 = (TextView) findViewById(R.id.tv_param1) ;
		tvParam2 = (TextView) findViewById(R.id.tv_param2) ;
		tvParam3 = (TextView) findViewById(R.id.tv_param3) ;
		
		etParam1 = (EditText) findViewById(R.id.et_param1) ;
		etParam2 = (EditText) findViewById(R.id.et_param2) ;
		etParam3 = (EditText) findViewById(R.id.et_param3) ;
		
		title = (TextView) findViewById(R.id.title) ;
		paramDesc = (TextView) findViewById(R.id.param_desc) ;
		
		btnSet = (Button) findViewById(R.id.btn_set) ;
		btnSet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setParam();
			}
		});
	}
	
	
	private AppSetting setting  ;
	
	public void setParam () {
		String norm  =  getIntent().getStringExtra("norm") ;
		setting = AppSetting.getInstance(this) ;
		if("sma".equalsIgnoreCase(norm)) {
			String param1 = etParam1.getText().toString() ;
			String param2 = etParam2.getText().toString() ;
			String param3 = etParam3.getText().toString() ;
			if(param1  == null || "".equals(param1)) {
				showCusToast("参数1不能为空");
				return;
			}
			if(param2  == null || "".equals(param2)) {
				showCusToast("参数2不能为空");
				return;
			}
			if(param3  == null || "".equals(param3)) {
				showCusToast("参数3不能为空");
				return;
			}
			setting.setSmaParam1(Integer.parseInt(param1));
			setting.setSmaParam2(Integer.parseInt(param2));
			setting.setSmaParam3(Integer.parseInt(param3));
		}else if("ema".equalsIgnoreCase(norm)) {
			String param1 = etParam1.getText().toString() ;
			if(param1  == null || "".equals(param1)) {
				showCusToast("参数1不能为空");
				return;
			}
			setting.setEmaParam(Integer.parseInt(param1));
		}else if("bool".equalsIgnoreCase(norm)) {
			String param1 = etParam1.getText().toString() ;
			String param2 = etParam2.getText().toString() ;
			if(param1  == null || "".equals(param1)) {
				showCusToast("参数1不能为空");
				return;
			}
			if(param2  == null || "".equals(param2)) {
				showCusToast("参数2不能为空");
				return;
			}
			setting.setBoolTParam(Integer.parseInt(param1));
			setting.setBoolKParam(Integer.parseInt(param2));
		}else if("macd".equalsIgnoreCase(norm)) {
			String param1 = etParam1.getText().toString() ;
			String param2 = etParam2.getText().toString() ;
			String param3 = etParam3.getText().toString() ;
			if(param1  == null || "".equals(param1)) {
				showCusToast("参数1不能为空");
				return;
			}
			if(param2  == null || "".equals(param2)) {
				showCusToast("参数2不能为空");
				return;
			}
			if(param3  == null || "".equals(param3)) {
				showCusToast("参数3不能为空");
				return;
			}
			setting.setMacdTParam1(Integer.parseInt(param1));
			setting.setMacdTParam2(Integer.parseInt(param2));
			setting.setMacdKParam(Integer.parseInt(param3));
		}else if("kdj".equalsIgnoreCase(norm)) {
			String param1 = etParam1.getText().toString() ;
			if(param1  == null || "".equals(param1)) {
				showCusToast("参数1不能为空");
				return;
			}
			setting.setKdjParam(Integer.parseInt(param1));
		}else if("rsi".equalsIgnoreCase(norm)) {
			String param1 = etParam1.getText().toString() ;
			String param2 = etParam2.getText().toString() ;
			String param3 = etParam3.getText().toString() ;
			if(param1  == null || "".equals(param1)) {
				showCusToast("参数1不能为空");
				return;
			}
			if(param2  == null || "".equals(param2)) {
				showCusToast("参数2不能为空");
				return;
			}
			if(param3  == null || "".equals(param3)) {
				showCusToast("参数3不能为空");
				return;
			}
			setting.setRsiParam1(Integer.parseInt(param1));
			setting.setRsiParam2(Integer.parseInt(param2));
			setting.setRsiParam3(Integer.parseInt(param3));
		}
		finish();
	}
	
	
	
	public void initFrame () {
		String norm  =  getIntent().getStringExtra("norm") ;
		
		if("sma".equalsIgnoreCase(norm)) {
			title.setText("SMA设置");
			paramDesc.setText("n（你设置的参数）个周期的收盘价简单均线,有三个参数") ;
			tvParam1.setText("N1");
			tvParam2.setText("N2");
			tvParam3.setText("N3");
			
			etParam1.setHint("当前参数："+AppSetting.getInstance(this).getSmaParam1());
			etParam2.setHint("当前参数："+AppSetting.getInstance(this).getSmaParam2());
			etParam3.setHint("当前参数："+AppSetting.getInstance(this).getSmaParam3());
			
		}else if("ema".equalsIgnoreCase(norm)) {
			title.setText("EMA设置");
			paramDesc.setText("参数为n的指数平均指标") ;
			tvParam1.setText("N");
			
			tvParam2.setVisibility(View.GONE);
			etParam2.setVisibility(View.GONE);
			tvParam3.setVisibility(View.GONE);
			etParam3.setVisibility(View.GONE);
			
			etParam1.setHint("当前参数："+AppSetting.getInstance(this).getEmaParam());
			
		}else if("bool".equalsIgnoreCase(norm)) {
			title.setText("BOOL设置");
			paramDesc.setText("n个周期的收盘价简单均线,k上下轨的参数") ;
			tvParam1.setText("N");
			tvParam2.setText("K");
			tvParam3.setVisibility(View.GONE);
			etParam3.setVisibility(View.GONE);
			
			etParam1.setHint("当前参数："+AppSetting.getInstance(this).getBoolTParam());
			etParam2.setHint("当前参数："+AppSetting.getInstance(this).getBoolKParam());
		}
		
		else if("macd".equalsIgnoreCase(norm)) {
			title.setText("MACD设置");
			paramDesc.setText("EMA 参数n的指数平均数") ;
			tvParam1.setText("N1");
			tvParam2.setText("N2");
			tvParam3.setText("P");
			etParam1.setHint("当前参数："+AppSetting.getInstance(this).getMacdTParam1());
			etParam2.setHint("当前参数："+AppSetting.getInstance(this).getMacdTParam2());
			etParam3.setHint("当前参数："+AppSetting.getInstance(this).getMacdKParam());
			
			
		}else if("kdj".equalsIgnoreCase(norm)) {
			title.setText("KDJ设置");
			paramDesc.setText("Cn第n周期收盘价 , Ln周期最低价 , Hn周期最高价 N为周期数") ;
			tvParam1.setText("N");
			tvParam2.setVisibility(View.GONE);
			etParam2.setVisibility(View.GONE);
			tvParam3.setVisibility(View.GONE);
			etParam3.setVisibility(View.GONE);
			
			etParam1.setHint("当前参数："+AppSetting.getInstance(this).getKdjKParam());
			
		}else if("rsi".equalsIgnoreCase(norm)) {
			title.setText("RSI设置");
			paramDesc.setText("N为周期数, n个周期中所有收盘价上涨数之和,n个周期中所有收盘价下跌数之和") ;
			tvParam1.setText("N1");
			tvParam2.setText("N2");
			tvParam3.setText("N3");
			
			etParam1.setHint("当前参数："+AppSetting.getInstance(this).getRsiParam1());
			etParam2.setHint("当前参数："+AppSetting.getInstance(this).getRsiParam2());
			etParam3.setHint("当前参数："+AppSetting.getInstance(this).getRsiParam3());
		}
	}

}
