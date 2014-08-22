package cn.precious.metal.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.precious.metal.R;
import cn.precious.metal.base.BaseActivity;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.services.UserService;
import cn.precious.metal.tools.ExcepUtils;

public class RegisterActivity extends BaseActivity implements OnClickListener{

	private EditText etPhoneNumber,etPassword,etPasswordAgian,etCode  ;
	
	private Button btnGetCode ,btnRegister ;
	
	private boolean isRun = true ;
	
	private int count = 120 ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		initView();
		initListen();
	}
	
	public void back(View view ) {
		finish();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isRun = false ;
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isRun  = false ;
	}
	
	
	public void initView(){
		etPhoneNumber = (EditText) findViewById(R.id.et_phonenumber) ;
		
		etPassword = (EditText) findViewById(R.id.et_password) ;
		
		etPasswordAgian = (EditText) findViewById(R.id.et_password_again);
		etCode = (EditText) findViewById(R.id.et_code) ;
		
		btnGetCode = (Button) findViewById(R.id.get_code) ;
		btnRegister = (Button) findViewById(R.id.btn_register) ;
	}
	
	public void initListen(){
		btnGetCode.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.get_code:
			getCode();
			break;
		case R.id.btn_register:
			regisger();
			break;
		default:
			break;
		}
	}
	
	
	public void regisger() {
		String pNumber = etPhoneNumber.getText().toString() ;
		String password  = etPassword.getText().toString() ;
		String passwordAgain = etPasswordAgian.getText().toString() ;
		String code = etCode.getText().toString() ;
		if(pNumber == null || "".equals(pNumber)) {
			showCusToast("请先输入手机号码");
			return ;
		}
		
		if(password == null || "".equals(password)) {
			showCusToast("密码不能为空");
			return ;
		}
		
		if(passwordAgain == null || "".equals(passwordAgain)) {
			showCusToast("请再次输入密码");
			return ;
		}
		
		if(!passwordAgain.equals(password)) {
			showCusToast("两次输入的密码不一致");
			return ;
		}
		
		if(code == null || "".equals(code)) {
			showCusToast("验证码不能为空");
			return ;
		}
		
		if(!code.equals(AppSetting.getInstance(RegisterActivity.this).getRegisterCode())) {
			showCusToast("请输入正确的验证码");
			return ;
		}
		
		new RegisterTask(pNumber,password).execute("") ;
	}
	
	
	public void getCode() {
		String pNumber = etPhoneNumber.getText().toString() ;
		if(pNumber == null || "".equals(pNumber)) {
			showCusToast("请先输入手机号码");
			return ;
		}else{
			new GetCodeTask().execute(new String[]{pNumber}) ;
		}
	}
	
	
	
	public void time(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(isRun) {
					count -- ;
					handler.sendEmptyMessage(10) ;
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
					}
				}
			}
		}).start(); 
	}
	
	private Handler  handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 10:
				if(count <= 0){
					btnGetCode.setEnabled(true);
					btnGetCode.setText("获取");
					isRun = false ;
				}else{
					btnGetCode.setEnabled(false);
					btnGetCode.setText("" + count);
				}
				
				break;
			default:
				break;
			}
		}
	} ;
	
	
	class GetCodeTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			UserService userService = new UserService(RegisterActivity.this) ;
			String result = null ;
			try {
				result = userService.getCode(params[0]) ;
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(RegisterActivity.this, null, e);
				
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null) {
				JSONObject obj = null ;
				try {
					obj = new JSONObject(result);
					String code =   obj.getString("error") ;
					
					if("0".equals(code)) {
						AppSetting.getInstance(RegisterActivity.this).setRegisterCode(obj.getString("code"));
						time();
					}else if("1".equals(result)){
						showCusToast("请检查你的手机号码是否正确");
					}else if("6".equals(result)){
						showCusToast("发送手机验证码失败");
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	class RegisterTask extends AsyncTask<String, Void, String> {
		private String number ;
		
		private String pass  ;
		
		public RegisterTask(String phoneNumber ,String password) {
			this.number = phoneNumber ;
			this.pass = password ;
		}
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			btnRegister.setText("注册中...");
			btnRegister.setEnabled(false);
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			UserService userService = new UserService(RegisterActivity.this) ;
			String result = null ;
			try {
				result = userService.register(number,pass) ;
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(RegisterActivity.this, null, e);
				
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			btnRegister.setText("注册");
			btnRegister.setEnabled(true);
			if("0".equals(result)) {
				AppSetting.getInstance(RegisterActivity.this).setPhoneNumber(number);
				if("virtual".equals(getIntent().getStringExtra("flag"))) {
					Intent intent  = new Intent(RegisterActivity.this, VirtualTradeActivity.class) ;
					startActivity(intent);
				}else if("remind".equals(getIntent().getStringExtra("flag"))) {
					Intent intent  = new Intent(RegisterActivity.this, RemindListActivity.class) ;
					startActivity(intent);
				}else if("rect_remind".equals(getIntent().getStringExtra("flag"))) {
					Intent it = new Intent(RegisterActivity.this,
							RemindActivity.class);
					it.putExtra("symbol", getIntent().getStringExtra("symbol"));
					it.putExtra("name", getIntent().getStringExtra("name"));
					it.putExtra("BuyPrice",getIntent().getDoubleExtra("BuyPrice", 0));
					it.putExtra("SalePrice",getIntent().getDoubleExtra("SalePrice", 0));
					it.putExtra("CurrentPrice",getIntent().getDoubleExtra("CurrentPrice", 0));
					startActivity(it);
				}else {
					Intent intent = new Intent(RegisterActivity.this, MainActivity.class) ;
					startActivity(intent);
				}
				
				AppSetting.getInstance(RegisterActivity.this).setRegisterCode("");
				
				finish();
				
			}else if("1".equals(result)){
				showCusToast("请检查你的手机号码是否正确");
			}else if("4".equals(result)){
				showCusToast("该账户已存在");
			}else if("6".equals(result)){
				showCusToast("发送手机验证码失败");
			}
		}
	}

}
