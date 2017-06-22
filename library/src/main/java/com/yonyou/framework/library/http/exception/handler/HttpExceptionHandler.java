package com.yonyou.framework.library.http.exception.handler;

import com.yonyou.framework.library.http.data.HttpStatus;
import com.yonyou.framework.library.http.exception.ClientException;
import com.yonyou.framework.library.http.exception.HttpClientException;
import com.yonyou.framework.library.http.exception.HttpException;
import com.yonyou.framework.library.http.exception.HttpNetException;
import com.yonyou.framework.library.http.exception.HttpServerException;
import com.yonyou.framework.library.http.exception.NetException;
import com.yonyou.framework.library.http.exception.ServerException;

/**
 * 作者：addison on 15/12/15 18:26
 * 邮箱：lsf@yonyou.com
 * Handle Response on UI Thread
 */
public abstract class HttpExceptionHandler {

    public HttpExceptionHandler handleException(HttpException e) {
        if (e != null) {
            if (e instanceof HttpClientException) {
                HttpClientException ce = ((HttpClientException) e);
                onClientException(ce, ce.getExceptionType());
            } else if (e instanceof HttpNetException) {
                HttpNetException ne = ((HttpNetException) e);
                onNetException(ne, ne.getExceptionType());
            } else if (e instanceof HttpServerException) {
                HttpServerException se = ((HttpServerException) e);
                onServerException(se, se.getExceptionType(), se.getHttpStatus());
            } else {
                HttpClientException ce = new HttpClientException(e);
                onClientException(ce, ce.getExceptionType());
            }
            e.setHandled(true);
        }
        return this;
    }

    /**
     * 比如 URL为空，构建参数异常以及请求过程中遇到的其他客户端异常。
     *
     * @param e
     */
    protected abstract void onClientException(HttpClientException e, ClientException type);

    /**
     * 比如 无网络，网络不稳定，该网络类型已被禁用等。
     *
     * @param e
     */
    protected abstract void onNetException(HttpNetException e, NetException type);

    /**
     * 这个时候，连接是成功的。http header已经返回，但是status code是400~599之间。
     * [400-499]：因为客户端原因，服务器拒绝服务
     * [500~599]：基本是服务器内部错误或者网关异常造成的
     *
     * @param e
     */
    protected abstract void onServerException(HttpServerException e, ServerException type, HttpStatus status);
}

