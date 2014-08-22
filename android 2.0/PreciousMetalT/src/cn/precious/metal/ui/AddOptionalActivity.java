package cn.precious.metal.ui;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.precious.metal.R;
import cn.precious.metal.adapter.AddOptionAdapter;
import cn.precious.metal.adapter.AddOptionAdapter.OptionaChangeListener;
import cn.precious.metal.base.BaseActivity;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.config.ParamConfig;
import cn.precious.metal.dao.OptionalDao;
import cn.precious.metal.entity.Optional;
import cn.precious.metal.services.OptionalService;
import cn.precious.metal.tools.ExcepUtils;

public class AddOptionalActivity extends BaseActivity implements
		OnClickListener {

	private ListView listView;

	private List<Optional> options;

	private AddOptionAdapter mAdapter;

	private LinearLayout jgs, sjs;

	private LinearLayout  commodity, currency, interIndex;

	private RelativeLayout back;

	private FrameLayout rightLayout;

	private TextView selectNumber;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_optional);
		initView();
		initListener();
		if (AppSetting.getInstance(this).isHaveInitTJSOptionalData()) {
			options = new OptionalDao(this)
					.queryOptionalsByType(ParamConfig.JIN_GUI_SUO);
			mAdapter.setItems(options, true);
		} else {
			loadDataByTags(ParamConfig.JIN_GUI_SUO);
		}
	}

	public void initView() {
		listView = (ListView) findViewById(R.id.listview);
		mAdapter = new AddOptionAdapter(this, options, false);
		mAdapter.optionalChangeListener = mOptionaChangeListener;
		listView.setAdapter(mAdapter);

		jgs = (LinearLayout) findViewById(R.id.jgs);// 津贵属
		sjs = (LinearLayout) findViewById(R.id.sjs);// 上交所

		commodity = (LinearLayout) findViewById(R.id.commodity);// 商品
		currency = (LinearLayout) findViewById(R.id.currency);// 外汇
		interIndex = (LinearLayout) findViewById(R.id.inter_index);// 国际指数

		back = (RelativeLayout) findViewById(R.id.back);
		rightLayout = (FrameLayout) findViewById(R.id.rightLayout);
		selectNumber = (TextView) findViewById(R.id.selectNumber);

		int number = new OptionalDao(this).getIsOptionalNumber();
		selectNumber.setText("" + number);
	}

	private OptionaChangeListener mOptionaChangeListener = new OptionaChangeListener() {
		@Override
		public void checkChange(String count) {
			// TODO Auto-generated method stub
			selectNumber.setText(count);
		}
	};

	public void initListener() {
		jgs.setOnClickListener(this);
		sjs.setOnClickListener(this);

		commodity.setOnClickListener(this);
		currency.setOnClickListener(this);
		interIndex.setOnClickListener(this);

		rightLayout.setOnClickListener(this);
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.back) {
			finish();
		} else if (v.getId() == R.id.sjs) { // 上交所
			if (AppSetting.getInstance(this).isHaveInitSJSOptionalData()) {
				options = new OptionalDao(this)
						.queryOptionalsByType(ParamConfig.SHANG_JIAO_SUO);
				mAdapter.setItems(options, true);
			} else {
				loadDataByTags(ParamConfig.SHANG_JIAO_SUO);
			}
		} else if (v.getId() == R.id.jgs) { // 津贵所
			if (AppSetting.getInstance(this).isHaveInitTJSOptionalData()) {
				options = new OptionalDao(this)
						.queryOptionalsByType(ParamConfig.JIN_GUI_SUO);
				mAdapter.setItems(options, true);
			} else {
				loadDataByTags(ParamConfig.JIN_GUI_SUO);
			}
		}else if (v.getId() == R.id.commodity) { // 商品
			if (AppSetting.getInstance(this).isHaveInitCommodityOptionalData()) {
				options = new OptionalDao(this)
						.queryOptionalsByType(ParamConfig.COMMODITY);
				mAdapter.setItems(options, true);
			} else {
				loadDataByTags(ParamConfig.COMMODITY);
			}
		} else if (v.getId() == R.id.currency) { // 外汇
			if (AppSetting.getInstance(this).isHaveInitForexOptionalData()) {
				options = new OptionalDao(this)
						.queryOptionalsByType(ParamConfig.FOREX);
				mAdapter.setItems(options, true);
			} else {
				loadDataByTags(ParamConfig.FOREX);
			}
		} else if (v.getId() == R.id.inter_index) { // 国际指数
			if (AppSetting.getInstance(this).isHaveInitIndiceOptionalData()) {
				options = new OptionalDao(this)
						.queryOptionalsByType(ParamConfig.INDICE);
				mAdapter.setItems(options, true);
			} else {
				loadDataByTags(ParamConfig.INDICE);
			}
		}

	}

	public void loadDataByTags(String type) {
		new OptionsTask(type).execute();
	}

	class OptionsTask extends AsyncTask<String, Void, String> {

		private String type;

		public OptionsTask(String type) {
			this.type = type;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showNetLoadingProgressDialog("加载中...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			OptionalService service = new OptionalService(
					AddOptionalActivity.this);
			try {
				options = service.getOptionalsByType(type);
				if (options != null && !options.isEmpty()) {
					return SUCCESS;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(AddOptionalActivity.this,
						null, e);
				return ERROR;
			}
			return FAIL;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			hideNetLoadingProgressDialog();
			if (SUCCESS.equalsIgnoreCase(result)) {
				mAdapter.setItems(options, true);
			}
		}
	}

}
