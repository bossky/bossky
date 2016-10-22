package org.bossky.store.sqlite;

import org.bossky.store.Store;
import org.bossky.store.Storeble;
import org.bossky.store.db.DbStoreHub;
import org.bossky.store.db.util.ConnectionPool;

/**
 * 基于sqlite的存储集中器
 * 
 * @author bo
 *
 */
public class SqliteStoreHub extends DbStoreHub {

	public SqliteStoreHub(String url) {
		this(url, null, null);
	}

	public SqliteStoreHub(String url, String username, String password) {
		this(new ConnectionPool(url, username, password));
	}

	public SqliteStoreHub(ConnectionPool pool) {
		super(pool);
	}

	@Override
	protected <T extends Storeble> Store<T> createStore(Class<T> clazz,
			Object[] initargs) {
		return new SqliteStore<T>(this, clazz, initargs);
	}

}
