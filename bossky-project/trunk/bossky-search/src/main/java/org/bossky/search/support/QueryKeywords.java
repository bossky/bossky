package org.bossky.search.support;

import org.bossky.search.QueryKeyword;

/**
 * 查询关键字
 * 
 * @author bo
 *
 */
public class QueryKeywords {
	/**
	 * 构造关键字
	 * 
	 * @param keyword
	 * @return
	 */
	public static QueryKeyword valueOfKeyword(String keyword) {
		return new SimpleQueryKeyword(keyword, QueryKeyword.TYPE_KEYWORD_ALL);
	}
	/**
	 * 构造条目前缀
	 * @param prefix
	 * @return
	 */
	public static QueryKeyword valueOfEntryPrefix(String prefix) {
		return new SimpleQueryKeyword(prefix, QueryKeyword.TYPE_ENTRY_PREFIX);
	}

	static class SimpleQueryKeyword implements QueryKeyword {

		protected String keyword;
		protected int type;

		public SimpleQueryKeyword(String keyword, int type) {
			this.keyword = keyword;
			this.type = type;
		}

		@Override
		public int getType() {
			return type;
		}

		@Override
		public String getValue() {
			return keyword;
		}

	}
}
