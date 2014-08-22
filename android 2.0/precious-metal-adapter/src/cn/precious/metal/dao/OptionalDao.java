package cn.precious.metal.dao;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import cn.precious.metal.context.SystemContext;
import cn.precious.metal.entity.Optional;

import com.j256.ormlite.dao.Dao;

public class OptionalDao extends BaseDao {

	private Context context;

	public OptionalDao(Context context) {
		this.context = context;
	}

	public boolean addOptional(Optional optional) {
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<Optional, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getOptionalDao();
			count = dao.create(optional);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return count > 0;
	}
	
	
	public boolean addOrUpdateOptional(Optional optional) {
		SystemContext.getInstance(context).openDB();
		int count = -1;
		List<Optional> list ;
		try {
			Dao<Optional, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getOptionalDao();
			list = dao.queryBuilder().where().eq("treaty", optional.getTreaty()).query() ;
			if(list != null && !list.isEmpty()){
				optional.setLocalId(list.get(0).getLocalId());
				optional.setOptional(list.get(0).isOptional());
				optional.setHostory(list.get(0).isHostory());
				optional.setType(list.get(0).getType());
				optional.setDrag(list.get(0).getDrag());
				optional.setTop(list.get(0).getTop());
				count = dao.update(optional);
			}else{ // 创建
				count = dao.create(optional);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return count > 0;
	}

	public boolean updateOptional(Optional optional) {
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<Optional, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getOptionalDao();
			count = dao.update(optional);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return count > 0;
	}

	public boolean updateOptionalList(List<Optional> optionals) {
		if (optionals != null && !optionals.isEmpty()) {
			SystemContext.getInstance(context).openDB();
			int count = -1;
			int updateCount = 0;
			try {
				Dao<Optional, Integer> dao = SystemContext
						.getInstance(context).getDatabaseHelper()
						.getOptionalDao();
				for (int i = 0; i < optionals.size(); i++) {
					count = dao.update(optionals.get(i));
					if (count > 0)
						updateCount++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			} finally {
				SystemContext.getInstance(context).closeDB();
			}
			return updateCount == optionals.size();
		}
		return false;
	}

	public List<Optional> queryOptionals() {
		SystemContext.getInstance(context).openDB();
		List<Optional> list = null;
		try {
			Dao<Optional, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getOptionalDao();
			list = dao.queryBuilder().orderBy("drag", false)
					.orderBy("top", false).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return list;
	}

	public List<Optional> queryOptionalsByType(String type) {
		SystemContext.getInstance(context).openDB();
		List<Optional> list = null;
		try {
			Dao<Optional, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getOptionalDao();
				list = dao.queryBuilder().orderBy("drag", false)
						.orderBy("top", false).where().eq("type", type).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return list;
	}

	public List<Optional> queryAllMyOptionals() {
		SystemContext.getInstance(context).openDB();
		List<Optional> list = null;
		try {
			Dao<Optional, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getOptionalDao();
			list = dao.queryBuilder().orderBy("drag", false)
					.orderBy("top", false).where().eq("isOptional", true)
					.query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return list;
	}

	public boolean isExits(Optional optional) {
		SystemContext.getInstance(context).openDB();
		List<Optional> list = null;
		try {
			Dao<Optional, Integer> dao = SystemContext.getInstance(context).getDatabaseHelper().getOptionalDao();
			list = dao.queryBuilder().where().eq("treaty", optional.getTreaty()).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return (list != null && !list.isEmpty());
	}

	public boolean deleteOptional(Optional optional) {
		if (optional == null)
			return false;
		SystemContext.getInstance(context).openDB();
		int count = -1;
		try {
			Dao<Optional, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getOptionalDao();
			optional.setOptional(false);
			optional.setDrag(0);
			optional.setTop(0);
			count = dao.update(optional);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return (count > 0);
	}

	public boolean deleteOptionalList(List<Optional> optionals) {
		if (optionals == null || optionals.isEmpty())
			return false;

		SystemContext.getInstance(context).openDB();
		int count = -1;
		int size = 0;
		try {
			Dao<Optional, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getOptionalDao();
			for (Optional hangqing_OPtional : optionals) {
				hangqing_OPtional.setOptional(false);
				hangqing_OPtional.setDrag(0);
				hangqing_OPtional.setTop(0);
				count = dao.update(hangqing_OPtional);
				if (count > 0)
					size++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return (size == optionals.size());
	}

	public List<Optional> queryOptionalsByKeyword(String keyword) {
		SystemContext.getInstance(context).openDB();
		List<Optional> list = null;
		try {
			Dao<Optional, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getOptionalDao();
			list = dao.queryBuilder().orderBy("drag", false)
					.orderBy("top", false).where()
					.like("treaty", "%" + keyword + "%").or()
					.like("title", "%" + keyword + "%").or()
					.query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return list;
	}
	
	//  获取当前添加的自选股数量
	public int getIsOptionalNumber() {
		SystemContext.getInstance(context).openDB();
		List<Optional> list = null;
		try {
			Dao<Optional, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getOptionalDao();
			list = dao.queryBuilder().where().eq("isOptional", true).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return list == null ? 0 : list.size();
	}
	
	public Optional queryOptionalsBySymbol(String symbol) {
		SystemContext.getInstance(context).openDB();
		List<Optional> list = null;
		try {
			Dao<Optional, Integer> dao = SystemContext
					.getInstance(context).getDatabaseHelper().getOptionalDao();
			list = dao.queryBuilder().where()
					.eq("treaty", symbol)
					.query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			SystemContext.getInstance(context).closeDB();
		}
		return list != null && !list.isEmpty() ? list.get(0) : null;
	}

}
