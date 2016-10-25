package org.bossky.search.lucene;

import java.util.Arrays;

import junit.framework.TestCase;

import org.bossky.search.IndexResult;
import org.bossky.search.IndexResults;
import org.bossky.search.Searcher;
import org.bossky.search.support.IndexEntrys;
import org.bossky.search.support.IndexKeywords;

/**
 * Unit test for simple App.
 */
public class LuceneTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public LuceneTest(String testName) {
		super(testName);
	}

	public void test() {
		LuceneSearcherHub ss = new LuceneSearcherHub("data/");
		Searcher searcher = ss.openSearcher("user");
		searcher.updateEntry(
				IndexEntrys.valueOf("user1"),
				Arrays.asList(IndexKeywords.valueOf("bossky"),
						IndexKeywords.valueOf("daibo1")));
		IndexResults irs = searcher.search(Searcher.OPTION_SORT_BY_SCORE_DESC,
				"bossky");
		for (int i = 1; irs.gotoPage(i); i++) {
			for (IndexResult ir : irs) {
				System.out.println(ir.getKey() + "," + ir.getScore());
			}
		}
		irs = searcher.search(Searcher.OPTION_SORT_BY_SCORE_DESC, "bossky");
		searcher.updateEntry(IndexEntrys.valueOf("user2"),
				Arrays.asList(IndexKeywords.valueOf("bossky")));
		for (int i = 1; irs.gotoPage(i); i++) {
			for (IndexResult ir : irs) {
				System.out.println(ir.getKey() + "," + ir.getScore());
			}
		}
	}
}
