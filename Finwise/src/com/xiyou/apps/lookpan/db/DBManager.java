package com.xiyou.apps.lookpan.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.model.HangQingDBBean;

public class DBManager {
	private final int BUFFER_SIZE = 400000;
	public static final String DB_NAME = "wscnquote.db"; // 保存的数据库文件名
	public static final String PACKAGE_NAME = "com.wallstreetcn.main";
	public static final String DB_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME; // 在手机里存放数据库的位置
	private SQLiteDatabase database;
	private Context context;
	private Cursor cursor;

	public DBManager(Context context) {
		this.context = context;
	}

	public void openDatabase() {
		this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
	}

	private SQLiteDatabase openDatabase(String dbfile) {
		try {
			if (!(new File(dbfile).exists())) {// 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
				InputStream is = this.context.getResources().openRawResource(
						R.raw.wscnquote); // 欲导入的数据库
				FileOutputStream fos = new FileOutputStream(dbfile);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,
					null);
			return db;
		} catch (FileNotFoundException e) {
			Log.e("Database", "File not found");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("Database", "IO exception");
			e.printStackTrace();
		}
		return null;
	} // do something else here<br>

	public void closeDatabase() {
		if (null != this.database) {
			this.database.close();

		}

		if (null != this.cursor) {
			this.cursor.close();
			this.cursor = null;
		}

	}

	public ArrayList<HangQingDBBean> selectDBAll() {
		ArrayList<HangQingDBBean> list = new ArrayList<HangQingDBBean>();
		try {
			this.openDatabase();
			cursor = this.database.query("quoteTable",
					new String[] { "symbol,title,isNeed,subTitle" }, null,
					null, null, null, null, null);
			cursor.moveToFirst();
			while (cursor.moveToNext()) {
				HangQingDBBean mHangQingDBBean = new HangQingDBBean();
				mHangQingDBBean.setSymbol(cursor.getString(0)); // 获取第一列的值,第一列的索引从0开始
				mHangQingDBBean.setTitle(cursor.getString(1));
				mHangQingDBBean.setIsNeed(cursor.getString(2));
				mHangQingDBBean.setSubTitle(cursor.getString(3));
				list.add(mHangQingDBBean);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeDatabase();
		}
		return list;
	}
}