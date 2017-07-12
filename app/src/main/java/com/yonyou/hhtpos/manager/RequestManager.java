package com.yonyou.hhtpos.manager;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.log.Elog;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.application.MyApplication;
import com.yonyou.hhtpos.bean.ResultBean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Joe on 2016/12/19.
 * 统一封装的网络请求入口类，提供网络的GET、POST请求（内部采用OKHTTP实现）
 */

public class RequestManager {

    private static final String TAG = RequestManager.class.getSimpleName();

    /**mdiatype 这个需要和服务端保持一致（请求数据类型）*/
//    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    /**mdiatype 这个需要和服务端保持一致（请求数据类型）*/
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    /**mdiatype 这个需要和服务端保持一致（请求数据类型）*/
    private static final MediaType MEDIA_OBJECT_STREAM = MediaType.parse("application/octet-stream");

    /**请求接口根地址*/
    private static final String BASE_URL = "http://xxx.com/openapi";
    /**请求接口实例对象*/
    private static volatile RequestManager mInstance;
    /**get请求*/
    public static final int TYPE_GET = 0;
    /**post请求参数为json*/
    public static final int TYPE_POST_JSON = 1;
    /**post请求参数为表单*/
    public static final int TYPE_POST_FORM = 2;
    /**编码格式*/
    private static final String ENCODE = "utf-8";

    /**okHttpClient 实例*/
    private OkHttpClient mOkHttpClient;
    /**全局处理子线程和M主线程通信*/
    private Handler okHttpHandler;

    /**普通连接的超时时间*/
    private final int CONNECT_TIMEOIUT = 10;
    /**文件上传的超时时间*/
    private final int CONNECT_TIMEOIUT_FILE = 50;

