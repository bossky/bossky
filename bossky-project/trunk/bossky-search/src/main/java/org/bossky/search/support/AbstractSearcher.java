package org.bossky.search.support;

import java.util.Arrays;
import java.util.List;

import org.bossky.common.support.TranformList;
import org.bossky.common.util.AssertUtil;
import org.bossky.common.util.Misc;
import org.bossky.search.IndexEntry;
import org.bossky.search.IndexKeyword;
import org.bossky.search.IndexResult;
import org.bossky.search.IndexResults;
import org.bossky.search.QueryKeyword;
import org.bossky.search.Searcher;

/**
 * 抽象的搜索器
 * 
 * @author bo
 *
 */
public abstract class AbstractSearcher implements Searcher {
	/** 搜索器名称 */
	protected String name;

	public AbstractSearcher(String name) {
		this.name = name;
	}

	/**
	 * 获取搜索器名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	@Override
	public IndexResult get(String... keywords) {
		IndexResults irs = search(keywords);
		if (irs.gotoPage(1)) {
			for (IndexResult ir : irs) {
				return ir;// 拿第一个就行了
			}
		}
		return null;
	}

	@Override
	public IndexResults search(String... kewords) {
		return search(OPTION_NONE, kewords);
	}

	@Override
	public IndexResults search(long options, String... kewords) {
		return search(options, new TranformList<QueryKeyword, String>(Arrays.asList(kewords)) {

			@Override
			public QueryKeyword tranform(String v) {
				return QueryKeywords.valueOfKeyword(v);
			}
		});
	}

	@Override
	public IndexResults search(long options, List<QueryKeyword> keywords) {
		return doSearch(options, null, null, keywords);
	}

	@Override
	public void updateEntry(IndexEntry entry, List<IndexKeyword> kewords) {
		AssertUtil.assertNotNull(entry, "条目不能是空");
		if (Misc.isEmpty(kewords)) {
			doRemoveEntry(entry);// 关键字为空则删除
		} else {
			doUpdateEntry(entry, kewords);
		}
	}

	@Override
	public void removeEntry(IndexEntry entry) {
		AssertUtil.assertNotNull(entry, "条目不能是空");
		doRemoveEntry(entry);
	}

	@Override
	public IndexResults searchRange(String begin, String end) {
		return searchRange(OPTION_NONE, begin, end);
	}

	@Override
	public IndexResults searchRange(long options, String begin, String end) {
		return searchRange(options, begin, end, null);
	}

	@Override
	public IndexResults searchRange(long options, String begin, String end, List<QueryKeyword> keywords) {
		return doSearch(options, begin, end, keywords);
	}

	/**
	 * 是否有指定选项
	 * 
	 * @param options
	 *            选项值
	 * @param option
	 *            要判断的选项
	 * @return
	 */
	protected static boolean isOption(long options, long option) {
		return (options & option) == option;
	}

	/**
	 * 执行更新方法
	 * 
	 * @param entry
	 * @param kewords
	 */
	protected abstract void doUpdateEntry(IndexEntry entry, List<IndexKeyword> kewords);

	/**
	 * 执行移除方法
	 * 
	 * @param entry
	 */
	protected abstract void doRemoveEntry(IndexEntry entry);

	/**
	 * 执行搜索的方法
	 * 
	 * @param option
	 * @param begin
	 * @paran end
	 * @param keywords
	 */
	protected abstract IndexResults doSearch(long options, String begin, String end, List<QueryKeyword> keywords);

}
