package com.yonyou.framework.library.http;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.yonyou.framework.library.http.concurrent.OverloadPolicy;
import com.yonyou.framework.library.http.concurrent.SchedulePolicy;
import com.yonyou.framework.library.http.concurrent.SmartExecutor;
import com.yonyou.framework.library.http.data.NameValuePair;
import com.yonyou.framework.library.http.data.StatisticsInfo;
import com.yonyou.framework.library.http.exception.ClientException;
import com.yonyou.framework.library.http.exception.HttpClientException;
import com.yonyou.framework.library.http.exception.HttpException;
import com.yonyou.framework.library.http.exception.HttpNetException;
import com.yonyou.framework.library.http.exception.HttpServerException;
import com.yonyou.framework.library.http.exception.NetException;
import com.yonyou.framework.library.http.impl.apache.ApacheHttpClient;
import com.yonyou.framework.library.http.listener.GlobalHttpListener;
import com.yonyou.framework.library.http.listener.HttpListener;
import com.yonyou.framework.library.http.listener.StatisticsListener;
import com.yonyou.framework.library.http.network.Network;
import com.yonyou.framework.library.http.parser.DataParser;
import com.yonyou.framework.library.http.request.AbstractRequest;
import com.yonyou.framework.library.http.request.JsonRequest;
import com.yonyou.framework.library.http.request.param.CacheMode;
import com.yonyou.framework.library.http.request.param.HttpMethods;
import com.yonyou.framework.library.http.request.param.HttpRichParamModel;
import com.yonyou.framework.library.http.response.InternalResponse;
import com.yonyou.framework.library.http.response.Response;
import com.yonyou.framework.library.common.HttpUtil;
import com.yonyou.framework.library.common.log.Elog;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 作者：addison on 15/12/15 18:37
 * 邮箱：lsf@yonyou.com
 */
@TargetApi(Build.VERSION_CODES.DONUT)
public abstract class MyHttp {

    private static final String TAG = MyHttp.class.getSimpleName();

    protected HttpConfig config;
    protected SmartExecutor smartExecutor;
    protected final Object lock = new Object();
    protected StatisticsInfo statisticsInfo = new StatisticsInfo();
    protected AtomicLong memCachedSize = new AtomicLong();
    protected ConcurrentHashMap<String, HttpCache> memCache = new ConcurrentHashMap<String, HttpCache>();

    protected MyHttp() {}

    public static MyHttp newApacheHttpClient(HttpConfig config) {
        return new ApacheHttpClient(config);
    }

    /* ____________________________ may be overridden by sub class ____________________________*/
    public void initConfig(HttpConfig config) {
        this.config = config;
        this.config.setLiteHttp(this);
        Log.d(TAG, config.toString());

        // Set config and smart-executor to lite-http.
        if (smartExecutor == null) {
            smartExecutor = new SmartExecutor(config.concurrentSize, config.waitingQueueSize);
        } else {
            setConfigForSmartExecutor(config.concurrentSize, config.waitingQueueSize);
        }
        setConfigForSmartExecutor(config.schedulePolicy, config.overloadPolicy);
    }

    protected void setUserAgent(String userAgent) {}

    protected void setConfigForHttpParams(int connectTimeout, int socketTimeout, int socketBufferSize) { }

    protected void setConfigForRetryHandler(int retrySleepMillis, boolean requestSentRetryEnabled) { }

    /* ____________________________ main methods ____________________________*/
    protected final void setConfigForSmartExecutor(int concurrentSize, int waitingQueueSize) {
        smartExecutor.setCoreSize(concurrentSize);
        smartExecutor.setQueueSize(waitingQueueSize);
    }

    protected final void setConfigForSmartExecutor(SchedulePolicy schedulePolicy, OverloadPolicy overloadPolicy) {
        if (schedulePolicy != null) {
            smartExecutor.setSchedulePolicy(schedulePolicy);
        }
        if (overloadPolicy != null) {
            smartExecutor.setOverloadPolicy(overloadPolicy);
        }
    }

    public final HttpConfig getConfig() {
        return config;
    }

    public final Context getAppContext() {
        return config.context;
    }

    public final StatisticsInfo getStatisticsInfo() {
        return statisticsInfo;
    }

    protected abstract <T> void connectWithRetries(AbstractRequest<T> request, InternalResponse response)
            throws HttpClientException, HttpNetException, HttpServerException;

