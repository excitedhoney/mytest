package cn.precious.metal.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.precious.metal.R;
import cn.precious.metal.base.BaseActivity;
import cn.precious.metal.dao.NoticeDao;
import cn.precious.metal.entity.CustomNotice;

public class RemindActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout chooseOptional;

	private RadioGroup rgFangshi, rgOriration, rgTime;

	private TextView optionaName, optionalBuy, optioanlSale;

	private EditText optionalCurrentPrice;

	private Button btnAddNotice;

	private LinearLayout llOptional;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remind);
		initView();
		initListener();
	}

	public void initView() {
		rgFangshi = (RadioGroup) findViewById(R.id.rg_fangshi);
		rgOriration = (RadioGroup) findViewById(R.id.rg_oritation);
		rgTime = (RadioGroup) findViewById(R.id.rg_time);

		optionaName = (TextView) findViewById(R.id.optional_name);
		optionalBuy = (TextView) findViewById(R.id.optional_buy_price);
		optioanlSale = (TextView) findViewById(R.id.optional_sale_price);

		chooseOptional = (RelativeLayout) findViewById(R.id.choose_optional);

		optionalCurrentPrice = (EditText) findViewById(R.id.et_current_price);

		btnAddNotice = (Button) findViewById(R.id.btn_add_notice);

		llOptional = (LinearLayout) findViewById(R.id.ll_optional);
		
		chooseOptional(getIntent());
	}

	
	public void chooseOptional(Intent intent) {
		if (intent.getStringExtra("name") != null
				&& !"".equals(intent.getStringExtra("name"))) {
			optionaName.setText(intent.getStringExtra("name"));
			llOptional.setVisibility(View.VISIBLE);
			optionalBuy.setText("买入价：" + intent.getDoubleExtra("BuyPrice", 0));
			optioanlSale
					.setText("卖出价：" + intent.getDoubleExtra("SalePrice", 0));
			optionalCurrentPrice.setText(""
					+ intent.getDoubleExtra("CurrentPrice", 0));
		} else {
			optionaName.setText("选择一个行情");
			llOptional.setVisibility(View.GONE);
		}
	}
	
	

	public void back(View v) {
		finish();
	}

	private int fangshi = 0 ;
	private int oritation = 1;
	private long time = 24 * 60 * 60 * 1000 ;

	public void initListener() {
		chooseOptional.setOnClickListener(this);
		btnAddNotice.setOnClickListener(this);

		rgFangshi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						switch (checkedId) {
						case R.id.rb_local:
							fangshi = 0;
							break;
						case R.id.rb_note:
							fangshi = 1;
							break;

						default:
							break;
						}
					}
				});
		rgOriration
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						switch (checkedId) {
						case R.id.rb_large_eq:
							oritation = 1;
							break;
						case R.id.rb_small_eq:
							oritation = 0;
							break;

						default:
							break;
						}
					}
				});
		rgTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_24_hours:
					time = 24 * 60 * 60 * 1000;
					break;
				case R.id.rb_48_hours:
					time = 48 * 60 * 60 * 1000;
					break;
				case R.id.rb_72_hours:
					time = 72 * 60 * 60 * 1000;
					break;

				default:
					break;
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_add_notice:
			if(getIntent().getStringExtra("name") == null || "".equals(getIntent().getStringExtra("name"))) {
				showCusToast("请先选择一个行情");
				return ;
			}
			
			CustomNotice notice = new CustomNotice();
			notice.setFangshi(fangshi);
			notice.setOritation(oritation);
			notice.setRunTime(time);
			notice.setHostory(false);
			notice.setName(getIntent().getStringExtra("name"));
			notice.setSymbol(getIntent().getStringExtra("symbol"));
			notice.setBuyPrice(getIntent().getDoubleExtra("BuyPrice", 0));
			notice.setSalePrice(getIntent().getDoubleExtra("SalePrice", 0));
			
			String cprice = optionalCurrentPrice.getText().toString() ;
			if(cprice == null || "".equals(cprice)) {
				notice.setSetPrice(getIntent().getDoubleExtra("CurrentPrice", 0));
			}else{
				notice.setSetPrice(Double.parseDouble(cprice));
			}
			notice.setCreateTime(System.currentTimeMillis());
			boolean flag = new NoticeDao(RemindActivity.this).addNotice(notice);
			if (flag) {
				showCusToast("添加成功");
				finish();
			}
			break;
		case R.id.choose_optional:
			Intent intentChoose = new Intent(RemindActivity.this,
					ChooseOptionalActivity.class);
			startActivityForResult(intentChoose, 10);
			
			break;
		default:
			break;
		}
	}
	
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		switch (arg0) {
		case 10:
			if(arg1 == Activity.RESULT_OK) {
				chooseOptional(arg2);
			}
			break;

		default:
			break;
		}
	}

}
