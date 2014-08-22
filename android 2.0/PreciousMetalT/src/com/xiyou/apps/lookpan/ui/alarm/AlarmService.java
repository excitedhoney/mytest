package com.xiyou.apps.lookpan.ui.alarm;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import cn.precious.metal.context.SystemContext;
import cn.precious.metal.dao.NoticeDao;
import cn.precious.metal.dao.OptionalDao;
import cn.precious.metal.entity.CustomNotice;
import cn.precious.metal.entity.Optional;

public class AlarmService extends Service {

	private static final String TAG = "AlarmService";

	private CustomNotice notice;
	
	private OptionalDao optionalDao ;

	private NoticeDao dao;
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SystemContext.getInstance(AlarmService.this);
        new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					alarm();
					try {
						Thread.sleep(20*1000);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}).start(); 
	}

	public void alarm() {
		dao = new NoticeDao(AlarmService.this);
		optionalDao = new OptionalDao(AlarmService.this) ;
		
		List<CustomNotice> list = dao.queryAlarmNotices();

		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				notice = list.get(i);
				String symbol  =  notice.getSymbol() ;
				
				long currentTime = System.currentTimeMillis();
				long triTime = notice.getCreateTime() + notice.getRunTime();
				if (triTime > currentTime) {
					Optional op = optionalDao.queryOptionalsBySymbol(symbol) ;
					if(notice.getOritation() == 1) {
						if(Double.parseDouble(op.getNewest()) >= notice.getSetPrice()) {
							Intent intent = new Intent() ;
							intent.setClass(AlarmService.this, AlarmAlertActivity.class);
							intent.putExtra("id", notice.getId());
					        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					        startActivity(intent) ;
						}
					}else {
						if(Double.parseDouble(op.getNewest()) <= notice.getSetPrice()) {
							Intent intent = new Intent() ;
							intent.setClass(AlarmService.this, AlarmAlertActivity.class);
					        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					        intent.putExtra("id", notice.getId());
					        startActivity(intent) ;
						}
					}
				} else {
					notice.setHostory(true);
					dao.updateNotice(notice);
				}
			}
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
