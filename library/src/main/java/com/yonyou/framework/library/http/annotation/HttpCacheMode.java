package com.yonyou.framework.library.http.annotation;

import com.yonyou.framework.library.http.request.param.CacheMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：addison on 15/12/15 17:59
 * 邮箱：lsf@yonyou.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpCacheMode {
    CacheMode value();
}
