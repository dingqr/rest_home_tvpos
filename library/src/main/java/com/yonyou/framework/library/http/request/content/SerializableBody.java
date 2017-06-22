package com.yonyou.framework.library.http.request.content;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 作者：addison on 15/12/15 19:21
 * 邮箱：lsf@yonyou.com
 */
public class SerializableBody extends ByteArrayBody {

    public SerializableBody(Serializable ser) {
        super(getBytes(ser), null);
    }

    public static byte[] getBytes(Serializable ser) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(baos);
            oos.writeObject(ser);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

