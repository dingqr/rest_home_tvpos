package com.yonyou.framework.library.http.request;

import com.yonyou.framework.library.http.parser.DataParser;
import com.yonyou.framework.library.http.parser.impl.JsonParser;
import com.yonyou.framework.library.http.request.param.HttpParamModel;
import com.yonyou.framework.library.http.request.param.NonHttpParam;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 作者：addison on 15/12/15 19:23
 * 邮箱：lsf@yonyou.com
 */
public abstract class JsonAbsRequest<T> extends AbstractRequest<T> {

    @NonHttpParam
    protected Type resultType;

    public JsonAbsRequest(String url) {
        super(url);
    }

    protected JsonAbsRequest(HttpParamModel model) {
        super(model);
    }

    protected JsonAbsRequest(String url, HttpParamModel model) {
        super(url, model);
    }

    @Override
    public DataParser<T> createDataParser() {
        return new JsonParser<T>(getResultType());
    }

    public Type getResultType() {
        if (resultType == null) {
            resultType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return resultType;
    }

    @SuppressWarnings("unchecked")
    public <R extends JsonAbsRequest> R setResultType(Type resultType) {
        this.resultType = resultType;
        return (R) this;
    }
}

