package com.xiyou.apps.lookpan.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.entity.Optional;
import cn.precious.metal.services.OptionalService;
import cn.precious.metal.utils.Utils;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.ui.LoginActivity;
import com.xiyou.apps.lookpan.ui.RemindActivity;

public class EditOptionalAdapter extends BaseAdapter {

	private List<Optional> items = new ArrayList<Optional>();

	private Context context;

	boolean[] checks;

	public CheckChangeListener changeListener;

	public EditOptionalAdapter(Context context, List<Optional> list) {
		this.context = context;
		if (list != null)
			items.addAll(list);
		checks = new boolean[items.size()];
	}

	public void setItems(List<Optional> list) {
		items.clear();
		if (list != null) {
			items.addAll(list);
		}
		checks = new boolean[list.size()];
		notifyDataSetChanged();
	}

	public List<Optional> getItems() {
		return items;
	}

	public void remove(Optional optional) {
		items.remove(optional);
	}

	public void insert(Optional optional, int to) {
		items.add(to, optional);
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
		View view = convertView;
		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(
					R.layout.item_edit_optional, parent, false);
			holder.title = (TextView) view.findViewById(R.id.title);
			holder.symbol = (TextView) view.findViewById(R.id.symbol);
			holder.remind = (ImageView) view.findViewById(R.id.remind);
			holder.top = (ImageView) view.findViewById(R.id.top);
			holder.drag = (ImageView) view.findViewById(R.id.drag_handle);
			holder.check = (CheckBox) view.findViewById(R.id.check);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		final int index = position;
		holder.check
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						checks[index] = isChecked;
						changeListener.checkChange();
					}
				});

		final Optional optional = items.get(position);
		holder.title.setText(optional.getTitle());
		holder.symbol.setText(optional.getTreaty());
		holder.check.setChecked(checks[index]);

		holder.top.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				remove(optional);
				insert(optional, 0);
				updateCheck(index, 0);
				notifyDataSetChanged();
				if (new OptionalService(context).updateDrag(items)) {
					Toast.makeText(context, optional.getTitle() + "置顶成功", 1000)
							.show();
				}
			}
		});

		if (position < 3) {
			holder.top.setImageResource(R.drawable.jiantoulan);
		} else {
			holder.top.setImageResource(R.drawable.jiantoudan);
		}

		holder.remind.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (AppSetting.getInstance(context).isLoginOn()) {
					Intent it = new Intent(context, RemindActivity.class);
					it.putExtra("symbol", optional.getTreaty());
					it.putExtra("name", optional.getTitle());
					it.putExtra("BuyPrice",
							Double.parseDouble(optional.getBuyone()));
					it.putExtra("SalePrice",
							Double.parseDouble(optional.getSellone()));
					it.putExtra("CurrentPrice",
							Double.parseDouble(optional.getNewest()));
					context.startActivity(it);
				} else {

					DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							Intent it = new Intent(context, LoginActivity.class);
							it.putExtra("flag", "rect_remind");
							it.putExtra("symbol", optional.getTreaty());
							it.putExtra("name", optional.getTitle());
							it.putExtra("BuyPrice",
									Double.parseDouble(optional.getBuyone()));
							it.putExtra("SalePrice",
									Double.parseDouble(optional.getSellone()));
							it.putExtra("CurrentPrice",
									Double.parseDouble(optional.getNewest()));
							context.startActivity(it);
						}
					};

					Utils.showCusAlertDialog(context, "提示信息", "本地提醒需要登录后才能使用",
							"确定", "取消", listener, null);
				}

			}
		});

		return view;
	}

	class ViewHolder {
		TextView title;
		TextView symbol;
		ImageView remind;
		ImageView top;
		ImageView drag;
		CheckBox check;
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

	public List<Optional> getCheckItems() {
		List<Optional> list = new ArrayList<Optional>();
		for (int i = 0; i < checks.length; i++) {
			if (checks[i]) {
				list.add(items.get(i));
			}
		}
		return list;
	}

}
