package org.bossky.search.support;

import org.bossky.search.IndexKeyword;

/**
 * 索引关键字工具类
 * 
 * @author bo
 *
 */
public class IndexKeywords {
	/**
	 * 构造一个关键字
	 * 
	 * @param keyword
	 * @return
	 */
	public static IndexKeyword valueOf(String keyword) {
		return new SimpleIndexKeyword(keyword);
	}

	static class SimpleIndexKeyword implements IndexKeyword {

		protected String keword;

		public SimpleIndexKeyword(String keyword) {
			this.keword = keyword;
		}

		@Override
		public String getKeword() {
			return keword;
		}
	}
}
