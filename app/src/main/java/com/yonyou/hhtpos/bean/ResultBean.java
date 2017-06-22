package com.yonyou.hhtpos.bean;

/**
 * Created by qyd on 2016/12/20.
 * 统一返回数据格式
 */

public  class ResultBean {
    /**200表示请求成功其他表示失败*/
    public int code;
    /**失败原因*/
    public String msg;
    /**请求成功数据*/
    public String data;


    @Override
    public String toString() {
        return "ResultBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}