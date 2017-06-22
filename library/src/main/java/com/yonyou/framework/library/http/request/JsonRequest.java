package com.yonyou.framework.library.http.request;

import com.yonyou.framework.library.http.data.TypeToken;
import com.yonyou.framework.library.http.parser.DataParser;
import com.yonyou.framework.library.http.parser.impl.JsonParser;
import com.yonyou.framework.library.http.request.param.HttpParamModel;

import java.lang.reflect.Type;

/**
 * 作者：addison on 15/12/15 19:24
 * 邮箱：lsf@yonyou.com
 */
public class JsonRequest<T> extends JsonAbsRequest<T> {

    public JsonRequest(String url, Type resultType) {
        super(url);
        setResultType(resultType);
    }

    public JsonRequest(HttpParamModel model, Type resultType) {
        super(model);
        setResultType(resultType);
    }

    public JsonRequest(String url, TypeToken<T> resultType) {
        super(url);
        setResultType(resultType.getType());
    }

    public JsonRequest(HttpParamModel model, TypeToken<T> resultType) {
        super(model);
        setResultType(resultType.getType());
    }

    @Override
    public DataParser<T> createDataParser() {
        return new JsonParser<T>(resultType);
    }
}

