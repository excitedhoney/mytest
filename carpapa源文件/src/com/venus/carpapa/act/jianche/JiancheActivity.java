package com.venus.carpapa.act.jianche;

import com.venus.carpapa.R;
import com.venus.carpapa.util.DialogUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

@SuppressLint("NewApi")
public class JiancheActivity extends Activity {
	JiancheDetailFrame fdf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.act_jianche);
		fdf=(JiancheDetailFrame)this.getFragmentManager().findFragmentById(R.id.frag_detail);
	}
	public void back(View v) {
		this.finish();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//DialogUtil.toast(this, "JiancheActivity onActivityResult");
		fdf.onar(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}
	//前保险钢
	public void qianbaoxiangang(View v){
		DialogUtil.toast(this, "你们睡吧");
	}
	//左前椅子板
	//发动机常盖
	//左前椅子板
	
	//左前门
	//右前门
	//车顶
	//左后门
	//右后门
	
	//左后翼子板
	//右后翼子板
	//行李箱盖
	//后保险缸
}
