package com.yonyou.framework.library.http.impl.apache.entity;

import com.yonyou.framework.library.http.request.content.multi.MultipartBody;

import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.Header;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 作者：addison on 15/12/15 18:46
 * 邮箱：lsf@yonyou.com
 * Simplified multipart entity mainly used for sending one or more files.
 */
public class MultipartEntity implements HttpEntity {

    private boolean isRepeatable = false;
    private MultipartBody multipartBody;

    public MultipartEntity(MultipartBody multipartBody) {
        this.multipartBody = multipartBody;
    }

    @Override
    public long getContentLength() {
        return multipartBody.getContentLength();
    }

    @Override
    public Header getContentType() {
        return new BasicHeader("Content-Type", multipartBody.getContentType());
    }

    @Override
    public void writeTo(final OutputStream outstream) throws IOException {
        multipartBody.writeTo(outstream);
    }

    @Override
    public boolean isChunked() {
        return false;
    }

    public void setIsRepeatable(boolean isRepeatable) {
        this.isRepeatable = isRepeatable;
    }

    @Override
    public boolean isRepeatable() {
        return isRepeatable;
    }

    @Override
    public boolean isStreaming() {
        return false;
    }

    @Override
    public Header getContentEncoding() {
        return null;
    }

    @Override
    public void consumeContent() throws IOException, UnsupportedOperationException {
        if (isStreaming()) {
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        }
    }

    @Override
    public InputStream getContent() throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException("getContent() is not supported. Use writeTo() instead.");
    }

}
