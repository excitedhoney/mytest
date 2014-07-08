package com.venus.carpapa;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.venus.carpapa.R;
import com.venus.carpapa.adapter.ChejianAdapter;
import com.venus.carpapa.resp.CarOrderResp;
import com.venus.carpapa.util.DialogUtil;
import com.venus.carpapa.util.HttpUtil;
import com.venus.carpapa.util.Logger;
import com.venus.carpapa.util.ReqJsonUtil;
import com.venus.carpapa.vo.CarSellDtoVo;
import com.venus.carpapa.vo.ChejianVo;

public class TaskIndexActivity extends BaseActivity {
	@ViewInject(id = R.id.chejianListView) ListView chejianListView;
	@ViewInject(id = R.id.exitBtn ,click = "exit")Button exitBtn;
	@ViewInject(id = R.id.countTextView)TextView countTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_index);
		//data();
		//order();
		data_test();
	}
	public void data_test(){
		List<CarSellDtoVo>  carSellDtoList=new ArrayList<CarSellDtoVo>();
		for(int i=1;i<7;i++){
			CarSellDtoVo vo=new CarSellDtoVo();
			vo.setLicenseNumber("SB01025"+i);
			vo.setCarSellCoding("SB01025"+i);
			vo.setTel("10086");
			vo.setAddress("上海市普陀区梅川路90" +i+"号");
			vo.setState(1);
			carSellDtoList.add(vo);
		}
		ChejianAdapter apapter =new ChejianAdapter(this,carSellDtoList);
		chejianListView.setAdapter(apapter);
		countTextView.setText("共"+carSellDtoList.size()+"条未完成");
	}
	public void data(){
		List<ChejianVo> list=new ArrayList<ChejianVo>();
		for(int i=1;i<10;i++)
		{
			ChejianVo vo=new ChejianVo();
			vo.setNo("SB01025"+i);
			vo.setAddress("上海市普陀区梅川路90" +i+"号");
					
			vo.setTime("14-01-02 1"+i+":00");
			vo.setPhone("10086");
			list.add(vo);
		}
//		ChejianAdapter apapter =new ChejianAdapter(this,list);
//		chejianListView.setAdapter(apapter);
//		countTextView.setText("共"+list.size()+"条未完成");
	}
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {

			case 1:
				String obj=(String)msg.obj;
				parseLogin(obj);
				break;
			}
		}

	};
	public void parseLogin(String obj){
		this.canelDialog();
		try {
//			JSONObject jo=new JSONObject(obj);
//			String login=jo.getString("login");
//			String message=jo.getString("message");
//			if(TextUtils.equals("success", login))
//			{
//				DialogUtil.toast(this,"成功");
//				startActivity(new Intent(this,TaskIndexActivity.class));
//			}else{
//				DialogUtil.toast(this,message);
//				startActivity(new Intent(this,TaskIndexActivity.class));
//			}
			CarOrderResp resp=	(CarOrderResp) ReqJsonUtil.changeToObject(obj, CarOrderResp.class);
			if(resp!=null)
			{
				ChejianAdapter apapter =new ChejianAdapter(this,resp.getCarSellDtoList());
				chejianListView.setAdapter(apapter);
				countTextView.setText("共"+resp.getCarSellDtoList().size()+"条未完成");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//startActivity(new Intent(this,TaskIndexActivity.class));
		}
	}
	public void order() {
		showDialog();
		new Thread() {
			@Override
			public void run() {
				String rtn1 = HttpUtil.CarOrder(1);
				Logger.d("rtn1:", "rtn1：" + rtn1);
				Message msg = new Message();
				msg.what = 1;
				msg.obj=rtn1;
				handler.sendMessage(msg);
			}
		}.start();
	}
	public void exit(View v){
		finish();
	}
}
