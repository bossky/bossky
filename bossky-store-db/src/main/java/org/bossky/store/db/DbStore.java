package org.bossky.store.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bossky.common.Pair;
import org.bossky.common.ResultPage;
import org.bossky.common.util.Misc;
import org.bossky.mapper.Mapper;
import org.bossky.mapper.Meta;
import org.bossky.mapper.MetaType;
import org.bossky.mapper.annotation.AnnotationMappers;
import org.bossky.store.StoreId;
import org.bossky.store.Storeble;
import org.bossky.store.db.support.DbExecuter;
import org.bossky.store.db.support.Table;
import org.bossky.store.exception.StoreException;
import org.bossky.store.support.AbstractStore;

/**
 * 基于数据库的存储器
 * 
 * @author bo
 *
 */
public abstract class DbStore<T extends Storeble> extends AbstractStore<T> {
	/** 映射器 */
	protected Mapper<T> mapper;
	/** 集中器 */
	protected DbStoreHub hub;
	/** 存储Id属性名 */
	protected static String __STORE_ID = "__store_id";
	/** 存储Id描述属性名 */
	protected static String __STORE_ID_CAPTION = "__store_id_caption";

	protected DbStore(DbStoreHub hub, Class<T> clazz, Object... initargs) {
		this.hub = hub;
		mapper = AnnotationMappers.valueOf(clazz, initargs);
		init();
	}

	/** 表名称 */
	protected String tableName() {
		return mapper.getName().toLowerCase();
	}

	/**
	 * 初始化方法
	 */
	protected void init() {
		createOrUpdateTable();
	}

	/**
	 * 创建或更新表
	 */
	protected void createOrUpdateTable() {
		Table table = null;
		try {
			table = hub.getExecuter().getTabel(tableName());
		} catch (SQLException e) {
			throw new StoreException("获取表结构异常", e);
		}
		if (null == table) {// 未创建的表
			createTable();
		} else {
			updateTable(table);
		}
	}

	/**
	 * 创建表
	 */
	protected void createTable() {
		DbExecuter executer = hub.getExecuter();
		String sql = createTabelSql();
		try {
			executer.executeUpdate(sql);
		} catch (SQLException e) {
			throw new StoreException("执行" + sql + "语句异常", e);
		}
	}

	/**
	 * 更新表
	 */
	protected void updateTable(Table table) {
		DbExecuter executer = hub.getExecuter();
		String sql = updateTabelSql(table);
		if (null == sql) {
			return;
		}
		try {
			executer.executeUpdate(sql);
		} catch (SQLException e) {
			throw new StoreException("执行" + sql + "语句异常", e);
		}
	}

	@Override
	protected T doGet(String id) {
		String sql = selectSql(id);
		DbExecuter executer = hub.getExecuter();
		ResultSet rs = null;
		try {
			rs = executer.executeQuery(sql);
			if (rs.next()) {
				T obj = fromSqlValue(rs);
				String idValue = rs.getString(__STORE_ID);
				String captionValue = rs.getString(__STORE_ID_CAPTION);
				StoreId sid = new StoreId(obj.getClass(), idValue, captionValue);
				obj.init(sid, this);
				return obj;
			}
		} catch (SQLException e) {
			throw new StoreException("执行" + sql + "语句异常", e);
		} finally {
			Misc.close(rs);
		}
		return null;
	}

	@Override
	protected void doSave(T storeble) {
		T old = get(storeble.getStoreId());
		if (null == old) {
			String sql = insertSql(storeble);
			DbExecuter executer = hub.getExecuter();
			try {
				executer.executeUpdate(sql);
			} catch (SQLException e) {
				throw new StoreException("执行" + sql + "语句异常", e);
			}
		} else {
			String sql = updateSql(storeble, old);
			if (null == sql) {
				return;// 没有改变
			}
			DbExecuter executer = hub.getExecuter();
			try {
				executer.executeUpdate(sql);
			} catch (SQLException e) {
				throw new StoreException("执行" + sql + "语句异常", e);
			}
		}
	}

	@Override
	protected T doRemove(String id) {
		T obj = get(id);
		if (obj == null) {
			return null;
		}
		String sql = deleteSql(id);
		DbExecuter executer = hub.getExecuter();
		try {
			executer.executeUpdate(sql);
		} catch (SQLException e) {
			throw new StoreException("执行" + sql + "语句异常", e);
		}
		return obj;
	}

	@Override
	protected ResultPage<T> doStartWith(String prefix) {
		if (null == prefix) {
			return search(null, null);
		}
		return search(prefix, prefix + Character.MAX_CODE_POINT);
	}

	/**
	 * 创建表语句
	 * 
	 * @return
	 */
	protected String createTabelSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE  IF NOT EXISTS `");
		sb.append(tableName());
		sb.append("`(");
		for (Meta m : mapper.getMetas()) {
			sb.append(m.getName());
			sb.append("   ");
			sb.append(toSqlType(m.getType()));
			sb.append(",");
		}
		sb.append(__STORE_ID_CAPTION);
		sb.append("  ");
		sb.append(toSqlType(MetaType.STRING));
		sb.append(",");
		sb.append(__STORE_ID);
		sb.append(" ");
		sb.append(toSqlType(MetaType.STRING));
		sb.append("  PRIMARY KEY     NOT NULL");
		sb.append(");");
		return sb.toString();
	}

