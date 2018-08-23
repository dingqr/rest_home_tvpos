package com.smart.tvpos.util;

/**
 * Created by qyd on 2016/12/22.
 * 服务器地址配置文件
 */

public class Constants {

    //用户标识
    public static String USER_SIGN = "";
    public static String USER_ID = "";
    public static String TYPE = "";
    public static String BRANCH_NAME = "";


    /**
     * 页面传值时的参数名
     */
    public static final String DataTitle = "DataTitle";
    /**
     * startActivityForResult 时页面间传值参数名
     */
    public static final String RESULT = "result";
    /**
     * 页面传值时的参数名(List)
     */
    public static final String DataList = "DataList";
    /**
     * 页面传值时的参数名
     */
    public static final String DataBean = "DataBean";
    /**
     * 页面传值时的参数名
     */
    public static final String DataInt = "DataInt";

    /**
     * 页面传值时的参数名
     */
    public static final String DataFloat = "DataFloat";
    /**
     * 页面传值时的参数名(Boolean)
     */
    public static final String DataBooleanBean = "DataBooleanBean";
    /**
     * 页面传值时的参数名-订单状态
     */
    public static final String OrderStatus = "order_status";

    /**
     * 页面传值时的参数名-订单ID
     */
    public static final String OrderId = "order_id";
    /**
     * 城市名
     */
    public static final String CITY_NAME = "city.name";

    /**
     * 调用外置SD卡的权限值
     */
    public static final int WRITE_EXTERNAL_STORAGE = 10002;
    public static final int READ_EXTERNAL_STORAGE = 10003;

    /**
     * 版本更新提醒标示
     */
    public static final String ALERT_USER_UPDATE = "alert_user_update";
    /**
     * 微信支付签名
     */
    public static final String WEIXIN_SIGN = "Sign=WXPay";
    /**
     * 微信支付IP
     */
    public static final String WEIXIN_PAY_IP = "127.0.0.1";

    public static final String APP_ID = "wx1c1186c0a19c60db";
    public static final String WX_APPSecret = "3baf1193c85774b3fd9d18447d76cab0";

    //列表每页请求数据
    public static final String DEFAULT_PAGE_SIZE = "20";
    //列表每页请求数据
    public static final int DEFAULT_PAGE_INT_SIZE = 20;


    public final static String ELDERLY_NAME = "elderly_name";

    public final static String ELDERLY_AGE = "elderly_age";

    public final static String ELDERLY_SEX = "elderly_sex";

    public final static String ELDERLY_NURSING_ADDRESS = "elderly_nursing_name";

    public final static String ELDERLY_AVATAR = "elderly_avatar";

    public final static String ELDERLY_NURSING_LEVEL = "elderly_nursing_level";

    public final static String ELDERLY_IN_OUT_ID = "elderly_in_out_id";

//    public final static String ORIGIN_SELECTED_PIC_PATH = "origin_selected_pic_path";

    public final static String ORIGIN_SELECTED_PIC_POSITION = "origin_selected_pic_position";

    public final static String ORIGIN_PIC_PATH_LIST = "origin_pic_path_list";

    public final static String ELDERLY_SEX_FEMAIL = "女";

    public final static int[] BloodPressureAxisText = new int[]{0, 50, 100, 150, 200, 250};
    public final static int[] BloodSugarAxisText = new int[]{0, 5, 10, 15, 20, 25};
}
