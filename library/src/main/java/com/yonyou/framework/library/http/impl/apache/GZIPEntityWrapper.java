package com.yonyou.framework.library.http.impl.apache;

import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * 作者：addison on 15/12/15 18:48
 * 邮箱：lsf@yonyou.com
 * Enclosing inputstream for gzip decoded data.
 * Improve network transmission speed quite a lot.
 */
class GZIPEntityWrapper extends HttpEntityWrapper {
    public GZIPEntityWrapper(HttpEntity wrapped) {
        super(wrapped);
    }

    @Override
    public InputStream getContent() throws IOException {
        return new GZIPInputStream(wrappedEntity.getContent());
    }

    @Override
    public long getContentLength() {
        //unknown
        return -1;
    }
}

