package com.venus.carpapa;

import net.tsz.afinal.annotation.view.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.venus.carpapa.R;
import com.venus.carpapa.util.DialogUtil;
import com.venus.carpapa.util.HttpUtil;
import com.venus.carpapa.util.Logger;

/***
 * 
 * */
public class LoginActivity extends BaseActivity {
	@ViewInject(id = R.id.userNameEdit)
	EditText userNameEdit;
	@ViewInject(id = R.id.passwordEdit)
	EditText passwordEdit;
	private static final int LOGIN = 1;
	String phone, pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	public void login(View v) {
		phone = userNameEdit.getText().toString();
		pwd = passwordEdit.getText().toString();
		if (TextUtils.isEmpty(phone)) {
			DialogUtil.toast(this, "用户名不能为空");
			userNameEdit.findFocus();
			return;
		}
		if (TextUtils.isEmpty(pwd)) {
			DialogUtil.toast(this, "密码不能为空");
			passwordEdit.findFocus();
			return;
		}

		login();
	}

	public void canel(View v) {
		// startActivity(new Intent(this,MainActivity.class));
		this.finish();
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOGIN:
				String obj = (String) msg.obj;
				DialogUtil.toast(LoginActivity.this, obj);
				parseLogin(obj);

				break;
			}
		}

	};

	public void parseLogin(String obj) {
		this.canelDialog();
		try {

			JSONObject jo = new JSONObject(obj);
			String login = jo.getString("login");
			String message = jo.getString("message");
			if (TextUtils.equals("success", login)) {
				DialogUtil.toast(this, "成功");
				// startActivity(new Intent(this, TaskIndexActivity.class));
			} else {
				DialogUtil.toast(this, message);
				// startActivity(new Intent(this,TaskIndexActivity.class));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			DialogUtil.toast(this, "异常");
			e.printStackTrace();
			// startActivity(new Intent(this, TaskIndexActivity.class));
		}
	}

	public void login() {

		new AsyncTask<Object, Object, String>() {

			@Override
			protected String doInBackground(Object... params) {
				// TODO Auto-generated method stub
				return HttpUtil.login(phone, pwd);
			}

			protected void onPostExecute(String result) {
				Message msg = new Message();
				msg.what = LOGIN;
				msg.obj = result;
				handler.sendMessage(msg);
			};

		}.execute();
	}

	/**
	 * 监听返回键，设置退出窗口
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Dialog qiutAlert = new AlertDialog.Builder(this)
					.setTitle("退出")
					.setMessage("确定要退出吗?")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).create();
			qiutAlert.show();
		}
		return super.dispatchKeyEvent(event);
	}
}
