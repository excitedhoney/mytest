/*
 * 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiyou.apps.lookpan.ui.alarm;

import java.io.IOException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import cn.precious.metal.dao.NoticeDao;
import cn.precious.metal.entity.CustomNotice;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.BaseActivity;
import com.xiyou.apps.lookpan.ui.PortOptionalActivity;

public class AlarmAlertActivity extends BaseActivity implements
		OnClickListener, OnDismissListener {

	MediaPlayer mPlayer;

	private int id;

	private CustomNotice currentNotice;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		final Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

		if (!isScreenOn()) {
			win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
					| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
					| WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
					| WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR);
		}

		try {
			id = getIntent().getIntExtra("id", -1);
			if (id != -1) {
				currentNotice = new NoticeDao(this).queryNoticeById(id);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return;
		}

		mPlayer = new MediaPlayer();
		if (currentNotice != null) {
			currentNotice.setHostory(true);
			new NoticeDao(this).updateNotice(currentNotice);
			showActionDialog();
			playAlarmSound();
		} else {
			finish();
		}
	}

	private boolean isScreenOn() {
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		return pm.isScreenOn();
	}

	private void playAlarmSound() {
		Uri url = RingtoneManager.getActualDefaultRingtoneUri(this,
				RingtoneManager.TYPE_ALARM);

		int silentModeStreams = Settings.System.getInt(getContentResolver(),
				Settings.System.MODE_RINGER_STREAMS_AFFECTED, 0);

		if ((silentModeStreams & (1 << AudioManager.STREAM_ALARM)) != 0) {
			mPlayer.setAudioStreamType(silentModeStreams);
		} else {
			mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
		}
		try {
			mPlayer.setDataSource(this, url);
			mPlayer.prepare();
			mPlayer.setLooping(true);
			mPlayer.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showActionDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(R.string.app_name);
		dialog.setMessage(currentNotice.getName()
				+ (currentNotice.getOritation() == 1 ? "大于等于" : "小于等于")
				+ "你设定的提醒价格" + currentNotice.getSetPrice());
		dialog.setPositiveButton("取消", this);
		if (isScreenOn()) {
			dialog.setNegativeButton("查看", this);
		}
		dialog.show().setOnDismissListener(this);
	}

	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case DialogInterface.BUTTON_NEGATIVE:
			Intent intent = new Intent(this, PortOptionalActivity.class);
			intent.setAction(Intent.ACTION_VIEW);
			intent.putExtra("symbol", currentNotice.getSymbol());
			startActivity(intent);
			break;
		case DialogInterface.BUTTON_POSITIVE:
			break;
		default:
			break;
		}
	}

	public void onDismiss(DialogInterface dialog) {
		stopAlarmSound();
		finish();
	}

	private void stopAlarmSound() {
		if (mPlayer != null) {
			mPlayer.stop();
			mPlayer.release();
			mPlayer = null;
		}
	}
}
