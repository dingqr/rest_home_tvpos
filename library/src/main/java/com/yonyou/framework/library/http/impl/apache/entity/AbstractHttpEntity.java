package com.yonyou.framework.library.http.impl.apache.entity;

import com.yonyou.framework.library.http.listener.HttpListener;
import com.yonyou.framework.library.http.request.AbstractRequest;

import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.Header;

import java.io.IOException;

/**
 * 作者：addison on 15/12/15 18:42
 * 邮箱：lsf@yonyou.com
 * Abstract base class for entities.
 * Provides the commonly used attributes for streamed and self-contained
 * implementations of HttpEntity

 */
public abstract class AbstractHttpEntity implements HttpEntity {
    /*______________________ add by MaTianyu ____________________*/
    protected final static int BUFFER_SIZE = 4096;
    protected HttpListener httpListener;
    protected AbstractRequest request;
    protected long bytesWritten;
    protected long totalSize;
    /*______________________ add by MaTianyu over____________________*/

    protected Header contentType;
    protected Header contentEncoding;
    protected boolean chunked;

    /**
     * Protected default constructor.
     * The attributes of the created object remain
     * <code>null</code> and <code>false</code>, respectively.
     */
    protected AbstractHttpEntity() {
        super();
    }


    /**
     * Obtains the Content-Type header.
     * The default implementation returns the value of the
     * {@link #contentType contentType} attribute.
     *
     * @return the Content-Type header, or <code>null</code>
     */
    public Header getContentType() {
        return this.contentType;
    }


    /**
     * Obtains the Content-Encoding header.
     * The default implementation returns the value of the
     * {@link #contentEncoding contentEncoding} attribute.
     *
     * @return the Content-Encoding header, or <code>null</code>
     */
    public Header getContentEncoding() {
        return this.contentEncoding;
    }

    /**
     * Obtains the 'chunked' flag.
     * The default implementation returns the value of the
     * {@link #chunked chunked} attribute.
     *
     * @return the 'chunked' flag
     */
    public boolean isChunked() {
        return this.chunked;
    }


    /**
     * Specifies the Content-Type header.
     * The default implementation sets the value of the
     * {@link #contentType contentType} attribute.
     *
     * @param contentType the new Content-Encoding header, or
     *                    <code>null</code> to unset
     */
    public void setContentType(final Header contentType) {
        this.contentType = contentType;
    }

    /**
     * Specifies the Content-Type header, as a string.
     * The default implementation calls
     * {@link #setContentType(Header) setContentType(Header)}.
     *
     * @param ctString the new Content-Type header, or
     *                 <code>null</code> to unset
     */
    public void setContentType(final String ctString) {
        Header h = null;
        if (ctString != null) {
            h = new BasicHeader(HTTP.CONTENT_TYPE, ctString);
        }
        setContentType(h);
    }


    /**
     * Specifies the Content-Encoding header.
     * The default implementation sets the value of the
     * {@link #contentEncoding contentEncoding} attribute.
     *
     * @param contentEncoding the new Content-Encoding header, or
     *                        <code>null</code> to unset
     */
    public void setContentEncoding(final Header contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    /**
     * Specifies the Content-Encoding header, as a string.
     * The default implementation calls
     * {@link #setContentEncoding(Header) setContentEncoding(Header)}.
     *
     * @param ceString the new Content-Encoding header, or
     *                 <code>null</code> to unset
     */
    public void setContentEncoding(final String ceString) {
        Header h = null;
        if (ceString != null) {
            h = new BasicHeader(HTTP.CONTENT_ENCODING, ceString);
        }
        setContentEncoding(h);
    }


    /**
     * Specifies the 'chunked' flag.
     * The default implementation sets the value of the
     * {@link #chunked chunked} attribute.
     *
     * @param b the new 'chunked' flag
     */
    public void setChunked(boolean b) {
        this.chunked = b;
    }


    /**
     * Does not consume anything.
     * The default implementation does nothing if
     * {@link HttpEntity#isStreaming isStreaming}
     * returns <code>false</code>, and throws an exception
     * if it returns <code>true</code>.
     * This removes the burden of implementing
     * an empty method for non-streaming entities.
     *
     * @throws IOException                   in case of an I/O problem
     * @throws UnsupportedOperationException if a streaming subclass does not override this method
     */
    public void consumeContent()
            throws IOException, UnsupportedOperationException {
        if (isStreaming()) {
            throw new UnsupportedOperationException
                    ("streaming entity does not implement consumeContent()");
        }
    } // consumeContent

    /*______________________ add by MaTianyu ____________________*/
    @SuppressWarnings("unchecked")
    protected void updateProgress(long count) {
        bytesWritten += count;
        if (httpListener != null) {
            httpListener.notifyCallUploading(request, totalSize, bytesWritten);
        }
    }

    public HttpListener getHttpListener() {
        return httpListener;
    }

    public void setHttpListener(HttpListener httpListener) {
        this.httpListener = httpListener;
    }

    public AbstractRequest getRequest() {
        return request;
    }

    public void setRequest(AbstractRequest request) {
        this.request = request;
        setHttpListener(request.getHttpListener());
    }
    /*______________________ add by MaTianyu voer ____________________*/
} // class AbstractHttpEntity

