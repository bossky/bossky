package org.bossky.search.lucene;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.store.AlreadyClosedException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.IOUtils;
import org.bossky.search.IndexEntry;
import org.bossky.search.IndexKeyword;
import org.bossky.search.IndexResults;
import org.bossky.search.QueryKeyword;
import org.bossky.search.exception.SearchException;
import org.bossky.search.support.AbstractSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于lucene的搜索器
 * 
 * @author bo
 *
 */
public class LuceneSearcher extends AbstractSearcher {
	/** id属性名 */
	protected static String ID_FIELD_NAME = "id";
	/** 评分属性名 */
	protected static String SCORE_FIELD_NAME = "s";
	/** 关键词属性名 */
	protected static String KEYWORD_FIELD_NAME = "ks";
	/** 关键词分隔符 */
	protected static char KEYWORD_SPLIT = ' ';
	/** 关键词分词器 */
	protected static Analyzer KEYWORD_ANALYZER = new WhitespaceAnalyzer();
	/** 存储目录 */
	protected String storeDir;
	/** 索引搜索器 */
	protected IndexSearcher searcher;
	/** 索引搜索器锁 */
	protected Object searcherlock = new Object();
	/** 日志 */
	final static Logger _Logger = LoggerFactory.getLogger(LuceneSearcher.class);

	public LuceneSearcher(String storeDir, String name) {
		super(name);
		this.storeDir = storeDir;
		init();
	}

	protected void init() {
		Path path = getPath();
		IndexWriter iw = null;
		try {
			iw = new IndexWriter(FSDirectory.open(path), new IndexWriterConfig(KEYWORD_ANALYZER));
			iw.commit();
		} catch (IOException e) {
			_Logger.warn("初始化" + path + "出错", e);
		} finally {
			try {
				IOUtils.close(iw);
			} catch (IOException e) {
				// 忽略
			}
		}
	}

	@Override
	public void doUpdateEntry(IndexEntry entry, List<IndexKeyword> kewords) {
		String id = entry.getKey();
		long score = entry.getScore();
		String keyword = createKeyword(kewords);
		Document doc = new Document();
		doc.add(new TextField(ID_FIELD_NAME, id, Field.Store.YES));
		doc.add(new LongField(SCORE_FIELD_NAME, score, Field.Store.YES));
		// lucene 5.1起要使用DocValuesFiled才能排序
		doc.add(new NumericDocValuesField(SCORE_FIELD_NAME, score));
		doc.add(new TextField(KEYWORD_FIELD_NAME, keyword, Field.Store.YES));
		Path path = getPath();
		IndexWriter iw = null;
		try {
			iw = new IndexWriter(FSDirectory.open(path), new IndexWriterConfig(KEYWORD_ANALYZER));
			iw.updateDocument(new Term(ID_FIELD_NAME, id), doc);
			destoryIndexSeacher();// // 清除缓存
		} catch (IOException e) {
			throw new SearchException("写入索引失败", e);
		} finally {
			try {
				IOUtils.close(iw);
			} catch (IOException e) {
				// 忽略
			}
		}
	}

	@Override
	protected void doRemoveEntry(IndexEntry entry) {
		String id = entry.getKey();
		Path path = getPath();
		Directory dir = null;
		IndexWriter iw = null;
		try {
			dir = FSDirectory.open(path);
			iw = new IndexWriter(dir, new IndexWriterConfig(KEYWORD_ANALYZER));
			iw.deleteDocuments(new Term(ID_FIELD_NAME, id));
			destoryIndexSeacher();// 清除缓存
		} catch (IOException e) {
			throw new SearchException("写入索引失败", e);
		} finally {
			try {
				IOUtils.close(iw);
			} catch (IOException e) {
				// 忽略
			} catch (AlreadyClosedException e) {

			}
			try {
				IOUtils.close(dir);
			} catch (IOException e) {
				// 忽略
			} catch (AlreadyClosedException e) {

			}
		}
	}

	@Override
	public IndexResults doSearch(long options, String begin, String end, List<QueryKeyword> keywords) {
		// 创建query
		Query query;
		Sort sort;
		BooleanQuery.Builder builder = new BooleanQuery.Builder();
		if (null != begin || null != end) {
			builder.add(TermRangeQuery.newStringRange(KEYWORD_FIELD_NAME, begin, end, true, false), Occur.MUST);
		}
		for (QueryKeyword qk : keywords) {
			Query ksquery = null;
			if (qk.getType() == QueryKeyword.TYPE_ENTRY_PREFIX) {
				ksquery = new PrefixQuery(new Term(ID_FIELD_NAME, qk.getValue()));
			} else if (qk.getType() == QueryKeyword.TYPE_KEYWORD_ALL) {
				ksquery = new TermQuery(new Term(KEYWORD_FIELD_NAME, qk.getValue()));
			} else {
				throw new IllegalArgumentException("未识别的状态" + qk.getType());
			}
			builder.add(ksquery, Occur.MUST);
		}
		query = builder.build();
		if (isOption(options, OPTION_SORT_BY_SCORE_ASC)) {
			SortField sortField = new SortField(SCORE_FIELD_NAME, SortField.Type.LONG, false);
			sort = new Sort(SortField.FIELD_SCORE, sortField);
		} else if (isOption(options, OPTION_SORT_BY_SCORE_DESC)) {
			SortField sortField = new SortField(SCORE_FIELD_NAME, SortField.Type.LONG, true);
			sort = new Sort(SortField.FIELD_SCORE, sortField);
		} else {
			sort = null;
		}
		return new LucenceIndexResults(this, query, sort);
	}

	/**
	 * 索引文件路径
	 * 
	 * @return
	 */
	protected Path getPath() {
		if (null == storeDir) {
			return Paths.get(getName());
		} else {
			return Paths.get(storeDir + getName());
		}

	}

	/**
	 * 创建关键字
	 * 
	 * @param keywords
	 * @return
	 */
	private static String createKeyword(List<IndexKeyword> keywords) {
		if (keywords.size() == 1) {
			return keywords.get(0).getKeword();
		} else {
			StringBuilder sb = new StringBuilder();
			int i = 0;
			String k = keywords.get(i++).getKeword();
			sb.append(k);
			for (; i < keywords.size(); i++) {
				sb.append(KEYWORD_SPLIT);
				sb.append(keywords.get(i).getKeword());
			}
			return sb.toString();
		}

	}

	/**
	 * 获取搜索器
	 * 
	 * @return
	 */
	protected IndexSearcher getIndexSearcher() {
		if (null != searcher) {
			return searcher;
		}
		IndexReader reader = null;
		boolean isSuccess = false;
		try {
			reader = DirectoryReader.open(FSDirectory.open(getPath()));
			isSuccess = true;
		} catch (IOException e) {
			throw new SearchException("获取搜索器失败", e);
		} finally {
			if (!isSuccess) {// 没成功...
				try {
					IOUtils.close(reader);
				} catch (IOException e) {
					// 忽略错误
				}
			}
		}
		IndexSearcher newSearcher = new IndexSearcher(reader);
		synchronized (searcherlock) {
			if (null != searcher) {
				return searcher;
			}
			searcher = newSearcher;
		}
		return searcher;
	}

	/**
	 * 销毁索引搜索器
	 */
	protected void destoryIndexSeacher() {
		if (null != searcher) {
			try {
				IOUtils.close(searcher.getIndexReader());
			} catch (IOException e) {
				// 忽略错误
			}
			searcher = null;
		}
	}
}
