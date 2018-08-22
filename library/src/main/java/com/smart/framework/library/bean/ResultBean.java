package com.smart.framework.library.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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

    public void fromJson(String jsonStr){
        if(null == jsonStr || jsonStr.isEmpty()){
            return;
        }

        JSONObject jsonObject = JSON.parseObject(jsonStr);

        data = jsonObject.getString("data");
        status = jsonObject.getString("status").toString();
        msg = jsonObject.getString("msg").toString();
        errorCode = jsonObject.getInteger("errorCode");
        success = jsonObject.getBoolean("success");
        error = jsonObject.getBoolean("error");
    }

}