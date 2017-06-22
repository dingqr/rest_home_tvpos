package com.yonyou.framework.library.http.request.content.multi;

import com.yonyou.framework.library.http.data.Consts;
import com.yonyou.framework.library.common.utils.StringCodingUtils;
import com.yonyou.framework.library.common.log.Elog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 作者：addison on 15/12/15 19:19
 * 邮箱：lsf@yonyou.com
 * 上传数据流
 */
public class InputStreamPart extends AbstractPart {
    protected InputStream inputStream;

    protected static final String TAG = InputStreamPart.class.getSimpleName();
    protected String fileName;

    public InputStreamPart(String key, InputStream inputStream) {
        this(key, inputStream, null, Consts.MIME_TYPE_OCTET_STREAM);
    }

    public InputStreamPart(String key, InputStream inputStream, String contentType) {
        this(key, inputStream, null, contentType);
    }

    public InputStreamPart(String key, InputStream inputStream, String fileName, String mimeType) {
        super(key, mimeType);
        if (inputStream == null) {
            throw new NullPointerException("InputStream not be null !");
        }
        this.inputStream = inputStream;
        this.fileName = fileName;
    }

    @Override
    protected byte[] createContentType() {
        return StringCodingUtils.getBytes(Consts.CONTENT_TYPE + ": " + mimeType + "\r\n", infoCharset);
    }

    @Override
    protected byte[] createContentDisposition() {
        String dis = "Content-Disposition: form-data; name=\"" + key;
        return fileName == null ? StringCodingUtils.getBytes(dis + "\"\r\n", infoCharset)
                : StringCodingUtils.getBytes(dis + "\"; filename=\"" + fileName + "\"\r\n", infoCharset);
    }

    @Override
    public long getTotalLength() throws IOException {
        long len = inputStream.available();
            Elog.v(TAG, TAG + "内容长度 header ： " + header.length + " ,body: " + len + " ," +
                    "换行：" + CR_LF.length);
        return header.length + len + CR_LF.length;
    }

    @Override
    public byte[] getTransferEncoding() {
        return TRANSFER_ENCODING_BINARY;
    }

    public void writeTo(OutputStream out) throws IOException {
        try {
            final byte[] tmp = new byte[4096];
            int l;
            while ((l = inputStream.read(tmp)) != -1) {
                out.write(tmp, 0, l);
                updateProgress(l);
            }
            out.write(CR_LF);
            updateProgress(CR_LF.length);
            out.flush();
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null)
                inputStream.close();
        }
    }
}