    /**服务器返回code值*/
    private final int RESPONCE_CODE_200 = 200;
    /**服务器返回code值*/
    private final int RESPONCE_CODE_400 = 400;
    /**服务器返回code值*/
    private final int RESPONCE_CODE_500 = 500;
    /**
     * 初始化RequestManager
     * @param context
     */
    public RequestManager(Context context) {
        //初始化Handler
        if(null == context){
            context = MyApplication.getInstance();
        }

        //初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOIUT, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(CONNECT_TIMEOIUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(CONNECT_TIMEOIUT, TimeUnit.SECONDS)//设置写入超时时间

                .cookieJar(new CookieJar() {//OkHttpClient创建时，传入这个CookieJar的实现，就能完成对Cookie的自动管理
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url);
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .sslSocketFactory(getSSLSocketFactory(context, "srca.cer"))//添加https

                .build();
        okHttpHandler = new Handler(context.getMainLooper());
    }

    /**
     * 获取单例引用
     * @param context
     * @return
     */
    public static RequestManager getInstance(Context context) {
        RequestManager inst = mInstance;
        if (inst == null) {
            synchronized (RequestManager.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new RequestManager(context);
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    /**
     * 获取单例引用
     *
     * @return
     */
    public static RequestManager getInstance() {
        return getInstance(null);
    }


    /**
     * okHttp get异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    public <T> Call requestGetByAsyn(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack) {
        StringBuilder tempParams = new StringBuilder();
        try {

            int pos = 0;
            if (null != paramsMap) {
                for (String key : paramsMap.keySet()) {
                    if (pos > 0) {
                        tempParams.append("&");
                    }
//                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                    tempParams.append(String.format("%s=%s", key, paramsMap.get(key)));
                    pos++;
                }
            }
            final String requestUrl;
            if (null != tempParams && !TextUtils.isEmpty(tempParams)) {
                requestUrl = String.format("%s?%s", actionUrl, tempParams.toString());
            } else {
                requestUrl = actionUrl;
            }
            Elog.e(TAG, "Method:get");
            Elog.e(TAG, requestUrl);
            final Request request = addHeaders().url(requestUrl).build();

            Call call = mOkHttpClient.newCall(request);
            call = onRequest(callBack, call);
            return call;
        } catch (Exception e) {
            Elog.e(TAG, e.toString());
            requestOnFailure(MyApplication.getInstance().getString(R.string.common_error_parse), callBack);
        }
        return null;
    }

    /**
     * okHttp post异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    public <T> Call requestPostByAsyn(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack) {
        try {
            // 字符串格式
//            StringBuilder tempParams = new StringBuilder();
//            int pos = 0;
//            for (String key : paramsMap.keySet()) {
//                if (pos > 0) {
//                    tempParams.append("&");
//                }
//                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), ENCODE)));
////                tempParams.append(String.format("%s=%s", key, paramsMap.get(key)));
//                pos++;
//            }

            // JSONObject格式
            JSONObject json = new JSONObject();
            for (String key : paramsMap.keySet()) {
                json.put(key, paramsMap.get(key));
            }

            String params = json.toString();
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
            String requestUrl = actionUrl;

            Elog.e(TAG, "Method:post");
            Elog.e(TAG, "URL:" + requestUrl);
            Elog.e(TAG, "Params:" + params);
            final Request request = addHeaders().url(requestUrl).post(body).build();
            Call call = mOkHttpClient.newCall(request);
            call = onRequest(callBack, call);
            return call;
        } catch (Exception e) {
            requestOnFailure(MyApplication.getInstance().getString(R.string.common_error_parse), callBack);
            Elog.e(TAG, e.toString());
        }
        return null;
    }

    /**
     * 上传文件
     * @param actionUrl 接口地址
     * @param filePath  本地文件地址
     */
    public <T> Call upLoadFile(String actionUrl, String filePath, final ReqCallBack<T> callBack) {
        //补全请求地址
//        String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
        String requestUrl = actionUrl;
        //创建File
        File file = new File(filePath);
        //创建RequestBody
        RequestBody body = RequestBody.create(MEDIA_OBJECT_STREAM, file);
        //创建Request
        final Request request = new Request.Builder().url(requestUrl).post(body).build();
        Call call = mOkHttpClient.newBuilder().writeTimeout(CONNECT_TIMEOIUT_FILE, TimeUnit.SECONDS).build().newCall(request);
        call = onRequest(callBack, call);
        return call;
    }
    /**
     *上传文件
     * @param actionUrl 接口地址
     * @param paramsMap 参数
     * @param callBack 回调
     * @param <T>
     */
    public <T> Call upLoadFile(String actionUrl, HashMap<String, Object> paramsMap, final ReqCallBack<T> callBack) {
        try {
            //补全请求地址
//          String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
            String requestUrl = actionUrl;
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置类型
            builder.setType(MultipartBody.FORM);
            //追加参数
            for (String key : paramsMap.keySet()) {
                Object object = paramsMap.get(key);
                if (!(object instanceof File)) {
                    builder.addFormDataPart(key, object.toString());
                } else {
                    File file = (File) object;
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(null, file));
                }
            }
            //创建RequestBody
            RequestBody body = builder.build();
            //创建Request
            final Request request = new Request.Builder().url(requestUrl).post(body).build();
            //单独设置参数 比如读取超时时间
            Call call = mOkHttpClient.newBuilder().writeTimeout(CONNECT_TIMEOIUT_FILE, TimeUnit.SECONDS).build().newCall(request);

            call = onRequest(callBack, call);
            return call;
        } catch (Exception e) {
            Elog.e(TAG, e.toString());
            requestOnFailure(MyApplication.getInstance().getString(R.string.common_error_parse), callBack);
        }
        return null;
    }

    /**
     * 请求数据
     *
     * @param callBack 请求返回数据回调
     * @param call  请求体
     * @param <T>
     * @return
     */
    private <T> Call onRequest(final ReqCallBack<T> callBack,Call call) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //服务器IP不对的时候、没有网络时返回
                failedCallBack(MyApplication.getInstance().getString(R.string.common_onfail), callBack);
                Elog.e(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Elog.e(TAG,response.code());
                    if (response.isSuccessful()||response.code()==RESPONCE_CODE_400||response.code()==RESPONCE_CODE_500) {
                        String string = response.body().string();
                        Elog.e(TAG, "response ----->" + string);
//                        ResultBean result = new Gson().fromJson(string, ResultBean.class);
                        ResultBean result = JSON.parseObject(string, ResultBean.class);

                        if (null != result) {
                            switch (result.code) {
                                case RESPONCE_CODE_200:
                                    if (callBack.type != null) {
                                       String data =  result.data;
                                        if(null==data||data.equals("")){
                                            successCallBack(null, callBack);
                                        } else {
                                            if (callBack.type == String.class || callBack.type == Integer.class || callBack.type == Double.class || callBack.type == Float.class) {
                                                successCallBack((T) data, callBack);
                                            } else {
                                                successCallBack((T) JSON.parseObject(data, callBack.type), callBack);
                                            }
                                        }
                                    }else{
                                        requestOnFailure(MyApplication.getInstance().getString(R.string.common_error_parse), callBack);
                                    }
                                    break;
                                default:
                                    requestOnFailure(result.msg,result.code, callBack);
                                    break;
                            }
                        } else {
                            failedCallBack(MyApplication.getInstance().getString(R.string.common_request_fail), callBack);
                        }
                    } else {
                        failedCallBack(MyApplication.getInstance().getString(R.string.common_request_fail), callBack);
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                    requestOnFailure(MyApplication.getInstance().getString(R.string.common_error_parse), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 统一处理成功信息
     *
     * @param result
     * @param callBack
     * @param <T>
     */
    private <T> void successCallBack(final T result, final ReqCallBack<T> callBack) {
        if (null != okHttpHandler) {
            okHttpHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (callBack != null) {
                        callBack.onReqSuccess(result);
                    }
                }
            });
        } else {
            if (callBack != null) {
                callBack.onReqSuccess(result);
            }
        }

    }

    /**
     * 统一处理失败信息
     *
     * @param errorMsg
     * @param callBack
     * @param <T>
     */
    private <T> void failedCallBack(final String errorMsg, final ReqCallBack<T> callBack) {
        if (null != okHttpHandler) {
            okHttpHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (callBack != null) {
                        callBack.onFailure(errorMsg);
                    }
                }
            });
        } else {
            if (callBack != null) {
                callBack.onFailure(errorMsg);
            }
        }
    }

