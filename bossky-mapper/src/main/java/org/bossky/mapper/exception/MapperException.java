package org.bossky.mapper.exception;

/**
 * 映射异常
 * 
 * @author daibo
 *
 */
public class MapperException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MapperException() {
		super();
	}

	public MapperException(String message) {
		super(message);
	}

	public MapperException(String message, Throwable cause) {
		super(message, cause);
	}
}
