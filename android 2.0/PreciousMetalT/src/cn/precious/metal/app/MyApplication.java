package cn.precious.metal.app;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

//检测用户各种信息
public class MyApplication extends Application {

	public static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

	public static MyApplication application;

	private static final String SYNC_TAG = "SYNC_TAG";

	@Override
	public void onCreate() {
		super.onCreate();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(3) 
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.memoryCache(new LruMemoryCache(1 * 1024 * 1024))  
				.discCacheSize(10 * 1024 * 1024)
				.build();

		ImageLoader.getInstance().init(config);
		application = this;
		
//		CrashHandler crashHandler = CrashHandler.getInstance();
//		crashHandler.init(this);
		
	}

	public static MyApplication getInstance() {
		synchronized (SYNC_TAG) {
			if (null == application) {
				application = new MyApplication();
			}
			return application;
		}
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
	

}
