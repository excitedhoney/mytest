package com.xiyou.apps.lookpan.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.services.UserService;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.BaseActivity;
import com.xiyou.apps.lookpan.tools.ExcepUtils;

public class LoginActivity extends BaseActivity implements OnClickListener {

	Button btnLogin, btnRegister;

	EditText userName, password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		initListener();
	}

	public void initView() {
		btnRegister = (Button) findViewById(R.id.btn_register);
		btnLogin = (Button) findViewById(R.id.btn_login);

		userName = (EditText) findViewById(R.id.user_name);
		password = (EditText) findViewById(R.id.password);
	}

	public void initListener() {
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
	}
	
	public void back(View v) {
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login:
			login();
			break;
		case R.id.btn_register:
			
			if("rect_remind".equals(getIntent().getStringExtra("flag"))) {
				Intent it = new Intent(LoginActivity.this,
						RegisterActivity.class);
				it.putExtra("flag", "rect_remid") ;
				it.putExtra("symbol", getIntent().getStringExtra("symbol"));
				it.putExtra("name", getIntent().getStringExtra("name"));
				it.putExtra("BuyPrice",getIntent().getDoubleExtra("BuyPrice", 0));
				it.putExtra("SalePrice",getIntent().getDoubleExtra("SalePrice", 0));
				it.putExtra("CurrentPrice",getIntent().getDoubleExtra("CurrentPrice", 0));
				startActivity(it);
			}else{
				Intent it = new Intent(LoginActivity.this, RegisterActivity.class) ;
				it.putExtra("flag", getIntent().getStringExtra("flag")) ;
				startActivity(it);
			}
			
			break;
		default:
			break;
		}
	}

	public void login() {
		String username = userName.getText().toString();
		if (username == null || "".equals(username)) {
			showCusToast("用户名不能为空");
			return;
		}

		String pass = password.getText().toString();
		if (pass == null || "".equals(pass)) {
			showCusToast("密码不能为空");
			return;
		}
		new LoginTask(username, pass).execute("");
	}

	class LoginTask extends AsyncTask<String, Void, String> {

		private String number;

		private String pass;

		public LoginTask(String phoneNumber, String password) {
			this.number = phoneNumber;
			this.pass = password;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			UserService userService = new UserService(LoginActivity.this);
			try {
				return userService.login(number, pass);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(LoginActivity.this, null, e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if ("0".equals(result)) {

				AppSetting.getInstance(LoginActivity.this).setPhoneNumber(
						number);
				if("virtual".equals(getIntent().getStringExtra("flag"))) {
					Intent intent  = new Intent(LoginActivity.this, VirtualTradeActivity.class) ;
					startActivity(intent);
				}else if("remind".equals(getIntent().getStringExtra("flag"))) {
					Intent intent  = new Intent(LoginActivity.this, RemindListActivity.class) ;
					startActivity(intent);
				}else if("rect_remind".equals(getIntent().getStringExtra("flag"))) {
					Intent it = new Intent(LoginActivity.this,
							RemindActivity.class);
					it.putExtra("symbol", getIntent().getStringExtra("symbol"));
					it.putExtra("name", getIntent().getStringExtra("name"));
					it.putExtra("BuyPrice",getIntent().getDoubleExtra("BuyPrice", 0));
					it.putExtra("SalePrice",getIntent().getDoubleExtra("SalePrice", 0));
					it.putExtra("CurrentPrice",getIntent().getDoubleExtra("CurrentPrice", 0));
					startActivity(it);
				}
				finish();
				
			} else if ("1".equals(result)) {
				showCusToast("参数错误");
			} else if ("2".equals(result)) {
				showCusToast("该账户被停用");
			} else if ("3".equals(result)) {
				showCusToast("用户或者密码错误");
			} else if ("4".equals(result)) {
				showCusToast("该用户已存在");
			} else if ("5".equals(result)) {
				showCusToast("该用户不存在");
			} else if ("6".equals(result)) {
				showCusToast("发送手机验证码失败");
			}
		}

	}
}
