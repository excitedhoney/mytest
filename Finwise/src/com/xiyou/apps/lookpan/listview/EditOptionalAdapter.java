package com.xiyou.apps.lookpan.listview;

import java.util.ArrayList;
import java.util.List;

import com.xiyou.apps.lookpan.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EditOptionalAdapter extends BaseAdapter {

	private List<String> items = new ArrayList<String>();

	private Context context;

	boolean[] checks;

	public CheckChangeListener changeListener;

	public EditOptionalAdapter(Context context, List<String> list) {
		this.context = context;
		if (list != null)
			items.addAll(list);
		checks = new boolean[items.size()];
	}

	public void setItems(List<String> list) {
		items.clear();
		if (list != null) {
			items.addAll(list);
		}
		checks = new boolean[list.size()];
		notifyDataSetChanged();
	}

	public List<String> getItems() {
		return items;
	}

	public void remove(String optional) {
		items.remove(optional);
	}

	public void insert(String optional, int to) {
		items.add(to, optional);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public String getItem(int position) {
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
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(
					R.layout.item_edit_optional, null);
			holder.title = (TextView) view.findViewById(R.id.title);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		final String optional = items.get(position);
		holder.title.setText(optional);
		return view;
	}

	class ViewHolder {
		TextView title;
		ImageView drag;
		View divider;
	}

	public void updateCheck(int from, int to) {
		if (from < to) {
			boolean temp = checks[from];
			for (int i = from; i < to; i++) {
				checks[i] = checks[i + 1];
			}
			checks[to] = temp;
		} else {
			boolean temp = checks[from];
			for (int i = from; i > to; i--) {
				checks[i] = checks[i - 1];
			}
			checks[to] = temp;
		}
	}

	public void setChecks(boolean ischeck) {
		for (int i = 0; i < checks.length; i++) {
			checks[i] = ischeck;
		}
		notifyDataSetChanged();
	}

	public interface CheckChangeListener {
		void checkChange();
	}

	public int getCheckCount() {
		int count = 0;
		for (int i = 0; i < checks.length; i++) {
			if (checks[i])
				count++;
		}
		return count;
	}

	public List<String> getCheckItems() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < checks.length; i++) {
			if (checks[i]) {
				list.add(items.get(i));
			}
		}
		return list;
	}

}
