package com.yonyou.framework.library.http.request.content;

import com.yonyou.framework.library.http.data.Consts;

import java.io.InputStream;

/**
 * 作者：addison on 15/12/15 19:16
 * 邮箱：lsf@yonyou.com
 */
public class InputStreamBody extends HttpBody {
    public InputStream inputStream;

    public InputStreamBody(InputStream inputStream) {
        this(inputStream, null);
    }

    public InputStreamBody(InputStream inputStream, String contentType) {
        this.inputStream = inputStream;
        this.contentType = (contentType != null) ? contentType : Consts.MIME_TYPE_OCTET_STREAM;
    }

    @Override
    public String toString() {
        return "InputStreamEntity{" +
                "inputStream=" + inputStream +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}

