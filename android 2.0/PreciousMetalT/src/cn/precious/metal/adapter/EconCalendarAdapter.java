package cn.precious.metal.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import cn.precious.metal.R;
import cn.precious.metal.entity.EconomicCalenda;

public class EconCalendarAdapter extends BaseAdapter {

	private List<EconomicCalenda> items = new ArrayList<EconomicCalenda>();

	private List<EconomicCalenda> tempItems = new ArrayList<EconomicCalenda>();

	private Context context;

	private String nationalSb;

	public EconCalendarAdapter(Context context, List<EconomicCalenda> list) {
		this.context = context;
		if (list != null) {
			items.addAll(list);
			tempItems.addAll(list);
		}
	}

	public void setItems(List<EconomicCalenda> list) {
		items.clear();
		if (list != null) {
			items.addAll(list);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public EconomicCalenda getItem(int position) {
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
			view = LayoutInflater.from(context).inflate(
					R.layout.item_econ_calendar, null);
			holder = new ViewHolder();
			holder.nationalFlag = (ImageView) view
					.findViewById(R.id.national_flag);
			holder.eventTitle = (TextView) view.findViewById(R.id.event_title);
			holder.eventTime = (TextView) view.findViewById(R.id.event_time);
			holder.eventType = (TextView) view.findViewById(R.id.event_type);

			holder.actual = (TextView) view.findViewById(R.id.actual);
			holder.previous = (TextView) view.findViewById(R.id.previous);
			holder.forecast = (TextView) view.findViewById(R.id.forecast);

			holder.ratingBar = (RatingBar) view.findViewById(R.id.ratingbar);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		EconomicCalenda item = items.get(position);

		if ("USD".equalsIgnoreCase(item.getCurrency())) {
			holder.nationalFlag.setBackgroundResource(R.drawable.usa);
		} else if ("AUD".equalsIgnoreCase(item.getCurrency())) {
			holder.nationalFlag.setBackgroundResource(R.drawable.australia);
		} else if ("EUR".equalsIgnoreCase(item.getCurrency())) {
			holder.nationalFlag.setBackgroundResource(R.drawable.eu);
		} else if ("JPY".equalsIgnoreCase(item.getCurrency())) {
			holder.nationalFlag.setBackgroundResource(R.drawable.japan);
		} else if ("INR".equalsIgnoreCase(item.getCurrency())) {// 印度
			holder.nationalFlag.setBackgroundResource(R.drawable.india);
		} else if ("CNY".equalsIgnoreCase(item.getCurrency())) {
			holder.nationalFlag.setBackgroundResource(R.drawable.china);
		}

		holder.eventTitle.setText(item.getTitle());
		holder.eventTime.setText(item.getTimestamp().substring(10, 16));
		String currentStr = item.getCalendarType();
		String previewStr = (position - 1) >= 0 ? items.get(position - 1)
				.getCalendarType() : "";
		if (!currentStr.equalsIgnoreCase(previewStr)) {
			holder.eventType.setVisibility(View.VISIBLE);
			holder.eventType.setText(currentStr);
		} else {
			holder.eventType.setVisibility(View.GONE);
		}
		String actual = item.getActual();
		if (actual == null || "".equalsIgnoreCase(actual))
			actual = "--";

		holder.actual.setText("今值:" + actual);
		String previous = item.getPrevious();
		if (previous == null || "".equalsIgnoreCase(previous))
			previous = "--";

		holder.previous.setText("前值:"+previous);
		String forecast = item.getForecast();
		if (forecast == null || "".equalsIgnoreCase(forecast))
			forecast = "--";

		holder.forecast.setText("预期:" + forecast);

		holder.ratingBar.setRating(item.getImportance());

		return view;
	}

	class ViewHolder {
		ImageView nationalFlag;
		TextView eventTitle;
		TextView eventTime;
		TextView eventType;
		TextView actual; // 今值
		TextView previous;// 前值
		TextView forecast;// 预期
		RatingBar ratingBar;
	}

	public List<EconomicCalenda> getTempItems() {
		return tempItems;
	}

	public void setTempItems(List<EconomicCalenda> tempItems) {
		this.tempItems = tempItems;
	}

	public void updateItems() {
		if (tempItems == null || tempItems.isEmpty())
			return;

		List<EconomicCalenda> list = new ArrayList<EconomicCalenda>();

		if (nationalSb == null || "".equalsIgnoreCase(nationalSb)) {
			if (important <= 1) {
				setItems(tempItems);
			} else {
				for (int i = 0; i < tempItems.size(); i++) {
					if (important <= tempItems.get(i).getImportance()) {
						list.add(tempItems.get(i));
					}
				}
				setItems(list);
			}

		} else {
			for (int i = 0; i < tempItems.size(); i++) {
				if (important <= 1) {
					if (nationalSb.indexOf(tempItems.get(i).getCurrency()) > 0) {
						list.add(tempItems.get(i));
					}
				} else {
					if (nationalSb.indexOf(tempItems.get(i).getCurrency()) > 0
							&& important <= tempItems.get(i).getImportance()) {
						list.add(tempItems.get(i));
					}
				}
			}
			setItems(list);
		}
		
		notifyDataSetChanged();
		
	}

	private int important;

	public int getImportant() {
		return important;
	}

	public void setImportant(int important) {
		this.important = important;
	}

	public String getNationalSb() {
		return nationalSb;
	}

	public void setNationalSb(String nationalSb) {
		this.nationalSb = nationalSb;
	}

}
