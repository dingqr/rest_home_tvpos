package com.yonyou.framework.library.http.request.content.multi;

import com.yonyou.framework.library.http.data.Consts;
import com.yonyou.framework.library.common.log.Elog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 作者：addison on 15/12/15 19:19
 * 邮箱：lsf@yonyou.com
 * 上传文件
 */
public class FilePart extends InputStreamPart {
    public File file;
    public static final String TAG = FilePart.class.getSimpleName();

    public FilePart(String key, File file) {
        this(key, file, Consts.MIME_TYPE_OCTET_STREAM);
    }

    public FilePart(String key, File file, String mimeType) {
        super(key, getInputStream(file), file.getName(), mimeType);
        this.file = file;
    }

    public static InputStream getInputStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getTotalLength() {
        long len = file.length();
         Elog.v(TAG, TAG + " 内容长度header ： " + header.length + " ,body: " + len + " ," +
                "换行：" + CR_LF.length);
        return header.length + len + CR_LF.length;
    }
}

