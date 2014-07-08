package com.xiyou.apps.lookpan.fragment.showactivity;

import java.util.ArrayList;
import java.util.Iterator;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.xiyou.apps.lookpan.MainApplication;
import com.xiyou.apps.lookpan.R;

public class ShowRiLi_2_Activity extends Activity {

	private ActionBar mActionBar;
	private CheckBox CBUSA, CBCHINA, CBEU, CBGermany, CBIndia, CBJapan,
			CBAurica, CBhigh, CBnomorl, CBdi;
	private MainApplication mApplication;
	private ArrayList<String> counanyList;
	private ArrayList<String> importlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_rili_2);
		mActionBar = getActionBar();
		mActionBar.setTitle("日历筛选");
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.title_shang));
		findView();
	}

	private void findView() {
		// TODO Auto-generated method stub
		mApplication = (MainApplication) this.getApplication();

		CBUSA = (CheckBox) findViewById(R.id.checkbox1);
		CBCHINA = (CheckBox) findViewById(R.id.checkbox2);
		CBEU = (CheckBox) findViewById(R.id.checkbox3);
		CBGermany = (CheckBox) findViewById(R.id.checkbox4);
		CBIndia = (CheckBox) findViewById(R.id.checkbox5);
		CBJapan = (CheckBox) findViewById(R.id.checkbox6);
		CBAurica = (CheckBox) findViewById(R.id.checkbox7);
		CBhigh = (CheckBox) findViewById(R.id.checkbox8);
		CBnomorl = (CheckBox) findViewById(R.id.checkbox9);
		CBdi = (CheckBox) findViewById(R.id.checkbox10);
		counanyList = mApplication.getCountrylist();
		importlist = mApplication.getImportList();
		inchecked(CBUSA);
		inchecked(CBCHINA);
		inchecked(CBEU);
		inchecked(CBGermany);
		inchecked(CBIndia);
		inchecked(CBJapan);
		inchecked(CBAurica);
		inchecked(CBhigh);
		inchecked(CBnomorl);
		inchecked(CBdi);

		CBUSA.setOnCheckedChangeListener(new myCheckBoxListener());
		CBCHINA.setOnCheckedChangeListener(new myCheckBoxListener());
		CBEU.setOnCheckedChangeListener(new myCheckBoxListener());
		CBIndia.setOnCheckedChangeListener(new myCheckBoxListener());
		CBGermany.setOnCheckedChangeListener(new myCheckBoxListener());
		CBJapan.setOnCheckedChangeListener(new myCheckBoxListener());
		CBAurica.setOnCheckedChangeListener(new myCheckBoxListener());
		CBhigh.setOnCheckedChangeListener(new myCheckBoxListener());
		CBnomorl.setOnCheckedChangeListener(new myCheckBoxListener());
		CBdi.setOnCheckedChangeListener(new myCheckBoxListener());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void inchecked(CheckBox cb) {
		switch (cb.getId()) {
		case R.id.checkbox1:
			for (int i = 0; i < counanyList.size(); i++) {
				if ("美国".equals(counanyList.get(i))) {
					cb.setChecked(true);
				}
			}

			break;
		case R.id.checkbox2:
			for (int i = 0; i < counanyList.size(); i++) {
				if ("中国".equals(counanyList.get(i))) {
					cb.setChecked(true);
				}
			}

			break;
		case R.id.checkbox3:
			for (int i = 0; i < counanyList.size(); i++) {
				if ("欧元区".equals(counanyList.get(i))) {
					cb.setChecked(true);
				}
			}

			break;
		case R.id.checkbox4:
			for (int i = 0; i < counanyList.size(); i++) {
				if ("德国".equals(counanyList.get(i))) {
					cb.setChecked(true);
				}
			}

			break;
		case R.id.checkbox5:
			for (int i = 0; i < counanyList.size(); i++) {
				if ("印度".equals(counanyList.get(i))) {
					cb.setChecked(true);
				}
			}

			break;
		case R.id.checkbox6:
			for (int i = 0; i < counanyList.size(); i++) {
				if ("日本".equals(counanyList.get(i))) {
					cb.setChecked(true);
				}
			}

			break;
		case R.id.checkbox7:
			for (int i = 0; i < counanyList.size(); i++) {
				if ("澳大利亚".equals(counanyList.get(i))) {
					cb.setChecked(true);
				}
			}

			break;
		case R.id.checkbox8:
			for (int i = 0; i < importlist.size(); i++) {
				if ("3".equals(importlist.get(i))) {
					cb.setChecked(true);
				}
			}

			break;
		case R.id.checkbox9:
			for (int i = 0; i < importlist.size(); i++) {
				if ("2".equals(importlist.get(i))) {
					cb.setChecked(true);
				}
			}

			break;
		case R.id.checkbox10:
			for (int i = 0; i < importlist.size(); i++) {
				if ("1".equals(importlist.get(i))) {
					cb.setChecked(true);
				}
			}

			break;

		default:
			break;
		}

	}

	class myCheckBoxListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {

			switch (buttonView.getId()) {
			case (R.id.checkbox1):
				if (isChecked) {
					counanyList.add("美国");
				} else {
					Iterator<String> sListIterator = counanyList.iterator();
					while (sListIterator.hasNext()) {
						String e = sListIterator.next();
						if ("美国".equals(e)) {
							sListIterator.remove();
						}
					}
				}
				mApplication.setCountrylist(counanyList);
				break;
			case (R.id.checkbox2):
				if (isChecked) {
					counanyList.add("中国");
				} else {
					Iterator<String> sListIterator = counanyList.iterator();
					while (sListIterator.hasNext()) {
						String e = sListIterator.next();
						if ("中国".equals(e)) {
							sListIterator.remove();
						}
					}
				}
				mApplication.setCountrylist(counanyList);
				break;

			case (R.id.checkbox3):
				if (isChecked) {
					counanyList.add("欧元区");
				} else {
					Iterator<String> sListIterator = counanyList.iterator();
					while (sListIterator.hasNext()) {
						String e = sListIterator.next();
						if ("欧元区".equals(e)) {
							sListIterator.remove();
						}
					}
				}
				mApplication.setCountrylist(counanyList);
				break;
			case (R.id.checkbox4):
				if (isChecked) {
					counanyList.add("德国");
				} else {
					Iterator<String> sListIterator = counanyList.iterator();
					while (sListIterator.hasNext()) {
						String e = sListIterator.next();
						if ("德国".equals(e)) {
							sListIterator.remove();
						}
					}
				}
				mApplication.setCountrylist(counanyList);
				break;
			case (R.id.checkbox5):
				if (isChecked) {
					counanyList.add("印度");

				} else {
					Iterator<String> sListIterator = counanyList.iterator();
					while (sListIterator.hasNext()) {
						String e = sListIterator.next();
						if ("印度".equals(e)) {
							sListIterator.remove();
						}
					}
				}
				mApplication.setCountrylist(counanyList);
				break;
			case (R.id.checkbox6):
				if (isChecked) {
					counanyList.add("日本");
				} else {
					Iterator<String> sListIterator = counanyList.iterator();
					while (sListIterator.hasNext()) {
						String e = sListIterator.next();
						if ("日本".equals(e)) {
							sListIterator.remove();
						}
					}
				}
				mApplication.setCountrylist(counanyList);

				break;
			case (R.id.checkbox7):
				if (isChecked) {
					counanyList.add("澳大利亚");
				} else {
					Iterator<String> sListIterator = counanyList.iterator();
					while (sListIterator.hasNext()) {
						String e = sListIterator.next();
						if ("澳大利亚".equals(e)) {
							sListIterator.remove();
						}
					}
				}
				mApplication.setCountrylist(counanyList);

				break;
			case (R.id.checkbox8):
				if (isChecked) {
					importlist.add("3");

				} else {
					Iterator<String> slIterator = importlist.iterator();
					while (slIterator.hasNext()) {

						String e = slIterator.next();
						if ("3".equals(e)) {
							slIterator.remove();
						}
					}
				}
				mApplication.setImportList(importlist);
				break;
			case (R.id.checkbox9):
				if (isChecked) {
					importlist.add("2");

				} else {
					Iterator<String> slIterator = importlist.iterator();
					while (slIterator.hasNext()) {

						String e = slIterator.next();
						if ("2".equals(e)) {
							slIterator.remove();
						}
					}
				}
				mApplication.setImportList(importlist);
				;
				break;
			case (R.id.checkbox10):
				if (isChecked) {
					importlist.add("1");

				} else {
					Iterator<String> slIterator = importlist.iterator();
					while (slIterator.hasNext()) {

						String e = slIterator.next();
						if ("1".equals(e)) {
							slIterator.remove();
						}
					}
				}
				mApplication.setImportList(importlist);
				break;
			default:
				break;
			}

		}
	}
}
