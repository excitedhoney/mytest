package com.venus.carpapa.act.jianche;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.venus.carpapa.R;

@SuppressLint("NewApi")
public class JiancheLeftFrame extends Fragment {
	TextView hexingText;
	TextView fuzhuText;
	TextView waiguanText, cheneiText, qitaText;
	JiancheDetailFrame frag_detail;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 在这里初始化fragment的页面
		return inflater.inflate(R.layout.jianche_left, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 由于fragment不是activity，不是oncreated，而是onActivityCreated
		setView();
		setListener();

		// startThread();// 启动控制button的线程，当wifi状态不是在1或者3的时候，不可点击，
		// if (frag != null && frag.isInLayout()) {
		// switch (arg2) {
		// case 0:
		// frag.setText("0000");
	}

	public void setView() {
		hexingText = (TextView) getView().findViewById(R.id.hexingText);
		fuzhuText = (TextView) getView().findViewById(R.id.fuzhuText);

		qitaText = (TextView) getView().findViewById(R.id.qitaText);
		cheneiText = (TextView) getView().findViewById(R.id.cheneiText);
		waiguanText = (TextView) getView().findViewById(R.id.waiguanText);

		frag_detail = (JiancheDetailFrame) getFragmentManager()
				.findFragmentById(R.id.frag_detail);
	}

	public void setListener() {
		hexingText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				frag_detail.setHexing();
			}
		});
		fuzhuText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				frag_detail.setFuzhu();
			}
		});
		qitaText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				frag_detail.setQita();
			}
		});
		waiguanText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				frag_detail.setWaiguan();
			}
		});
		cheneiText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				frag_detail.setChenei();
			}
		});
	}
}
