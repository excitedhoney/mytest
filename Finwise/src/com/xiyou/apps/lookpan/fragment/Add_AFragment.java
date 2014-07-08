package com.xiyou.apps.lookpan.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.db.BaseDataBase;

public class Add_AFragment extends Fragment {

	private CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb10, cb11;
	String tag;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tag = getArguments().getString("tag");
		if ("1".equals(tag)) {
			return inflater.inflate(R.layout.add_jingui, container, false);
		} else if ("2".equals(tag)) {
			return inflater.inflate(R.layout.add_shj, container, false);
		} else if ("3".equals(tag)) {
			return inflater.inflate(R.layout.add_waihui, container, false);
		} else if ("4".equals(tag)) {
			return inflater.inflate(R.layout.add_qihuo, container, false);
		} else {
			return inflater.inflate(R.layout.add_guzhi, container, false);
		}

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		if ("1".equals(tag)) {
			cb1 = (CheckBox) view.findViewById(R.id.checkBox1);
			cb2 = (CheckBox) view.findViewById(R.id.checkBox2);
			cb3 = (CheckBox) view.findViewById(R.id.checkBox3);
			cb4 = (CheckBox) view.findViewById(R.id.checkBox4);
		} else if ("2".equals(tag)) {
			cb1 = (CheckBox) view.findViewById(R.id.checkBox1);
			cb2 = (CheckBox) view.findViewById(R.id.checkBox2);
			cb3 = (CheckBox) view.findViewById(R.id.checkBox3);
			cb4 = (CheckBox) view.findViewById(R.id.checkBox4);
			cb5 = (CheckBox) view.findViewById(R.id.checkBox5);
			cb6 = (CheckBox) view.findViewById(R.id.checkBox6);
			cb7 = (CheckBox) view.findViewById(R.id.checkBox7);
		} else if ("3".equals(tag)) {
			cb1 = (CheckBox) view.findViewById(R.id.checkBox1);
			cb2 = (CheckBox) view.findViewById(R.id.checkBox2);
			cb3 = (CheckBox) view.findViewById(R.id.checkBox3);
			cb4 = (CheckBox) view.findViewById(R.id.checkBox4);
			cb5 = (CheckBox) view.findViewById(R.id.checkBox5);
			cb6 = (CheckBox) view.findViewById(R.id.checkBox6);
			cb7 = (CheckBox) view.findViewById(R.id.checkBox7);
			cb8 = (CheckBox) view.findViewById(R.id.checkBox8);
			cb9 = (CheckBox) view.findViewById(R.id.checkBox9);
			cb10 = (CheckBox) view.findViewById(R.id.checkBox10);
		} else {
			cb1 = (CheckBox) view.findViewById(R.id.checkBox1);
			cb2 = (CheckBox) view.findViewById(R.id.checkBox2);
			cb3 = (CheckBox) view.findViewById(R.id.checkBox3);
			cb4 = (CheckBox) view.findViewById(R.id.checkBox4);
			cb5 = (CheckBox) view.findViewById(R.id.checkBox5);
			cb6 = (CheckBox) view.findViewById(R.id.checkBox6);
			cb7 = (CheckBox) view.findViewById(R.id.checkBox7);
			cb8 = (CheckBox) view.findViewById(R.id.checkBox8);
			cb9 = (CheckBox) view.findViewById(R.id.checkBox9);
			cb10 = (CheckBox) view.findViewById(R.id.checkBox10);
			cb11 = (CheckBox) view.findViewById(R.id.checkBox11);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
		if ("1".equals(tag)) {
			cb1.setOnCheckedChangeListener(mcheked());
			cb2.setOnCheckedChangeListener(mcheked());
			cb3.setOnCheckedChangeListener(mcheked());
			cb4.setOnCheckedChangeListener(mcheked());
		} else if ("2".equals(tag)) {
			cb1.setOnCheckedChangeListener(mcheked());
			cb2.setOnCheckedChangeListener(mcheked());
			cb3.setOnCheckedChangeListener(mcheked());
			cb4.setOnCheckedChangeListener(mcheked());
			cb5.setOnCheckedChangeListener(mcheked());
			cb6.setOnCheckedChangeListener(mcheked());
			cb7.setOnCheckedChangeListener(mcheked());
		} else if ("3".equals(tag)) {
			cb1.setOnCheckedChangeListener(mcheked());
			cb2.setOnCheckedChangeListener(mcheked());
			cb3.setOnCheckedChangeListener(mcheked());
			cb4.setOnCheckedChangeListener(mcheked());
			cb5.setOnCheckedChangeListener(mcheked());
			cb6.setOnCheckedChangeListener(mcheked());
			cb7.setOnCheckedChangeListener(mcheked());
			cb8.setOnCheckedChangeListener(mcheked());
			cb9.setOnCheckedChangeListener(mcheked());
			cb10.setOnCheckedChangeListener(mcheked());
		} else {
			cb1.setOnCheckedChangeListener(mcheked());
			cb2.setOnCheckedChangeListener(mcheked());
			cb3.setOnCheckedChangeListener(mcheked());
			cb4.setOnCheckedChangeListener(mcheked());
			cb5.setOnCheckedChangeListener(mcheked());
			cb6.setOnCheckedChangeListener(mcheked());
			cb7.setOnCheckedChangeListener(mcheked());
			cb8.setOnCheckedChangeListener(mcheked());
			cb9.setOnCheckedChangeListener(mcheked());
			cb10.setOnCheckedChangeListener(mcheked());
			cb11.setOnCheckedChangeListener(mcheked());
		}
	}

