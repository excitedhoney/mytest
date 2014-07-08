package com.xiyou.apps.lookpan.fragment.showactivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.baidu.mobstat.StatService;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.sso.UMWXHandler;
import com.xiyou.apps.lookpan.R;

public abstract class BaseActivity extends Activity {
	protected ActionBar mActionBar;
	final UMSocialService mController = UMServiceFactory.getUMSocialService(
			"com.umeng.share", RequestType.SOCIAL);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.title_shang));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.news_share, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.action_item_share:
			// 设置分享内容
			// 设置分享图片，参数2为本地图片的资源引用
			mController.setShareMedia(new UMImage(this, R.drawable.logo));
			mController
					.setShareContent("市场上最好的中文贵金属APP，海量的全球市场行情和国内的贵金属行情（纸黄金纸白银、黄金T+D白银T+D、天通银），专业的华尔街见闻和权威的分析师直播。");
			// mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
			// SHARE_MEDIA.DOUBAN);
			mController.getConfig().removePlatform(SHARE_MEDIA.SMS,
					SHARE_MEDIA.TENCENT, SHARE_MEDIA.EMAIL, SHARE_MEDIA.RENREN,
					SHARE_MEDIA.DOUBAN);
			// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
			String contentUrl = "http://www.06866.com/app/app";

			String appID = "wx281da66d6366526e";

			String QQappID = "101078710";
			// 微信图文分享必须设置一个url
			// 添加微信平台，参数1为当前Activity, 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
			UMWXHandler wxHandler = mController.getConfig().supportWXPlatform(
					this, appID, contentUrl);
			// 支持微信朋友圈
			UMWXHandler circleHandler = mController.getConfig()
					.supportWXCirclePlatform(this, appID, contentUrl);
			circleHandler.setCircleTitle("金智融贵金属");
			wxHandler.setWXTitle("金智融贵金属");
			// QQ好友

			mController.getConfig()
					.supportQQPlatform(this, QQappID, contentUrl);
			// QQ空间
			mController.getConfig().setSsoHandler(
					new QZoneSsoHandler(this, QQappID));

			// 新浪
			mController.getConfig().setSsoHandler(new SinaSsoHandler());

			mController.getConfig().setPlatformOrder(SHARE_MEDIA.WEIXIN,
					SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QZONE,
					SHARE_MEDIA.QQ, SHARE_MEDIA.SINA);
			mController.openShare(this, false);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

}
