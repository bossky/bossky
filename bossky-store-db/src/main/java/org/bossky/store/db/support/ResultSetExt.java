package org.bossky.store.db.support;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bossky.common.util.Misc;
import org.bossky.store.db.util.ConnectionPool;

/**
 * 扩展ResultSet,支持关闭ResultSet的同时释放资源
 * 
 * @author daibo
 *
 */
class ResultSetExt extends ResultSetWrap {
	protected ConnectionPool pool;
	protected Connection connection;
	protected Statement statement;

	protected ResultSetExt(ConnectionPool pool, Connection conn, Statement st,
			ResultSet rs) {
		super(rs);
		this.pool = pool;
		this.connection = conn;
		this.statement = st;
	}

	public static ResultSetExt valueOf(ConnectionPool pool, String sql)
			throws SQLException {
		Connection conn = pool.get();
		Statement st = null;
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			pool.free(conn);
			throw e;
		}
		ResultSet rs = null;
		try {
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			pool.free(conn);
			Misc.close(st);
			throw e;
		}
		return new ResultSetExt(pool, conn, st, rs);
	}

	@Override
	public void close() throws SQLException {
		super.close();
		pool.free(connection);
		Misc.close(statement);
	}

}
