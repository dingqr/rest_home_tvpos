package com.yonyou.framework.library.http.parser.impl;

import com.yonyou.framework.library.http.data.Json;
import com.yonyou.framework.library.http.parser.MemCacheableParser;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * 作者：addison on 15/12/15 19:11
 * 邮箱：lsf@yonyou.com
 */
public class JsonParser<T> extends MemCacheableParser<T> {
    protected Type claxx;
    protected String json;

    public JsonParser(Type claxx) {
        this.claxx = claxx;
    }

    @Override
    protected T parseNetStream(InputStream stream, long totalLength
            , String charSet) throws IOException {
        json = streamToString(stream, totalLength, charSet);
        return Json.get().toObject(json, claxx);
    }

    @Override
    protected T parseDiskCache(InputStream stream, long length) throws IOException {
        json = streamToString(stream, length, charSet);
        return Json.get().toObject(json, claxx);
    }

    @Override
    protected boolean tryKeepToCache(T data) throws IOException {
        return keepToCache(json);
    }

    /**
     * get the row string
     */
    @Override
    public String getRawString() {
        return json;
    }

    /**
     * get the json model
     */
    public <C> C getJsonModel(Class<C> claxx) {
        return Json.get().toObject(json, claxx);
    }

    @Override
    public String toString() {
        return "JsonParser{" +
                "claxx=" + claxx +
                "} " + super.toString();
    }
}

