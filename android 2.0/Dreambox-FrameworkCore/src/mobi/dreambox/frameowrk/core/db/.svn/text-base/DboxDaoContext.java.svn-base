package mobi.dreambox.frameowrk.core.db;


import android.database.sqlite.SQLiteDatabase;


public class DboxDaoContext {
	private final static String SYNC_KEY="SYNC_KEY";
	private static DboxDaoContext instance;
	SQLiteDatabase db;
	int retainCount=0;
	public static DboxDaoContext getInstance(){
		synchronized (SYNC_KEY) {
			if(instance==null)
				instance = new DboxDaoContext();
			return instance;
		}
	}
	
	private DboxDaoContext(){
		
	}
	
	public SQLiteDatabase getDb(){
		return db;
	}

	public void openDB(){
		synchronized (SYNC_KEY) {
			if(retainCount==0||db==null){
				db = DboxDaoHelper.getInstance().getWritableDatabase();
			}
			retainCount++;
		}
	}
	
	public void closeDB(){
		synchronized (SYNC_KEY) {
			if(retainCount>0){
				retainCount--;
			}else if(retainCount==0){
				if(db!=null && db.isOpen()){
					db.close();
					db = null;
				}
			}
		}
	}
}