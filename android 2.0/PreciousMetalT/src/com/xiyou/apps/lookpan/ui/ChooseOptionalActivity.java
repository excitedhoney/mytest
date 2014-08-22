package com.xiyou.apps.lookpan.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.config.ParamConfig;
import cn.precious.metal.dao.OptionalDao;
import cn.precious.metal.entity.Optional;
import cn.precious.metal.services.OptionalService;
import cn.precious.metal.utils.Utils;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.adapter.ChooseOptionalAdapter;
import com.xiyou.apps.lookpan.base.BaseActivity;
import com.xiyou.apps.lookpan.tools.ExcepUtils;

public class ChooseOptionalActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout llsjs, lltjs;

	private ListView sjsList, tjsListView;

	private boolean sjs_bool, tjs_bool;

	private ImageView iv_sjs, iv_tjs;

	private ChooseOptionalAdapter optionalAdapter1, optionalAdapter2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_optional);
		initView();
	}

	public void back(View v) {
		finish();
	}

	public void initView() {
		llsjs = (LinearLayout) findViewById(R.id.ll_sjs);
		lltjs = (LinearLayout) findViewById(R.id.ll_tjs);
		iv_sjs = (ImageView) findViewById(R.id.iv_sjs);
		iv_tjs = (ImageView) findViewById(R.id.iv_tjs);
		sjsList = (ListView) findViewById(R.id.sjsListView);
		tjsListView = (ListView) findViewById(R.id.tjsListView);

		optionalAdapter1 = new ChooseOptionalAdapter(this, options1);
		optionalAdapter2 = new ChooseOptionalAdapter(this, options2);

		sjsList.setAdapter(optionalAdapter1);

		tjsListView.setAdapter(optionalAdapter2);

		llsjs.setOnClickListener(this);
		lltjs.setOnClickListener(this);

		sjsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Optional op = optionalAdapter1.getItem(arg2);
				if (op != null) {
					Intent it = new Intent();
					it.putExtra("symbol", op.getTreaty());
					it.putExtra("name", op.getTitle());
					it.putExtra("BuyPrice", Double.parseDouble(op.getBuyone()));
					it.putExtra("SalePrice",
							Double.parseDouble(op.getSellone()));
					it.putExtra("CurrentPrice",
							Double.parseDouble(op.getNewest()));
					setResult(Activity.RESULT_OK, it);
					finish();
				}
			}
		});

		tjsListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Optional op = optionalAdapter2.getItem(arg2);
						if (op != null) {
							Intent it = new Intent();
							it.putExtra("symbol", op.getTreaty());
							it.putExtra("name", op.getTitle());
							it.putExtra("BuyPrice",
									Double.parseDouble(op.getBuyone()));
							it.putExtra("SalePrice",
									Double.parseDouble(op.getSellone()));
							it.putExtra("CurrentPrice",
									Double.parseDouble(op.getNewest()));
							setResult(Activity.RESULT_OK, it);
							finish();
						}
					}
				});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_sjs:

			if (sjs_bool) {
				sjsList.setVisibility(View.GONE);
				sjs_bool = false;
				iv_sjs.setImageResource(R.drawable.arrow_down);
			} else {
				sjsList.setVisibility(View.VISIBLE);
				iv_sjs.setImageResource(R.drawable.arrow_down_blue);
				if (AppSetting.getInstance(this).isHaveInitSJSOptionalData()) {
					options1 = new OptionalDao(this)
							.queryOptionalsByType(ParamConfig.SHANG_JIAO_SUO);
					optionalAdapter1.setItems(options1);
					Utils.setListViewHeightBasedOnChildren(
							ChooseOptionalActivity.this, sjsList, false);
				} else {
					loadDataByTags(ParamConfig.SHANG_JIAO_SUO);
				}
				sjs_bool = true;
			}

			break;
		case R.id.ll_tjs:
			if (tjs_bool) {
				tjsListView.setVisibility(View.GONE);
				tjs_bool = false;
				iv_tjs.setImageResource(R.drawable.arrow_down);
			} else {
				tjsListView.setVisibility(View.VISIBLE);
				iv_tjs.setImageResource(R.drawable.arrow_down_blue);
				if (AppSetting.getInstance(this).isHaveInitTJSOptionalData()) {
					options2 = new OptionalDao(this)
							.queryOptionalsByType(ParamConfig.JIN_GUI_SUO);
					optionalAdapter2.setItems(options2);
					Utils.setListViewHeightBasedOnChildren(
							ChooseOptionalActivity.this, tjsListView, false);
				} else {
					loadDataByTags(ParamConfig.JIN_GUI_SUO);
				}

				tjs_bool = true;
			}
			break;

		default:
			break;
		}
	}

	private List<Optional> options1;
	private List<Optional> options2;

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
					ChooseOptionalActivity.this);
			try {
				if ("tg".equals(type)) {
					options2 = service.getOptionalsByType(type);
					if (options2 != null && !options2.isEmpty()) {
						return SUCCESS;
					}
				} else if ("sg".equals(type)) {
					options1 = service.getOptionalsByType(type);
					if (options1 != null && !options1.isEmpty()) {
						return SUCCESS;
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(ChooseOptionalActivity.this,
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
				if ("tg".equals(type)) {
					optionalAdapter2.setItems(options2);
					Utils.setListViewHeightBasedOnChildren(
							ChooseOptionalActivity.this, tjsListView, false);
				} else if ("sg".equals(type)) {
					optionalAdapter1.setItems(options1);
					Utils.setListViewHeightBasedOnChildren(
							ChooseOptionalActivity.this, sjsList, false);
				}

			}
		}
	}

}
