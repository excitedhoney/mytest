package com.venus.carpapa.act.info;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.venus.carpapa.R;
import com.venus.carpapa.util.DateUtil;


@SuppressLint("NewApi")
public class InfoDetailFrame extends Fragment {
	RelativeLayout  shouxu;
	RelativeLayout peizhi;
	
	RelativeLayout beizhu;
	EditText dengjiDate;
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {  
        // 在这里初始化fragment的页面  
        return inflater.inflate(R.layout.activity_info_right, container, false);  
    }  
  
    @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);  
        // 由于fragment不是activity，不是oncreated，而是onActivityCreated  
        setView();  
        setListener();  
  
        //startThread();// 启动控制button的线程，当wifi状态不是在1或者3的时候，不可点击，  
        // if (frag != null && frag.isInLayout()) {  
        // switch (arg2) {  
        // case 0:  
        // frag.setText("0000");  
    }
    DatePickerDialog dpd;  
   
    public void setView() {  
    	
    	shouxu=(RelativeLayout) getView().findViewById(R.id.shouxu);
    	peizhi=(RelativeLayout) getView().findViewById(R.id.peizhi);
    	beizhu=(RelativeLayout) getView().findViewById(R.id.beizhu);
    	dengjiDate=(EditText) getView().findViewById(R.id.dengjiDate);
    	dengjiDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dpd.show();
			}
		});
    	date_dialog();
    	
    }
    public void date_dialog(){
     int year=Integer.parseInt(DateUtil.getYear());
     Date date=new Date();
     int month=date.getMonth();
   	 dpd=new DatePickerDialog(this.getActivity(),new DatePickerDialog.OnDateSetListener() 
   	 {  
            
	            @Override  
	            public void onDateSet(DatePicker view, int year, int monthOfYear,  
	                    int dayOfMonth) {  
	                //System.out.println();  
	            	dengjiDate.setText(year+"-"+monthOfYear//注意：这里的数字是从0到11  
		                    +"-"+dayOfMonth);
	            }  
	     },year,month,DateUtil.getMM());
    }
    
    
    public void setListener() {   
    	
    }
    
    public void setShouxu(){
    	shouxu.setVisibility(View.VISIBLE);
    	peizhi.setVisibility(View.GONE);
    	beizhu.setVisibility(View.GONE);
    }
    public void setPeizhi(){
    	shouxu.setVisibility(View.GONE);
    	peizhi.setVisibility(View.VISIBLE);
    	beizhu.setVisibility(View.GONE);
    }
    public void setBeizhu(){
    	shouxu.setVisibility(View.GONE);
    	peizhi.setVisibility(View.GONE);
    	beizhu.setVisibility(View.VISIBLE);
    }
}
