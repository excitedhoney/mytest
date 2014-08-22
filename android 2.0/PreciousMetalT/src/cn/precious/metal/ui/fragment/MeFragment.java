package cn.precious.metal.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.precious.metal.R;
import cn.precious.metal.base.BaseFragment;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.ui.CalcToolActivity;
import cn.precious.metal.ui.ETFReportActivity;
import cn.precious.metal.ui.EconomicCalendaActivity;
import cn.precious.metal.ui.LoginActivity;
import cn.precious.metal.ui.MeFanKuiActivity;
import cn.precious.metal.ui.MeKaiHuActivity;
import cn.precious.metal.ui.RemindListActivity;
import cn.precious.metal.ui.SettingActivity;
import cn.precious.metal.ui.VirtualTradeActivity;
import cn.precious.metal.utils.Utils;

public class MeFragment extends BaseFragment implements OnClickListener {

	private LinearLayout llEconCalendar, llETFReport, llUnitCalc,
			llVirtualTrade, llSetting, llRemind, llkaihu, llkankui;

	private Button loginBtn;

	private TextView userName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tools, null);
		initView(view);
		initListener();
		return view;
	}

	public void initView(View view) {
		llEconCalendar = (LinearLayout) view.findViewById(R.id.LinearLayout12);
		llETFReport = (LinearLayout) view.findViewById(R.id.ll_etf_report);
		llUnitCalc = (LinearLayout) view.findViewById(R.id.ll_unit_calc);
		llkaihu = (LinearLayout) view.findViewById(R.id.ll_kaihu);
		llkankui = (LinearLayout) view.findViewById(R.id.ll_fankui);

		llVirtualTrade = (LinearLayout) view
				.findViewById(R.id.ll_virtual_trade);
		llSetting = (LinearLayout) view.findViewById(R.id.ll_setting);
		llRemind = (LinearLayout) view.findViewById(R.id.ll_remind_setting);

		loginBtn = (Button) view.findViewById(R.id.loginBtn);
		userName = (TextView) view.findViewById(R.id.username);
	}

	public void initListener() {
		llEconCalendar.setOnClickListener(this);
		llETFReport.setOnClickListener(this);
		llUnitCalc.setOnClickListener(this);
		llVirtualTrade.setOnClickListener(this);
		llSetting.setOnClickListener(this);
		llRemind.setOnClickListener(this);
		llkaihu.setOnClickListener(this);
		llkankui.setOnClickListener(this);

		loginBtn.setOnClickListener(this);

	}

	@Override
	public void onResume() {
		super.onResume();
		if (AppSetting.getInstance(getActivity()).getPhoneNumber() != null
				&& !"".equals(AppSetting.getInstance(getActivity())
						.getPhoneNumber())) {
			userName.setText(AppSetting.getInstance(getActivity())
					.getPhoneNumber());
			loginBtn.setText("退出登录");
		} else {
			userName.setText("未登录");
			loginBtn.setVisibility(View.VISIBLE);
		}
	}

	private DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			Intent intentVirtual = new Intent(getActivity(),
					LoginActivity.class);
			intentVirtual.putExtra("flag", "virtual");
			startActivity(intentVirtual);
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.LinearLayout12:
			Intent intentEcon = new Intent(getActivity(),
					EconomicCalendaActivity.class);
			startActivity(intentEcon);
			break;
		case R.id.ll_virtual_trade:
			if (AppSetting.getInstance(getActivity()).isLoginOn()) {
				Intent intentVirtual = new Intent(getActivity(),
						VirtualTradeActivity.class);
				startActivity(intentVirtual);
			} else {
				Utils.showCusAlertDialog(getActivity(), "提示信息",
						"模拟交易需要登陆后才能使用", "登录", "取消", negativeListener, null);
			}
			break;
		case R.id.ll_etf_report:
			Intent intentETF = new Intent(getActivity(),
					ETFReportActivity.class);
			startActivity(intentETF);
			break;
		case R.id.ll_unit_calc:
			Intent intentCacl = new Intent(getActivity(),
					CalcToolActivity.class);
			startActivity(intentCacl);
			break;
		case R.id.ll_setting:
			Intent intentSetting = new Intent(getActivity(),
					SettingActivity.class);
			startActivity(intentSetting);
			break;
		case R.id.ll_remind_setting:
			if (AppSetting.getInstance(getActivity()).isLoginOn()) {
				if (!isFastDoubleClick()) {
					Intent intentRemind = new Intent(getActivity(),
							RemindListActivity.class);
					startActivity(intentRemind);
				}
			} else {
				showRemindDialog("提示信息", "添加提醒需要登陆后才能使用", "登录", "取消");
			}

			break;
		case R.id.loginBtn:
			if (!AppSetting.getInstance(getActivity()).isLoginOn()) {
				Intent loginIntent = new Intent(getActivity(),
						LoginActivity.class);
				startActivity(loginIntent);
			} else {
				AppSetting.getInstance(getActivity()).clearUserInfo();
				userName.setText("未登录");
				loginBtn.setText("登录");
			}
			break;
		case R.id.ll_kaihu:
			Intent intentkaihu = new Intent(getActivity(),
					MeKaiHuActivity.class);
			startActivity(intentkaihu);
			break;
		case R.id.ll_fankui:
			Intent intentfankui = new Intent(getActivity(),
					MeFanKuiActivity.class);
			startActivity(intentfankui);
			break;
		default:
			break;
		}
	}

	private DialogInterface.OnClickListener remindListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			Intent intentVirtual = new Intent(getActivity(),
					LoginActivity.class);
			intentVirtual.putExtra("flag", "remind");
			startActivity(intentVirtual);
		}
	};

	private AlertDialog mAlertDialog = null;

	public void showRemindDialog(String title, String message,
			String negativeBtn, String positiveBtn) {

		if (mAlertDialog == null) {
			mAlertDialog = new AlertDialog.Builder(getActivity())
					.setTitle(title).setMessage(message)
					.setNegativeButton(negativeBtn, negativeListener)
					.setPositiveButton(positiveBtn, null).create();
		}

		if (!mAlertDialog.isShowing())
			mAlertDialog.show();
	}

	private long lastClickTime = 0;

	public boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

}
