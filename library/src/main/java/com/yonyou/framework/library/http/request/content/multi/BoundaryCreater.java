package com.yonyou.framework.library.http.request.content.multi;

import com.yonyou.framework.library.http.data.Consts;
import com.yonyou.framework.library.common.utils.StringCodingUtils;

import java.nio.charset.Charset;
import java.util.Random;

/**
 * 作者：addison on 15/12/15 19:17
 * 邮箱：lsf@yonyou.com
 */
public class BoundaryCreater {
    public static final Charset charset         = Charset.forName(Consts.DEFAULT_CHARSET);
    private final static char[]  MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private String boundary;
    private byte[] boundaryLine;
    private byte[] boundaryEnd;

    public BoundaryCreater() {
        final StringBuilder buf = new StringBuilder();
        final Random rand = new Random();
        for (int i = 0; i < 30; i++) {
            buf.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        }
        boundary = buf.toString();
        boundaryLine =  StringCodingUtils.getBytes("--" + boundary + "\r\n", charset);
        boundaryEnd = StringCodingUtils.getBytes("--" + boundary + "--\r\n", charset);
    }

    public String getBoundary() {
        return boundary;
    }

    public byte[] getBoundaryLine() {
        return boundaryLine;
    }

    public byte[] getBoundaryEnd() {
        return boundaryEnd;
    }
}

