package com.xiyou.apps.lookpan.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.tools.AndroidUtils;
import com.xiyou.apps.lookpan.ui.email.MailSenderInfo;
import com.xiyou.apps.lookpan.ui.email.SimpleMailSender;

public class MeKaiHuActivity extends Activity {

	private EditText name, phone, qq;
	private Button conform;
	private String str_name;
	private String str_phone;
	private String str_qq;
	private RelativeLayout loadingProg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.me_kaihu);
		name = (EditText) findViewById(R.id.name);
		phone = (EditText) findViewById(R.id.phone);
		qq = (EditText) findViewById(R.id.qqq);
		conform = (Button) findViewById(R.id.button1);
		loadingProg = (RelativeLayout) findViewById(R.id.loadingProg);

		conform.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				str_name = name.getText().toString();
				str_phone = phone.getText().toString();
				str_qq = qq.getText().toString();
				if (null == str_name || str_name.length() < 1
						|| null == str_phone || str_phone.length() < 0) {
					AndroidUtils.toastTips(MeKaiHuActivity.this, "内容不能为空噢");
					if (str_phone.length() != 11) {
						AndroidUtils.toastTips(MeKaiHuActivity.this,
								"手机号码格式不正确");
					}

				} else {
					loadingProg.setVisibility(View.VISIBLE);
					loading();
				}

			}
		});
	}

	public void back(View v) {
		finish();
	}

	private void loading() {

		new AsyncTask<String, String, String>() {
			@Override
			protected String doInBackground(String... params) {
				try {
					conform.setClickable(false);
					MailSenderInfo mailInfo = new MailSenderInfo();
					mailInfo.setMailServerHost("smtp.exmail.qq.com");
					mailInfo.setMailServerPort("25");
					mailInfo.setValidate(true);
					mailInfo.setUserName("app@06866.com"); // 你的邮箱地址
					mailInfo.setPassword("jzr789");// 您的邮箱密码
					mailInfo.setFromAddress("app@06866.com");
					mailInfo.setToAddress("app@06866.com");
					mailInfo.setSubject("开户");
					mailInfo.setContent("姓名 : " + str_name + "\n" + " 手机号 : "
							+ str_phone + "\n" + "QQ:" + str_qq + "\n" + "\n"
							+ "from---Android(2.0)");
					// 这个类主要来发送邮件
					SimpleMailSender sms = new SimpleMailSender();
					sms.sendTextMail(mailInfo);// 发送文体格式
					return "success";
				} catch (Exception e) {
					e.printStackTrace();
					return "false";
				}
			}

			protected void onPostExecute(String result) {
				conform.setClickable(true);
				loadingProg.setVisibility(View.GONE);
				if ("success".equals(result)) {
					AndroidUtils.toastTips(MeKaiHuActivity.this, "提交成功！");
					MeKaiHuActivity.this.finish();
				} else {
					AndroidUtils.toastTips(MeKaiHuActivity.this, "提交没成功！");
				}
			};

		}.execute();
	}

}
