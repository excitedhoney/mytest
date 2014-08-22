package cn.precious.metal.dao;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import cn.precious.metal.context.SystemContext;
import cn.precious.metal.entity.CustomNotice;
import cn.precious.metal.entity.Optional;

import com.j256.ormlite.dao.Dao;

public class NoticeDao extends BaseDao {

	
	private Context context;

	public NoticeDao(Context context) {
		this.context = context;
	}

	public boolean addNotice(CustomNotice notice) {
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<CustomNotice, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getNoticeDao() ;
			count = dao.create(notice);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return count > 0;
	}
	
	public boolean deleteNotice(CustomNotice notice) {
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<CustomNotice, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getNoticeDao() ;
			count = dao.delete(notice);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return count > 0;
	}
	
	
	public boolean updateNotice(CustomNotice notice) {
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<CustomNotice, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getNoticeDao() ;
			count = dao.update(notice);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return count > 0;
	}
	
	
	
	/**
	 * 获取本地的消息提醒
	 * @return
	 */
	public List<CustomNotice> queryAlarmNotices() {
		SystemContext.getInstance(context).openDB();
		List<CustomNotice> list = null ;
		try {
			Dao<CustomNotice, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getNoticeDao() ;
			list = dao.queryBuilder().where().eq("fangshi", 0).and().eq("isHostory", false).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return list ;
	}
	/**
	 * 获取短信的消息提醒
	 * @return
	 */
	public List<CustomNotice> queryNoteNotices() {
		SystemContext.getInstance(context).openDB();
		List<CustomNotice> list = null ;
		try {
			Dao<CustomNotice, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getNoticeDao() ;
			list = dao.queryBuilder().where().eq("fangshi", 1).and().eq("isHostory", false).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return list ;
	}
	
	
	public CustomNotice queryNoticeById (int id) {
		SystemContext.getInstance(context).openDB();
		List<CustomNotice> list = null ;
		try {
			Dao<CustomNotice, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getNoticeDao() ;
			list = dao.queryBuilder().where().eq("id", id).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return list != null ? list.get(0) : null  ;
	}
}
