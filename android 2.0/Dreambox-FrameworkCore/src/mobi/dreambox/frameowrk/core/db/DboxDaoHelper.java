package mobi.dreambox.frameowrk.core.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mobi.dreambox.frameowrk.core.config.PropertiesReader;
import mobi.dreambox.frameowrk.core.constant.DboxConstants;
import mobi.dreambox.frameowrk.core.context.SystemContext;
import mobi.dreambox.frameowrk.core.lifecycle.DboxFrameworkComponentManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Xml;


public class DboxDaoHelper extends SQLiteOpenHelper {
	private static DboxDaoHelper instance;
	private ArrayList<HashMap<String, String>> ALL_TABLES;
	private DboxDaoHelper(Context context){
		super(context,PropertiesReader.getInstance().getSystemProperties(DboxConstants.PROPERTIES_DB_NAME), null, Integer.parseInt(PropertiesReader.getInstance().getSystemProperties(DboxConstants.PROPERTIES_DB_VERSION)));
		
		getAllTables();
	}
	/** <p>
	 * Description:[方法功能中文描述]
	 * </p>
	 * 
	 */
	private void getAllTables() {
		ALL_TABLES = new ArrayList<HashMap<String,String>>();
		fillTableStructure(DboxConstants.DB_FILE_NAME);
		DboxFrameworkComponentManager componentMgr = new DboxFrameworkComponentManager();
		List<String> tableConfigFlieList = componentMgr.getTableConfigFileList();
		if(tableConfigFlieList!=null&&tableConfigFlieList.size()>0){
			for(String path : tableConfigFlieList){
				fillTableStructure(path);
			}
		}
	}
	/** <p>
	 * Description:[方法功能中文描述]
	 * </p>
	 * 
	 * @param string
	 */
	private void fillTableStructure(String configFileName) {
		InputStream systemInputStream;
		try {
			systemInputStream = SystemContext.getInstance().getContext().getAssets().open(configFileName);
			XmlPullParser parser = Xml.newPullParser();
			try {
				parser.setInput(systemInputStream, "UTF-8");
				int eventType = parser.getEventType();
				HashMap<String,String> table=null;
				String tableName=null;
				String tableFields=null;
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
						case XmlPullParser.START_DOCUMENT://文档开始事件,可以进行数据初始化处理
							break;
						case XmlPullParser.START_TAG://开始元素事件
							String name = parser.getName();
							if (name.equalsIgnoreCase("table")) {
								table = new HashMap<String, String>();
								tableName = parser.getAttributeValue(0);
							}else if(name.equalsIgnoreCase("fields")){
								tableFields = parser.nextText();
							}
							break;
						case XmlPullParser.END_TAG://结束元素事件
							String name2 = parser.getName();
							if(name2.equalsIgnoreCase("table")){
									table.put("TABLE_NAME", tableName);
									table.put("FIELD_NAME", tableFields);
									ALL_TABLES.add(table);
							}
							break;
					}
					eventType = parser.next();
				}
			} catch (XmlPullParserException e) {
				
			} catch (IOException e) {

			} finally{
				
			}
		} catch (IOException e) {
//			e.printStackTrace();
		}
		
	}
	public static DboxDaoHelper getInstance(){
		if(instance==null)
			instance = new DboxDaoHelper(SystemContext.getInstance().getContext());
		return instance;
	}
	public DboxDaoHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create Table
		for (int i = 0; i < ALL_TABLES.size(); i++) {
			db.execSQL("CREATE TABLE IF NOT EXISTS " + ALL_TABLES.get(i).get("TABLE_NAME") + " ( "
					+ ALL_TABLES.get(i).get("FIELD_NAME") + " );");
		}

		// create Index table
		db.execSQL("DROP TABLE IF EXISTS dual;");
		db.execSQL("CREATE TABLE dual (idx INTEGER PRIMARY KEY, rownum TEXT);");
		db.execSQL("CREATE UNIQUE INDEX idx_rownum ON dual (rownum);");

		// create all tables
		db.beginTransaction();
		try {
			for (int i = 1; i < 100; i++) {
				String idxStr = "0" + i;
				if (i > 9)
					idxStr = idxStr.substring(1);

				db.execSQL("INSERT INTO dual (idx, rownum) VALUES (" + i + ",'" + idxStr + "');");
			}
			db.setTransactionSuccessful();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (int i = 0; i < ALL_TABLES.size(); i++) {
			db.execSQL("DROP TABLE IF EXISTS " + ALL_TABLES.get(i).get("TABLE_NAME") + ";");
		}
		onCreate(db);
	}

}
