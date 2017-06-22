package com.yonyou.framework.library.http.parser.impl;

import com.yonyou.framework.library.http.parser.MemCacheableParser;

import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：addison on 15/12/15 19:11
 * 邮箱：lsf@yonyou.com
 */
public class StringParser extends MemCacheableParser<String> {

    @Override
    public String parseNetStream(InputStream stream, long len, String charSet) throws IOException {
        return streamToString(stream, len, charSet);
    }

    @Override
    protected String parseDiskCache(InputStream stream, long length) throws IOException {
        return streamToString(stream, length, charSet);
    }

    @Override
    protected boolean tryKeepToCache(String data) throws IOException {
        return keepToCache(data);
    }
}

