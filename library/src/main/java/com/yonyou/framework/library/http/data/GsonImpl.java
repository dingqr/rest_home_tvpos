package com.yonyou.framework.library.http.data;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * 作者：addison on 15/12/15 18:20
 * 邮箱：lsf@yonyou.com
 */
public class GsonImpl extends Json {
    private Gson gson = new Gson();

    @Override
    public String toJson(Object src) {
        return gson.toJson(src);
    }

    @Override
    public <T> T toObject(String json, Class<T> claxx) {
        return gson.fromJson(json, claxx);
    }

    @Override
    public <T> T toObject(String json, Type type) {
        return gson.fromJson(json, type);
    }

    @Override
    public <T> T toObject(byte[] bytes, Class<T> claxx) {
        return gson.fromJson(new String(bytes), claxx);
    }

}
