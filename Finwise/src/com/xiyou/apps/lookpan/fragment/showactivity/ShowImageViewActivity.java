package com.xiyou.apps.lookpan.fragment.showactivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.polites.android.GestureImageView;
import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.utils.Util;

public class ShowImageViewActivity extends Activity {

	private String Url;
	private GestureImageView view;
	private Bitmap mBitmap;
	private ActionBar mActionBar;
	private ImageView mLoadErrorView;
	private LinearLayout mLoadingProgress;
	private ProgressDialog myDialog;
	private final static String ALBUM_PATH = "/sdcard/myalbum/";
	private String message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Url = getIntent().getExtras().getString("BigImageURL");
		setContentView(R.layout.show_image);
		mActionBar = getActionBar();
		mActionBar.setTitle("图片展示");
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.title_shang));
		view = (GestureImageView) findViewById(R.id.large_image);
		mLoadErrorView = (ImageView) findViewById(R.id.load_error);
		mLoadingProgress = (LinearLayout) findViewById(R.id.loading_progress);

		// DisplayImageOptions mOptionss = new DisplayImageOptions.Builder()
		// .displayer(new RoundedBitmapDisplayer(5)).build();
		// ImageLoader.getInstance().displayImage(Url, view, mOptionss);

		new AsyncTask<Object, Object, Bitmap>() {

			@Override
			protected Bitmap doInBackground(Object... params) {
				mBitmap = returnBitMap(Url);
				return mBitmap;
			}

			protected void onPostExecute(Bitmap result) {
				if (null != result) {
					view.setImageBitmap(mBitmap);
					mLoadingProgress.setVisibility(View.GONE);
				}
			}

		}.execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.image, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.action_item_imamge:
			if (null != mBitmap) {

				MediaStore.Images.Media.insertImage(getContentResolver(),
						mBitmap, "myPhoto", "");
				// sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
				// Uri.parse("file://"
				// + Environment.getExternalStorageDirectory())));

				MediaScannerConnection.scanFile(this,
						new String[] { Environment
								.getExternalStorageDirectory().toString() },
						null,
						new MediaScannerConnection.OnScanCompletedListener() {
							/*
							 * (non-Javadoc)
							 * 
							 * @see android.media.MediaScannerConnection.
							 * OnScanCompletedListener
							 * #onScanCompleted(java.lang.String,
							 * android.net.Uri)
							 */
							public void onScanCompleted(String path, Uri uri) {
								Log.i("ExternalStorage", "Scanned " + path
										+ ":");
								Log.i("ExternalStorage", "-> uri=" + uri);
							}
						});
				Util.toastTips(ShowImageViewActivity.this, "图片已保存");
			}

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public static Bitmap returnBitMap(String url) {
		Log.i("returnBitMap", "url=" + url);
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
			conn = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mBitmap = null;
	}
}
