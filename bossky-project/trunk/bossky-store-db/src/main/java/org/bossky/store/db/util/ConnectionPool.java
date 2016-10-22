package org.bossky.store.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bossky.common.Pool;
import org.bossky.common.util.Misc;

/**
 * 链接池
 * 
 * @author daibo
 *
 */
public class ConnectionPool extends Pool<Connection> {
	/** 数据库链接 */
	protected String url;
	/** 数据库用户名 */
	protected String username;
	/** 数据库密码 */
	protected String password;

	public ConnectionPool(String url, String username, String password) {
		this(url, username, password, 10, 100);
	}

	public ConnectionPool(String url, String username, String password,
			int min, int max) {
		super(min, max);
		this.url = url;
		this.username = username;
		this.password = password;
		start();
	}

	@Override
	public Connection createValue() {
		Connection conn;
		try {
			if (Misc.isEmpty(username)) {
				conn = DriverManager.getConnection(url);
			} else {
				conn = DriverManager.getConnection(url, username, password);
			}
		} catch (SQLException e) {
			throw new RuntimeException("链接" + url + "失败", e);
		}
		return conn;
	}
}
