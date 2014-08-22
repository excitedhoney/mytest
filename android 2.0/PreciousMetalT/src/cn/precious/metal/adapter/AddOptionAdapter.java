package cn.precious.metal.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.precious.metal.R;
import cn.precious.metal.dao.OptionalDao;
import cn.precious.metal.entity.Optional;

/**
 * 添加自选的adapter
 * 
 * @author mac
 * 
 */

public class AddOptionAdapter extends BaseAdapter {

	private Context context;

	private List<Optional> items = new ArrayList<Optional>();

	private boolean[] checks;
	
	private boolean isShowSymbol ;
	
	public OptionaChangeListener optionalChangeListener ;

	public AddOptionAdapter(Context context, List<Optional> list,
			boolean isShowSymbol) {
		this.context = context;
		if (list != null) {
			items.addAll(list);
		}
		checks = new boolean[items.size()];
		
		this.isShowSymbol = isShowSymbol ;
	}

	public void setItems(List<Optional> list, boolean clear) {
		if (clear)
			items.clear();
		if (list != null)
			items.addAll(list);

		checks = new boolean[items.size()];

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

	private ViewHolder holder = null;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;

		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_add_optional, null);
			holder = new ViewHolder();
			holder.check = (CheckBox) view.findViewById(R.id.check);
			holder.title = (TextView) view.findViewById(R.id.title);
			holder.symbol = (TextView) view.findViewById(R.id.symbol);
			holder.RelativeLayout_check=(RelativeLayout) view.findViewById(R.id.RelativeLayout_check);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		final Optional optional = items.get(position);

		holder.title.setText(optional.getTitle());

		final int index = position;

		if (optional.isOptional()) {
			checks[index] = true;
		} else {
			checks[index] = false;
		}

		
		
		holder.check.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean isChecked = !checks[index] ;
				Optional op = optional;
				op.setOptional(isChecked);
				int count = new OptionalDao(context).getIsOptionalNumber() ;
				if(!isChecked){
					if(count <= 3){
						Toast.makeText(context, "最少保留三条自选行情", 1000).show() ;
						return ;
					}
				}
				boolean isSuccess  = new OptionalDao(context).updateOptional(op);
				if(isSuccess){
					checks[index] = isChecked;
					if(optionalChangeListener != null){
						if(isChecked) {
							count++ ;
							optionalChangeListener.checkChange(count + "");
						}else{
							count -- ;
							optionalChangeListener.checkChange(count + "");
						}
						
					}
				}
			}
		});
		
		holder.check.setChecked(checks[index]);
		
		if(isShowSymbol){
			holder.symbol.setVisibility(View.VISIBLE);
			holder.symbol.setText(optional.getTreaty());
		}else{
			holder.symbol.setVisibility(View.GONE);
		}

		return view;
	}
	
	
	public  interface OptionaChangeListener {
		void checkChange(String count);
	}

	
	class ViewHolder {
		CheckBox check;
		TextView title;
		TextView symbol;
		RelativeLayout RelativeLayout_check;
	}
}
