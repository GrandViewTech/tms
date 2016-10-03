/**
 * 
 */
package com.loylty.application.entity.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
/**
 * @author puneets
 *
 */
public @interface UserLevelAuthorization
	{
		
		String[] roles() default "default";
		
		String[] activities() default "default";
	}
