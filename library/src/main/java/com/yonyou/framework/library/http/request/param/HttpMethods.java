package com.yonyou.framework.library.http.request.param;

/**
 * 作者：addison on 15/12/15 19:26
 * 邮箱：lsf@yonyou.com
 */
public enum HttpMethods {
	/* ******************* Http Get(Query) Request *************/
    /**
     * get
     */
    Get,
    /**
     * get http header only
     */
    Head,
    /**
     * debug
     */
    Trace,
    /**
     * query
     */
    Options,
    /**
     * delete
     */
    Delete,
	/* ******************* Http Upate(Entity Enclosing) Request *************/
    /**
     * update
     */
    Put,
    /**
     * add
     */
    Post,
    /**
     * incremental update
     */
    Patch
}

