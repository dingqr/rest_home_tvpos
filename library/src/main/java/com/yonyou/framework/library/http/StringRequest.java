package com.yonyou.framework.library.http;

import com.yonyou.framework.library.http.parser.DataParser;
import com.yonyou.framework.library.http.parser.impl.StringParser;
import com.yonyou.framework.library.http.request.AbstractRequest;
import com.yonyou.framework.library.http.request.param.HttpParamModel;

/**
 * 作者：addison on 15/12/15 19:34
 * 邮箱：lsf@yonyou.com
 */
public class StringRequest extends AbstractRequest<String> {

    public StringRequest(String url) {
        super(url);
    }

    public StringRequest(HttpParamModel model) {
        super(model);
    }

    public StringRequest(String url, HttpParamModel model) {
        super(url, model);
    }

    @Override
    public DataParser<String> createDataParser() {
        return new StringParser();
    }

}

