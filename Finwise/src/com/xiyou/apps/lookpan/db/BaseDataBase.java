package com.xiyou.apps.lookpan.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xiyou.apps.lookpan.model.NewsChannelBean;

/**
 * 
 * 
 * @author RayMon
 * 
 */
public class BaseDataBase {
	// 数据库名�?

	private static final String DB_NAME = "Finwise.db";
	// 数据库版�?
	private static final int DB_VERSION = 1;

	private SQLiteDatabase mSQLiteDatabase = null;

	private DatabaseHelper mDatabaseHelper = null;

	private Context mContext = null;

	private static BaseDataBase dbConn = null;
	private Cursor cursor;

	/**
	 * SQLiteOpenHelper
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL("create table " + "NewsChannel" + "( " + "id"
					+ " integer primary key AUTOINCREMENT," + "ChannelName"
					+ " text not null," + "IsChecked" + " text not null);");

			db.execSQL("create table " + "WaiHuiChannel" + "( " + "id"
					+ " integer primary key AUTOINCREMENT,"
					+ "title text not null);");
			db.execSQL("create table " + "ZiXuanChannel" + "( " + "id"
					+ " integer primary key AUTOINCREMENT,"
					+ "title text not null);");

			db.execSQL("create table " + "ShangPinChannel" + "( " + "id"
					+ " integer primary key AUTOINCREMENT,"
					+ "title text not null);");

			db.execSQL("create table " + "GuZhiChannel" + "( " + "id"
					+ " integer primary key AUTOINCREMENT,"
					+ "title  text not null);");

			db.execSQL("create table " + "ZhaiQuanChannel" + "( " + "id"
					+ " integer primary key AUTOINCREMENT,"
					+ "title text not null);");

			db.execSQL("create table " + "loading" + "( " + "id"
					+ " integer primary key AUTOINCREMENT,"
					+ "title text not null);");

			db.execSQL("create table " + "loading_s" + "( " + "id"
					+ " integer primary key AUTOINCREMENT,"
					+ "title text not null);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}

	/**
	 * 初始�?
	 * 
	 * @param mContext
	 */
	private BaseDataBase(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public static BaseDataBase getInstance(Context mContext) {
		if (null == dbConn) {
			dbConn = new BaseDataBase(mContext);
		}
		return dbConn;
	}

	/**
	 * 打开数据�?
	 */
	public void open() {
		mDatabaseHelper = new DatabaseHelper(mContext);
		mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		if (null != mDatabaseHelper) {
			mDatabaseHelper.close();
		}
		if (null != cursor) {
			cursor.close();
		}
	}

	/**
	 * 更新News页面标签
	 * 
	 * @param NewsChannelName
	 * @param IsChecked
	 * @return
	 */
	public int UpdateNewsChannel(String NewsChannelName, String IsChecked) {
		ContentValues cv = new ContentValues();
		cv.put("IsChecked", IsChecked);
		String[] args = { String.valueOf(NewsChannelName) };
		return mSQLiteDatabase.update("NewsChannel", cv, "ChannelName=?", args);

	}

	/**
	 * 查找News页面全部的标�?
	 * 
	 * @return list
	 */

	public ArrayList<NewsChannelBean> SelectNewsChannel() {
		ArrayList<NewsChannelBean> list = new ArrayList<NewsChannelBean>();
		cursor = mSQLiteDatabase.query("NewsChannel",
				new String[] { "ChannelName,IsChecked" }, null, null, null,
				null, null, null);
		while (cursor.moveToNext()) {
			NewsChannelBean mNewsChannelBean = new NewsChannelBean();
			mNewsChannelBean.setNewsChannelName(cursor.getString(0)); // 获取第一列的�?第一列的索引�?�?��
			mNewsChannelBean.setIsChecked(cursor.getString(1));
			list.add(mNewsChannelBean);
		}
		return list;

	}

	/**
	 * 插入News频道标签
	 * 
	 * @param list
	 */
	public void insertNewsChannel(ArrayList<NewsChannelBean> list) {
		// 实例化常量�?
		for (NewsChannelBean bean : list) {
			ContentValues cValue = new ContentValues();
			cValue.put("ChannelName", bean.getNewsChannelName());
			cValue.put("IsChecked", bean.getIsChecked());
			// 调用insert()方法插入数据
			mSQLiteDatabase.insert("NewsChannel", null, cValue);
		}
	}

	/**
	 * 查找Loading
	 * 
	 * @return list
	 */

	public ArrayList<String> SelectLoading() {
		ArrayList<String> list = new ArrayList<String>();
		cursor = mSQLiteDatabase.query("loading", new String[] { "title" },
				null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}
		return list;

	}

	/**
	 * 更新Loading
	 */

	public void Update(String VersionCode) {
		mSQLiteDatabase
				.execSQL("update loading set title=" + VersionCode + ";");

	}

	/**
	 * 插入Loading
	 * 
	 * @param list
	 */
	public void insertLoading(String str) {
		ContentValues cValue = new ContentValues();
		cValue.put("title", str);
		// 调用insert()方法插入数据
		mSQLiteDatabase.insert("loading", null, cValue);
	}

	// /**
	// * 删除单个 自�?的数�?
	// *
	// * @param list
	// */
	//
	// public void DelectSingleLoading(String str) {
	// mSQLiteDatabase
	// .delete("loading", "title=?", new String[] { str });
	//
	// }

	/**
	 * 查找loading_s
	 * 
	 * @return list
	 */

	public ArrayList<String> SelectLoading2() {
		ArrayList<String> list = new ArrayList<String>();
		cursor = mSQLiteDatabase.query("loading_s", new String[] { "title" },
				null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}
		return list;

	}

	/**
	 * 插入loading_s
	 * 
	 * @param list
	 */
	public void insertLoading2(String str) {
		ContentValues cValue = new ContentValues();
		cValue.put("title", str);
		// 调用insert()方法插入数据
		mSQLiteDatabase.insert("loading_s", null, cValue);
	}

	/**
	 * 插入WaiHui的数�?
	 * 
	 * @param list
	 */

	public void insertWaiHuiChannel(ArrayList<String> list) {
		for (String str : list) {
			ContentValues cValue = new ContentValues();
			// 添加用户�?
			cValue.put("title", str);
			mSQLiteDatabase.insert("WaiHuiChannel", null, cValue);
		}

	}

	/**
	 * 查找外汇的全部数�?
	 * 
	 * @return list
	 */
	public ArrayList<String> selectWaiHuiChannel() {
		ArrayList<String> list = new ArrayList<String>();
		cursor = mSQLiteDatabase.query("WaiHuiChannel",
				new String[] { "title" }, null, null, null, null, null, null);
		cursor.moveToFirst();
		while (cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}
		return list;
	}

	/**
	 * 插入商品的数�?
	 * 
	 * @param list
	 */

	public void insertShangPinChannel(ArrayList<String> list) {
		for (String str : list) {
			ContentValues cValue = new ContentValues();
			// 添加用户�?
			cValue.put("title", str);
			mSQLiteDatabase.insert("ShangPinChannel", null, cValue);
		}

	}

	/**
	 * 查找商品的全部数�?
	 * 
	 * @return list
	 */
	public ArrayList<String> selectShangPinChannel() {
		ArrayList<String> list = new ArrayList<String>();
		cursor = mSQLiteDatabase.query("ShangPinChannel",
				new String[] { "title" }, null, null, null, null, null, null);
		cursor.moveToFirst();
		while (cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}
		return list;
	}

	/**
	 * 插入股指的数�?
	 * 
	 * @param list
	 */

	public void insertGuZhiChannel(ArrayList<String> list) {
		for (String str : list) {
			ContentValues cValue = new ContentValues();
			// 添加用户�?
			cValue.put("title", str);
			mSQLiteDatabase.insert("GuZhiChannel", null, cValue);
		}

	}

	/**
	 * 查找 股指的全部数�?
	 * 
	 * @return list
	 */
	public ArrayList<String> selectGuZhiChannel() {
		ArrayList<String> list = new ArrayList<String>();
		cursor = mSQLiteDatabase.query("GuZhiChannel",
				new String[] { "title" }, null, null, null, null, null, null);
		cursor.moveToFirst();
		while (cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}
		return list;
	}

	/**
	 * 插入债券的数�?
	 * 
	 * @param list
	 */

	public void insertZhaiQuanChannel(ArrayList<String> list) {
		for (String str : list) {
			ContentValues cValue = new ContentValues();
			// 添加用户�?
			cValue.put("title", str);
			mSQLiteDatabase.insert("ZhaiQuanChannel", null, cValue);
		}

	}

	/**
	 * 查找 债券的全部数�?
	 * 
	 * @return list
	 */
	public ArrayList<String> selectZhaiQuanChannel() {
		ArrayList<String> list = new ArrayList<String>();
		cursor = mSQLiteDatabase.query("ZhaiQuanChannel",
				new String[] { "title" }, null, null, null, null, null, null);
		cursor.moveToFirst();
		while (cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}
		return list;
	}

	/**
	 * 插入自�?的数�?
	 * 
	 * @param list
	 */

	public void inserZiXuanChannel(ArrayList<String> list) {
		for (String str : list) {
			ContentValues cValue = new ContentValues();
			cValue.put("title", str);
			mSQLiteDatabase.insert("ZiXuanChannel", null, cValue);
		}

	}

	/**
	 * 插入单个 自�?的数�?
	 * 
	 * @param list
	 */

	public void inserSingleZiXuanChannel(String str) {
		ContentValues cValue = new ContentValues();
		cValue.put("title", str);
		Log.i("tag", mSQLiteDatabase.insert("ZiXuanChannel", null, cValue) + "");

	}

	/**
	 * 删除单个 自�?的数�?
	 * 
	 * @param list
	 */

	public void DelectSingleZiXuanChannel(String str) {
		mSQLiteDatabase
				.delete("ZiXuanChannel", "title=?", new String[] { str });

	}

	/**
	 * DELETE * FROM table_name
	 */
	public void DelectAllZiXuanChannel() {
		mSQLiteDatabase.execSQL("DELETE  from ZiXuanChannel");
		inserSingleZiXuanChannel("");
	}

	/**
	 * 查找 自�?的全部数�?
	 * 
	 * @return list
	 */
	public ArrayList<String> selectZiXuanChannel() {
		ArrayList<String> list = new ArrayList<String>();
		Cursor cursor = mSQLiteDatabase.rawQuery("select * from ZiXuanChannel",
				null);
		cursor.moveToFirst();
		while (cursor.moveToNext()) {
			list.add(cursor.getString(cursor.getColumnIndex("title")));
		}
		return list;
	}
}
