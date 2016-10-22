package org.bossky.mapper.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 元信息属性注释
 * 
 * @author bo
 *
 */
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MetaField {
	/**
	 * 名称
	 * 
	 * @return
	 */
	public String name() default "";

}
