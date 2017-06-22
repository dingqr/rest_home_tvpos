package com.yonyou.framework.library.http.listener;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.yonyou.framework.library.http.exception.HttpException;
import com.yonyou.framework.library.http.request.AbstractRequest;
import com.yonyou.framework.library.http.response.Response;
import com.yonyou.framework.library.common.log.Elog;

/**
 * 作者：addison on 15/12/15 18:55
 * 邮箱：lsf@yonyou.com
 */

public abstract class GlobalHttpListener {
    private static final String TAG = GlobalHttpListener.class.getSimpleName();

    private static final int M_START = 1;
    private static final int M_SUCCESS = 2;
    private static final int M_FAILURE = 3;
    private static final int M_CANCEL = 4;

    private HttpHandler handler;
    private boolean runOnUiThread = true;

    /**
     * default run on UI thread
     */
    public GlobalHttpListener() {
        this(true);
    }

    public GlobalHttpListener(boolean runOnUiThread) {
        setRunOnUiThread(runOnUiThread);
    }

    public boolean isRunOnUiThread() {
        return runOnUiThread;
    }

    public GlobalHttpListener setRunOnUiThread(boolean runOnUiThread) {
        this.runOnUiThread = runOnUiThread;
        if (runOnUiThread) {
            handler = new HttpHandler(Looper.getMainLooper());
        } else {
            handler = null;
        }
        return this;
    }

    /**
     * note: hold an implicit reference to outter class
     */
    private class HttpHandler extends Handler {
        private HttpHandler(Looper looper) {
            super(looper);
        }

        @Override
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            Object[] data;
            switch (msg.what) {
                case M_START:
                    onStart((AbstractRequest<Object>) msg.obj);
                    break;
                case M_SUCCESS:
                    data = (Object[]) msg.obj;
                    onSuccess(data[0], (Response<Object>) data[1]);
                    break;
                case M_FAILURE:
                    data = (Object[]) msg.obj;
                    onFailure((HttpException) data[0], (Response<Object>) data[1]);
                    break;
                case M_CANCEL:
                    data = (Object[]) msg.obj;
                    onCancel(data[0], (Response<Object>) data[1]);
                    break;
            }
        }
    }

    //____________lite called method ____________
    public final void notifyCallStart(AbstractRequest<?> req) {
        if (runOnUiThread) {
            Message msg = handler.obtainMessage(M_START);
            msg.obj = req;
            handler.sendMessage(msg);
        } else {
            onStart(req);
        }
    }

    public final void notifyCallSuccess(Object data, Response<?> response) {
        if (runOnUiThread) {
            Message msg = handler.obtainMessage(M_SUCCESS);
            msg.obj = new Object[]{data, response};
            handler.sendMessage(msg);
        } else {
            onSuccess(data, response);
        }
    }

    public final void notifyCallFailure(HttpException e, Response<?> response) {
        if (runOnUiThread) {
            Message msg = handler.obtainMessage(M_FAILURE);
            msg.obj = new Object[]{e, response};
            handler.sendMessage(msg);
        } else {
            onFailure(e, response);
        }
    }

    public final void notifyCallCancel(Object data, Response<?> response) {
        Elog.w(TAG, "Request be Cancelled!  isCancelled: " + response.getRequest().isCancelled()
                    + "  Thread isInterrupted: " + Thread.currentThread().isInterrupted());
        if (runOnUiThread) {
            Message msg = handler.obtainMessage(M_CANCEL);
            msg.obj = new Object[]{data, response};
            handler.sendMessage(msg);
        } else {
            onCancel(data, response);
        }
    }

    //____________ developer override method ____________
    public void onStart(AbstractRequest<?> request){}

    public abstract void onSuccess(Object data, Response<?> response);

    public abstract void onFailure(HttpException e, Response<?> response);

    public void onCancel(Object data, Response<?> response){}

}