	private void init() {
		BaseDataBase.getInstance(getActivity()).open();
		ArrayList<String> list = BaseDataBase.getInstance(getActivity())
				.selectZiXuanChannel();
		if ("1".equals(tag)) {
			if (list.contains("TTYXAGUSD")) {
				cb1.setChecked(true);
			}
			if (list.contains("TTJPD")) {
				cb2.setChecked(true);
			}
			if (list.contains("TTJPT")) {
				cb3.setChecked(true);
			}
			if (list.contains("TTNI")) {
				cb4.setChecked(true);
			}

		} else if ("2".equals(tag)) {

			if (list.contains("AUTD")) {
				cb1.setChecked(true);
			}
			if (list.contains("AGTD")) {
				cb2.setChecked(true);
			}
			if (list.contains("MAUTD")) {
				cb3.setChecked(true);
			}
			if (list.contains("AU9999")) {
				cb4.setChecked(true);
			}
			if (list.contains("AU9995")) {
				cb5.setChecked(true);
			}
			if (list.contains("AU100G")) {
				cb6.setChecked(true);
			}
			if (list.contains("PT9995")) {
				cb7.setChecked(true);
			}
		} else if ("3".equals(tag)) {

			if (list.contains("USDollarIndex")) {
				cb1.setChecked(true);
			}
			if (list.contains("USDCNY")) {
				cb2.setChecked(true);
			}
			if (list.contains("EURUSD")) {
				cb3.setChecked(true);
			}
			if (list.contains("GBPUSD")) {
				cb4.setChecked(true);
			}
			if (list.contains("USDJPY")) {
				cb5.setChecked(true);
			}
			if (list.contains("USDCHF")) {
				cb6.setChecked(true);
			}
			if (list.contains("AUDUSD")) {
				cb7.setChecked(true);
			}
			if (list.contains("USDHKD")) {
				cb8.setChecked(true);
			}
			if (list.contains("EURJPY")) {
				cb9.setChecked(true);
			}
			if (list.contains("BTCUSD")) {
				cb10.setChecked(true);
			}
		} else if ("4".equals(tag)) {

			if (list.contains("XAUUSD")) {
				cb1.setChecked(true);
			}
			if (list.contains("XAGUSD")) {
				cb2.setChecked(true);
			}
			if (list.contains("Copper")) {
				cb3.setChecked(true);
			}
			if (list.contains("UKOil")) {
				cb4.setChecked(true);
			}
			if (list.contains("XPTUSD")) {
				cb5.setChecked(true);
			}
			if (list.contains("XPDUSD")) {
				cb6.setChecked(true);
			}
			if (list.contains("NGAS")) {
				cb7.setChecked(true);
			}
			if (list.contains("CORN")) {
				cb8.setChecked(true);
			}
			if (list.contains("SOYBEAN")) {
				cb9.setChecked(true);
			}
			if (list.contains("WHEAT")) {
				cb10.setChecked(true);
			}
			if (list.contains("SUGAR")) {
				cb11.setChecked(true);
			}
		} else if ("5".equals(tag)) {

			if (list.contains("000001")) {
				cb1.setChecked(true);
			}
			if (list.contains("399001")) {
				cb2.setChecked(true);
			}
			if (list.contains("399006")) {
				cb3.setChecked(true);
			}
			if (list.contains("399300")) {
				cb4.setChecked(true);
			}
			if (list.contains("HKG33INDEX")) {
				cb5.setChecked(true);
			}
			if (list.contains("SPX500INDEX")) {
				cb6.setChecked(true);
			}
			if (list.contains("NAS100INDEX")) {
				cb7.setChecked(true);
			}
			if (list.contains("US30INDEX")) {
				cb8.setChecked(true);
			}
			if (list.contains("JPN225INDEX")) {
				cb9.setChecked(true);
			}
			if (list.contains("GER30INDEX")) {
				cb10.setChecked(true);
			}
			if (list.contains("FRA40INDEX")) {
				cb11.setChecked(true);
			}
		}

		BaseDataBase.getInstance(getActivity()).close();
	}

