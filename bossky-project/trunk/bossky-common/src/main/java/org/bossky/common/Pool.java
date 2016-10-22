package org.bossky.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.bossky.common.util.Misc;

/**
 * 链接池
 * 
 * @author daibo
 *
 */
public abstract class Pool<E> implements Runnable {
	/** 池名称 */
	protected String name;
	/** 最小值 */
	protected int min;
	/** 最大值 */
	protected int max;
	/** 使用数 */
	protected AtomicInteger use = new AtomicInteger();
	/** 元素 */
	protected List<Element> elements;
	/** 线程 */
	protected Thread thread;
	/** 检查闲置的间隔 */
	protected long checkIdleInterval = DEFAULT_CHECK_IDLE_INTERVAL;
	/** 最大闲置时间 */
	protected long maxIdleTime = DEFAULT_MAX_IDLE_TIME;

	/** 默认检查闲置的间隔 */
	public static long DEFAULT_CHECK_IDLE_INTERVAL = 3 * 60 * 1000l;
	/** 默认最大闲置时间 */
	public static long DEFAULT_MAX_IDLE_TIME = 10 * 60 * 1000l;

	public Pool(int min, int max) {
		this(null, min, max);
	}

	public Pool(String name, int min, int max) {
		this.min = min;
		this.max = max;
		this.elements = new ArrayList<Element>((max - min) / 2);
		if (Misc.isEmpty(name)) {
			name = getClass().getSimpleName() + "-"
					+ System.currentTimeMillis();
		}
		this.name = name;
	}

	/**
	 * 初始化方法
	 * 
	 * @throws SQLException
	 */
	protected void init() {
		for (int i = 0; i < min; i++) {
			elements.add(create());
		}

	}

	/**
	 * 销毁
	 */
	protected void destory() {
		for (Element e : elements) {
			Misc.close(e.value);
		}
	}

	/**
	 * 累加使用数
	 * 
	 * @return
	 */
	protected int incUse() {
		return use.incrementAndGet();
	}

	/**
	 * 减少使用数
	 * 
	 * @return
	 */
	protected int decUse() {
		return use.decrementAndGet();
	}

	/**
	 * 启动线程
	 */
	public void start() {
		if (this.thread != null) {
			return;
		}
		Thread thread = new Thread(this, name);
		thread.setDaemon(true);
		thread.start();
		this.thread = thread;
	}

	/**
	 * 停止
	 */
	public void stop() {
		thread.interrupt();
		thread = null;
	}

	/**
	 * 获取链接
	 * 
	 * @return
	 */
	public E get() {
		if (use.intValue() > max) {
			throw new RuntimeException("资源池已满");
		}
		synchronized (elements) {
			for (Element e : elements) {
				if (!e.isBeUse()) {
					e.use();
					return e.value;
				}
			}
			return create().value;
		}
	}

	/**
	 * 释放链接
	 * 
	 * @param conn
	 */
	public void free(E v) {
		boolean isExist = false;
		for (Element e : elements) {
			if (e.value == v) {
				e.unUse();
				isExist = true;
			}
		}
		if (!isExist) {
			Misc.close(v);
		}
	}

	/**
	 * 创建链接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Element create() {
		E e = createValue();
		return new Element(e);
	}

	public abstract E createValue();

	/**
	 * 元素
	 * 
	 * @author daibo
	 *
	 */
	protected class Element {
		/** 链接 */
		E value;
		/** 是否被使用 */
		boolean beuse;
		/** 最后活动时间 */
		long lastActive;

		public Element(E value) {
			this.value = value;
			this.lastActive = System.currentTimeMillis();
		}

		/**
		 * 是否已经过时了
		 * 
		 * @return
		 */
		public boolean isOutTime() {
			return !isBeUse()
					&& (System.currentTimeMillis() - lastActive) > maxIdleTime;
		}

		/**
		 * 是否已被使用
		 * 
		 * @return
		 */
		public boolean isBeUse() {
			return beuse;
		}

		/**
		 * 使用
		 */
		public void use() {
			beuse = true;
			lastActive = System.currentTimeMillis();
			incUse();
		}

		/**
		 * 用完了
		 */
		public void unUse() {
			beuse = false;
			decUse();
		}

	}

	@Override
	public void run() {
		init();
		while (true) {
			if (elements.size() > min) {
				List<Element> news = new ArrayList<Element>();
				for (Element e : elements) {
					if (!e.isOutTime()) {
						news.add(e);
					}
				}
				if (news.size() != elements.size()) {
					elements = news;
				}
			}
			synchronized (this) {
				try {
					wait(checkIdleInterval);
				} catch (InterruptedException e) {
					break;
				}
			}
		}
		destory();
	}
}
