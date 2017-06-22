package com.yonyou.framework.library.http.parser.impl;

import com.yonyou.framework.library.http.parser.MemCacheableParser;

import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：addison on 15/12/15 19:08
 * 邮箱：lsf@yonyou.com
 */
public class BytesParser extends MemCacheableParser<byte[]> {

    @Override
    public byte[] parseNetStream(InputStream stream, long len, String charSet) throws IOException {
        return streamToByteArray(stream, len);
    }

    @Override
    protected byte[] parseDiskCache(InputStream stream, long length) throws IOException {
        return streamToByteArray(stream, length);
    }


    @Override
    protected boolean tryKeepToCache(byte[] data) throws IOException {
        return keepToCache(data);
    }

}

