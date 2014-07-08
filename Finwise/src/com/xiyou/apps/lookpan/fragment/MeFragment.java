package com.xiyou.apps.lookpan.fragment;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.fragment.showactivity.MeAboutUsActivity;
import com.xiyou.apps.lookpan.fragment.showactivity.MeFanKuiActivity;
import com.xiyou.apps.lookpan.fragment.showactivity.MeGongGaoActivity;
import com.xiyou.apps.lookpan.fragment.showactivity.MeHuoDongActivity;
import com.xiyou.apps.lookpan.fragment.showactivity.MeKaiHuActivity;
import com.xiyou.apps.lookpan.fragment.showactivity.ShowETFActivity;
import com.xiyou.apps.lookpan.fragment.showactivity.ShowParterActivity;
import com.xiyou.apps.lookpan.fragment.showactivity.showRiLiActivity;

public class MeFragment extends Fragment {

	private RelativeLayout kaihu;
	private RelativeLayout huodong;
	private RelativeLayout gonggao;
	private RelativeLayout fankui;
	private RelativeLayout aboutus;
	private RelativeLayout me_rili;
	private RelativeLayout me_hezuo;
	private RelativeLayout me_etf;
	private ActionBar mActionBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_me, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		kaihu = (RelativeLayout) view.findViewById(R.id.me_kaihu);
		huodong = (RelativeLayout) view.findViewById(R.id.me_huodong);
		gonggao = (RelativeLayout) view.findViewById(R.id.me_gonggao);
		fankui = (RelativeLayout) view.findViewById(R.id.me_yijianfankui);
		aboutus = (RelativeLayout) view.findViewById(R.id.me_aboutus);
		me_rili = (RelativeLayout) view.findViewById(R.id.me_rili);
		me_hezuo = (RelativeLayout) view.findViewById(R.id.me_huoban);
		me_etf = (RelativeLayout) view.findViewById(R.id.me_etf);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActionBar = getActivity().getActionBar();
		mActionBar.setTitle("我");
		kaihu.setOnClickListener(myOnclicklister());
		huodong.setOnClickListener(myOnclicklister());
		gonggao.setOnClickListener(myOnclicklister());
		fankui.setOnClickListener(myOnclicklister());
		aboutus.setOnClickListener(myOnclicklister());
		me_rili.setOnClickListener(myOnclicklister());
		me_hezuo.setOnClickListener(myOnclicklister());
		me_etf.setOnClickListener(myOnclicklister());
	}

	private OnClickListener myOnclicklister() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.me_kaihu:
					Intent i = new Intent();
					i.setClass(getActivity(), MeKaiHuActivity.class);
					startActivity(i);
					break;
				case R.id.me_huodong:
					Intent i1 = new Intent();
					i1.setClass(getActivity(), MeHuoDongActivity.class);
					startActivity(i1);
					break;
				case R.id.me_gonggao:
					Intent i2 = new Intent();
					i2.setClass(getActivity(), MeGongGaoActivity.class);
					startActivity(i2);
					break;
				case R.id.me_yijianfankui:
					Intent i3 = new Intent();
					i3.setClass(getActivity(), MeFanKuiActivity.class);
					startActivity(i3);
					break;
				case R.id.me_aboutus:
					Intent i4 = new Intent();
					i4.setClass(getActivity(), MeAboutUsActivity.class);
					startActivity(i4);
					break;
				case R.id.me_rili:
					Intent i5 = new Intent();
					i5.setClass(getActivity(), showRiLiActivity.class);
					startActivity(i5);
					break;
				case R.id.me_huoban:
					Intent i6 = new Intent();
					i6.setClass(getActivity(), ShowParterActivity.class);
					startActivity(i6);
					break;
				case R.id.me_etf:
					Intent i7 = new Intent();
					i7.setClass(getActivity(), ShowETFActivity.class);
					startActivity(i7);
					break;

				default:
					break;
				}

			}
		};

	}

}
