package com.smart.framework.library.common.utils;

import android.os.Build;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * 作者：addison on 15/12/15 19:43
 * 邮箱：lsf@yonyou.com
 */
public class StringCodingUtils {

    public static byte[] getBytes(String src, Charset charSet) {
        // Build.VERSION_CODES.GINGERBREAD = 9
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            try {
                return src.getBytes(charSet.name());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return src.getBytes(charSet);
        }
    }

}
