package org.bossky.store.mysql;

import org.bossky.store.Assistant;
import org.bossky.store.Store;
import org.bossky.store.StoreHub;
import org.bossky.store.db.util.ConnectionPool;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class MysqlTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public MysqlTest(String testName) {
		super(testName);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		try {
			ConnectionPool pool = new ConnectionPool("jdbc:mysql://rdsz6nfqyz6nfqyo.mysql.rds.aliyuncs.com:3306/testdb",
					"testdb", "testdb");
			final MysqlStoreHub hub = new MysqlStoreHub(pool);
			Assistant a = new Assistant() {

				@Override
				public StoreHub getStoreHub() {
					return hub;
				}
			};
			hub.setServerId(255);
			Store<BaseTest> store = hub.openStore(BaseTest.class, a);
			BaseTest test = new BaseTest(a);
			test.init();
			test.flush();
			BaseTest newTest = store.get(test.getStoreId());
			System.out.println(newTest);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
