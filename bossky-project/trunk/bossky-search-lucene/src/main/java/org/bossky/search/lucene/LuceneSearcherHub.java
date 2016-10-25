package org.bossky.search.lucene;

import org.bossky.search.Searcher;
import org.bossky.search.support.AbstractSearcherHub;

/**
 * 基于lucene的搜索器集合
 * 
 * @author bo
 *
 */
public class LuceneSearcherHub extends AbstractSearcherHub {
	/** 存储目录 */
	protected String storeDir;

	public LuceneSearcherHub(String storeDir) {
		this.storeDir = storeDir;
	}

	@Override
	protected Searcher createSearcher(String name) {
		return new LuceneSearcher(storeDir, name);
	}

}
