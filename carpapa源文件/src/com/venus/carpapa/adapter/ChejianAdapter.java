package com.venus.carpapa.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.venus.carpapa.MainActivity;
import com.venus.carpapa.MapActivity;
import com.venus.carpapa.R;
import com.venus.carpapa.vo.CarSellDtoVo;

public class ChejianAdapter extends BaseAdapter {
	Context context;
	List<CarSellDtoVo> result;
	private LayoutInflater layoutInflater;
	String TAG = ChejianAdapter.class.getName();

	public ChejianAdapter(Context context, List<CarSellDtoVo> result) {
		this.context = context;
		this.result = result;
		layoutInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return result.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return result.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TextView noTextView = null, timeTextView = null, addressTextView = null;
		TextView statusTextView;
		ImageView phoneImageView = null;
		convertView = layoutInflater.inflate(R.layout.item_chejian, null);
		noTextView = (TextView) convertView.findViewById(R.id.noTextView);
		timeTextView = (TextView) convertView.findViewById(R.id.timeTextView);
		addressTextView = (TextView) convertView
				.findViewById(R.id.addressTextView);

		statusTextView = (TextView) convertView
				.findViewById(R.id.statusTextView);
		phoneImageView = (ImageView) convertView
				.findViewById(R.id.phoneImageView);
		if (convertView == null) {
			// convertView.setTag(lvw_custom_img);
		} else {
			// lvw_custom_img = (ImageView) convertView.getTag();
		}
		final CarSellDtoVo vo = result.get(position);
		if (vo != null) {
			try {
				noTextView.setText("车辆编号：" + vo.getCarSellCoding());

				// timeTextView.setText("预约时间："+vo.getTime());
				if (vo.getState() == 0) {
					statusTextView.setText("未审核");
				} else if (vo.getState() == 1) {
					statusTextView.setText("审核通过");
				} else if (vo.getState() == 2) {
					statusTextView.setText("审核未通过");
				}
				addressTextView.setText(vo.getAddress());
				noTextView.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						task(vo.getCarSellCoding());
					}
				});

				phoneImageView.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						start(vo.getTel());
					}
				});
				addressTextView.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						map(vo.getAddress());

					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return convertView;
	}

	//
	private void task(String no) {

		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra("no", no);

		this.context.startActivity(intent);

	}

	// 地图
	private void map(String phone) {

		Intent intent = new Intent(context, MapActivity.class);

		this.context.startActivity(intent);

	}

	// 电话
	private void start(String phone) {
		Uri uri = Uri.parse("tel:" + phone);

		Intent intent = new Intent(Intent.ACTION_DIAL, uri);

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.context.startActivity(intent);

	}
}
