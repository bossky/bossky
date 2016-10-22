package org.bossky.store.mysql;

import org.bossky.store.Store;
import org.bossky.store.Storeble;
import org.bossky.store.db.DbStoreHub;
import org.bossky.store.db.util.ConnectionPool;

/**
 * 基于mysql的存储集中器
 * 
 * @author bo
 *
 */
public class MysqlStoreHub extends DbStoreHub {

	public MysqlStoreHub(String url) {
		this(url, null, null);
	}

	public MysqlStoreHub(String url, String username, String password) {
		this(new ConnectionPool(url, username, password));
	}

	public MysqlStoreHub(ConnectionPool pool) {
		super(pool);
	}

	@Override
	protected <T extends Storeble> Store<T> createStore(Class<T> clazz, Object[] initargs) {
		return new MysqlStore<T>(this, clazz, initargs);
	}

}
