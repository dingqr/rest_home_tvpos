package com.yonyou.framework.library.http.impl.apache;

import com.yonyou.framework.library.http.data.Consts;
import com.yonyou.framework.library.http.exception.HttpClientException;
import com.yonyou.framework.library.http.impl.apache.entity.FileEntity;
import com.yonyou.framework.library.http.impl.apache.entity.InputStreamEntity;
import com.yonyou.framework.library.http.impl.apache.entity.MultipartEntity;
import com.yonyou.framework.library.http.request.AbstractRequest;
import com.yonyou.framework.library.http.request.content.ByteArrayBody;
import com.yonyou.framework.library.http.request.content.FileBody;
import com.yonyou.framework.library.http.request.content.HttpBody;
import com.yonyou.framework.library.http.request.content.InputStreamBody;
import com.yonyou.framework.library.http.request.content.StringBody;
import com.yonyou.framework.library.http.request.content.multi.MultipartBody;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;

/**
 * 作者：addison on 15/12/15 18:47
 * 邮箱：lsf@yonyou.com
 * help us to build.HttpEntity
 */
public class EntityBuilder {

    public static HttpEntity build(AbstractRequest req) throws HttpClientException {
        try {
            HttpBody body = req.getHttpBody();
            if (body != null) {
                req.addHeader(Consts.CONTENT_TYPE, body.getContentType());
                if (body instanceof StringBody) {
                    // StringBody JsonBody UrlEncodedFormBody
                    StringBody b = (StringBody) body;
                    return new StringEntity(b.string, b.charset);
                } else if (body instanceof ByteArrayBody) {
                    // ByteArrayBody SerializableBody
                    ByteArrayBody b = (ByteArrayBody) body;
                    return new ByteArrayEntity(b.bytes);
                } else if (body instanceof InputStreamBody) {
                    InputStreamBody b = (InputStreamBody) body;
                    return new InputStreamEntity(b.inputStream, b.inputStream.available(), req);
                } else if (body instanceof FileBody) {
                    FileBody b = (FileBody) body;
                    return new FileEntity(b.file, b.contentType, req);
                } else if (body instanceof MultipartBody) {
                    return new MultipartEntity((MultipartBody) body);
                } else {
                    throw new RuntimeException("Unpredictable Entity Body(非法实体)");
                }
            }
        } catch (Exception e) {
            throw new HttpClientException(e);
        }
        return null;
    }
}