	/**
	 * 更新表的sql
	 * 
	 * @param table
	 * @return 返回null表示不修改更新
	 */
	protected String updateTabelSql(Table table) {
		List<Meta> newMetas = new ArrayList<Meta>();
		for (Meta m : mapper.getMetas()) {
			boolean isExist = false;
			for (Table.Column c : table.getColumns()) {
				if (Misc.eq(m.getName(), c.getName())) {
					String sqlType = toSqlType(m.getType());
					if (!Misc.eq(sqlType, c.getType())) {
						throw new IllegalArgumentException("不允许改变已有属性的类型");
					}
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				newMetas.add(m);// 新加的
			}
		}
		if (newMetas.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (Meta m : newMetas) {
			sb.append("ALTER TABLE `");
			sb.append(tableName());
			sb.append("` ADD COLUMN ");
			sb.append(m.getName());
			sb.append("   ");
			sb.append(toSqlType(m.getType()));
			sb.append(";");
		}
		return sb.toString();
	}

	/**
	 * 插入语句
	 * 
	 * @param storeble
	 * @return
	 */
	protected String insertSql(T storeble) {
		StoreId id = storeble.getStoreId();
		if (null == id || Misc.isEmpty(id.getId())) {
			throw new NullPointerException("id不能为空");
		}
		List<Meta> metas = mapper.getMetas();
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO `");
		sb.append(tableName());
		sb.append("`(");
		for (Meta m : metas) {
			sb.append("`");
			sb.append(m.getName());
			sb.append("`");
			sb.append(",");
		}
		sb.append("`");
		sb.append(__STORE_ID_CAPTION);
		sb.append("`,`");
		sb.append(__STORE_ID);
		sb.append("`)");
		sb.append(" VALUES ( ");
		for (Meta m : metas) {
			sb.append(toSqlValue(m.getType(), m.getValue(storeble)));
			sb.append(",");
		}
		sb.append(toSqlValue(MetaType.STRING, id.getCaption()));
		sb.append(",");
		sb.append(toSqlValue(MetaType.STRING, id.getId()));
		sb.append(");");
		return sb.toString();
	}

	/**
	 * 更新语句
	 * 
	 * @param storeble
	 * @return
	 */
	protected String updateSql(T storeble, T old) {
		StoreId id = storeble.getStoreId();
		if (null == id) {
			throw new NullPointerException("id不能为空");
		}
		// 需要更新的属性
		List<Pair<String, Object>> dirtys = new ArrayList<Pair<String, Object>>();
		for (Meta m : mapper.getMetas()) {
			Object newValue = toSqlValue(m.getType(), m.getValue(storeble));
			Object oldValue = toSqlValue(m.getType(), m.getValue(old));
			if (!Misc.eq(newValue, oldValue)) {
				dirtys.add(new Pair<String, Object>(m.getName(), newValue));
			}
		}
		if (dirtys.isEmpty()
				&& Misc.eq(id.getCaption(), old.getStoreId().getCaption())) {
			return null;// 没有更新
		}
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE `");
		sb.append(tableName());
		sb.append("`  set ");
		for (Pair<String, Object> m : dirtys) {
			sb.append("`");
			sb.append(m.getKey());
			sb.append("`");
			sb.append("=");
			sb.append(m.getValue());
			sb.append(",");
		}
		sb.append("`");
		sb.append(__STORE_ID_CAPTION);
		sb.append("`=");
		sb.append(toSqlValue(MetaType.STRING, id.getCaption()));
		sb.append(" WHERE `");
		sb.append(__STORE_ID);
		sb.append("`=");
		sb.append(toSqlValue(MetaType.STRING, id.getId()));
		sb.append(" ;");
		return sb.toString();
	}

	/**
	 * 选择语句
	 * 
	 * @param id
	 * @return
	 */
	protected String selectSql(String id) {
		if (null == id) {
			throw new NullPointerException("id不能为空");
		}
		List<Meta> metas = mapper.getMetas();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		for (Meta m : metas) {
			sb.append("`");
			sb.append(m.getName());
			sb.append("`");
			sb.append(",");
		}
		sb.append("`");
		sb.append(__STORE_ID_CAPTION);
		sb.append("`,`");
		sb.append(__STORE_ID);
		sb.append("` FROM `");
		sb.append(tableName());
		sb.append("` WHERE `");
		sb.append(__STORE_ID);
		sb.append("`=");
		sb.append(toSqlValue(MetaType.STRING, id));
		sb.append(";");
		return sb.toString();
	}

	/**
	 * 删除语句
	 * 
	 * @param id
	 * @return
	 */
	protected String deleteSql(String id) {
		if (null == id) {
			throw new NullPointerException("id不能为空");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE ");
		sb.append(" FROM `");
		sb.append(tableName());
		sb.append("` WHERE `");
		sb.append(__STORE_ID);
		sb.append("`=");
		sb.append(toSqlValue(MetaType.STRING, id));
		sb.append(";");
		return sb.toString();
	}

	/**
	 * 将MetaType类型转换成sql类型
	 * 
	 * @param type
	 * @return
	 */
	protected abstract String toSqlType(MetaType type);

	/**
	 * 将对象值转换成sql值
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	protected abstract Object toSqlValue(MetaType type, Object value);

	/**
	 * 从数据结果集中获取对象
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	protected abstract T fromSqlValue(ResultSet rs) throws SQLException;

}
