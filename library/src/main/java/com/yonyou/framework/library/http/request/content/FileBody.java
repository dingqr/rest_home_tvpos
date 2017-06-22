package com.yonyou.framework.library.http.request.content;

import com.yonyou.framework.library.http.data.Consts;

import java.io.File;

/**
 * 作者：addison on 15/12/15 19:15
 * 邮箱：lsf@yonyou.com
 */
public class FileBody extends HttpBody {
    public File file;

    public FileBody(File file) {
        this(file, Consts.MIME_TYPE_OCTET_STREAM);
    }

    public FileBody(File file, String contentType) {
        this.file = file;
        this.contentType = contentType;
    }
}
