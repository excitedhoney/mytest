package com.xiyou.apps.lookpan.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import cn.precious.metal.entity.ETFReport;

import com.xiyou.apps.lookpan.R;

@SuppressLint("ResourceAsColor")
public class ETFReportAdapter extends BaseAdapter {
	private List<ETFReport> items = new ArrayList<ETFReport>();

	private Context context;

	private LayoutInflater mLayuotInflater;

	public ETFReportAdapter(Context context, List<ETFReport> list) {
		this.context = context;
		if (list != null)
			items.addAll(list);

		mLayuotInflater = LayoutInflater.from(this.context);
	}

	public void setItems(List<ETFReport> list) {
		items.clear();
		if (list != null) {
			items.addAll(list);
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size() > 40 ? 40 : items.size();
	}

	@Override
	public ETFReport getItem(int position) {
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
		View view = convertView;
		ViewHolder holder = null;
		if (view == null) {
			view = mLayuotInflater.inflate(R.layout.item_eft_report, parent,
					false);
			holder = new ViewHolder();
			holder.change = (Button) view.findViewById(R.id.change);
			holder.date = (TextView) view.findViewById(R.id.date);
			holder.totalValue = (TextView) view.findViewById(R.id.totalValue);
			holder.widget = (TextView) view.findViewById(R.id.widget);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		ETFReport report = items.get(position);
		if (report.getChange() == 0) {
			holder.change.setText("持平");
			holder.change.setBackgroundResource(R.drawable.huidi);
		} else {
			holder.change.setText("" + report.getChange() + "吨");
			if (report.getChange() < 0)
				holder.change.setBackgroundResource(R.drawable.ludi);
			else
				holder.change.setBackgroundResource(R.drawable.hongdi);
		}

		holder.date.setText(report.getTime());
		holder.totalValue.setText("总价值：");
		holder.widget.setText(report.getVal() + "" + report.getUnit());

		return view;
	}

	class ViewHolder {
		Button change;
		TextView date;
		TextView totalValue;
		TextView widget;

	}

}
