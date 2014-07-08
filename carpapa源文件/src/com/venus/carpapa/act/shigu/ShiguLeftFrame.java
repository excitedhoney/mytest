package com.venus.carpapa.act.shigu;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.venus.carpapa.R;

@SuppressLint("NewApi")
public class ShiguLeftFrame extends Fragment {
	TextView hexingText;
	TextView fuzhuText;
	ShiguDetailFrame frag_detail;
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {  
        // 在这里初始化fragment的页面  
        return inflater.inflate(R.layout.shigu_left, container, false);  
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
    public void setView() {  
    	hexingText=(TextView) getView().findViewById(R.id.hexingText);
    	fuzhuText=(TextView) getView().findViewById(R.id.fuzhuText);
    	frag_detail=(ShiguDetailFrame) getFragmentManager().findFragmentById(R.id.frag_detail); 
    }
    public void setListener() {   
    	hexingText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				frag_detail.setHexing();
			}
		});
    	fuzhuText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				frag_detail.setFuzhu();
			}
		});
    }
}
