package mobi.dreambox.frameowrk.core.lifecycle;

import java.util.List;

import mobi.dreambox.frameowrk.core.config.PropertiesReader;
import mobi.dreambox.frameowrk.core.context.SystemContext;


import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;

public class DboxFrameworkLifecycle {
	
	private DboxFrameworkLifecycle(){
		
	}
	private static DboxFrameworkLifecycle instance;
	private static final String SYNC_KEY= "SYNC_KEY";
	public static DboxFrameworkLifecycle getInstance(){
		synchronized (SYNC_KEY) {
			if(instance==null)
				instance = new DboxFrameworkLifecycle();
			return instance;
		}
	}
	
	public void initFramework(Context appContext,SharedPreferences appReadablePreferences,SharedPreferences appPrivatePreferences,
			ContentResolver contentResolver){
		SystemContext.getInstance().init(appContext, appReadablePreferences, appPrivatePreferences, contentResolver);
		PropertiesReader.getInstance().init();
		DboxFrameworkComponentManager componentMgr = new DboxFrameworkComponentManager();
		List<String> classList = componentMgr.getComponentLifeCycleClass();
		if(classList!=null){
			for(String componentClass:classList){
				try {
					//com.wondersgroup.w3studio.android.framework.component.updown.lifecycle.UpdownComponentLifecycle
					DboxComponentLifecycle componentLifeCycle = (DboxComponentLifecycle)Class.forName(componentClass).newInstance();
					componentLifeCycle.init();
				}
				catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void finishFramework(){
		SystemContext.getInstance().close();
	}
}
