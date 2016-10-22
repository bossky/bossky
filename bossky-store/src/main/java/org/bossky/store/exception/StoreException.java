package org.bossky.store.exception;

/**
 * 存储异常
 * 
 * @author bo
 *
 */
public class StoreException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StoreException() {
		super();
	}

	public StoreException(String message) {
		super(message);
	}

	public StoreException(String message, Throwable e) {
		super(message, e);
	}

}
