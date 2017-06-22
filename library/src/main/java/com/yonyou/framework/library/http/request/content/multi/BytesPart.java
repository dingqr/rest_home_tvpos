package com.yonyou.framework.library.http.request.content.multi;

import com.yonyou.framework.library.http.data.Consts;
import com.yonyou.framework.library.common.utils.StringCodingUtils;
import com.yonyou.framework.library.common.log.Elog;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 作者：addison on 15/12/15 19:18
 * 邮箱：lsf@yonyou.com
 * 上传操作
 */
public class BytesPart extends AbstractPart {
    public byte[] bytes;
    public static final String TAG = BytesPart.class.getSimpleName();
    //protected           String type = Consts.MIME_TYPE_OCTET_STREAM;

    public BytesPart(String key, byte[] bytes) {
        this(key, bytes, null);
        this.bytes = bytes;
    }

    public BytesPart(String key, byte[] bytes, String mimeType) {
        super(key, mimeType);
        this.bytes = bytes;
    }

    @Override
    protected byte[] createContentType() {
        return  StringCodingUtils.getBytes(Consts.CONTENT_TYPE + ": " + mimeType + "\r\n", infoCharset);
    }

    @Override
    protected byte[] createContentDisposition() {
        return StringCodingUtils.getBytes("Content-Disposition: form-data; name=\"" + key + "\"\r\n", infoCharset);
    }

    public long getTotalLength() {
        Elog.v(TAG, TAG + "内容长度 header ： " + header.length + " ,body: "
                + bytes.length + " ," + "换行：" + CR_LF.length);
        return header.length + bytes.length + CR_LF.length;
    }

    @Override
    public byte[] getTransferEncoding() {
        return TRANSFER_ENCODING_BINARY;
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        out.write(bytes);
        out.write(CR_LF);
        updateProgress(bytes.length + CR_LF.length);
    }
}

