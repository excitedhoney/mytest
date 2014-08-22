package com.xiyou.apps.lookpan.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.precious.metal.dao.OptionalDao;
import cn.precious.metal.entity.Optional;
import cn.precious.metal.entity.VirtualTrade;
import cn.precious.metal.utils.Utils;

import com.xiyou.apps.lookpan.R;

public class VirtualTradeAdapter extends BaseAdapter {

	private Context context;

	private List<VirtualTrade> items = new ArrayList<VirtualTrade>();

	private OptionalDao optionalDao;

	public VirtualTradeAdapter(Context context, List<VirtualTrade> list) {
		this.context = context;
		optionalDao = new OptionalDao(context);
		if (list != null) {
			items.addAll(list);
		}
	}

	public void setItems(List<VirtualTrade> list) {
		items.clear();
		if (list != null) {
			items.addAll(list);
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public VirtualTrade getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View view = convertView;
		ViewHolder holder = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_virtual_trade, null);
			holder = new ViewHolder();
			holder.trade_time = (TextView) view.findViewById(R.id.trade_time);
			holder.trade_name = (TextView) view.findViewById(R.id.trade_name);
			holder.trade_value = (TextView) view.findViewById(R.id.trade_value);
			holder.trade_price = (TextView) view.findViewById(R.id.trade_price);
			holder.trade_oritation = (TextView) view
					.findViewById(R.id.trade_oritation);
			holder.trade_volume = (TextView) view
					.findViewById(R.id.trade_volume);
			holder.trade_profit_lose = (TextView) view
					.findViewById(R.id.trade_profit_lose);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		VirtualTrade trade = items.get(position);

		holder.trade_time.setText(trade.getUpdateTime());
		holder.trade_name.setText(trade.getTitle());
		holder.trade_value.setText(""+Utils.getFloatDecimal(trade.getTransactionPrice() * trade.getCloseVolume()));
		holder.trade_price.setText(""+trade.getTransactionPrice());
		holder.trade_volume.setText(""+trade.getCloseVolume());
		
		if(trade.getOrientation() == 1) {
			holder.trade_oritation.setTextColor(R.color.red);
			holder.trade_oritation.setText("做多");
			holder.trade_oritation.setBackgroundResource(R.drawable.zuoduo);
		} else {
			holder.trade_oritation.setText("做空");
			holder.trade_oritation.setBackgroundResource(R.drawable.zuokong);
		}
		
		Optional  optional  = optionalDao.queryOptionalsBySymbol(trade.getTreaty()) ;
		
		float  profitLose  = Float.parseFloat(optional.getNewest())  - trade.getTransactionPrice() ; 
		float  prefitLosePre  = Utils.getFloatDecimal(profitLose * 100 / trade.getTransactionPrice())  ;
		
		if(profitLose >= 0) {
			holder.trade_profit_lose
			.setText(Html.fromHtml("<font color='#ff0000'>" +Utils.getFloatDecimal(profitLose) + "/" + prefitLosePre + "%" + "</font>"));
		}else{
			holder.trade_profit_lose
			.setText(Html.fromHtml("<font color='#00ff00'>" +Utils.getFloatDecimal(profitLose) + "/" + prefitLosePre + "%" + "</font>"));
		}

		return view;
	}

	class ViewHolder {

		TextView trade_time;
		TextView trade_name;
		TextView trade_value;
		TextView trade_price;
		TextView trade_volume;
		TextView trade_profit_lose;
		TextView trade_oritation;

	}

}
