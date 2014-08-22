package cn.precious.metal.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import cn.precious.metal.R;
import cn.precious.metal.base.BaseActivity;
import cn.precious.metal.utils.Utils;

public class CalcToolActivity extends BaseActivity {

	// 汇率：（美元/人民币）=E——一般是六点几 rmb/g）=（$/ounce） /31.1035*E

	public static final double parities = 6.1537;

	private EditText etRmb, etDollar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matrixing);
		initView();
		initListener();
	}

	public void initView() {
		etRmb = (EditText) findViewById(R.id.et_rmb);
		etDollar = (EditText) findViewById(R.id.et_dollar);
	}

	public void initListener() {

		etRmb.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (etRmb.isFocused() && s != null && !"".equals(s.toString()) && s.toString().length() < 8) {
					etDollar.setText(""
							+ Utils.bigDecimalDoubleNum(remTodollar(Double.parseDouble(s.toString()))));

				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		etDollar.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (etDollar.isFocused() && s != null && !"".equals(s.toString()) && s.toString().length() < 8) {
					etRmb.setText(""
							+ Utils.bigDecimalDoubleNum(dollarToRmb(Double.parseDouble(s.toString()))));
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void back(View view) {
		// TODO Auto-generated method stub
		finish();
	}

	public double remTodollar(double rmb) {
		return rmb * parities / 31.1035;
	}

	public double dollarToRmb(double dollar) {
		return dollar * 31.1035 / parities;
	}

}
