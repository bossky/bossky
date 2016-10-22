package org.bossky.store.db;

import org.bossky.store.db.support.DbExecuter;
import org.bossky.store.db.util.ConnectionPool;
import org.bossky.store.support.AbstractStoreHub;

/**
 * 基于数据库的存储集中器
 * 
 * @author bo
 *
 */
public abstract class DbStoreHub extends AbstractStoreHub {
	/** sql执行器 */
	protected DbExecuter executer;

	public DbStoreHub(ConnectionPool pool) {
		executer = new DbExecuter(pool);
	}

	public DbExecuter getExecuter() {
		return executer;
	}
}
