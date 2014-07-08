package com.venus.carpapa.act.shigu;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.venus.carpapa.R;
import com.venus.carpapa.adapter.JiancheAdapter;
import com.venus.carpapa.util.GetJiancheAdapterData;
import com.venus.carpapa.vo.JianCheItemVo;

@SuppressLint("NewApi")
public class ShiguDetailFrame extends Fragment {
	RelativeLayout  chejia;
	RelativeLayout fuzhu;
	ListView fuzhuListView,chejiaListView;
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {  
        // 在这里初始化fragment的页面  
        return inflater.inflate(R.layout.shigu_detail, container, false);  
    }  
  
    @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);  
        // 由于fragment不是activity，不是oncreated，而是onActivityCreated  
        setView();  
        setListener();  
        setData();
        //startThread();// 启动控制button的线程，当wifi状态不是在1或者3的时候，不可点击，  
        // if (frag != null && frag.isInLayout()) {  
        // switch (arg2) {  
        // case 0:  
        // frag.setText("0000");  
    }
    public void setView() {  
    	chejia=(RelativeLayout) getView().findViewById(R.id.chejia);
    	fuzhu=(RelativeLayout) getView().findViewById(R.id.fuzhu);
    	
    	fuzhuListView=(ListView) getView().findViewById(R.id.fuzhuListView);
    	chejiaListView=(ListView) getView().findViewById(R.id.chejiaListView);
    }
    public void setData() {   
    	List<JianCheItemVo> result=new ArrayList<JianCheItemVo>();
    	
    	
    	String[] sa = getResources().getStringArray(R.array.jcfuzhu);
    	for(int i=0;i<sa.length;i++){
    		result.add(new JianCheItemVo(sa[i],"正常"));
    	}
    	
    	JiancheAdapter adapter=new JiancheAdapter(this.getActivity(),result);
    	fuzhuListView.setAdapter(adapter);
    	//fuzhuListView.setAdapter(adapter);
    	chejiaListView.setAdapter(
    			new JiancheAdapter(this.getActivity(),
    			GetJiancheAdapterData.getData(R.array.jcchejia, this.getActivity()))
    			);
    }
    public void setListener() {   
    	
    }
    
    public void setHexing(){
    	chejia.setVisibility(View.VISIBLE);
    	fuzhu.setVisibility(View.GONE);
    }
    public void setFuzhu(){
    	chejia.setVisibility(View.GONE);
    	fuzhu.setVisibility(View.VISIBLE);
    }
}
