package com.yonyou.framework.library.http.impl.apache.entity;

import com.yonyou.framework.library.http.request.AbstractRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 作者：addison on 15/12/15 18:45
 * 邮箱：lsf@yonyou.com
 *  A self contained, repeatable entity that obtains its content from a file.
 */
public class FileEntity extends AbstractHttpEntity implements Cloneable {

    protected final File file;

    public FileEntity(final File file, final String contentType, AbstractRequest request) {
        super();
        if (file == null) {
            throw new IllegalArgumentException("File may not be null");
        }
        this.file = file;
        setContentType(contentType);
        setRequest(request);
    }

    public boolean isRepeatable() {
        return true;
    }

    public long getContentLength() {
        return this.file.length();
    }

    public InputStream getContent() throws IOException {
        return new FileInputStream(this.file);
    }

    /**
     * modified by MaTianyu
     */
    public void writeTo(final OutputStream outstream) throws IOException {
        if (outstream == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        bytesWritten = 0;
        totalSize = file.length();
        InputStream instream = new FileInputStream(this.file);
        try {
            byte[] tmp = new byte[BUFFER_SIZE];
            int l;
            while ((l = instream.read(tmp)) != -1) {
                outstream.write(tmp, 0, l);
                updateProgress(l);
            }
            outstream.flush();
        } finally {
            instream.close();
        }
    }

    /**
     * Tells that this entity is not streaming.
     *
     * @return <code>false</code>
     */
    public boolean isStreaming() {
        return false;
    }

}

