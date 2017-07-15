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
//    public static final String BASE_SERVER_IP = "http://10.220.18.216:9000"; //马诗雨ip
    public static final String BASE_SERVER_IP = "http://10.220.17.51:"; //线上ip
    public static final String BASE_SERVER_LOGIN = BASE_SERVER_IP + "8062";// login端口号
    public static final String BASE_SERVER_SHOP = BASE_SERVER_IP + "8063";// shop端口号
//    public static final String BASE_SERVER_IP = "http://10.220.23.251:9000"; //吴贤川ip
//    public static final String BASE_SERVER_IP = "http://test-api-c2b.honghuotai.com"; //测试服务器地址

    /**
     * 运营平台地址
     */
    public static final String URL_OPERATION_PALTFORM = "http://blog.csdn.net/zhangjinhuang";//超链接测试地址

    /**
     * 服务器版本
     */
    public static final String BASE_SERVER_URL = BASE_SERVER_IP + "/api/v1/";

    /**
     * 获取验证码
     */
    public static final String GET_SMS_CODE = BASE_SERVER_LOGIN + "/auth/messageSender";

    /**
     * 验证手机号
     */
    public static final String VERIFY_PHONE_NUMBER = BASE_SERVER_LOGIN + "/auth/message";

    /**
     * 登录
     */
    public static final String URL_PASSPORT_LOGIN = BASE_SERVER_LOGIN + "/login";

    /**
     * 重置密码
     */
    public static final String URL_RESET_PWD = BASE_SERVER_LOGIN + "/changePassword";

    /**
     * 左侧导航栏
     */
    public static final String GET_NAVIGATION_LIST = BASE_SERVER_SHOP + "/proFunction/listProFunction";

    /**
     * 堂食桌台开单接口
     */
    public static final String URL_TS_OPEN_ORDER = BASE_SERVER_SHOP + "/dineIn/openOrder";

    /**
     * 外卖列表接口
     */
    public static final String URL_TAKE_OUT_LIST = BASE_SERVER_SHOP + "/takeOut/orderList";
}
