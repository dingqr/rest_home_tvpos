package com.yonyou.framework.library.http.impl.apache;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：addison on 15/12/15 18:53
 * 邮箱：lsf@yonyou.com
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS) // The original version used RUNTIME
public @interface NotThreadSafe {
}
