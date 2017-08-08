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

    public static final String DISH_WEIGHT = "1";// 称重确认
    public static final String DISH_TURN = "2";// 转台

    /**退菜：retreat， 赠菜：gift*/
    public static final String SERVE_DISH = "gift";
    public static final String RETURN_DISH = "retreat";

    /**等叫 waitCall 即起 callNow 催菜 reminder */
    public static final String STATUS_REMINDER = "reminder";
    public static final String STATUS_WAIT_CALLED = "waitCall";
    public static final String STATUS_SERVING = "callNow";
}
