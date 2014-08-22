package cn.precious.metal.adapter;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.precious.metal.R;
import cn.precious.metal.entity.VirtualTrade;
import cn.precious.metal.utils.Utils;

public class TradeHostoryAdapter extends BaseAdapter {

	private Context context;

	private List<VirtualTrade> items = new ArrayList<VirtualTrade>();

	public TradeHostoryAdapter(Context context, List<VirtualTrade> list) {
		this.context = context;
		if (list != null) {
			items.addAll(list);
		}
		notifyDataSetChanged();
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
					R.layout.item_hostory_trade, null);
			holder = new ViewHolder();
			holder.trade_time = (TextView) view.findViewById(R.id.trade_time);
			holder.trade_name = (TextView) view.findViewById(R.id.trade_name);
			holder.trade_value = (TextView) view.findViewById(R.id.trade_value);
			holder.trade_price = (TextView) view.findViewById(R.id.trade_price);
			holder.trade_oritation = (TextView) view
					.findViewById(R.id.trade_oritation);
			holder.trade_volume = (TextView) view
					.findViewById(R.id.trade_volume);
			holder.trade_profit = (TextView) view
					.findViewById(R.id.trade_profit);
			holder.llProfit = (LinearLayout) view.findViewById(R.id.ll_profit);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		VirtualTrade trade = items.get(position);

		holder.trade_time.setText(trade.getUpdateTime());
		
		if(trade.getIsClose() == 1){
			if(trade.getIsOpening() == 1) {
				if(trade.getOrientation() == 1) {
					holder.trade_oritation.setText("做多");
				}else{
					holder.trade_oritation.setText("做空");
				}

				if (trade.getOrientation() == 1) {
					holder.trade_name.setText(trade.getTitle());
				} else {
					holder.trade_name.setText(trade.getTitle());
				}

			} else {
				holder.trade_oritation.setText("平仓");
				if (trade.getOrientation() == 1) {
					holder.trade_name.setText(Html
							.fromHtml("<font color='#ff0000'>"
									+ trade.getTitle()
									+ "</font><font color='green'>做多</font>"));
				} else {
					holder.trade_name.setText(Html
							.fromHtml("<font color='#ff0000'>"
									+ trade.getTitle()
									+ "</font><font color='green'>做空</font>"));
				}
			}

		} else {
			holder.trade_name.setText(trade.getTitle());
			if (trade.getOrientation() == 1) {
				holder.trade_oritation.setText("做多");
			} else {
				holder.trade_oritation.setText("做空");
			}
		}
		
		if(trade.getIsOpening() == 1 && trade.getIsClose() == 1) {
			holder.trade_value.setText(""+Utils.getFloatDecimal(trade.getTransactionPrice() * trade.getOpenVolume()));
			holder.trade_price.setText(""+trade.getTransactionPrice());
			holder.trade_volume.setText(""+trade.getOpenVolume());
		}else{
			holder.trade_value.setText(""+Utils.getFloatDecimal(trade.getTransactionPrice() * trade.getCloseVolume()));
			holder.trade_price.setText(""+trade.getTransactionPrice());
			holder.trade_volume.setText(""+trade.getCloseVolume());
		}
		
		
		
		if(trade.getIsClose() == 1) {
			if(trade.getIsOpening() == 0){
				holder.llProfit.setVisibility(View.VISIBLE);
				if(trade.getOrientation() == 1) {//做多
					float profit = (trade.getTransactionPrice() - trade.getOpenPrice()) * trade.getCloseVolume() ;
					if(profit >= 0) {
						holder.trade_profit.setText(Html.fromHtml("</font><font color='red'>" + Utils.getFloatDecimal(profit) + "</font>"));
					}else{
						holder.trade_profit.setText(Html.fromHtml("</font><font color='green'>" + Utils.getFloatDecimal(profit) + "</font>"));
					}
				}else{//做空
					float profit = (trade.getOpenPrice()  -  trade.getTransactionPrice() ) * trade.getCloseVolume() ;
					if(profit >= 0) {
						holder.trade_profit.setText(Html.fromHtml("</font><font color='red'>" + Utils.getFloatDecimal(profit) + "</font>"));
					}else{
						holder.trade_profit.setText(Html.fromHtml("</font><font color='green'>" + Utils.getFloatDecimal(profit) + "</font>"));
					}
				}
			}else{
				holder.llProfit.setVisibility(View.GONE);
			}
		} else {
			holder.llProfit.setVisibility(View.GONE);
		}

		return view;
	}

	class ViewHolder {
		TextView trade_oritation;
		TextView trade_time;
		TextView trade_name;
		TextView trade_price;
		TextView trade_volume;
		TextView trade_value;
		TextView trade_profit;
		LinearLayout llProfit;
	}

}
