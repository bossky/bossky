package org.bossky.search.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.bossky.common.support.AbstractResultPage;
import org.bossky.common.util.Misc;
import org.bossky.search.IndexResult;
import org.bossky.search.IndexResults;
import org.bossky.search.exception.SearchException;

/**
 * lucence搜索的结果集合
 * 
 * @author bo
 *
 */
public class LucenceIndexResults extends AbstractResultPage<IndexResult> implements IndexResults {
	/** 搜索器 */
	protected LuceneSearcher searcher;
	/** 查询条件 */
	protected Query query;
	/** 排序 */
	protected Sort sort;
	/** 总数 */
	protected int count = -1;
	/** 当前结果集 */
	protected List<IndexResult> currentResult = Collections.emptyList();
	/** 当前结果位置 */
	protected int index = 0;
	/** 每页的最后一个文档id和评分 */
	protected Map<Integer, ScoreDoc> lastDocIdfromPage = new HashMap<Integer, ScoreDoc>();

	public LucenceIndexResults(LuceneSearcher searcher, Query query, Sort sort) {
		this.searcher = searcher;
		this.query = query;
		this.sort = sort;
	}

	/**
	 * 获取结果数
	 * 
	 * @return
	 */
	protected int doGetCount() {
		// 搜索
		try {
			IndexSearcher is = searcher.getIndexSearcher();
			return is.count(query);
		} catch (IOException e) {
			throw new SearchException("读取索引失败", e);
		}
	}

	/**
	 * 获取第几页的结果
	 * 
	 * @param page
	 * @return
	 */
	protected List<IndexResult> doGetResult(int page) {
		// 在这个之后
		ScoreDoc after = null;
		// 前面有多少个元素
		int preNum = (page - 1) * getPageSize();
		if (page > 0) {
			// 上一页的最后一个文档id
			ScoreDoc last = lastDocIdfromPage.get(page - 1);
			if (null != last) {
				after = last;
				preNum = 0;
			}
		}
		try {
			IndexSearcher is = searcher.getIndexSearcher();
			TopDocs topDocs;
			if (null == sort) {
				topDocs = is.searchAfter(after, query, preNum + getPageSize());
			} else {
				topDocs = is.searchAfter(after, query, preNum + getPageSize(), sort);
			}
			ScoreDoc[] hits = topDocs.scoreDocs;
			if (null == hits || hits.length == 0) {
				return Collections.emptyList();
			}
			List<IndexResult> list = new ArrayList<IndexResult>(hits.length);
			for (int i = preNum; i < hits.length; i++) {
				Document doc = is.doc(hits[i].doc);
				if (null != doc) {
					String key = doc.get(LuceneSearcher.ID_FIELD_NAME);
					long score = Misc.toLong(doc.get(LuceneSearcher.SCORE_FIELD_NAME), 0);
					list.add(new SimpleIndexResult(key, score));
				}
			}
			// 保存当页的最后一个
			lastDocIdfromPage.put(page, hits[hits.length - 1]);
			return list;
		} catch (IOException e) {
			throw new SearchException("读取索引失败", e);
		}
	}

	@Override
	public void setPageSize(int size) {
		super.setPageSize(size);
		// 有可能雪崩了
		lastDocIdfromPage = new HashMap<Integer, ScoreDoc>();
		gotoPage(getPage());
	}

	@Override
	public boolean gotoPage(int page) {
		if (page <= 0) {
			return false;
		}
		if (page == getPage()) {
			return true;// 就是当前页啦
		}
		if (page > getPageSum()) {
			return false;// 超过总页数了
		}
		currentResult = doGetResult(page);
		return currentResult.size() > 0;
	}

	@Override
	public int getCount() {
		if (count == -1) {
			this.count = doGetCount();
		}
		return count;
	}

	@Override
	public IndexResult next() {
		return currentResult.get(index++);
	}

	@Override
	public boolean hasNext() {
		return index < currentResult.size();
	}

	@Override
	public Iterator<IndexResult> iterator() {
		return currentResult.iterator();
	}

	static class SimpleIndexResult implements IndexResult {

		protected String key;
		protected long score;

		public SimpleIndexResult(String key, long score) {
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
