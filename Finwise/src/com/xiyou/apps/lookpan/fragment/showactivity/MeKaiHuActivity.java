package com.xiyou.apps.lookpan.fragment.showactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.email.MailSenderInfo;
import com.xiyou.apps.lookpan.email.SimpleMailSender;
import com.xiyou.apps.lookpan.utils.Util;

public class MeKaiHuActivity extends BaseActivity {

	private EditText name, phone, qq;
	private Button conform;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.me_kaihu);
		mActionBar.setTitle("轻松开户");
		name = (EditText) findViewById(R.id.name);
		phone = (EditText) findViewById(R.id.phone);
		qq = (EditText) findViewById(R.id.qqq);
		conform = (Button) findViewById(R.id.button1);
		conform.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String str_name = name.getText().toString();
				final String str_phone = phone.getText().toString();
				final String str_qq = qq.getText().toString();
				if (null == str_name || str_name.length() < 1
						|| null == str_phone || str_phone.length() < 0) {
					Util.toastTips(MeKaiHuActivity.this, "内容不能为空噢");
					if (str_phone.length() != 11) {
						Util.toastTips(MeKaiHuActivity.this, "手机号码格式不正确");
					}

				} else {
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								MailSenderInfo mailInfo = new MailSenderInfo();
								mailInfo.setMailServerHost("smtp.exmail.qq.com");
								mailInfo.setMailServerPort("25");
								mailInfo.setValidate(true);
								mailInfo.setUserName("app@06866.com"); // 你的邮箱地址
								mailInfo.setPassword("jzr789");// 您的邮箱密码
								mailInfo.setFromAddress("app@06866.com");
								mailInfo.setToAddress("app@06866.com");
								mailInfo.setSubject("开户");
								mailInfo.setContent("name : " + str_name + "\n"
										+ " phone : " + str_phone + "\n"
										+ "QQ:" + str_qq);
								// 这个类主要来发送邮件
								SimpleMailSender sms = new SimpleMailSender();

								sms.sendTextMail(mailInfo);// 发送文体格式

								Message msg = myhandler.obtainMessage();
								msg.what = 1;
								msg.sendToTarget();
							} catch (Exception e) {
								e.printStackTrace();
								Message msg = myhandler.obtainMessage();
								msg.what = 2;
								msg.sendToTarget();
							}
						}
					}).start();
				}

			}
		});
	}

	@SuppressLint("HandlerLeak")
	Handler myhandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				Util.toastTips(MeKaiHuActivity.this, "提交成功！");
				MeKaiHuActivity.this.finish();
				break;
			case 2:
				Util.toastTips(MeKaiHuActivity.this, "提交没成功！");
				break;
			default:
				break;
			}
		}
	};

}
