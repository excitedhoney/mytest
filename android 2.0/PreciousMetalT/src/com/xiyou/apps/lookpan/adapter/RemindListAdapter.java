package com.xiyou.apps.lookpan.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.precious.metal.dao.NoticeDao;
import cn.precious.metal.entity.CustomNotice;

import com.xiyou.apps.lookpan.R;

public class RemindListAdapter extends BaseAdapter{
	
	private Context context  ;
	
	private List<CustomNotice> items  = new ArrayList<CustomNotice>() ;
	
	private LayoutInflater mInflater ;
	
	public RemindListAdapter(Context context , List<CustomNotice> list) {
		this.context = context ;
		mInflater = LayoutInflater.from(context) ;
		if(list != null) {
			items.addAll(list) ;
		}
	}

	public void setItems(List<CustomNotice> list) {
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
	public CustomNotice getItem(int position) {
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
		View view = convertView ;
		ViewHolder holder = null ;
		if(view  == null){
			view = mInflater.inflate(R.layout.item_remind_list, null) ;
			holder = new ViewHolder() ;
			holder.btnDelete = (Button) view.findViewById(R.id.btn_delete) ;
			holder.noticeCreateTime = (TextView) view.findViewById(R.id.notice_createtime) ;
			holder.noticeName = (TextView) view.findViewById(R.id.notice_name) ;
			holder.noticePrice = (TextView) view.findViewById(R.id.notice_price) ;
			holder.noticeShixiao = (TextView) view.findViewById(R.id.notice_shixiao) ;
			view.setTag(holder); 
		}else{
			holder =  (ViewHolder) view.getTag() ;
		}
		
		final CustomNotice notice = items.get(position) ;
		holder.noticeName.setText(notice.getName());
		holder.noticePrice.setText("价格" + (notice.getOritation() == 0 ? "<=" : " >=") + notice.getSetPrice() );
		holder.noticeShixiao.setText("时效 :" + (notice.getRunTime() / 1000 /60 /60) + "小时"); 
		holder.noticeCreateTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(notice.getCreateTime())));
		
		holder.btnDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(new NoticeDao(context).deleteNotice(notice))
					items.remove(notice) ;
					notifyDataSetChanged();
					Toast.makeText(context, "删除成功", 1000).show();
			}
		});
		
		return view;
	}
	
	
	class ViewHolder {
		TextView  noticeName;
		TextView  noticePrice;
		TextView  noticeShixiao;
		TextView  noticeCreateTime;
		Button  btnDelete;
	}
}
