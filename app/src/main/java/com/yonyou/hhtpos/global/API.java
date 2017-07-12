package com.yonyou.hhtpos.global;

/**
 * 接口地址
 * 作者：liushuofei on 2017/6/22 10:12
 */
public class API {

    /**
     * 服务器ip
     */
//    public static final String BASE_SERVER_IP = "http://api-c2b.honghuotai.com"; //正式服务器地址
    public static final String BASE_SERVER_IP = "http://10.220.17.37"; //开发服务器地址
//    public static final String BASE_SERVER_IP = "http://test-api-c2b.honghuotai.com"; //测试服务器地址

    /**
     * 运营平台地址
     */
    public static final String URL_OPERATION_PALTFORM = "http://blog.csdn.net/zhangjinhuang";//测试地址

    /**
     * 服务器版本
     */
    public static final String BASE_SERVER_URL = BASE_SERVER_IP + "/api/v1/";

    /**
     * 获取验证码
     */
    public static final String GET_SMS_CODE = BASE_SERVER_IP + "/auth/messageSender";

    /**
     * 验证手机号
     */
    public static final String VERIFY_PHONE_NUMBER = BASE_SERVER_IP + "/auth/message";

    /**
     * 登录
     */
    public static final String URL_PASSPORT_LOGIN = BASE_SERVER_IP + "/login";

    /**
     * 重置密码
     */
    public static final String URL_RESET_PWD = BASE_SERVER_IP + "/changePassword";

}
