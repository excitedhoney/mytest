package com.xiyou.apps.lookpan.base;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import com.umeng.message.PushAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * @author andy
 * 
 */

@SuppressLint("NewApi")
public abstract class BaseActivity extends FragmentActivity {

	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	public static final String FAIL = "FAIL";

	@SuppressLint("SimpleDateFormat")
	protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static LinkedList<Activity> sAllActivitys = new LinkedList<Activity>();


	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		sAllActivitys.add(this);
		super.onCreate(savedInstanceState);

	}

	public void onStart() {
		super.onStart();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public static void finishAll() {
		for (Activity activity : sAllActivitys) {
			activity.finish();
		}
		sAllActivitys.clear();
	}

	private ProgressDialog mProgressDialog = null;

	public void showNetLoadingProgressDialog(String loadingMsg) {
		mProgressDialog = new ProgressDialog(BaseActivity.this);
		mProgressDialog.setMessage(loadingMsg);
		mProgressDialog.setCancelable(true);
		mProgressDialog
				.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub
						if (mProgressDialog.isShowing()) {
							mProgressDialog.dismiss();
						}

					}
				});
		if (!mProgressDialog.isShowing()) {
			mProgressDialog.show();
		}
	}

	public boolean isProgressDialogShowing() {

		return mProgressDialog != null && mProgressDialog.isShowing();
	}

	public void hideNetLoadingProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	public void intentTo(Context packageContext, Class<?> cls) {
		Intent i = new Intent();
		i.setClass(packageContext, cls);
		startActivity(i);
	}

	@SuppressLint("ShowToast")
	public void showCusToast(String msg) {
		Toast.makeText(this, msg, 1000).show();
	}

}
