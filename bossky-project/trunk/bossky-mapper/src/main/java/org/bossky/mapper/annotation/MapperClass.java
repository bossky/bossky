package org.bossky.mapper.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 映射器注释
 * 
 * @author bo
 *
 */
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MapperClass {
	/**
	 * 名称
	 * 
	 * @return
	 */
	public String name() default "";

	/**
	 * 是否子类
	 * 
	 * @return
	 */
	public boolean subclass() default false;
}
