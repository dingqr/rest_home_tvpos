package com.yonyou.framework.library.http.request.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：addison on 15/12/15 19:25
 * 邮箱：lsf@yonyou.com
 * help to build custom parameters for AbstractRequest
 */
public interface HttpCustomParam {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CustomValueBuilder {}

    @CustomValueBuilder
    public CharSequence buildValue();

}