    /**
     * 统一处理调用失败信息（接口返回的失败信息）
     *
     * @param errorMsg
     * @param callBack
     * @param <T>
     */
//    private <T> void requestOnFailure(final String errorMsg, final ReqCallBack<T> callBack) {
//        if (null != okHttpHandler) {
//            okHttpHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    if (callBack != null) {
//                        callBack.onReqFailed(errorMsg);
//                    }
//                }
//            });
//        } else {
//            if (callBack != null) {
//                callBack.onReqFailed(errorMsg);
//            }
//        }
//
//    }

    private <T> void requestOnFailure(final String errorMsg, final ReqCallBack<T> callBack) {
        this.requestOnFailure(errorMsg,RESPONCE_CODE_500,callBack);
    }
    /**
     * 统一处理调用失败信息（接口返回的失败信息）
     *
     * @param errorMsg
     * @param code 错误码
     * @param callBack
     * @param <T>
     */
    private <T> void requestOnFailure(final String errorMsg, final int code, final ReqCallBack<T> callBack) {
        if (null != okHttpHandler) {
            okHttpHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (callBack != null) {
                        ErrorBean errorBean = new ErrorBean();
                        errorBean.setMsg(errorMsg);
                        errorBean.setCode(code+"");
                        callBack.onReqFailed(errorBean);
                    }
                }
            });
        } else {
            if (callBack != null) {
                ErrorBean errorBean = new ErrorBean();
                errorBean.setMsg(errorMsg);
                errorBean.setCode(code+"");
                callBack.onReqFailed(errorBean);
            }
        }

    }



    /**
     * okHttp post同步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    private <T> Call requestPostBySynWithForm(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                builder.add(key, paramsMap.get(key));
            }
            RequestBody formBody = builder.build();
//            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
            String requestUrl = actionUrl;
            Elog.e(TAG, requestUrl);
            final Request request = addHeaders().url(requestUrl).post(formBody).build();
            Call call = mOkHttpClient.newCall(request);
            call = onRequest(callBack, call);
            return call;
        } catch (Exception e) {
            Elog.e(TAG, e.toString());
        }
        return null;
    }
    /**
     * okHttp get同步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    private void requestGetBySyn(String actionUrl, HashMap<String, String> paramsMap) {
        StringBuilder tempParams = new StringBuilder();
        try {
            //处理参数
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                //对参数进行URLEncoder
//                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                tempParams.append(String.format("%s=%s", key, paramsMap.get(key)));
                pos++;
            }
            //补全请求地址
            String requestUrl = String.format("%s/%s", actionUrl, tempParams.toString());
            //创建一个请求
            Request request = addHeaders().url(requestUrl).build();
            //创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            //执行请求
            final Response response = call.execute();
            response.body().string();
        } catch (Exception e) {
            Elog.e(TAG, e.toString());
        }

    }

    /**请求头信息*/
    private final String HEADER_CONNECTION = "keep-alive";
    /**请求头信息*/
    private final String HEADER_PLATFORM = "2";
    /**请求头信息*/
    private final String HEADER_APPVERSION= "3.2.0";
    /**
     * 统一为请求添加头信息
     *
     * @return
     */
    private Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", HEADER_CONNECTION)
                .addHeader("platform", HEADER_PLATFORM)
                .addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE)
                .addHeader("appVersion", HEADER_APPVERSION);
        return builder;
    }

    /**
     * 实现https请求
     */
    private static SSLSocketFactory getSSLSocketFactory(Context context, String name) {


        if (context == null) {
            throw new NullPointerException("context == null");
        }

        //CertificateFactory用来证书生成
        CertificateFactory certificateFactory;
        InputStream inputStream = null;
        Certificate certificate;

        try {
            inputStream = context.getResources().getAssets().open(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            certificateFactory = CertificateFactory.getInstance("X.509");
            certificate = certificateFactory.generateCertificate(inputStream);

            //Create a KeyStore containing our trusted CAs
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry(name,certificate);

            //Create a TrustManager that trusts the CAs in our keyStore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            //Create an SSLContext that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();

        } catch (Exception e) {

        }
        return null;
    }

//    /**
//     * 设置证书
//     * @param certificates
//     */
//    public void setCertificates(InputStream... certificates)
//    {
//        try
//        {
//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            keyStore.load(null);
//            int index = 0;
//            for (InputStream certificate : certificates)
//            {
//                String certificateAlias = Integer.toString(index++);
//                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
//
//                try
//                {
//                    if (certificate != null)
//                        certificate.close();
//                } catch (IOException e)
//                {
//                }
//            }
//
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//
//            TrustManagerFactory trustManagerFactory =
//                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//
//            trustManagerFactory.init(keyStore);
//            sslContext.init
//                    (
//                            null,
//                            trustManagerFactory.getTrustManagers(),
//                            new SecureRandom()
//                    );
//            mOkHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
//
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//    }

}
