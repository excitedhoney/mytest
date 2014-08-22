package cn.precious.metal.dao;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import cn.precious.metal.context.SystemContext;
import cn.precious.metal.entity.VirtualTrade;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.query.OrderBy;

public class VirtualTradeDao extends BaseDao {
	private Context context;

	public VirtualTradeDao(Context context) {
		this.context = context;
		
	}
	
	public boolean addTrade(VirtualTrade trade) {
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<VirtualTrade, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getTradeDao() ;
			count = dao.create(trade);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return count > 0;
	}
	
	public boolean updateTrade(VirtualTrade trade) {
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<VirtualTrade, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getTradeDao() ;
			count = dao.update(trade);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return count > 0;
	}
	
	/**
	 * 查询所有持仓的   记录
	 * @return
	 */
	public List<VirtualTrade>  queryOpeningTrades() {
		SystemContext.getInstance(context).openDB();
		List<VirtualTrade> list = null ;
		try {
			Dao<VirtualTrade, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getTradeDao() ;
			list = dao.queryBuilder().where().eq("isOpening", 1).and().eq("isClose", 0).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return list;
	}
	
	/**
	 * 查询所有平仓的   记录 
	 * @return
	 */
	public List<VirtualTrade>  queryCloseTrades() {
		SystemContext.getInstance(context).openDB();
		List<VirtualTrade> list = null ;
		try {
			Dao<VirtualTrade, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getTradeDao() ;
			list = dao.queryBuilder().orderBy("createTime", false).where().eq("isClose", 1).and().eq("isOpening", 0).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return list;
	}
	/**
	 * 查询所有的   记录 
	 * @return
	 */
	public List<VirtualTrade>  queryTrades() {
		SystemContext.getInstance(context).openDB();
		List<VirtualTrade> list = null ;
		try {
			Dao<VirtualTrade, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getTradeDao() ;
			list = dao.queryBuilder().query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return list;
	}
	
}
