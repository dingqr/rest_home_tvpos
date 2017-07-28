package com.yonyou.hhtpos.global;

/**
 * 点菜常量
 * 作者：liushuofei on 2017/7/25 09:39
 */
public class DishConstants {
    /**堂食：1，外带：2，外卖：3 */
    public static final int TYPE_TS = 1;
    public static final int TYPE_WD = 2;
    public static final int TYPE_WM = 3;

    /**催菜：6，等叫：7，叫起：8 */
    public static final String STATUS_REMINDER = "6";
    public static final String STATUS_WAIT_CALLED = "7";
    public static final String STATUS_SERVING = "8";

    /**赠菜：3， 退菜：4 */
    public static final String DISH_WEIGHT = "1";// 称重确认
    public static final String DISH_TURN = "2";// 转台
    public static final String SERVE_DISH = "3";
    public static final String RETURN_DISH = "4";
}
