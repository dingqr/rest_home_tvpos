package com.yonyou.framework.library.http.parser.impl;

import com.yonyou.framework.library.http.parser.FileCacheableParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：addison on 15/12/15 19:09
 * 邮箱：lsf@yonyou.com
 */
public class FileParser extends FileCacheableParser<File> {
    public FileParser() {
    }
    public FileParser(File saveToFile) {
        this.file = saveToFile;
    }

    @Override
    public File parseNetStream(InputStream stream, long len, String charSet) throws IOException {
        return streamToFile(stream, len);
    }

    @Override
    public File parseDiskCache(File file) {
        return file;
    }
}

