package com.xiyou.apps.lookpan.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.precious.metal.utils.Utils;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.BaseActivity;
import com.xiyou.apps.lookpan.tools.AndroidUtils;

/**
 * 财经日历
 * 
 * @author mac
 * 
 */

@SuppressLint("SimpleDateFormat")
public class EconomicCalendaActivity extends BaseActivity implements
		OnClickListener {

	private String day;
	private String yesteday;
	private ArrayList<String> countryList = new ArrayList<String>();
	private ArrayList<String> importList = new ArrayList<String>();

	public static final String TAG = "EconomicCalendaActivity";

	private SimpleDateFormat ymdSdf = new SimpleDateFormat("yyyy-MM-dd");

	private SimpleDateFormat weekSdf = new SimpleDateFormat("EEEE");

	private ListView mListViewFD, mListViewFE, mListViewVN;
	private myadapter mAdapterFD, mAdapterFE, mAdapterVN;

	private LinearLayout nationalFlagDown, ratingDown;
	private TextView times, weeks;
	private boolean contry_checked, import_checked;
	private LinearLayout lin_contry, lin_import;
	private ImageView contry_img, import_img;
	private CheckBox CBUSA, CBCHINA, CBEU, CBGermany, CBIndia, CBJapan,
			CBAurica, one, two, three;
	private ScrollView top_relativelayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_econ_calenda);
		initView();
		initListener();
	}

	public void initView() {

		nationalFlagDown = (LinearLayout) findViewById(R.id.ll_natioanl_flag);
		ratingDown = (LinearLayout) findViewById(R.id.ll_rating);
		lin_contry = (LinearLayout) findViewById(R.id.LinearLayout_contcry);
		lin_import = (LinearLayout) findViewById(R.id.import_liner);

		times = (TextView) findViewById(R.id.times);
		weeks = (TextView) findViewById(R.id.weeks);

		contry_img = (ImageView) findViewById(R.id.img_contry);
		import_img = (ImageView) findViewById(R.id.img_import);

		top_relativelayout = (ScrollView) findViewById(R.id.linearLayout11111);
		CBUSA = (CheckBox) findViewById(R.id.checkbox1);
		CBCHINA = (CheckBox) findViewById(R.id.checkbox2);
		CBEU = (CheckBox) findViewById(R.id.checkbox3);
		CBGermany = (CheckBox) findViewById(R.id.checkbox4);
		CBIndia = (CheckBox) findViewById(R.id.checkbox5);
		CBJapan = (CheckBox) findViewById(R.id.checkbox6);
		CBAurica = (CheckBox) findViewById(R.id.checkbox7);
		one = (CheckBox) findViewById(R.id.one);
		two = (CheckBox) findViewById(R.id.two);
		three = (CheckBox) findViewById(R.id.three);
		times.setText(ymdSdf.format(new Date()));
		weeks.setText(weekSdf.format(new Date()));

		mListViewFD = (ListView) findViewById(R.id.listfd);
		mListViewFE = (ListView) findViewById(R.id.listFE);
		mListViewVN = (ListView) findViewById(R.id.listVN);
		mAdapterFD = new myadapter(this, 0);
		mAdapterFE = new myadapter(this, 1);
		mAdapterVN = new myadapter(this, 1);
		day = ymdSdf.format(new Date());
		yesteday = ymdSdf.format(AndroidUtils.getSpecifiedDayAfter(new Date(),
				-1));
		mListViewFD.setAdapter(mAdapterFD);
		mListViewFE.setAdapter(mAdapterFE);
		mListViewVN.setAdapter(mAdapterVN);
		onLoadData();
		CBUSA.setOnCheckedChangeListener(new myCheckBoxListener("美国"));
		CBCHINA.setOnCheckedChangeListener(new myCheckBoxListener("中国"));
		CBEU.setOnCheckedChangeListener(new myCheckBoxListener("欧元区"));
		CBGermany.setOnCheckedChangeListener(new myCheckBoxListener("德国"));
		CBIndia.setOnCheckedChangeListener(new myCheckBoxListener("印度"));
		CBJapan.setOnCheckedChangeListener(new myCheckBoxListener("日本"));
		CBAurica.setOnCheckedChangeListener(new myCheckBoxListener("澳大利亚"));

		one.setOnCheckedChangeListener(new myCheckBoxListener());
		two.setOnCheckedChangeListener(new myCheckBoxListener());
		three.setOnCheckedChangeListener(new myCheckBoxListener());

		top_relativelayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (contry_checked) {
					lin_contry.setVisibility(View.GONE);
					contry_checked = false;
					contry_img.setImageResource(R.drawable.arrow_down);
				}
				if (import_checked) {
					lin_import.setVisibility(View.GONE);
					import_checked = false;
					import_img.setImageResource(R.drawable.arrow_down);
				}
			}
		});
	}

	class myCheckBoxListener implements OnCheckedChangeListener {

		private String contryName;

		public myCheckBoxListener() {
		}

		public myCheckBoxListener(String contryName) {
			this.contryName = contryName;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			switch (buttonView.getId()) {
			case (R.id.checkbox1):
				checked(isChecked, contryName);
				Log.i("tag",
						"contryName:" + contryName + "list:"
								+ countryList.toString());
				onLoadData();
				break;
			case (R.id.checkbox2):
				checked(isChecked, contryName);
				onLoadData();
				break;
			case (R.id.checkbox3):
				checked(isChecked, contryName);
				onLoadData();
				break;
			case (R.id.checkbox4):
				checked(isChecked, contryName);
				onLoadData();
				break;
			case (R.id.checkbox5):
				checked(isChecked, contryName);
				onLoadData();
				break;
			case (R.id.checkbox6):
				checked(isChecked, contryName);
				onLoadData();
				break;
			case (R.id.checkbox7):
				checked(isChecked, contryName);
				onLoadData();
				break;
			case (R.id.one):
				if (isChecked) {
					importList.add("1");

				} else {
					importList.remove("1");
				}
				onLoadData();
				break;
			case (R.id.two):
				if (isChecked) {
					importList.add("2");
				} else {
					importList.remove("2");
				}
				onLoadData();
				break;
			case (R.id.three):
				if (isChecked) {
					importList.add("3");
				} else {
					importList.remove("3");
				}
				onLoadData();
				break;
			default:
				break;
			}
		}

		private void checked(boolean b, String name) {

			if (b) {
				countryList.add(name);
			} else {
				countryList.remove(name);
			}
		}

	}

	private void onLoadData() {

		new AsyncTask<Object, Object, ArrayList<CalendarBean>>() {
			@Override
			protected ArrayList<CalendarBean> doInBackground(Object... params) {
				return AndroidUtils.getCalendar2JSON(day, yesteday,
						countryList, importList);
			}

			protected void onPostExecute(
					java.util.ArrayList<CalendarBean> result) {
				if (null != result) {
					mAdapterFD.clear();
					mAdapterFE.clear();
					mAdapterVN.clear();
					mAdapterFD.addAll(getResult(result, "FD"));
					mAdapterFE.addAll(getResult(result, "FE"));
					mAdapterVN.addAll(getResult(result, "VN"));
					AndroidUtils.setListViewHeightBasedOnChildren(mListViewFD);
					AndroidUtils.setListViewHeightBasedOnChildren(mListViewFE);
					AndroidUtils.setListViewHeightBasedOnChildren(mListViewVN);

				}
			};

		}.execute();

	}

	public ArrayList<CalendarBean> getResult(ArrayList<CalendarBean> result,
			String type) {
		ArrayList<CalendarBean> list = new ArrayList<CalendarBean>();
		for (CalendarBean c : result) {
			if (type.equals(c.getCalendarType())) {
				list.add(c);
			}
		}
		return list;
	}

	public void initListener() {
		nationalFlagDown.setOnClickListener(this);
		ratingDown.setOnClickListener(this);
	}

	public void onBack(View view) {
		finish();
	}

	public void onCalenda(View view) {
		showCardWindowView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_natioanl_flag:
			if (contry_checked) {
				lin_contry.setVisibility(View.GONE);
				contry_checked = false;
				contry_img.setImageResource(R.drawable.arrow_down);
			} else {
				if (import_checked) {
					lin_import.setVisibility(View.GONE);
					import_checked = false;
					import_img.setImageResource(R.drawable.arrow_down);
				}
				lin_contry.setVisibility(View.VISIBLE);
				contry_checked = true;
				contry_img.setImageResource(R.drawable.arrow_down_blue);
			}
			break;
		case R.id.ll_rating:
			if (import_checked) {
				lin_import.setVisibility(View.GONE);
				import_checked = false;
				import_img.setImageResource(R.drawable.arrow_down);
			} else {
				if (contry_checked) {
					lin_contry.setVisibility(View.GONE);
					contry_checked = false;
					contry_img.setImageResource(R.drawable.arrow_down);
				}
				lin_import.setVisibility(View.VISIBLE);
				import_checked = true;
				import_img.setImageResource(R.drawable.arrow_down_blue);
			}
  
			
			break;

		default:
			break;
		}
	}

	private boolean cart_menu_display = false;
	private View mCardMenuView;
	private PopupWindow popupCardWindow;
	private CalendarView mCalendarView;

	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	public void showCardWindowView() {
		if (!cart_menu_display) {
			if (mCardMenuView == null) {
				mCardMenuView = LayoutInflater.from(this).inflate(
						R.layout.popup_calendar_card, null);
				mCalendarView = (CalendarView) mCardMenuView
						.findViewById(R.id.calendar_view);
				mCalendarView
						.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

							@Override
							public void onSelectedDayChange(CalendarView view,
									int year, int month, int dayOfMonth) {
								Calendar c = Calendar.getInstance();
								c.set(year, month, dayOfMonth);
								times.setText(ymdSdf.format(c.getTime()));
								weeks.setText(weekSdf.format(c.getTime()));
								day = ymdSdf.format(c.getTime());
								c.add(Calendar.DATE, -1);
								yesteday = ymdSdf.format((Date) c.getTime());
								onLoadData();
							}
						});

			}

			if (popupCardWindow == null) {
				popupCardWindow = new PopupWindow(
						mCardMenuView,
						Utils.getWindowWidth(EconomicCalendaActivity.this) - 48,
						Utils.getWindowWidth(EconomicCalendaActivity.this));
				popupCardWindow.setBackgroundDrawable(new BitmapDrawable());
				popupCardWindow.setOutsideTouchable(true);
			}
			popupCardWindow.showAtLocation(findViewById(R.id.parent_view),
					Gravity.CENTER, 0, 0);
			cart_menu_display = true;
		} else {
			popupCardWindow.dismiss();
			cart_menu_display = false;
		}
	}

	public String getDate(boolean isToday) {
		if (isToday) {
			return ymdSdf.format(new Date()) + " 00:00:00";
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			return ymdSdf.format(calendar.getTime()) + " 00:00:00";
		}

	}

	class myadapter extends ArrayAdapter<CalendarBean> {

		private int log;

		public myadapter(Context context, int log) {
			super(context, 0);
			this.log = log;
		}

		@SuppressWarnings("unchecked")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final ViewHolder holder;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.adapter_item_rili, parent, false);
				holder = new ViewHolder();
				holder.imageview_conutry = (ImageView) convertView
						.findViewById(R.id.country);

				holder.title = (TextView) convertView
						.findViewById(R.id.Textcontenxt);
				holder.mRatingBar = (RatingBar) convertView
						.findViewById(R.id.ratingbar);
				holder.qianzhi = (TextView) convertView
						.findViewById(R.id.qianzhi);
				holder.yuzhi = (TextView) convertView.findViewById(R.id.yuqi);
				holder.jieguo = (TextView) convertView
						.findViewById(R.id.jinzhi);
				holder.time = (TextView) convertView
						.findViewById(R.id.textView1);
				holder.qianzhi_ = (TextView) convertView
						.findViewById(R.id.textView5);
				holder.yuqi_ = (TextView) convertView
						.findViewById(R.id.textView4);
				holder.jinzhi_ = (TextView) convertView
						.findViewById(R.id.textView3);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.title.setText(getItem(position).getTitle());
			holder.time.setText(getTime(getItem(position).getLocalDateTime()));
			holder.mRatingBar.setRating(Integer.valueOf(getItem(position)
					.getImportance()));
			if (" ".equals(getItem(position).getPrevious().trim())
					|| getItem(position).getPrevious().trim().endsWith("sp;")
					|| null == getItem(position).getPrevious()
					|| "null" == getItem(position).getPrevious()
					|| "".equals(getItem(position).getPrevious().trim())) {
				holder.qianzhi.setText("N/A");
			} else {
				holder.qianzhi.setText(getItem(position).getPrevious());
			}
			if (" ".equals(getItem(position).getForecast().trim())
					|| getItem(position).getForecast().trim().endsWith("sp;")
					|| null == getItem(position).getForecast()
					|| "null" == getItem(position).getForecast()
					|| "".equals(getItem(position).getPrevious().trim())) {
				holder.yuzhi.setText("N/A");
			} else {
				holder.yuzhi.setText(getItem(position).getForecast());
			}
			if (" ".equals(getItem(position).getActual().trim())
					|| getItem(position).getActual().trim().endsWith("sp;")
					|| null == getItem(position).getActual()
					|| "null" == getItem(position).getActual()
					|| "".equals(getItem(position).getPrevious().trim())) {
				holder.jieguo.setText("N/A");
			} else {
				holder.jieguo.setText(getItem(position).getActual());
			}
			if (log == 0) {
				holder.qianzhi_.setVisibility(View.VISIBLE);
				holder.yuqi_.setVisibility(View.VISIBLE);
				holder.jinzhi_.setVisibility(View.VISIBLE);
				holder.qianzhi.setVisibility(View.VISIBLE);
				holder.yuzhi.setVisibility(View.VISIBLE);
				holder.jieguo.setVisibility(View.VISIBLE);
			}
			holder.imageview_conutry.setImageResource(getcountry(getItem(
					position).getCountry()));

			return convertView;
		}

		public int getcountry(String country) {

			if ("韩国".equals(country)) {
				return R.drawable.country_korea;
			}
			if ("英国".equals(country)) {
				return R.drawable.country_england;
			}
			if ("加拿大".equals(country)) {
				return R.drawable.country_canada;
			}
			if ("中国".equals(country)) {
				return R.drawable.country_china;
			}
			if ("欧元区".equals(country)) {
				return R.drawable.country_eurozone;
			}
			if ("法国".equals(country)) {
				return R.drawable.country_france;
			}
			if ("印度".equals(country)) {
				return R.drawable.country_india;
			}
			if ("意大利".equals(country)) {
				return R.drawable.country_italy;
			}
			if ("日本".equals(country)) {
				return R.drawable.country_japan;
			}
			if ("瑞士".equals(country)) {
				return R.drawable.country_swit;
			}
			if ("美国".equals(country)) {
				return R.drawable.country_usa;
			}
			if ("德国".equals(country)) {
				return R.drawable.country_germany;
			}
			if ("西班牙".equals(country)) {
				return R.drawable.country_spain;
			} else {
				return R.drawable.country_others;
			}

		}

		@SuppressLint("SimpleDateFormat")
		public String getTime(String str) {

			SimpleDateFormat format1 = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");

			try {
				Date date = format1.parse(str);
				String time = format2.format(date);
				return time;
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return " ";

		}

		private class ViewHolder {
			ImageView imageview_conutry;
			TextView title;
			RatingBar mRatingBar;
			TextView qianzhi;
			TextView yuzhi;
			TextView jieguo;
			TextView time;
			TextView qianzhi_, yuqi_, jinzhi_;

		}
	}
}
