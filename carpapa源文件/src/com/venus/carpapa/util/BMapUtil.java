package com.venus.carpapa.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;




import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BMapUtil {
	// 拍照
	// private static final String imagePath = MyConfig.path + "test.jpg";
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	Activity mContext;

	public BMapUtil(Activity mContext) {
		this.mContext = mContext;
	}

	/**
	 * 从view 得到图片
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	/**
	 * 拍照和取图
	 * 
	 * @param mContext
	 */
	public void getPicture(final String path) {
		new AlertDialog.Builder(mContext)
				.setIconAttribute(android.R.attr.alertDialogIcon)
				.setTitle("选择图片")

				.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						openCamera(path);
					}
				})
				.setNeutralButton("取图", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						enterSD();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				}).create().show();

	}

	void openCamera(String imagePath) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(imagePath)));
		mContext.startActivityForResult(intent, PHOTOHRAPH);

	}

	void enterSD() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				IMAGE_UNSPECIFIED);
		mContext.startActivityForResult(intent, PHOTOZOOM);

	}

	/**
	 * 缩放
	 * 
	 * @param uri
	 * @param mContext
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 100);
		intent.putExtra("return-data", true);
		mContext.startActivityForResult(intent, PHOTORESOULT);
	}

	public static final int IMAGE_MAX_WIDTH = 600;
	public static final int IMAGE_MAX_HEIGHT = 600;

	/**
	 * 压缩
	 * 
	 * @param filepath
	 * @param quality
	 */
	public void compressImage(String filepath, int quality) {
		// 1. Calculate scale
		int scale = 1;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filepath, options);
		if (options.outWidth > IMAGE_MAX_WIDTH
				|| options.outHeight > IMAGE_MAX_HEIGHT) {
			scale = (int) Math.pow(
					2.0,
					(int) Math.round(Math.log(IMAGE_MAX_WIDTH
							/ (double) Math.max(options.outHeight,
									options.outWidth))
							/ Math.log(0.5)));
			// scale = 2;
		}
		// 2. File -> Bitmap (Returning a smaller image)
		options.inJustDecodeBounds = false;
		options.inSampleSize = scale;
		Bitmap bitmap = BitmapFactory.decodeFile(filepath, options);

		// 3. Bitmap -> File
		writeToFile(filepath, bitmap, quality);
	}

	/**
	 * 将Bitmap写入本地缓存文件.
	 * 
	 * @param file
	 *            URL/PATH
	 * @param bitmap
	 * @param quality
	 */
	public void writeToFile(String file, Bitmap bitmap, int quality) {
		if (bitmap == null) {
			return;
		}

		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));

			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos); // PNG
			Log.d("", "Writing file: " + file);
		} catch (IOException ioe) {
			Log.e("", ioe.getMessage());
		} finally {
			try {
				if (bos != null) {
					bos.flush();
					bos.close();
				}
				// bitmap.recycle();
			} catch (IOException e) {
				Log.e("", "Could not close file.");
			}
		}
	}

	public static Bitmap getImageByURI(String path) {
		try {
			HttpURLConnection conn = null;
			URL imgURL = new URL(path);
			conn = (HttpURLConnection) imgURL.openConnection();
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(45000);
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			Bitmap bm = BitmapFactory.decodeStream(bis);

			bis.close();
			is.close();
			return bm;

		} catch (Exception e) {
			System.out.println("e  is  " + e.getCause());
			return null;
		}
	}

	public static Bitmap bitmap;

	public static void showImg(final ImageView img, final String imgUrl,
			final Activity mActivity) {
		HandleAsync hanlde = new HandleAsync();
		hanlde.excute(new HandleAsync.Listener() {

			@SuppressWarnings("deprecation")
			@Override
			public void parse(String result) {
				System.out.println(bitmap);
				if (bitmap != null) {
					int width = bitmap.getWidth();// 1
					int height = bitmap.getHeight();// 2
					int realWidth = Device.getWidth(mActivity);// 2
					int realHeight = realWidth * height / width;
				
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(realWidth, realHeight);
					img.setLayoutParams(params);
					img.setBackgroundDrawable(new BitmapDrawable(bitmap));
				}
			}

			@Override
			public String getResult() {
				// bitmap = HttpGetConnection.getImageByURI(imgUrl);
				return null;
			}
		});

	}

	//调用下面的方法 竟然没有得到图片
	private static Bitmap decodePath(String path) {
		try {
			HttpURLConnection conn = null;
			URL imgURL = new URL(path);
			conn = (HttpURLConnection) imgURL.openConnection();
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(45000);
			conn.connect();
			InputStream in = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(in);

			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(bis, null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(in, null, o2);
		} catch (Exception e) {
			Log.e("BMapUtil", e.getStackTrace().toString());
		}
		return null;
	}
}
