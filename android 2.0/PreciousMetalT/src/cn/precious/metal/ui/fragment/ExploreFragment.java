package cn.precious.metal.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import cn.precious.metal.R;
import cn.precious.metal.base.BaseFragment;

public class ExploreFragment extends BaseFragment implements OnClickListener{
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_explore, null) ;
		view.findViewById(R.id.ll_trade_circle).setOnClickListener(this); ;
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
