package com.xiyou.apps.lookpan.fragment.showactivity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;

import com.xiyou.apps.lookpan.MainApplication;
import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.utils.Util;

public class ShowRiLi_3_Activity extends Activity {

	private ActionBar mActionBar;
	private CalendarView mCalendarView;
	private MainApplication mApplication;

	private String today, yesteday;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_rili_3);
		mActionBar = getActionBar();
		mActionBar.setTitle("日期筛选");
		mActionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.title_shang));
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mApplication = (MainApplication) getApplication();
		mCalendarView = (CalendarView) findViewById(R.id.calendarView1);
		mCalendarView.setOnDateChangeListener(new OnDateChangeListener() {

			@Override
			public void onSelectedDayChange(CalendarView view, int year,
					int month, int dayOfMonth) {
				today = year + "-" + checkedDay(month) + "-"
						+ checkedDay(dayOfMonth);
				yesteday = Util.getSpecifiedDayBefore(today);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.rili_riqi, menu);
		return super.onCreateOptionsMenu(menu);

	}

	private String checkedDay(int month) {
		switch (month) {
		case 1:
			return "01";
		case 2:
			return "02";
		case 3:
			return "03";
		case 4:
			return "04";
		case 5:
			return "05";
		case 6:
			return "06";
		case 7:
			return "07";
		case 8:
			return "08";
		case 9:
			return "09";

		default:
			return month + "";

		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.action_item_share:

			mApplication.setToday(today);
			mApplication.setYesteday(yesteday);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}