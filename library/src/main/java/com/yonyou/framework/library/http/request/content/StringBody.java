package com.yonyou.framework.library.http.request.content;

import com.yonyou.framework.library.http.data.Consts;

import org.apache.http.protocol.HTTP;

/**
 * 作者：addison on 15/12/15 19:21
 * 邮箱：lsf@yonyou.com
 */
public class StringBody extends HttpBody {
    public String string;
    public String charset;

    public StringBody(String string) {
        this(string, null, null);
    }

    public StringBody(String string, String mimeType, String charset) {
        this.string = string;
        if (mimeType == null) {
            mimeType = Consts.MIME_TYPE_TEXT;
        }
        if (charset == null) {
            charset = Consts.DEFAULT_CHARSET;
        }
        this.charset = charset;
        this.contentType = mimeType + HTTP.CHARSET_PARAM + charset;
    }

    @Override
    public String toString() {
        return "StringEntity{" +
                "string='" + string + '\'' +
                ", charset='" + charset + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}

