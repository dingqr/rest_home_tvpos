package com.yonyou.framework.library.http.exception;

/**
 * 作者：addison on 15/12/15 18:28
 * 邮箱：lsf@yonyou.com
 * Base HttpException
 * Start--1(build request)-->Reqeust--2(connect network)-->Response--3(handle response)-->End
 */
public abstract class HttpException extends Exception {
    private static final long serialVersionUID = -8585446012573642784L;
    public static boolean printStackTrace = true;
    protected boolean handled = true;

    public boolean isHandled() {
        return handled;
    }

    public <T extends HttpException> T setHandled(boolean handled) {
        this.handled = handled;
        return (T) this;
    }

    public HttpException() {}

    public HttpException(String name) {
        super(name);
    }

    public HttpException(String name, Throwable cause) {
        super(name, cause);
    }

    public HttpException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "HttpException{" +
                "handled=" + handled +
                "} " + super.toString();
    }
}

