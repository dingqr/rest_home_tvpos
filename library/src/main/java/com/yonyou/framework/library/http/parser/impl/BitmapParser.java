package com.yonyou.framework.library.http.parser.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yonyou.framework.library.http.parser.FileCacheableParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：addison on 15/12/15 19:08
 * 邮箱：lsf@yonyou.com
 */
public class BitmapParser extends FileCacheableParser<Bitmap> {
    public BitmapParser() {}
    public BitmapParser(File file) {
        this.file = file;
    }

    @Override
    public Bitmap parseNetStream(InputStream stream, long len, String charSet) throws IOException {
        //if (this.file != null || request.isCachedModel()
        //    || (request.getHttpListener() != null && request.getHttpListener().isReadingNotify())) {
        //    File file = streamToFile(stream, len, cacheDir);
        //    return BitmapFactory.decodeFile(file.getAbsolutePath());
        //} else {
        //    return BitmapFactory.decodeStream(stream);
        //}
        file = streamToFile(stream, len);
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    @Override
    public Bitmap parseDiskCache(File file) {
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }
}
