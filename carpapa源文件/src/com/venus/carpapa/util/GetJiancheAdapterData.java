package com.venus.carpapa.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.venus.carpapa.R;
import com.venus.carpapa.vo.JianCheItemVo;

public class GetJiancheAdapterData {
	public static List<JianCheItemVo> getData(int sid,Activity act){
		List<JianCheItemVo> result=new ArrayList<JianCheItemVo>();
    	
    	
    	String[] sa = act.getResources().getStringArray(sid);
    	for(int i=0;i<sa.length;i++){
    		result.add(new JianCheItemVo(sa[i],"正常"));
    	}
    	return result;
	}
}
