package com.smart.framework.library.bean;

/**
 * Created by qyd on 2016/12/20.
 * 统一返回数据格式
 */

public class ResultBean {
    /**
     * success表示请求成功其他表示失败
     */
    public String status;
    /**
     * 失败原因
     */
    public String msg;
    /**
     * 请求成功数据
     */
    public String data;

    public boolean success;
    public boolean error;
    //非0表示成功
    public int errorCode;

}