package com.xiyou.apps.lookpan.tools;

import mobi.dreambox.frameowrk.core.constant.ErrorCodeConstants;
import android.app.Activity;
import android.widget.Toast;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.exception.AClientException;
import cn.precious.metal.exception.ErrorCodeConst;


public class ExcepUtils {

	/**
	 * 
	 * @param context
	 * @param exception
	 */
	public static void showException(final Activity context,final AClientException e) {
		context.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (e != null) {
					if (ErrorCodeConstants.HTTP_SOCKET_TIMEOUT
							.equalsIgnoreCase(e.getErrCode())) {
						Toast.makeText(
								context,"网络连接超时",
								Toast.LENGTH_SHORT).show();
						
						
					} else if (ErrorCodeConst.CODE_CONNECT_NETWORK_FAIL
							.equalsIgnoreCase(e.getErrCode())) {
						Toast.makeText(
								context,"网络连接失败",
								Toast.LENGTH_SHORT).show();
						
						
					} 
				}
			}
		});
	}

	/**
	 * 把可视的异常信息展示出来
	 * 
	 * @param msg
	 * @param exception
	 */
	public static void showImpressiveException(Activity activity, String msg,
			Exception exception) {
		if (exception != null && (exception instanceof ServiceException ))
			showException(activity, (AClientException) exception);
		else {
			if (msg != null)
				Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
		}
	}

}
