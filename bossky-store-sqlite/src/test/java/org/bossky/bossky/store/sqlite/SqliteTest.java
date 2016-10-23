package org.bossky.bossky.store.sqlite;

import org.bossky.store.db.util.ConnectionPool;
import org.bossky.store.sqlite.SqliteStoreHub;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class SqliteTest extends TestCase {

	/**
	 * Rigourous Test :-)
	 */
	public void testSqlite() {
		try {
			ConnectionPool pool = new ConnectionPool("jdbc:sqlite:sample.db", null, null);
			SqliteStoreHub hub = new SqliteStoreHub(pool);
			hub.setServerId(255);
			AssistantImpl a = new AssistantImpl(hub);
			// Store<ArrayTest> store ;
			// hub.openStore(ArrayTest.class, a);
			// ArrayTest test = new ArrayTest(a);
			// Store<ListTest> store =
			// hub.openStore(ListTest.class, a);
			// ListTest test = new ListTest(a);
			// SetTest test = hub.openStore(SetTest.class,
			// a).get("SetTest$157e2b04a1d-ff");
			// hub.openStore(SetTest.class, a);
			// SetTest test = new SetTest(a);

			// MapTest test = hub.openStore(MapTest.class,
			// a).get("MapTest$157e2bbbb81-ff");
			// MapTest test = new MapTest(a);
			hub.openStore(ObjectTest.class, a);
			ObjectTest test = new ObjectTest(a);
			test.init();
			test.flush();
			test.flush();
			System.out.println(test.getStoreId());
			System.out.println(test.internal);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
