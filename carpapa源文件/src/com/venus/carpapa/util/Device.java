package com.venus.carpapa.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/** 此类存放设备信息 包括屏幕宽高 以后需要别的再加 */
public class Device {
	static private int screen_width = 0;
	static private int screen_height = 0;
	static private float rate = 0.0f;
	static private float density = 0.0f;
	final static float standardHeight = 800.0f;

	public static int getHeight(Activity mActivity) {
		if (screen_height == 0) {
			DisplayMetrics dm = new DisplayMetrics();
			mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			screen_height = dm.heightPixels;
			System.out.println("高度是    " + screen_height);
		}
		return screen_height;

	}

	public static int getWidth(Activity mActivity) {
		if (screen_width == 0) {
			DisplayMetrics dm = new DisplayMetrics();
			mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			screen_width = dm.widthPixels;
			System.out.println("宽度是    " + screen_width);
		}
		return screen_width;
	}

	/** 获取比例 用高度800为准 */
	public static float getRate(Activity mActivity) {
		if (rate == 0.0f)
			rate = getHeight(mActivity) / standardHeight;
		return rate;
	}

	/** 获取比例 用高度800为准 */
	public static float getDensity(Activity mActivity) {
		if (density == 0.0f) {
			DisplayMetrics dm = new DisplayMetrics();
			mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			density = dm.density;
		}
		return density;
	}

}