	public OnCheckedChangeListener mcheked() {
		return new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				switch (buttonView.getId()) {
				case R.id.checkBox1:
					BaseDataBase.getInstance(getActivity()).open();
					if ("1".equals(tag)) {
						if (isChecked) {
							if (!check("TTYXAGUSD")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("TTYXAGUSD");
							}
						} else {
							if (check("TTYXAGUSD")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("TTYXAGUSD");
							}
						}
					}
					if ("2".equals(tag)) {
						if (isChecked) {
							if (!check("AUTD")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("AUTD");
							}
						} else {
							if (check("AUTD")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("AUTD");
							}
						}
					}
					if ("3".equals(tag)) {
						if (isChecked) {
							if (!check("USDollarIndex")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel(
												"USDollarIndex");
							}
						} else {
							if (check("USDollarIndex")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel(
												"USDollarIndex");
							}
						}
					}
					if ("4".equals(tag)) {
						if (isChecked) {
							if (!check("XAUUSD")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("XAUUSD");
							}
						} else {
							if (check("XAUUSD")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("XAUUSD");
							}
						}
					}

					if ("5".equals(tag)) {
						if (isChecked) {
							if (!check("000001")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("000001");
							}
						} else {
							if (check("000001")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("000001");
							}
						}
					}
					BaseDataBase.getInstance(getActivity()).close();
					break;

				case R.id.checkBox2:
					BaseDataBase.getInstance(getActivity()).open();
					if ("1".equals(tag)) {
						if (isChecked) {
							if (!check("TTJPD")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("TTJPD");
							}
						} else {
							if (check("TTJPD")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("TTJPD");
							}
						}
					}
					if ("2".equals(tag)) {
						if (isChecked) {
							if (!check("AGTD")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("AGTD");
							}
						} else {
							if (check("AGTD")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("AGTD");
							}
						}
					}
					if ("3".equals(tag)) {
						if (isChecked) {
							if (!check("USDCNY")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("USDCNY");
							}
						} else {
							if (check("USDCNY")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("USDCNY");
							}
						}
					}
					if ("4".equals(tag)) {
						if (isChecked) {
							if (!check("XAGUSD")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("XAGUSD");
							}
						} else {
							if (check("XAGUSD")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("XAGUSD");
							}
						}
					}

					if ("5".equals(tag)) {
						if (isChecked) {
							if (!check("399001")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("399001");
							}
						} else {
							if (check("399001")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("399001");
							}
						}
					}
					BaseDataBase.getInstance(getActivity()).close();
					break;

				case R.id.checkBox3:

					BaseDataBase.getInstance(getActivity()).open();
					if ("1".equals(tag)) {
						if (isChecked) {
							if (!check("TTJPT")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("TTJPT");
							}
						} else {
							if (check("TTJPT")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("TTJPT");
							}
						}
					}
					if ("2".equals(tag)) {
						if (isChecked) {
							if (!check("MAUTD")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("MAUTD");
							}
						} else {
							if (check("MAUTD")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("MAUTD");
							}
						}
					}
					if ("3".equals(tag)) {
						if (isChecked) {
							if (!check("EURUSD")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("EURUSD");
							}
						} else {
							if (check("EURUSD")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("EURUSD");
							}
						}
					}
					if ("4".equals(tag)) {
						if (isChecked) {
							if (!check("Copper")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("Copper");
							}
						} else {
							if (check("Copper")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("Copper");
							}
						}
					}
					if ("5".equals(tag)) {
						if (isChecked) {
							if (!check("399006")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("399006");
							}
						} else {
							if (check("399006")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("399006");
							}
						}
					}
					BaseDataBase.getInstance(getActivity()).close();
					break;

				case R.id.checkBox4:

					BaseDataBase.getInstance(getActivity()).open();
					if ("1".equals(tag)) {
						if (isChecked) {
							if (!check("TTNI")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("TTNI");
							}
						} else {
							if (check("TTNI")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("TTNI");
							}
						}
					}
					if ("2".equals(tag)) {
						if (isChecked) {
							if (!check("AU9999")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("AU9999");
							}
						} else {
							if (check("AU9999")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("AU9999");
							}
						}
					}
					if ("3".equals(tag)) {
						if (isChecked) {
							if (!check("GBPUSD")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("GBPUSD");
							}
						} else {
							if (check("GBPUSD")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("GBPUSD");
							}
						}
					}
					if ("4".equals(tag)) {
						if (isChecked) {
							if (!check("UKOil")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("UKOil");
							}
						} else {
							if (check("UKOil")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("UKOil");
							}
						}
					}
					if ("5".equals(tag)) {
						if (isChecked) {
							if (!check("399300")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("399300");
							}
						} else {
							if (check("399300")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("399300");
							}
						}
					}
					BaseDataBase.getInstance(getActivity()).close();
					break;
				case R.id.checkBox5:

					BaseDataBase.getInstance(getActivity()).open();
					if ("2".equals(tag)) {
						if (isChecked) {
							if (!check("AU9995")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("AU9995");
							}
						} else {
							if (check("AU9995")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("AU9995");

							}
						}
					}
					if ("3".equals(tag)) {
						if (isChecked) {
							if (!check("USDJPY")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("USDJPY");
							}
						} else {
							if (check("USDJPY")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("USDJPY");
							}
						}
					}
					if ("4".equals(tag)) {
						if (isChecked) {
							if (!check("XPTUSD")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("XPTUSD");
							}
						} else {
							if (check("XPTUSD")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("XPTUSD");
							}
						}
					}
					if ("5".equals(tag)) {
						if (isChecked) {
							if (!check("HKG33INDEX")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("HKG33INDEX");
							}
						} else {
							if (check("HKG33INDEX")) {
								BaseDataBase
										.getInstance(getActivity())
										.DelectSingleZiXuanChannel("HKG33INDEX");
							}
						}
					}
					BaseDataBase.getInstance(getActivity()).close();

				case R.id.checkBox6:
					BaseDataBase.getInstance(getActivity()).open();
					if ("2".equals(tag)) {
						if (isChecked) {
							if (!check("AU100G")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("AU100G");
							}
						} else {
							if (check("AU100G")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("AU100G");
							}
						}
					}
					if ("3".equals(tag)) {
						if (isChecked) {
							if (!check("USDCHF")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("USDCHF");
							}
						} else {
							if (check("USDCHF")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("USDCHF");
							}
						}
					}
					if ("4".equals(tag)) {
						if (isChecked) {
							if (!check("XPDUSD")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("XPDUSD");
							}
						} else {
							if (check("XPDUSD")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("XPDUSD");
							}
						}
					}
					if ("5".equals(tag)) {
						if (isChecked) {
							if (!check("SPX500INDEX")) {
								BaseDataBase
										.getInstance(getActivity())
										.inserSingleZiXuanChannel("SPX500INDEX");
							}
						} else {
							if (check("SPX500INDEX")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel(
												"SPX500INDEX");
							}
						}
					}
					BaseDataBase.getInstance(getActivity()).close();
				case R.id.checkBox7:
					BaseDataBase.getInstance(getActivity()).open();
					if ("2".equals(tag)) {
						if (isChecked) {
							if (!check("PT9995")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("PT9995");
							}
						} else {
							if (check("PT9995")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("PT9995");
							}
						}
					}
					if ("3".equals(tag)) {
						if (isChecked) {
							if (!check("AUDUSD")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("AUDUSD");
							}
						} else {
							if (check("AUDUSD")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("AUDUSD");
							}
						}
					}
					if ("4".equals(tag)) {
						if (isChecked) {
							if (!check("NGAS")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("NGAS");
							}
						} else {
							if (check("NGAS")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("NGAS");
							}
						}
					}
					if ("5".equals(tag)) {
						if (isChecked) {
							if (!check("NAS100INDEX")) {
								BaseDataBase
										.getInstance(getActivity())
										.inserSingleZiXuanChannel("NAS100INDEX");
							}
						} else {
							if (check("NAS100INDEX")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel(
												"NAS100INDEX");
							}
						}
					}
					BaseDataBase.getInstance(getActivity()).close();
				case R.id.checkBox8:
					BaseDataBase.getInstance(getActivity()).open();
					if ("3".equals(tag)) {
						if (isChecked) {
							if (!check("USDHKD")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("USDHKD");
							}
						} else {
							if (check("USDHKD")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("USDHKD");
							}
						}
					}
					if ("4".equals(tag)) {
						if (isChecked) {
							if (!check("CORN")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("CORN");
							}
						} else {
							if (check("CORN")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("CORN");
							}
						}
					}
					if ("5".equals(tag)) {
						if (isChecked) {
							if (!check("US30INDEX")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("US30INDEX");
							}
						} else {
							if (check("US30INDEX")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("US30INDEX");
							}
						}
					}
					BaseDataBase.getInstance(getActivity()).close();
				case R.id.checkBox9:
					BaseDataBase.getInstance(getActivity()).open();
					if ("3".equals(tag)) {
						if (isChecked) {
							if (!check("EURJPY")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("EURJPY");
							}
						} else {
							if (check("EURJPY")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("EURJPY");
							}
						}
					}
					if ("4".equals(tag)) {
						if (isChecked) {
							if (!check("SOYBEAN")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("SOYBEAN");
							}
						} else {
							if (check("SOYBEAN")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("SOYBEAN");
							}
						}
					}
					if ("5".equals(tag)) {
						if (isChecked) {
							if (!check("JPN225INDEX")) {
								BaseDataBase
										.getInstance(getActivity())
										.inserSingleZiXuanChannel("JPN225INDEX");
							}
						} else {
							if (check("JPN225INDEX")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel(
												"JPN225INDEX");
							}
						}
					}
					BaseDataBase.getInstance(getActivity()).close();
				case R.id.checkBox10:
					BaseDataBase.getInstance(getActivity()).open();
					if ("3".equals(tag)) {
						if (isChecked) {
							if (!check("BTCUSD")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("BTCUSD");
							}
						} else {
							if (check("BTCUSD")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("BTCUSD");
							}
						}
					}
					if ("4".equals(tag)) {
						if (isChecked) {
							if (!check("WHEAT")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("WHEAT");
							}
						} else {
							if (check("WHEAT")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("WHEAT");
							}
						}
					}
					if ("5".equals(tag)) {
						if (isChecked) {
							if (!check("GER30INDEX")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("GER30INDEX");
							}
						} else {
							if (check("GER30INDEX")) {
								BaseDataBase
										.getInstance(getActivity())
										.DelectSingleZiXuanChannel("GER30INDEX");
							}
						}
					}
					BaseDataBase.getInstance(getActivity()).close();
				case R.id.checkBox11:
					BaseDataBase.getInstance(getActivity()).open();
					if ("4".equals(tag)) {
						if (isChecked) {
							if (!check("SUGAR")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("SUGAR");
							}
						} else {
							if (check("SUGAR")) {
								BaseDataBase.getInstance(getActivity())
										.DelectSingleZiXuanChannel("SUGAR");
							}
						}
					}
					if ("5".equals(tag)) {
						if (isChecked) {
							if (!check("FRA40INDEX")) {
								BaseDataBase.getInstance(getActivity())
										.inserSingleZiXuanChannel("FRA40INDEX");
							}

						} else {
							if (check("FRA40INDEX")) {
								BaseDataBase
										.getInstance(getActivity())
										.DelectSingleZiXuanChannel("FRA40INDEX");
							}
						}
					}
					BaseDataBase.getInstance(getActivity()).close();
				default:
					break;
				}
			}
		};

	}

	public boolean check(String str) {
		ArrayList<String> list = BaseDataBase.getInstance(getActivity())
				.selectZiXuanChannel();
		for (int i = 0; i < list.size(); i++) {
			if (str.equals(list.get(i))) {
				return true;
			}
		}
		return false;

	}
}