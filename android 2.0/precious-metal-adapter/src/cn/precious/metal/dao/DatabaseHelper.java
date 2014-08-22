package cn.precious.metal.dao;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cn.precious.metal.entity.CustomNotice;
import cn.precious.metal.entity.Optional;
import cn.precious.metal.entity.VirtualTrade;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class used to manage the creation and upgrading of your
 * database. This class also usually provides the DAOs used by the other
 * classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "test1.12.db";
	
	private static final int DATABASE_VERSION = 1;

	// the DAO object we use to access the SimpleData table
	private Dao<Optional, Integer> optionalDao = null;
	private Dao<VirtualTrade, Integer> tradeDao = null;
	private Dao<CustomNotice, Integer> noticeDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This is called when the database is first created. Usually you should
	 * call createTable statements here to create the tables that will store
	 * your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		Log.i(DatabaseHelper.class.getName(), "onCreate");
		createTable();

	}

	private void createTable() {
		try {

			TableUtils.createTableIfNotExists(connectionSource,
					Optional.class);
			TableUtils.createTableIfNotExists(connectionSource,
					VirtualTrade.class);
			TableUtils.createTableIfNotExists(connectionSource,
					CustomNotice.class);
			
			
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}

	}

	public void delTables() {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.clearTable(connectionSource, Optional.class);
			TableUtils.clearTable(connectionSource, VirtualTrade.class);
			TableUtils.clearTable(connectionSource, CustomNotice.class);

			// after we drop the old databases, we create the new ones

		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher
	 * version number. This allows you to adjust the various data to match the
	 * new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Optional.class, true);
			TableUtils.dropTable(connectionSource, VirtualTrade.class, true);
			TableUtils.dropTable(connectionSource, CustomNotice.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		optionalDao = null;
		tradeDao = null;
		noticeDao = null;
	}

	/**
	 * Returns the Database Access Object (DAO)
	 */
	public Dao<Optional, Integer> getOptionalDao() throws SQLException {
		if (optionalDao == null) {
			optionalDao = getDao(Optional.class);
		}
		return optionalDao;
	}
	/**
	 * Returns the Database Access Object (DAO)
	 */
	public Dao<VirtualTrade, Integer> getTradeDao() throws SQLException {
		if (tradeDao == null) {
			tradeDao = getDao(VirtualTrade.class);
		}
		return tradeDao;
	}
	/**
	 * Returns the Database Access Object (DAO)
	 */
	public Dao<CustomNotice, Integer> getNoticeDao() throws SQLException {
		if (noticeDao == null) {
			noticeDao = getDao(CustomNotice.class);
		}
		return noticeDao;
	}
	
	public void clearTradeTable() {
		try {
			TableUtils.clearTable(connectionSource, VirtualTrade.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
