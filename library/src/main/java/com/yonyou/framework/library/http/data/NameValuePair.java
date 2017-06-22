package com.yonyou.framework.library.http.data;

import java.io.Serializable;

/**
 * 作者：addison on 15/12/15 18:23
 * 邮箱：lsf@yonyou.com
 */
public class NameValuePair implements Serializable {
    private static final long serialVersionUID = -1339856642868580559L;
    private final String name;
    private final String value;

    public NameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.format("%-20s", this.name) + "=  " + this.value;
    }

}

