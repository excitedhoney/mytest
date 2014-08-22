package mobi.dreambox.frameowrk.core.context;

import mobi.dreambox.frameowrk.core.util.StringUtil;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;


public class SystemContext {
	private SystemContext(){
		
	}
	
	private static SystemContext instance;
	private static final String SYNK_KEY="SYNK_KEY";
	private int screenWidgh;
	private int screenHeight;
	private float aspectRatio;
	private SharedPreferences readablePreferences;
	private SharedPreferences privatePreferences;
	private Context context;
	private ContentResolver contentResolver;
	private Activity currentActivity;
	private String userId;
	private String token;
	private String currentAppVersion;
	public static SystemContext getInstance(){
		synchronized (SYNK_KEY) {
			if(instance==null)
				instance = new SystemContext();
			return instance;
		}
	}
	
	
	
	public String getCurrentAppVersion() {
		return currentAppVersion;
	}



	public int getScreenWidgh() {
		return screenWidgh;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenSize(int screenWidgh,int screenHeight) {
		this.screenWidgh = screenWidgh;
		this.screenHeight = screenHeight;
		this.aspectRatio = Float.parseFloat(StringUtil.toString(this.screenWidgh))/320;
	}


	public float getAspectRatio() {
		return aspectRatio;
	}



	public void init(Context appContext,SharedPreferences appReadablePreferences,SharedPreferences appPrivatePreferences,
			ContentResolver contentResolver){
		this.context = appContext;
		this.readablePreferences = appReadablePreferences;
		this.privatePreferences = appPrivatePreferences;
		this.contentResolver = contentResolver;
		
	}
	
	public void close(){
		
	}

	public SharedPreferences getReadablePreferences() {
		return readablePreferences;
	}

	public SharedPreferences getPrivatePreferences() {
		return privatePreferences;
	}

	public Context getContext() {
		return context;
	}

	public ContentResolver getContentResolver() {
		return contentResolver;
	}

	public Activity getCurrentActivity() {
		return currentActivity;
	}

	public void setCurrentActivity(Activity currentActivity) {
		this.currentActivity = currentActivity;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/** <p>
	 * Description:[方法功能中文描述]
	 * </p>
	 * 
	 * @param versionName
	 */
	public void setCurrentAppVersion(String versionName) {
		currentAppVersion = versionName;
	}
	
	
	
}
