package com.yonyou.framework.library.http.response;

import com.yonyou.framework.library.http.data.HttpStatus;
import com.yonyou.framework.library.http.data.NameValuePair;
import com.yonyou.framework.library.http.exception.HttpException;
import com.yonyou.framework.library.http.request.AbstractRequest;

/**
 * 作者：addison on 15/12/15 19:37
 * 邮箱：lsf@yonyou.com
 */
public interface Response<T> {


    public NameValuePair[] getHeaders();

    public HttpStatus getHttpStatus();

    public T getResult();

    public <R extends AbstractRequest<T>> R getRequest();

    public long getReadedLength();

    public long getContentLength();

    public String getCharSet();

    public long getUseTime();

    public boolean isConnectSuccess();

    public int getRetryTimes();

    public int getRedirectTimes();

    public HttpException getException();

    public boolean isCacheHit();

    public String getRawString();

    public Response<T> setTag(Object tag);

    public Object getTag();

    String resToString();

    void printInfo();

}

