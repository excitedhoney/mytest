package com.xiyou.apps.lookpan.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.precious.metal.entity.Optional;
import cn.precious.metal.utils.Utils;

import com.xiyou.apps.lookpan.R;

public class OptionalAdapter extends BaseAdapter {

	private List<Optional> items = new ArrayList<Optional>();

	private Context context;

	private int count = 0;

	public DiffOrPercentChangeListener listener;

	private AnimationDrawable cusRedAnimationDrawable;

	private AnimationDrawable cusGreenAnimationDrawable;

	private HashMap<String, Double> getMap = new HashMap<String, Double>();

	public OptionalAdapter(Context context, List<Optional> list) {
		this.context = context;
		if (list != null) {
			items.addAll(list);
		}
		for (Optional op : items) {
			getMap.put(op.getTreaty(), null);
		}

	}

	public void setItems(List<Optional> list) {
		
		if(!items.isEmpty()) {
			if(getMap.isEmpty()) {
				for (Optional optional : items) {
					getMap.put(optional.getTreaty(), Double.parseDouble(optional.getSellone())) ;
				}
			}
		}
		items.clear();
		if (list != null) {
			items.addAll(list);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		return items.size();

	}

	@Override
	public Optional getItem(int position) {
		return items.get(position);

	}

	@Override
	public long getItemId(int position) {
		return position;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.item_optional,
					parent, false);
			holder.symbol = (TextView) view.findViewById(R.id.symbol);
			holder.title = (TextView) view.findViewById(R.id.title);
			holder.buyingRate = (TextView) view.findViewById(R.id.buying_rate);
			holder.sellingRate = (TextView) view
					.findViewById(R.id.selling_rate);
			holder.changeRate = (Button) view.findViewById(R.id.change_rate);
			holder.llOptional = (LinearLayout) view
					.findViewById(R.id.ll_optional);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		Optional optional = items.get(position);

		holder.title.setText(optional.getTitle());
		holder.symbol.setText(optional.getTreaty());
		holder.buyingRate.setText(optional.getBuyone());
		holder.sellingRate.setText(optional.getSellone());
		double diff = 0;
		double diffPercent = 0;
		if (!"".equals(optional.getSellone()) && null != optional.getSellone()
				&& !"".equals(optional.getClosed())
				&& null != optional.getClosed()) {
			diff = Double.parseDouble(optional.getSellone())
					- Double.parseDouble(optional.getClosed());
			diffPercent = (diff * 100)
					/ Double.parseDouble(optional.getSellone());

			
		}
		
		checkChange(holder.llOptional, optional.getTreaty(),
				Double.parseDouble(optional.getSellone()));

		if (diff > 0) {
			holder.changeRate.setBackgroundResource(R.drawable.hongdi);
		} else if (diff == 0) {
			holder.changeRate.setBackgroundResource(R.drawable.huidi);
		} else {
			holder.changeRate.setBackgroundResource(R.drawable.ludi);
		}
		switch (count % 2) {

		case 0:
			holder.changeRate.setText(Utils.getBigDecimal(diffPercent) + "%");
			break;
		case 1:
			holder.changeRate.setText(Utils.getBigDecimal(diff));
			break;

		default:
			break;
		}

		holder.changeRate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				count++;
				if (listener != null)
					listener.change(count);
				notifyDataSetChanged();

			}
		});

		return view;
	}

	private void checkChange(View v, String getTreaty, Double Sellone) {

		Iterator<?> iter = getMap.entrySet().iterator();
		while (iter.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();

			if (key.equals(getTreaty)) {
				Log.i("tag", "Val与现值:" + val + "==" + Sellone);
				
				if (val == null) {
					
				} else {
					if (Sellone > (Double) val) {
						v.setBackgroundResource(R.anim.shansuo_red);
						cusRedAnimationDrawable = (AnimationDrawable) v
								.getBackground();
						cusRedAnimationDrawable.stop();
						cusRedAnimationDrawable.start();
						getMap.put(getTreaty, Sellone);
						
					} else if (Sellone < (Double) val) {
						v.setBackgroundResource(R.anim.shansuo_green);
						cusGreenAnimationDrawable = (AnimationDrawable) v
								.getBackground();
						cusGreenAnimationDrawable.stop();
						cusGreenAnimationDrawable.start();
						getMap.put(getTreaty, Sellone);
					}
				}
				break ;
			}
		}

	}

	class ViewHolder {
		TextView title;
		TextView symbol;
		TextView buyingRate;
		TextView sellingRate;
		Button changeRate;
		LinearLayout llOptional;
	}

	public interface DiffOrPercentChangeListener {
		void change(int count);
	}

}
