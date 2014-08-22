package cn.precious.metal.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import cn.precious.metal.R;
import cn.precious.metal.entity.NationalFlag;

public class NationalFlagAdapter extends BaseAdapter {

	private List<NationalFlag> items = new ArrayList<NationalFlag>() ;

	private Context context;

	boolean[] checks;
	
	public CheckChangeListener changeListener ;

	public NationalFlagAdapter(Context context, List<NationalFlag> list) {
		this.context = context;
		if(list != null)
			items.addAll(list) ;
		checks = new boolean[items.size()];
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size() ;
	}

	@Override
	public NationalFlag getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position) ;
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
					R.layout.item_natioanl_flag, null);
			holder.check = (CheckBox)view.findViewById(R.id.check);
			holder.nationalFlag = (ImageView) view.findViewById(R.id.national_flag)	;
			holder.nationalName = (TextView) view.findViewById(R.id.national_name)	;
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		NationalFlag national = items.get(position);
		holder.nationalName.setText(national.getCountry()) ;
		holder.nationalFlag.setBackgroundResource(national.getDrawableId());
		final int index = position;
		holder.check
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						checks[index] = isChecked;
						if(changeListener != null)
							changeListener.checkChange(); 
					}
				});

		return view;
	}

	class ViewHolder {
		CheckBox check;
		TextView nationalName ;
		ImageView nationalFlag;
	}

	
	public interface CheckChangeListener{
		void checkChange() ;
	}

	public boolean[] getChecks() {
		return checks;
	}

}
