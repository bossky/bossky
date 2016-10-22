package org.bossky.store.db.support;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.bossky.common.util.Misc;
import org.bossky.store.db.util.ConnectionPool;

/**
 * sql语句执行器
 * 
 * @author daibo
 *
 */
public class DbExecuter {
	/** 链接池 */
	protected ConnectionPool pool;

	public DbExecuter(ConnectionPool pool) {
		this.pool = pool;
	}

	/**
	 * 执行查询,使用完ResultSet必须调用close方法
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		return ResultSetExt.valueOf(pool, sql);
	}

	/**
	 * 执行更新
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int executeUpdate(String sql) throws SQLException {
		Connection conn = pool.get();
		Statement st = null;
		try {
			st = conn.createStatement();
			return st.executeUpdate(sql);
		} finally {
			pool.free(conn);
			Misc.close(st);
		}
	}

	/**
	 * 执行批处理
	 * 
	 * @param sqls
	 * @return
	 * @throws SQLException
	 */
	public int[] executeBatch(List<String> sqls) throws SQLException {
		if (sqls.isEmpty()) {
			return new int[0];
		}
		if (sqls.size() == 1) {
			return new int[] { executeUpdate(sqls.get(0)) };
		}
		Connection conn = pool.get();
		Statement st = null;
		try {
			st = conn.createStatement();
			for (String sql : sqls) {
				st.addBatch(sql);
			}
			return st.executeBatch();
		} finally {
			pool.free(conn);
			Misc.close(st);
		}
	}

	/**
	 * 获取表结构
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public Table getTabel(String tableName) throws SQLException {
		ResultSet rs = null;
		Connection conn = pool.get();
		try {
			DatabaseMetaData dmd = conn.getMetaData();
			rs = dmd.getColumns(null, null, tableName, "%");
			return Table.valueOf(rs);
		} finally {
			pool.free(conn);
			Misc.close(rs);
		}
	}

	/**
	 * 测试链接
	 * 
	 * @throws SQLException
	 */
	public void test() throws SQLException {
		executeQuery("select 1=1");
	}

	public static void main(String[] args) {
		try {

			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String url = "jdbc:mysql://rdsz6nfqyz6nfqyo.mysql.rds.aliyuncs.com:3306";
		String username = "testdb";
		String password = "testdb";
		ConnectionPool pool = new ConnectionPool(url, username, password);
		DbExecuter executer = new DbExecuter(pool);
		try {
			executer.test();
			System.out.println("ok");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
