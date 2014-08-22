package cn.precious.metal.ui;

import cn.precious.metal.R;
import cn.precious.metal.tools.AndroidUtils;
import cn.precious.metal.ui.email.MailSenderInfo;
import cn.precious.metal.ui.email.SimpleMailSender;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MeFanKuiActivity extends Activity {
	private EditText content;
	private EditText mEmailaddress;
	private Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.me_fankui);
		content = (EditText) findViewById(R.id.EditText1);
		mEmailaddress = (EditText) findViewById(R.id.EditText2);
		btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String str = content.getText().toString();
				final String strr = mEmailaddress.getText().toString();
				if (null == str || str.length() < 1) {
					AndroidUtils.toastTips(MeFanKuiActivity.this, "内容不能为空噢");

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
								mailInfo.setSubject("意见反馈:");
								mailInfo.setContent("内容 : " + str + "\n"
										+ " 邮箱 : " + strr
										+ "From android(2.0)");
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
				AndroidUtils.toastTips(MeFanKuiActivity.this, "发送成功！");
				MeFanKuiActivity.this.finish();
				break;
			case 2:
				AndroidUtils.toastTips(MeFanKuiActivity.this, "发送没成功！");
				break;
			default:
				break;
			}
		}
	};

}
