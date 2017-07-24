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
//    public static final String BASE_SERVER_IP = "http://10.220.23.197:"; //线上ip
    public static final String BASE_SERVER_LOGIN = BASE_SERVER_IP + "8062";// login端口号
    public static final String BASE_SERVER_SHOP = BASE_SERVER_IP + "8063";// shop端口号
//    public static final String BASE_SERVER_SHOP = BASE_SERVER_IP + "8088";// shop端口号
//    public static final String BASE_SERVER_IP = "http://10.220.17.51:8062"; //线上ip
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
     * 外卖开单接口
     */
    public static final String URL_WM_OPEN_ORDER = BASE_SERVER_SHOP + "/takeOut/order";

    /**
     * 外卖列表接口
     */
    public static final String URL_TAKE_OUT_LIST = BASE_SERVER_SHOP + "/takeOut/orderList";

    /**
     * 外带开单接口
     */
    public static final String URL_WD_OPEN_ORDER = BASE_SERVER_SHOP + "/proTakeAway/takeAwayOpenOrder";

    /**
     * 外带列表接口
     */
    public static final String URL_PACKING_LIST = BASE_SERVER_SHOP + "/proTakeAway/takeAwayOrderList";

    /**
     * 已点菜品列表接口
     */
    public static final String URL_DISH_LIST = BASE_SERVER_SHOP + "/prodish/getbilldishinfo";

    /**
     * 点菜-获取所有菜品/菜类
     */
    public static final String URL_GET_ALL_DISHES = BASE_SERVER_SHOP + "/prodish/getalldishs";

    /**
     * 外卖-获取所有外卖公司
     */
    public static final String URL_WM_COMPANY = BASE_SERVER_SHOP + "/takeOut/getAllTakeOutCompany";
    /**
     * 外卖-获取所有外卖市别
     */
    public static final String URL_WM_SCHEDULE = BASE_SERVER_SHOP + "/takeOut/getAllSchedule";
    /**
     * 外卖-获取所有退款原因
     */
    public static final String URL_WM_REFUND_REASON = BASE_SERVER_SHOP + "/takeOut/getAllRefundReason";
    /**
     * 外带订单详情
     */
    public static final String URL_WD_ORDER_DETAIL = BASE_SERVER_SHOP + "/proTakeAway/takeAwayOrderDetail";
    /**
     * 外卖订单详情
     */
    public static final String URL_WM_ORDER_DETAIL = BASE_SERVER_SHOP + "/takeOut/getBillDetail";

    /**
     * 点菜时，新加菜品
     */
    public static final String URL_ADD_DISH = BASE_SERVER_SHOP + "/prodish/adddish";
    /**
     * 查询服务员
     */
    public static final String URL_GET_ALL_WAITERS = BASE_SERVER_SHOP + "/dineIn/getAllWaiter";
    /**
     * 堂食-查询桌台列表
     */
    public static final String URL_TS_TABLE_LIST = BASE_SERVER_SHOP + "/protabledynamic/getTableDynamicList";
    /**
     * 修改菜品数量接口
     */
    public static final String URL_UPDATE_DISH_QUANTITY = BASE_SERVER_SHOP + "/prodish/plusorsubtract";
    /**
     * 删除菜品接口
     */
    public static final String URL_DELETE_DISH = BASE_SERVER_SHOP + "/prodish/deletedish";
//    public static final String URL_DELETE_DISH = "http://10.220.23.81:9001/prodish/deletedish";
    /**
     * 修改菜品状态接口
     */
    public static final String URL_UPDATE_DISH_STATUS = BASE_SERVER_SHOP + "/prodish/changestate";
}
