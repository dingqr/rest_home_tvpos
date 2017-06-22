package com.yonyou.framework.library.http.impl.apache.entity;

import com.yonyou.framework.library.http.request.AbstractRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 作者：addison on 15/12/15 18:45
 * 邮箱：lsf@yonyou.com
 */
public class InputStreamEntity extends AbstractHttpEntity {

    private final InputStream content;
    private final long length;
    private boolean consumed = false;

    public InputStreamEntity(final InputStream instream, long length, AbstractRequest request) {
        super();
        if (instream == null) {
            throw new IllegalArgumentException("Source input stream may not be null");
        }
        this.content = instream;
        this.length = length;
        setRequest(request);
    }

    public boolean isRepeatable() {
        return false;
    }

    public long getContentLength() {
        return this.length;
    }

    public InputStream getContent() throws IOException {
        return this.content;
    }

    /**
     * modified by MaTianyu
     */
    public void writeTo(final OutputStream outstream) throws IOException {
        if (outstream == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        InputStream instream = this.content;
        byte[] buffer = new byte[BUFFER_SIZE];
        int l;
        bytesWritten = 0;
        if (this.length < 0) {
            totalSize = instream.available();
            // consume until EOF
            while ((l = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, l);
                updateProgress(l);
            }
        } else {
            // consume no more than length
            totalSize = this.length;
            long remaining = this.length;
            while (remaining > 0) {
                l = instream.read(buffer, 0, (int) Math.min(BUFFER_SIZE, remaining));
                if (l == -1) {
                    break;
                }
                outstream.write(buffer, 0, l);
                updateProgress(l);
                remaining -= l;
            }
        }
        this.consumed = true;
    }

    // non-javadoc, see interface HttpEntity
    public boolean isStreaming() {
        return !this.consumed;
    }

    // non-javadoc, see interface HttpEntity
    public void consumeContent() throws IOException {
        this.consumed = true;
        // If the input stream is from a connection, closing it will read to
        // the end of the content. Otherwise, we don't care what it does.
        this.content.close();
    }

}