    public <T> Response<T> execute(AbstractRequest<T> request) {
        final InternalResponse<T> response = handleRequest(request);
        HttpException httpException = null;
        final HttpListener<T> listener = request.getHttpListener();
        final GlobalHttpListener globalListener = request.getGlobalHttpListener();
        try {
                Thread t = Thread.currentThread();
                Elog.i(TAG,
                        "lite http request: " + request.getFullUri()
                                + " , tag: " + request.getTag()
                                + " , method: " + request.getMethod()
                                + " , cache mode: " + request.getCacheMode()
                                + " , thread ID: " + t.getId()
                                + " , thread name: " + t.getName());
            if (globalListener != null) {
                globalListener.notifyCallStart(request);
            }
            if (listener != null) {
                listener.notifyCallStart(request);
            }
            if (request.getCacheMode() == CacheMode.CacheOnly) {
                tryHitCache(response);
                return response;
            } else if (request.getCacheMode() == CacheMode.CacheFirst && tryHitCache(response)) {
                return response;
            } else {
                tryToConnectNetwork(request, response);
            }
        } catch (HttpException e) {
            e.printStackTrace();
            httpException = e;
            response.setException(httpException);
        } catch (Throwable e) {
            e.printStackTrace();
            httpException = new HttpClientException(e);
            response.setException(httpException);
        } finally {
            try {
                Thread t = Thread.currentThread();
                Elog.i(TAG, "lite http response: " + request.getUri()
                            + " , tag: " + request.getTag()
                            + " , method: " + request.getMethod()
                            + " , cache mode: " + request.getCacheMode()
                            + " , thread ID: " + t.getId()
                            + " , thread name: " + t.getName());
                if (listener != null) {
                    if (request.isCancelledOrInterrupted()) {
                        listener.notifyCallCancel(response.getResult(), response);
                    } else if (httpException != null) {
                        listener.notifyCallFailure(httpException, response);
                    } else {
                        listener.notifyCallSuccess(response.getResult(), response);
                    }
                    listener.notifyCallEnd(response);
                }
                if (globalListener != null) {
                    if (request.isCancelledOrInterrupted()) {
                        globalListener.notifyCallCancel(response.getResult(), response);
                    } else if (httpException != null) {
                        globalListener.notifyCallFailure(httpException, response);
                    } else {
                        globalListener.notifyCallSuccess(response.getResult(), response);
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public <T> void executeAsync(final AbstractRequest<T> request) {
        smartExecutor.execute(new Runnable() {
            @Override
            public void run() {
                execute(request);
            }
        });
    }

    public <T> Response<T> execute(HttpRichParamModel<T> model) {
        return execute(model.buildRequest());
    }

    public <T> JsonRequest<T> executeAsync(HttpRichParamModel<T> model) {
        JsonRequest<T> request = model.buildRequest();
        executeAsync(request);
        return request;
    }

    public <T> T perform(AbstractRequest<T> request) {
        return execute(request).getResult();
    }

    public <T> FutureTask<T> performAsync(final AbstractRequest<T> request) {
        FutureTask<T> futureTask = new FutureTask<T>(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return execute(request).getResult();
            }
        });
        smartExecutor.execute(futureTask);
        return futureTask;
    }

    public <T> Response<T> executeOrThrow(AbstractRequest<T> request) throws HttpException {
        final Response<T> response = execute(request);
        HttpException e = response.getException();
        if (e != null) {
            throw e;
        }
        return response;
    }

    public <T> T performOrThrow(AbstractRequest<T> request) throws HttpException {
        return executeOrThrow(request).getResult();
    }

    public String get(String uri) {
        return perform(new StringRequest(uri).setMethod(HttpMethods.Get));
    }

    public <T> T get(String uri, Class<T> claxx) {
        return perform(new JsonRequest<T>(uri, claxx).setMethod(HttpMethods.Get));
    }

    public <T> T get(AbstractRequest<T> request) {
        return perform(request.setMethod(HttpMethods.Get));
    }

    public <T> T put(AbstractRequest<T> request) {
        return perform(request.setMethod(HttpMethods.Put));
    }

    public <T> T post(AbstractRequest<T> request) {
        return perform(request.setMethod(HttpMethods.Post));
    }

    public <T> T delete(AbstractRequest<T> request) {
        return perform(request.setMethod(HttpMethods.Delete));
    }

    public <T> NameValuePair[] head(AbstractRequest<T> request) {
        return execute(request.setMethod(HttpMethods.Head)).getHeaders();
    }

    /**
     * if some of request params is null or 0, set global default value to it.
     */
    protected <T> InternalResponse<T> handleRequest(AbstractRequest<T> request) {
        if (config.commonHeaders != null) {
            request.addHeader(config.commonHeaders);
        }
        if (request.getCacheMode() == null) {
            request.setCacheMode(config.defaultCacheMode);
        }
        if (request.getCacheDir() == null) {
            request.setCacheDir(config.defaultCacheDir);
        }
        if (request.getCacheExpireMillis() < 0) {
            request.setCacheExpireMillis(config.defaultCacheExpireMillis);
        }
        if (request.getCharSet() == null) {
            request.setCharSet(config.defaultCharSet);
        }
        if (request.getMethod() == null) {
            request.setMethod(config.defaultHttpMethod);
        }
        if (request.getMaxRedirectTimes() < 0) {
            request.setMaxRedirectTimes(config.defaultMaxRedirectTimes);
        }
        if (request.getMaxRetryTimes() < 0) {
            request.setMaxRetryTimes(config.defaultMaxRetryTimes);
        }
        if (request.getQueryBuilder() == null) {
            request.setQueryBuilder(config.defaultModelQueryBuilder);
        }
        if (config.globalHttpListener != null) {
            request.setGlobalHttpListener(config.globalHttpListener);
        }
        if (request.getSchemeHost() == null) {
            request.setSchemeHost(config.globalSchemeHost);
        }
        return new InternalResponse<T>(request);
    }

    /**
     * try to detect the network
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     */
    protected void tryToDetectNetwork() throws HttpClientException, HttpNetException {
        if (config.detectNetwork || config.disableNetworkFlags > 0) {
            if (config.context == null) {
                throw new HttpClientException(ClientException.ContextNeeded);
            }
            try {
                Network.NetType type = null;
                if (config.detectNetwork) {
                    type = Network.getConnectedType(config.context);
                    if (type == Network.NetType.None) {
                        throw new HttpNetException(NetException.NetworkNotAvilable);
                    }
                }
                if (config.disableNetworkFlags > 0) {
                    if (type == null) {
                        type = Network.getConnectedType(config.context);
                    }
                    if (type != null) {
                        if (config.isDisableAllNetwork() || config.isDisableNetwork(type.value)) {
                            throw new HttpNetException(NetException.NetworkDisabled);
                        }
                    } else {
                        Elog.e(TAG, "DisableNetwork but cant get network type !!!");
                    }
                }
            } catch (SecurityException e) {
                throw new HttpClientException(e, ClientException.PermissionDenied);
            }
        }
    }

    /**
     * connect to network
     */
    protected <T> boolean tryToConnectNetwork(AbstractRequest<T> request, InternalResponse<T> response)
            throws HttpNetException, HttpClientException, HttpServerException, IOException {
        StatisticsListener statistic = null;
        if (config.doStatistics) {
            statistic = new StatisticsListener(response, statisticsInfo);
            response.setStatistics(statistic);
        }
        try {
            if (statistic != null) {
                statistic.onStart(request);
            }
            if (!request.isCancelledOrInterrupted()) {
                Elog.v(TAG, "lite http try to connect network...  url: " + request.getUri()
                            + "  tag: " + request.getTag());
                tryToDetectNetwork();
                connectWithRetries(request, response);
                tryToKeepCacheInMemory(response);
                return true;
            }
        } finally {
            if (statistic != null) {
                statistic.onEnd(response);
            }
            if (request.getCacheMode() == CacheMode.NetFirst
                    && !response.isResultOk()
                    && !request.isCancelledOrInterrupted()) {
                tryHitCache(response);
            }
        }
        return false;
    }

    /**
     * Multi-Level cache design.
     * <ul>
     * <li>Memory</li>
     * <li>Disk</li>
     * <li>Network</li>
     * </ul>
     */
    @SuppressWarnings("unchecked")
    protected <T> boolean tryHitCache(InternalResponse<T> response) throws IOException {
        AbstractRequest<T> request = response.getRequest();
        String key = request.getCacheKey();
        long expire = request.getCacheExpireMillis();
        boolean isMemCacheSupport = request.getDataParser().isMemCacheSupport();

        // 1. try to hit memory cache
        if (isMemCacheSupport) {
            HttpCache<T> cache = (HttpCache<T>) memCache.get(key);
            if (cache != null) {
                if (expire < 0 || expire > getCurrentTimeMillis() - cache.time) {
                    // memory hit!
                    request.getDataParser().readFromMemoryCache(cache.data);
                    response.setCacheHit(true);
                    Elog.i(TAG, "lite-http mem cache hit!  "
                                + "  url:" + request.getUri()
                                + "  tag:" + request.getTag()
                                + "  key:" + key
                                + "  cache time:" + HttpUtil.formatDate(cache.time)
                                + "  expire: " + expire);
                    return true;
                }
            }
        }

        // 2. try to hit disk cache
        File file = request.getCachedFile();
        if (file.exists()) {
            if (expire < 0 || expire > getCurrentTimeMillis() - file.lastModified()) {
                // disk hit!
                request.getDataParser().readFromDiskCache(file);
                response.setCacheHit(true);
                Elog.i(TAG, "lite-http disk cache hit!  "
                            + "  url:" + request.getUri()
                            + "  tag:" + request.getTag()
                            + "  key:" + key
                            + "  cache time:" + HttpUtil.formatDate(file.lastModified())
                            + "  expire: " + expire);
                return true;
            }
        }
        return false;
    }

    /**
     * try to save data into cache
     */
    protected <T> boolean tryToKeepCacheInMemory(InternalResponse<T> response) {
        AbstractRequest<T> request = response.getRequest();
        if (request.isCachedModel()) {
            DataParser<T> dataParser = request.getDataParser();
            Elog.v(TAG, "lite http try to keep cache.. maximum cache len: " + config.maxMemCacheBytesSize
                        + "   now cache len: " + memCachedSize.get()
                        + "   wanna put len: " + dataParser.getReadedLength()
                        + "   url: " + request.getUri()
                        + "   tag: " + request.getTag());
            if (dataParser.isMemCacheSupport()) {
                if (memCachedSize.get() + dataParser.getReadedLength() > config.maxMemCacheBytesSize) {
                    clearMemCache();
                    Elog.i(TAG, "lite http cache full ______________ and clear all mem cache success");
                }
                if (dataParser.getReadedLength() < config.maxMemCacheBytesSize) {
                    HttpCache<T> cache = new HttpCache<T>();
                    cache.time = getCurrentTimeMillis();
                    cache.key = request.getCacheKey();
                    cache.charSet = response.getCharSet();
                    cache.data = dataParser.getData();
                    cache.length = dataParser.getReadedLength();
                    synchronized (lock) {
                        memCache.put(cache.key, cache);
                        memCachedSize.addAndGet(cache.length);
                        Elog.v(TAG, "lite http keep mem cache success, "
                                    + "   url: " + request.getUri()
                                    + "   tag: " + request.getTag()
                                    + "   key: " + cache.key
                                    + "   len: " + cache.length);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public final long cleanCacheForRequest(AbstractRequest request) {
        long len = 0;
        if (request.getCacheDir() == null) {
            request.setCacheDir(config.defaultCacheDir);
        }
        synchronized (lock) {
            if (memCache.get(request.getCacheKey()) != null) {
                HttpCache cache = memCache.remove(request.getCacheKey());
                len = cache.length;
                memCachedSize.addAndGet(-len);
            }
        }
        File file = request.getCachedFile();
        if (file != null) {
            len = file.length();
            file.delete();
        }
        return len;
    }

    public final long clearMemCache() {
        long len;
        synchronized (lock) {
            len = memCachedSize.get();
            memCache.clear();
            memCachedSize.set(0);
        }
        return len;
    }

    public final boolean deleteCachedFile(String cacehKey) {
        File file = new File(config.defaultCacheDir, cacehKey);
        return file.delete();
    }

    public final long deleteCachedFiles() {
        File file = new File(config.defaultCacheDir);
        long len = 0;
        if (file.isDirectory()) {
            File[] fs = file.listFiles();
            if (fs != null) {
                for (File f : fs) {
                    len += f.length();
                    f.delete();
                }
            }
        }
        return len;
    }

    protected long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    static class HttpCache<T> {
        T data;
        String key;
        long time;
        long length;
        String charSet;
    }

}

