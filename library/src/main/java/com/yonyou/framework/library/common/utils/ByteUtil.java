package com.yonyou.framework.library.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 作者：addison on 8/1/16 13:28
 * 邮箱：lsf@yonyou.com
 * 字节数组和对象之间的转换
 */
public class ByteUtil {
    /**
     * byte[] 转为 对象
     *
     * @param bytes
     * @return
     */
    public static Object byteToObject(byte[] bytes) throws Exception {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } finally {
            if (ois != null) ois.close();
        }
    }

    /**
     * 对象 转为 byte[]
     *
     * @param obj
     * @return
     */
    public static byte[] objectToByte(Object obj) throws Exception {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return bos.toByteArray();
        } finally {
            if (oos != null) oos.close();
        }
    }

    public static void byteToBit(byte[] bytes, StringBuilder sb) {
        for (int i = 0; i < Byte.SIZE * bytes.length; i++)
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
    }

    public static String byteToBit(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Byte.SIZE * bytes.length; i++)
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }

}
