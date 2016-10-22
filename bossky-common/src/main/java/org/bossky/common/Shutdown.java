package org.bossky.common;

import java.io.Closeable;

import org.bossky.common.util.IoUtil;

/**
 * 关闭的工具类,主要用来注册一些关机前要执行完的线程
 * 
 * @author bo
 *
 */
public class Shutdown {
	/**
	 * 注册关机线程
	 * 
	 * @param run
	 */
	public static void register(Runnable run) {
		// 目前只是简单的注册一个线程过去
		Thread shutdownHook = new Thread(run);
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	}

	/**
	 * 注册关机前要关闭的对象
	 * 
	 * @param close
	 */
	public static void register(Closeable close) {
		// 目前只是简单的注册一个线程过去
		Thread shutdownHook = new Thread(new CloseableRun(close));
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	}

	/**
	 * 注册关机前要关闭的对象
	 * 
	 * @param close
	 */
	public static void register(AutoCloseable close) {
		// 目前只是简单的注册一个线程过去
		Thread shutdownHook = new Thread(new AutoCloseableRun(close));
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	}

	static class AutoCloseableRun implements Runnable {
		AutoCloseable close;

		public AutoCloseableRun(AutoCloseable close) {
			this.close = close;
		}

		@Override
		public void run() {
			IoUtil.closeIo(close);
		}

	}

	static class CloseableRun implements Runnable {
		Closeable close;

		public CloseableRun(Closeable close) {
			this.close = close;
		}

		@Override
		public void run() {
			IoUtil.closeIo(close);
		}

	}
}
