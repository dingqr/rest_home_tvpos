package com.yonyou.framework.library.http.impl.apache;

import org.apache.http.HttpRequest;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 作者：addison on 15/12/15 18:53
 * 邮箱：lsf@yonyou.com
 */
public class StandardHttpRequestRetryHandler extends DefaultHttpRequestRetryHandler {

    private final Map<String, Boolean> idempotentMethods;

    /**
     * Default constructor
     */
    public StandardHttpRequestRetryHandler(final int retryCount, final boolean requestSentRetryEnabled) {
        super(retryCount, requestSentRetryEnabled);
        this.idempotentMethods = new ConcurrentHashMap<String, Boolean>();
        this.idempotentMethods.put("GET", Boolean.TRUE);
        this.idempotentMethods.put("HEAD", Boolean.TRUE);
        this.idempotentMethods.put("PUT", Boolean.TRUE);
        this.idempotentMethods.put("DELETE", Boolean.TRUE);
        this.idempotentMethods.put("OPTIONS", Boolean.TRUE);
        this.idempotentMethods.put("TRACE", Boolean.TRUE);
    }

    /**
     * Default constructor
     */
    public StandardHttpRequestRetryHandler() {
        this(3, false);
    }

    @Override
    protected boolean handleAsIdempotent(final HttpRequest request) {
        if (request == null) {
            return true;
        }
        final String method = request.getRequestLine().getMethod().toUpperCase(Locale.US);
        final Boolean b = this.idempotentMethods.get(method);
        return b != null && b.booleanValue();
    }

}

