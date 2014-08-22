package cn.precious.metal.context;

import android.content.Context;
import cn.precious.metal.dao.DatabaseHelper;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class SystemContext {

	private static SystemContext instance;

	private static final String SYNC_KEY = "SYNC_KEY";

	private Context context;

	int retainCount=0;
	
	private DatabaseHelper databaseHelper;
	

	private SystemContext(Context context){
		this.context = context ;
	}
	
	public static SystemContext getInstance(Context context) {
		synchronized (SYNC_KEY) {
			if (instance == null)
				instance = new SystemContext(context);
			else
				instance.context = context;
			
			return instance;
		}
	}
	
	/**
	 * open Database
	 */
	public void openDB(){
		synchronized (SYNC_KEY) {
			if(retainCount==0 || databaseHelper==null){
				databaseHelper = (DatabaseHelper) OpenHelperManager.getHelper(context, DatabaseHelper.class);
			}
			retainCount++;
		}
	}
	/**
	 * close Database
	 */
	public void closeDB(){
		synchronized (SYNC_KEY) {
			if(retainCount>0){
				retainCount--;
			}else if(retainCount==0){
				if(databaseHelper!=null){
					OpenHelperManager.releaseHelper();
					databaseHelper = null;
				}
			}
		}
	}
	
	public DatabaseHelper getDatabaseHelper(){
		return databaseHelper;
	}

}
