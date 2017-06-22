package com.yonyou.framework.library.http.request.content;

import com.yonyou.framework.library.http.data.Consts;

/**
 * 作者：addison on 15/12/15 19:15
 * 邮箱：lsf@yonyou.com
 */
public class ByteArrayBody extends HttpBody {
    public byte[] bytes;

    public ByteArrayBody(byte[] bytes) {
        this(bytes, Consts.MIME_TYPE_OCTET_STREAM);
    }

    public ByteArrayBody(byte[] bytes, String contentType) {
        this.bytes = bytes;
        this.contentType = contentType;
    }
}

