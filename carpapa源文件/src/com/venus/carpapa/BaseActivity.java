package com.venus.carpapa;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.venus.carpapa.R;
import com.venus.carpapa.util.DialogUtil;
import com.venus.carpapa.util.TaskUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.http.AjaxParams;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class BaseActivity extends FinalActivity {
	String ret = "";
	TaskUtil task = null;
	AjaxParams params = new AjaxParams();
	Dialog dialog;
	Handler hand = null;
	//
	public static int screenWidth;
	//
	public static int screenHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		task = new TaskUtil(this);

	}

	public void showDialog() {
		dialog = DialogUtil.showProgressDialog(this,
				getString(R.string.request_data), true);
		dialog.show();
	}

	public void canelDialog() {
		if (dialog != null) {
			// dialog.cancel();
			dialog.dismiss();
		}

	}

	public void full() {

	}

	public void taskCallBack(String jsonObject, int type) {

	}

	public void taskCallBack(String t) {
		// TODO Auto-generated method stub

	}

	public void taskLogin(String t) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getLoacalBitmapByAssets(Context c, String url) {
		Bitmap bitmap = null;
		InputStream in = null;
		try {
			in = c.getResources().getAssets().open(url);
			bitmap = BitmapFactory.decodeStream(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(in, null);
		}
		return bitmap;
	}

	/**
	 * 
	 * 
	 * @param in
	 * @param out
	 */
	public static void closeStream(InputStream in, OutputStream out) {
		try {
			if (null != in) {
				in.close();
			}
			if (null != out) {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
