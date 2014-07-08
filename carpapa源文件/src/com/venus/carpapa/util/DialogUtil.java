package com.venus.carpapa.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class DialogUtil {
	
	public static Dialog showProgressDialog(Context context, String msg, boolean cancelable){
		ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(msg);
        dialog.setIndeterminate(true);
        dialog.setCancelable(cancelable);
        return dialog;
	}
	public static void toast(Context context, String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	
}
