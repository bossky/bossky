package org.bossky.search.exception;

/**
 * 搜索异常
 * 
 * @author bo
 *
 */
public class SearchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SearchException() {
		super();
	}

	public SearchException(String message) {
		super(message);
	}

	public SearchException(String message, Throwable e) {
		super(message, e);
	}
}
