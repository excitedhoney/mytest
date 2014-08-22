package com.xiyou.apps.lookpan.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.precious.metal.entity.Optional;

import com.xiyou.apps.lookpan.R;

public class ChooseOptionalAdapter extends BaseAdapter{

	private Context context ;
	
	private List<Optional> items = new ArrayList<Optional>() ;
	
	public ChooseOptionalAdapter (Context context , List<Optional> list) {
		this.context  =  context ;
		if(list != null) {
			items.addAll(list) ;
		}
	}
	
	public void setItems (List<Optional> list) {
		items.clear(); 
		if(list != null) {
			items.addAll(list) ;
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Optional getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view  = convertView ;
		ViewHolder holder = null ;
		
		if(view == null ){
			view = LayoutInflater.from(context).inflate(R.layout.item_choose_optional, null) ;
			holder = new ViewHolder() ;
			holder.optionalName =  (TextView) view.findViewById(R.id.optional_name) ;
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag() ;
		}
		Optional op = items.get(position) ;
		holder.optionalName.setText(op.getTitle()); 
		return view;
	}
	
	class ViewHolder {
		TextView optionalName ;
	}
	
}
