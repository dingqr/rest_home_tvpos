package com.yonyou.framework.library.http.request.param;

import com.yonyou.framework.library.http.listener.HttpListener;
import com.yonyou.framework.library.http.query.ModelQueryBuilder;
import com.yonyou.framework.library.http.request.JsonRequest;
import com.yonyou.framework.library.http.request.content.HttpBody;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;

/**
 * 作者：addison on 15/12/15 19:29
 * 邮箱：lsf@yonyou.com
 */
public abstract class HttpRichParamModel<T> implements HttpParamModel {
    private HttpListener<T> httpListener;

    public final LinkedHashMap<String, String> getHeaders() {
        return createHeaders();
    }

    public ModelQueryBuilder getModelQueryBuilder() {
        return createQueryBuilder();
    }

    public final HttpBody getHttpBody() {
        return createHttpBody();
    }

    public final HttpListener<T> getHttpListener() {
        if (httpListener == null) {
            httpListener = createHttpListener();
        }
        return httpListener;
    }

    public boolean isFieldsAttachToUrl() {
        return true;
    }

    /**
     * craete headers for request.
     */
    protected LinkedHashMap<String, String> createHeaders() {return null;}

    /**
     * craete uri query builder for request.
     */
    protected ModelQueryBuilder createQueryBuilder() {
        return null;
    }

    /**
     * create http body for POST/PUT... request.
     *
     * @return such as StringBody,  UrlEncodedFormBody, MultipartBody
     */
    protected HttpBody createHttpBody() {return null;}

    /**
     * create http listener for request.
     */
    protected HttpListener<T> createHttpListener() {return null;}

    /**
     * build request and set http listener.
     */
    @SuppressWarnings("unchecked")
    public final <M extends HttpRichParamModel<T>> M setHttpListener(HttpListener<T> httpListener) {
        this.httpListener = httpListener;
        return (M) this;
    }

    /**
     * build as a request.
     */
    public JsonRequest<T> buildRequest() {
        Type type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return new JsonRequest<T>(this, type);
    }
}

