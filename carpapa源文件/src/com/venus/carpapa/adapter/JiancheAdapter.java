package com.venus.carpapa.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.venus.carpapa.MainActivity;
import com.venus.carpapa.MapActivity;
import com.venus.carpapa.R;
import com.venus.carpapa.resp.JiancheResp;
import com.venus.carpapa.util.DialogUtil;
import com.venus.carpapa.util.ReqJsonUtil;
import com.venus.carpapa.vo.JianCheItemVo;

public class JiancheAdapter extends BaseAdapter {
	Context context;
	List<JianCheItemVo> result;
	private LayoutInflater layoutInflater;
	String TAG = JiancheAdapter.class.getName();
	CheckBox checkBoxZc, checkBoxMhy, checkBoxQwsy, checkBoxCgz, checkBoxFdjyx,
	checkBoxFdjddyz,

	checkBoxYzsy, checkBoxFdjxybl, checkBoxMly;
	public JiancheAdapter(Context context, List<JianCheItemVo> result) {
		this.context = context;
		this.result = result;
		layoutInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return result.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return result.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

	
		TextView itemNameTextView,statusTextView;
		ImageView exceptionTextView=null;
		convertView = layoutInflater.inflate(R.layout.item_jianche, null);
		itemNameTextView = (TextView) convertView.findViewById(R.id.itemNameTextView);
		statusTextView = (TextView) convertView.findViewById(R.id.statusTextView);
		
		
		
		statusTextView= (TextView) convertView
				.findViewById(R.id.statusTextView);
		exceptionTextView = (ImageView) convertView.findViewById(R.id.exceptionTextView);
		if (convertView == null) {
			// convertView.setTag(lvw_custom_img);
		} else {
			// lvw_custom_img = (ImageView) convertView.getTag();
		}
		final JianCheItemVo vo = result.get(position);
		if (vo != null) 
		{
			try 
			{
				itemNameTextView.setText(vo.getName());
				statusTextView.setText(vo.getDefaul());
				statusTextView.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//hexingDialog_v3(vo.getName());
						String[] sa = context.getResources().getStringArray(R.array.fuzhe);
						hexingDialog_v2(vo.getName(),sa);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return convertView;
	}
	
	Map map = new HashMap();
	OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			int id = buttonView.getId();
			if (isChecked) {
				map.put(id, id);

				if (R.id.checkBoxZc == id) {

					checkBoxMhy.setChecked(false);
					checkBoxQwsy.setChecked(false);
					checkBoxCgz.setChecked(false);

					checkBoxFdjyx.setChecked(false);
					checkBoxFdjddyz.setChecked(false);
					checkBoxYzsy.setChecked(false);

					checkBoxFdjxybl.setChecked(false);
					checkBoxMly.setChecked(false);

					// qdxtTextView.setBackgroundColor(R.color.zhengc_bg);

				} else {

					checkBoxZc.setChecked(false);
					// qdxtTextView.setBackgroundColor(R.color.zhengc_bg);
				}
			} else {
				map.remove(id);
			}

		}

	};
	
	@SuppressLint("ResourceAsColor")
	public void hexingDialog_v3(String title) {

		final Dialog mDialog = new Dialog(this.context);
		View view = LayoutInflater.from(context).inflate(
				R.layout.dialog_hexing, null);
		mDialog.setTitle(title);
		mDialog.setContentView(view);

		checkBoxZc = (CheckBox) view.findViewById(R.id.checkBoxZc);
		checkBoxMhy = (CheckBox) view.findViewById(R.id.checkBoxMhy);
		checkBoxQwsy = (CheckBox) view.findViewById(R.id.checkBoxQwsy);

		checkBoxCgz = (CheckBox) view.findViewById(R.id.checkBoxCgz);
		checkBoxFdjyx = (CheckBox) view.findViewById(R.id.checkBoxFdjyx);
		checkBoxFdjddyz = (CheckBox) view.findViewById(R.id.checkBoxFdjddyz);

		checkBoxYzsy = (CheckBox) view.findViewById(R.id.checkBoxYzsy);
		checkBoxFdjxybl = (CheckBox) view.findViewById(R.id.checkBoxFdjxybl);
		checkBoxMly = (CheckBox) view.findViewById(R.id.checkBoxMly);

		checkBoxZc.setOnCheckedChangeListener(listener);
		checkBoxMhy.setOnCheckedChangeListener(listener);
		checkBoxQwsy.setOnCheckedChangeListener(listener);

		checkBoxCgz.setOnCheckedChangeListener(listener);
		checkBoxFdjyx.setOnCheckedChangeListener(listener);
		checkBoxFdjddyz.setOnCheckedChangeListener(listener);

		checkBoxYzsy.setOnCheckedChangeListener(listener);
		checkBoxFdjxybl.setOnCheckedChangeListener(listener);
		checkBoxMly.setOnCheckedChangeListener(listener);

		Button submitBtn = (Button) view.findViewById(R.id.submitBtn);
		Button canelBtn = (Button) view.findViewById(R.id.canelBtn);
		// final EditText nameEdit = (EditText)
		// view.findViewById(R.id.nameEdit);
		// nameEdit.setText(um.getUserName());
		//
		// final EditText phoneEdit = (EditText)
		// view.findViewById(R.id.phoneEdit);

		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				addTextView();
//				qdxtTextView.setBackgroundColor(R.color.yellow);
				mDialog.dismiss();
				
				map.clear();
				// add(phone,name);
			}
		});
		canelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDialog.dismiss();

			}
		});

		mDialog.show();

	}
	
	
	public void d(String title,String[] arrayFruit){
		final AlertDialog builder = new AlertDialog.Builder(context)
		.setTitle(title)
		.setMultiChoiceItems(arrayFruit,null,null
				)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		}).setNegativeButton("取消", null).create();

		builder.show();
	}
	public void hexingDialog_v2(final String title,String[] arrayFruit) {
		String url="http://42.120.20.215/party/index.php/Api/getCarpapa";
		FinalHttp fh=new FinalHttp();
		AjaxParams ap=new AjaxParams();
		ap.put("name", title);
		fh.post(url,ap, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, strMsg);
				DialogUtil.toast(context, "异常"+strMsg);
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				String s=(String) t;
				JiancheResp resp=(JiancheResp)	ReqJsonUtil.changeToObject(s, JiancheResp.class);
				if(resp!=null){
					if(resp.getData()!=null)
					{
						if(resp.getData().size()==0){
							DialogUtil.toast(context, "项目为空:");
						}else{

							String[] arr=new String[resp.getData().size()+1];
							arr[0]="正常";
							for(int i=0;i<resp.getData().size();i++)
							{
								arr[i+1]=resp.getData().get(i).getItname();
							}
							
							d(title,arr);
							
						}
						
					}
				}else{
					DialogUtil.toast(context, "项目为空");
				}
			}
			
		});
		
		//String[] sa = context.getResources().getStringArray(R.array.fuzhe);
		//arrayFruit=sa;
		

	}
	//
   void task(String no){
		
		
		Intent intent = new Intent(context, MainActivity.class);  
		intent.putExtra("no", no);
		
		this.context.startActivity(intent); 
		
	}
	
	//地图
	void map(String phone){
		
		
		Intent intent = new Intent(context, MapActivity.class);  
		
		
		this.context.startActivity(intent); 
		
	}
	//电话
	void start(String phone){
		Uri uri = Uri.parse("tel:"+phone); 
		
		Intent intent = new Intent(Intent.ACTION_DIAL, uri);  
		
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		this.context.startActivity(intent); 
		
	}
}
