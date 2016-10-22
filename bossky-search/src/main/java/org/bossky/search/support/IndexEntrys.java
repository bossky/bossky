package org.bossky.search.support;

import org.bossky.search.IndexEntry;

/**
 * 索引条目工作类
 * 
 * @author bo
 *
 */
public class IndexEntrys {
	/**
	 * 构造一个索引条目
	 * 
	 * @param key
	 * @return
	 */
	public static IndexEntry valueOf(String key) {
		return valueOf(key, 0);
	}

	/**
	 * 构造一个索引条目
	 * 
	 * @param key
	 * @param score
	 * @return
	 */
	public static IndexEntry valueOf(String key, long score) {
		return new SimpleIndexEntry(key, score);
	}

	static class SimpleIndexEntry implements IndexEntry {

		protected String key;
		protected long score;

		public SimpleIndexEntry(String key, long score) {
			this.key = key;
			this.score = score;
		}

		@Override
		public String getKey() {
			return key;
		}

		@Override
		public long getScore() {
			return score;
		}

	}
}
